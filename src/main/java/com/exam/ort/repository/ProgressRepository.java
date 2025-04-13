package com.exam.ort.repository;

import com.exam.ort.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Progress findByUserIdAndExamId(Long userId, Long examId);
    List<Progress> findAllByUserId(Long userId);
    Progress findByUserId(Long userId);
}
