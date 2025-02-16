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
@Table(name = "exams_results")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    Exam exam;

    @Column(nullable = false)
    int score;

    @Column(nullable = false)
    LocalDateTime passedDateTime;
}
