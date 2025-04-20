package com.exam.ort.entity;

import com.exam.ort.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    int number;

    @Column(nullable = false)
    String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Answer> answers;

    @Column(nullable = false)
    double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    Section section;

    @Enumerated(EnumType.STRING)
    QuestionType questionType;

    @Lob
    @Column(name = "image_data")
    byte[] imageData;

    @Column
    LocalDateTime createdAt;

    @Column
    LocalDateTime updatedAt;
}
