package com.exam.ort.repository;

import com.exam.ort.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.exam.id = :examId")
    List<Section> findAllByExamId(@Param("examId") long examId);

    List<Section> findAllByExamIsNull();
}

