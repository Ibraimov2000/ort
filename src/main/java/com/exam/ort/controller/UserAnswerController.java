package com.exam.ort.controller;

import com.exam.ort.model.UserAnswerRecord;
import com.exam.ort.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-answers")
@RequiredArgsConstructor
public class UserAnswerController {

    private final UserAnswerService userAnswerService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswers(@RequestBody List<UserAnswerRecord> answers) {
        // Логика для обработки отправленных ответов
        // Сохранение в базу данных и вычисление результатов
        userAnswerService.submitAnswers(answers);
        return ResponseEntity.ok("Test submitted successfully");
    }
}

