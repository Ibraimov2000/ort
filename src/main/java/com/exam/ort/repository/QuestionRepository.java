package com.exam.ort.repository;

import com.exam.ort.entity.Question;
import com.exam.ort.model.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q where q.section.id = :sectionId")
    List<Question> findAllBySectionId(@Param("sectionId") Long sectionId);
}

