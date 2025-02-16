package com.exam.ort.controller;

import com.exam.ort.entity.Progress;
import com.exam.ort.model.UserRecord;
import com.exam.ort.service.StatisticsService;
import com.exam.ort.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;
    final StatisticsService statisticsService;

    @Operation(summary = "Get all users", description = "Fetch all users from the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRecord.class)))
    @GetMapping
    public List<UserRecord> getUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Get user by ID", description = "Fetch a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRecord.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserRecord> getUser(@PathVariable long id) {
        try {
            UserRecord userRecord = userService.findById(id);
            return ResponseEntity.ok(userRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new user", description = "Create a new user and save it in the database")
    @ApiResponse(responseCode = "201", description = "Successfully created user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRecord.class)))
    @PostMapping("/create")
    public ResponseEntity<UserRecord> createUser(@RequestBody UserRecord user) {
        UserRecord createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Delete user by ID", description = "Delete a specific user by their ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get user progress for a specific exam", description = "Retrieve the progress of a specific user for a given exam")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user progress",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Progress.class)))
    @GetMapping("/{userId}/progress/{examId}")
    public Progress getUserProgress(
            @Parameter(description = "ID of the user whose progress is to be fetched") @PathVariable Long userId,
            @Parameter(description = "ID of the exam for which progress is tracked") @PathVariable Long examId) {
        return statisticsService.getUserProgress(userId, examId);
    }

    @Operation(summary = "Get user score percentage", description = "Retrieve the score percentage of a specific user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user score percentage")
    @GetMapping("/{userId}/score")
    public double getUserScore(@Parameter(description = "ID of the user whose score is to be fetched") @PathVariable Long userId) {
        return statisticsService.getUserScorePercentage(userId);
    }

    @GetMapping("/current")
    public UserRecord getCurrentUser() {
        return userService.getCurrentUser();
    }
}
