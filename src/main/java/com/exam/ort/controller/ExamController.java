package com.exam.ort.controller;

import com.exam.ort.model.ExamRecord;
import com.exam.ort.model.request.ExamCreateRequest;
import com.exam.ort.service.ExamService;
import com.exam.ort.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamController {

    final ExamService examService;

    @Operation(summary = "Get all exams", description = "Fetch all exams, with optional filters for type and language")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exams",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamRecord.class)))
    @GetMapping
    public List<ExamRecord> getExams(
            @Parameter(description = "Optional filter for exam type") @RequestParam(required = false) String type,
            @Parameter(description = "Optional filter for exam language") @RequestParam(required = false) String language) {
        return examService.findAll(type, language);
    }

    @Operation(summary = "Get exam by ID", description = "Fetch a specific exam by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exam",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamRecord.class)))
    @ApiResponse(responseCode = "404", description = "Exam not found")
    @GetMapping("/{id}")
    public ResponseEntity<ExamRecord> getExam(@PathVariable long id) {
        try {
            ExamRecord examRecord = examService.findById(id);
            return ResponseEntity.ok(examRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new exam", description = "Create a new exam record in the system")
    @ApiResponse(responseCode = "201", description = "Successfully created exam",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<ExamRecord> createExam(@RequestBody @Valid ExamCreateRequest request) {
        ExamRecord exam = new ExamRecord(
                null,
                request.name(),
                request.examType(),
                request.description(),
                request.language(),
                Duration.ofMinutes(request.durationInMinutes()),
                List.of()
        );

        ExamRecord createdExam = examService.save(exam);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
    }


    @Operation(summary = "Delete an exam", description = "Delete a specific exam by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted exam")
    @ApiResponse(responseCode = "404", description = "Exam not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable long id) {
        try {
            examService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{examId}/endTime")
    public ResponseEntity<Map<String, String>> getEndTime(@PathVariable Long examId) {
        LocalDateTime endTime = examService.getEndTime(examId);
        Map<String, String> response = new HashMap<>();
        response.put("endTime", endTime.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ExamRecord> updateExam(@RequestBody ExamRecord exam) {
        return ResponseEntity.ok(examService.update(exam));
    }
}
