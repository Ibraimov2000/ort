package com.exam.ort.entity;


import com.exam.ort.enums.ExamLanguage;
import com.exam.ort.enums.ExamType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ExamType examType;

    @Column
    String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ExamLanguage language;

    @Column(nullable = false)
    Duration duration;

    @OneToMany
    List<Section> sections;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    LocalDateTime createdAt;
}
