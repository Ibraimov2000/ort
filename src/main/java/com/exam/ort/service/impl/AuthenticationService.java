package com.exam.ort.service.impl;


import com.exam.ort.entity.User;
import com.exam.ort.exception.EmailAlreadyExistsException;
import com.exam.ort.exception.PasswordDontMatchException;
import com.exam.ort.model.AuthenticationRequest;
import com.exam.ort.model.AuthenticationResponse;
import com.exam.ort.model.RegisterRequest;
import com.exam.ort.service.EmailService;
import com.exam.ort.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final EmailService emailService;



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userService.validateCredentials(request.email(), request.password());
        log.info("User {} successfully authenticated with role {}", user.getFirstName(), user.getRole());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, accessToken);

        return new AuthenticationResponse(accessToken, refreshToken);
    }


    public void registerUser(RegisterRequest registerRequest) {
        if (!isPasswordAndPasswordConfirmMatches(registerRequest)) {
            log.error("Password and password confirm doesn't match");
            throw new PasswordDontMatchException();
        }

        if (userService.emailExists(registerRequest.email())) {
            log.error("Email already exists");
            throw new EmailAlreadyExistsException();
        }

        var jwtToken = jwtService.generateTokenForEnableAccount(registerRequest.email());

        String activationLink = "http://localhost:8080/api/v1/auth/enable-user/" + jwtToken;

        try {
            log.info("Sending activation link to user {}", registerRequest.email());
            emailService.sendActivationLink(registerRequest.email(), registerRequest.firstName(), activationLink);

            User user = registerRequest.toUser();

            var savedUser = userService.saveUser(user);

            tokenService.saveUserToken(savedUser, jwtToken);
            log.info("User successfully registered with request {}", registerRequest);

        } catch (Exception e) {
            log.error("Cannot create user with request {}", registerRequest);
            log.error("Error: {}", e.getMessage());
        }
    }


    public void sendResetPasswordRequestToUser(String email) {
        var user = userService.findUserByEmail(email);

        var jwtToken = jwtService.generateTokenForResetPassword(user.getEmail());

        String resetPasswordLink = "http://localhost:5173/reset-password?token=" + jwtToken;

        try {
            log.info("Sending reset password link to user with email {}", email);
            emailService.sendResetPasswordRequestToUser(email, user.getFirstName(), resetPasswordLink);
        } catch (Exception e) {
            log.warn("Error while sending reset password link to user with email {}", email);
            log.info("If u didn't receive the email, due to the fact that we are in dev mode, we can pretend that the following link is sent : {}", resetPasswordLink);
            throw new MailSendException("Error while sending reset password link to user with email :" + email);
        }
        log.info("Reset password link sent to user with email {}", email);
    }


    public void upDatePassword(String token, String password, String passwordConfirm) {
        String email = jwtService.extractUsername(token);
        userService.updatePassword(email, password, passwordConfirm);
    }


    public void enableUser(String token) {
        String email = jwtService.extractUsername(token);
        userService.enableUser(email);
    }


    public boolean isPasswordAndPasswordConfirmMatches(RegisterRequest registerRequest) {
        return registerRequest.password().equals(registerRequest.confirmPassword());
    }


    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticationResponse result = null;

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header.");
            log.error("Missing or invalid Authorization header.");
        } else {
            try {
                log.info("Refreshing token for request {}", request.getHeader("Authorization"));
                final String refreshToken = authHeader.substring(7);

                var username = jwtService.extractUsername(refreshToken);
                log.info("User email is {}", username);

                if (username != null) {
                    var userDetails = userService.loadUserByUsername(username);
                    log.info("User is {}", userDetails);

                    if (jwtService.isTokenValid(refreshToken, userDetails)) {
                        var accessToken = jwtService.generateAccessToken(userDetails.user());
                        log.info("Access token is {}", accessToken);
                        tokenService.revokeAllUserTokens(userDetails.user());
                        tokenService.saveUserToken(userDetails.user(), accessToken);

                        result = new AuthenticationResponse(accessToken, refreshToken);
                    }
                }
            } catch (ExpiredJwtException ex) {
                log.warn("refresh token expired: {}", ex.getMessage());
                response.sendError(SC_UNAUTHORIZED, "refresh token expired");
            } catch (MalformedJwtException e) {
                log.warn("refresh token expired: {}", e.getMessage());
                response.sendError(SC_UNAUTHORIZED, "invalid refresh token.");
            }
        }

        return result;
    }
}

