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
@Table(name = "users_answers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    Answer answer;

    @Column(nullable = false)
    boolean isCorrect;

    @Column
    LocalDateTime createdAt;
}
