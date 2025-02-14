package com.exam.ort.model;

import com.exam.ort.enums.Role;

public record UserRecord(Long id, String firstName, String lastName, String email, Role role) {
}
