package com.exam.ort.controller;


import com.exam.ort.model.ExamResultRecord;
import com.exam.ort.service.ExamResultService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/examResults")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResultController {

    final ExamResultService examResultService;

    @GetMapping
    public List<ExamResultRecord> getExamResults() {
        return examResultService.findAll();
    }

    @GetMapping("/{id}")
    public ExamResultRecord getExamResult(@PathVariable long id) {
        return examResultService.findById(id);
    }

    @PostMapping("/create")
    public ExamResultRecord createExamResult(@RequestBody ExamResultRecord examResultRecord) {
        return examResultService.save(examResultRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteExamResult(@PathVariable long id) {
        examResultService.deleteById(id);
    }
}
