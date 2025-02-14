package com.exam.ort.model;


import java.util.List;

public record SectionRecord (Long id, String name, int questionsCount, ExamRecord mainExam, List<QuestionRecord> questions){
}
