package com.exam.ort.config;

import com.exam.ort.model.UserDetailsImpl;
import com.exam.ort.service.UserService;
import com.exam.ort.service.impl.JwtService;
import com.exam.ort.service.impl.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/refresh-token",
            "/api/v1/auth/enable-user",
            "/api/v1/auth/authenticate",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/reset-password",
            "/api/v1/auth/login"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isPublicEndpoint(request)) {
            log.info("Skipping the filter for the following request URL {}", request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            handleMissingToken(response, request);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = extractUsernameFromJwt(jwt);

        if (username == null) {
            handleInvalidToken(response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetailsImpl userDetails = userService.loadUserByUsername(username);

        if (!isTokenValid(jwt, userDetails)) {
            handleInvalidToken(response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(request.getServletPath()::startsWith);
    }

    private boolean isTokenValid(String jwt, UserDetailsImpl userDetails) {
        return tokenService.isTokenValid(jwt) && jwtService.isTokenValid(jwt, userDetails);
    }

    private void handleMissingToken(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.error("Authorization header is missing or does not contain a valid JWT for the following URL: {}", request.getServletPath());
        response.sendError(SC_UNAUTHORIZED, "Authorization header is missing or does not contain a valid JWT");
    }

    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        log.warn("JWT is not valid");
        response.setStatus(SC_UNAUTHORIZED);
        response.getWriter().write("JWT is not valid");
    }

    private String extractUsernameFromJwt(String jwt) {
        try {
            return jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException ex) {
            log.warn("JWT has expired: {}", ex.getMessage());
            return null;
        } catch (MalformedJwtException ex) {
            log.warn("JWT is malformed: {}", ex.getMessage());
            return null;
        } catch (SignatureException ex) {
            log.warn("JWT signature is invalid: {}", ex.getMessage());
            return null;
        } catch (UnsupportedJwtException ex) {
            log.warn("JWT is unsupported: {}", ex.getMessage());
            return null;
        }
    }
}

