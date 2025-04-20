package com.exam.ort.entity;

import com.exam.ort.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true, nullable = false)
    String email;

    @JsonIgnore
    String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column
    boolean enabled;

    boolean accountNonLocked;

    int failedAttempts;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Token> tokens;


    public static User of(String firstName, String lastName, String email, String password, String confirmPassword, Role role) {
        return new User(null, firstName, lastName, email, password, confirmPassword, role, false, true, 0, null);
    }
}
