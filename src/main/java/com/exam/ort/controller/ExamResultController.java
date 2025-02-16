package com.exam.ort.controller;

import com.exam.ort.model.ExamResultRecord;
import com.exam.ort.service.ExamResultService;
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
@RequestMapping("/api/v1/exam-results")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResultController {

    final ExamResultService examResultService;

    @Operation(summary = "Get all exam results", description = "Fetch all exam results, with optional filters for exam ID and user ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exam results",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamResultRecord.class)))
    @GetMapping
    public List<ExamResultRecord> getExamResults(
            @Parameter(description = "Optional filter for exam ID") @RequestParam(required = false) Long examId,
            @Parameter(description = "Optional filter for user ID") @RequestParam(required = false) Long userId) {
        return examResultService.findAllByExamIdAndUserId(examId, userId);
    }

    @Operation(summary = "Get exam result by ID", description = "Fetch a specific exam result by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exam result",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamResultRecord.class)))
    @ApiResponse(responseCode = "404", description = "Exam result not found")
    @GetMapping("/{id}")
    public ResponseEntity<ExamResultRecord> getExamResult(@PathVariable long id) {
        try {
            ExamResultRecord examResultRecord = examResultService.findById(id);
            return ResponseEntity.ok(examResultRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new exam result", description = "Create a new exam result for a user")
    @ApiResponse(responseCode = "201", description = "Successfully created exam result",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamResultRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<ExamResultRecord> createExamResult(@RequestBody ExamResultRecord examResultRecord) {
        ExamResultRecord createdExamResult = examResultService.save(examResultRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExamResult);
    }

    @Operation(summary = "Delete an exam result", description = "Delete a specific exam result by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted exam result")
    @ApiResponse(responseCode = "404", description = "Exam result not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamResult(@PathVariable long id) {
        try {
            examResultService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
