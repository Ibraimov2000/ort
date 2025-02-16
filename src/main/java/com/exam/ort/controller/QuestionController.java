package com.exam.ort.controller;

import com.exam.ort.model.QuestionRecord;
import com.exam.ort.service.QuestionService;
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
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionController {

    final QuestionService questionService;

    @Operation(summary = "Get all questions", description = "Fetch all questions, with an optional filter by section ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved questions",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionRecord.class)))
    @GetMapping
    public List<QuestionRecord> getQuestions() {
        return questionService.findAll();
    }

    @Operation(summary = "Get questions by section ID", description = "Fetch questions for a specific section using optional section ID filter")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved questions by section ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionRecord.class)))
    @GetMapping("/sectionId")
    public List<QuestionRecord> getQuestions(@Parameter(description = "ID of the section to filter questions") @RequestParam(required = false) Long sectionId) {
        return questionService.findAllBySectionId(sectionId);
    }

    @Operation(summary = "Get question by ID", description = "Fetch a specific question by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved question",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionRecord.class)))
    @ApiResponse(responseCode = "404", description = "Question not found")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionRecord> getQuestion(@PathVariable long id) {
        try {
            QuestionRecord questionRecord = questionService.findById(id);
            return ResponseEntity.ok(questionRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new question", description = "Create a new question and save it in the database")
    @ApiResponse(responseCode = "201", description = "Successfully created question",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<QuestionRecord> createQuestion(@RequestBody QuestionRecord question) {
        QuestionRecord createdQuestion = questionService.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @Operation(summary = "Delete a question", description = "Delete a question by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted question")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable long id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
