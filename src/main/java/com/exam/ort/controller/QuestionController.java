package com.exam.ort.controller;

import com.exam.ort.model.QuestionRecord;
import com.exam.ort.service.QuestionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionController {

    final QuestionService questionService;

    @GetMapping
    public List<QuestionRecord> getQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public QuestionRecord getQuestion(@PathVariable int id) {
        return questionService.findById(id);
    }

    @PostMapping("/create")
    public QuestionRecord createQuestion(@RequestBody QuestionRecord question) {
        return questionService.save(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable int id) {
        questionService.deleteById(id);
    }
}
