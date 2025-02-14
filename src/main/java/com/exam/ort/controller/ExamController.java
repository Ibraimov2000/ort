package com.exam.ort.controller;

import com.exam.ort.model.ExamRecord;
import com.exam.ort.service.ExamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamController {

    final ExamService examService;

    @GetMapping
    public List<ExamRecord> getExams() {
        return examService.findAll();
    }

    @GetMapping("/{id}")
    public ExamRecord getExam(@PathVariable int id) {
        return examService.findById(id);
    }

    @PostMapping("/create")
    public ExamRecord createExam(@RequestBody ExamRecord examRecord) {
        return examService.save(examRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable long id) {
        examService.deleteById(id);
    }
}
