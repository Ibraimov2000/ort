package com.exam.ort.controller;

import com.exam.ort.model.LoginRequestRecord;
import com.exam.ort.model.LoginResponseRecord;
import com.exam.ort.model.UserRecord;
import com.exam.ort.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Bad request - validation errors")
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Parameter(description = "User details for registration") @RequestBody UserRecord userRecord) {
        return ResponseEntity.ok(Collections.singletonMap("token", authService.register(userRecord)));
    }

    @Operation(summary = "Login user", description = "Logs in a user and returns a JWT token.")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseRecord.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseRecord> login(
            @Parameter(description = "User credentials for login") @RequestBody LoginRequestRecord loginRequest) {
        String token = authService.login(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(new LoginResponseRecord(token));
    }
}
