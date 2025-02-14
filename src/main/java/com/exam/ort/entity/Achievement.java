package com.exam.ort.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "achievements")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user; // Пользователь, который получил достижение

    @Column(nullable = false)
    String description; // Описание достижения

    @Column(nullable = false)
    LocalDateTime awardedAt; // Время получения достижения

    @PrePersist
    public void prePersist() {
        if (awardedAt == null) {
            awardedAt = LocalDateTime.now(); // Устанавливаем время достижения
        }
    }
}
