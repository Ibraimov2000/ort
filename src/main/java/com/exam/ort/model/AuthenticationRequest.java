package com.exam.ort.model;

public record AuthenticationRequest(
        String email,
        String password
) {
}
