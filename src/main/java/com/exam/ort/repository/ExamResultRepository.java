package com.exam.ort.repository;

import com.exam.ort.entity.ExamResult;
import com.exam.ort.model.ExamResultRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findAllByExamIdAndUserId(Long examId, Long userId);
}

