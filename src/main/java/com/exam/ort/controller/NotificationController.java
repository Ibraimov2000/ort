package com.exam.ort.controller;

import com.exam.ort.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    final NotificationService notificationService;

    @Operation(summary = "Send reminder to a user", description = "Sends a test reminder notification to a user")
    @ApiResponse(responseCode = "200", description = "Successfully sent reminder")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PostMapping("/send-reminder")
    public void sendReminder(
            @Parameter(description = "ID of the user to send a reminder to") @RequestParam Long userId) {
        notificationService.sendTestReminder(userId);
    }
}
