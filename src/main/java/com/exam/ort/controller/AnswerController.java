package com.exam.ort.controller;

import com.exam.ort.model.AnswerRecord;
import com.exam.ort.service.AnswerService;
import com.exam.ort.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerController {

    final AnswerService answerService;

    @Operation(summary = "Get all answers", description = "Fetch all answers")
    @GetMapping
    public List<AnswerRecord> getAnswers() {
        return answerService.findAll();
    }

    @Operation(summary = "Get answers by question ID", description = "Fetch answers by optional question ID")
    @GetMapping("/byQuestion")
    public List<AnswerRecord> getAnswers(@RequestParam(required = false) Long questionId) {
        return answerService.findAllByQuestionId(questionId);
    }

    @Operation(summary = "Get answer by ID", description = "Fetch answer by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved answer",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerRecord.class)))
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerRecord> getAnswer(@PathVariable long id) {
        try {
            AnswerRecord answerRecord = answerService.findById(id);
            return ResponseEntity.ok(answerRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get answers by question ID", description = "Fetch answers for a specific question ID")
    @GetMapping("/byQuestionId/{questionId}")
    public List<AnswerRecord> getAnswersByQuestionId(
            @Parameter(description = "ID of the question") @PathVariable long questionId) {
        return answerService.findAnswersByQuestionId(questionId);
    }

    @Operation(summary = "Create a new answer", description = "Create a new answer for a question")
    @ApiResponse(responseCode = "201", description = "Successfully created answer",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<AnswerRecord> createAnswer(@RequestBody AnswerRecord answer) {
        AnswerRecord createdAnswer = answerService.save(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @Operation(summary = "Delete an answer", description = "Delete an answer by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted answer")
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable long id) {
        try {
            answerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
