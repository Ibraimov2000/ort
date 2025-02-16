package com.exam.ort.repository;

import com.exam.ort.entity.Exam;
import com.exam.ort.enums.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByExamType(ExamType examType);
}
