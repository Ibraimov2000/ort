package com.exam.ort.repository.specification;

import com.exam.ort.entity.Exam;
import com.exam.ort.enums.ExamType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ExamSpecification {

    public static Specification<Exam> hasType(String type) {
        return (Root<Exam> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (type == null || type.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("examType"), ExamType.valueOf(type));
        };
    }

    public static Specification<Exam> hasLanguage(String language) {
        return (Root<Exam> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (language == null || language.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("language"), language);
        };
    }

}
