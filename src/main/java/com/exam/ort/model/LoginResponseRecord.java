package com.exam.ort.model;


import jakarta.validation.constraints.NotNull;

public record LoginResponseRecord(
        @NotNull String token
) {
}
