package com.example.demo.controller;

import com.example.demo.dto.ReportDTO;
import com.example.demo.dto.ErrorResponseDTO;
import com.example.demo.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Endpoints for generating session reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Get report for a session",
               description = "Fetches a detailed report for a specific session, including capture statistics and metadata.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sessionId format"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getSessionReport(
            @Parameter(description = "UUID of the session") @PathVariable String sessionId) {

        UUID uuid;

        // Validate and parse UUID
        try {
            uuid = UUID.fromString(sessionId.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponseDTO("Invalid sessionId format"));
        }

        // Fetch report
        ReportDTO report = reportService.getSessionReport(uuid);
        if (report == null) {
            return ResponseEntity
                    .status(404)
                    .body(new ErrorResponseDTO("Session not found: " + sessionId));
        }

        return ResponseEntity.ok(report);
    }
}
