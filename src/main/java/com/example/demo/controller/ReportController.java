package com.example.demo.controller;

import com.example.demo.dto.ReportDTO;
import com.example.demo.dto.ErrorResponseDTO;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getSessionReport(@PathVariable String sessionId) {
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
