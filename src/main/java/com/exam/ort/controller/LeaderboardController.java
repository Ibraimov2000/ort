package com.exam.ort.controller;

import com.exam.ort.entity.Leaderboard;
import com.exam.ort.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    final LeaderboardService leaderboardService;

    @Operation(summary = "Update leaderboard", description = "Update the leaderboard with new score for a user")
    @ApiResponse(responseCode = "200", description = "Successfully updated leaderboard")
    @ApiResponse(responseCode = "400", description = "Bad request - validation errors")
    @PostMapping("/update")
    public void updateLeaderboard(
            @Parameter(description = "ID of the user to update the score") @RequestParam Long userId,
            @Parameter(description = "New score to be assigned to the user") @RequestParam int score) {
        leaderboardService.updateLeaderboard(userId, score);
    }

    @Operation(summary = "Get top users", description = "Fetch the top users based on their leaderboard score")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Leaderboard.class)))
    @GetMapping("/top")
    public List<Leaderboard> getTopUsers() {
        return leaderboardService.getTopUsers();
    }
}
