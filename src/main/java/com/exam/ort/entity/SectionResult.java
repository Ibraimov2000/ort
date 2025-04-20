package com.exam.ort.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Table(name = "sections_results")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_result_id")
    ExamResult examResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    Section section;

    @Column(nullable = false)
    int score;

    @Column(nullable = false)
    boolean isPassed;
}
