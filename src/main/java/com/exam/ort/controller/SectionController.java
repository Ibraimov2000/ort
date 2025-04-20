package com.exam.ort.controller;

import com.exam.ort.model.SectionRecord;
import com.exam.ort.model.request.SectionRequest;
import com.exam.ort.service.SectionService;
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
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionController {

    final SectionService sectionService;

    @Operation(summary = "Get all sections", description = "Fetch all sections")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved sections",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectionRecord.class)))
    @GetMapping
    public List<SectionRecord> getSections() {
        return sectionService.findAll();
    }

    @Operation(summary = "Get sections by exam ID", description = "Fetch all sections filtered by exam ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved sections for exam ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectionRecord.class)))
    @GetMapping("/examId")
    public List<SectionRecord> getSectionsByExamId(
            @Parameter(description = "ID of the exam to filter sections") @RequestParam(required = false) Long examId) {
        return sectionService.findAllByExamId(examId);
    }

    @Operation(summary = "Get section by ID", description = "Fetch a specific section by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved section",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectionRecord.class)))
    @ApiResponse(responseCode = "404", description = "Section not found")
    @GetMapping("/{id}")
    public ResponseEntity<SectionRecord> getSection(@PathVariable long id) {
        try {
            SectionRecord sectionRecord = sectionService.findById(id);
            return ResponseEntity.ok(sectionRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new section", description = "Create a new section for an exam")
    @ApiResponse(responseCode = "201", description = "Successfully created section",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SectionRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<SectionRecord> createSection(@RequestBody SectionRecord section) {
        SectionRecord createdSection = sectionService.save(section);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSection);
    }

    @Operation(summary = "Delete a section", description = "Delete a specific section by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted section")
    @ApiResponse(responseCode = "404", description = "Section not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable long id) {
        try {
            sectionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionRecord> updateSection(@PathVariable Long id, @RequestBody SectionRequest section) {
        return ResponseEntity.ok(sectionService.update(id, section));
    }
}
