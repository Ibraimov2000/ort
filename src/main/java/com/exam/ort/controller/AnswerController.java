package com.exam.ort.controller;

import com.exam.ort.model.AnswerRecord;
import com.exam.ort.service.AnswerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerController {

    final AnswerService answerService;

    @GetMapping
    public List<AnswerRecord> getAnswers() {
        return answerService.findAll();
    }

    @GetMapping("/{id}")
    public AnswerRecord getAnswer(@PathVariable int id) {
        return answerService.findById(id);
    }

    @GetMapping("/byQuestionId/{questionId}")
    public List<AnswerRecord> getAnswersByQuestionId(int questionId) {
        return answerService.findAnswersByQuestionId(questionId);
    }

    @PostMapping("/create")
    public AnswerRecord createAnswer(@RequestBody AnswerRecord answer) {
        return answerService.save(answer);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable int id) {
        answerService.deleteById(id);
    }
}
