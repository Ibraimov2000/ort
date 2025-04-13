package com.exam.ort.model;

import com.exam.ort.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record UserRecord(
        Long id,
        @NotNull @Size(min = 1) String firstName,
        @NotNull @Size(min = 1) String lastName,
        @NotNull @Email String email,
        @NotNull Role role,
        @NotNull @Size(min = 6) String password
) {
}
