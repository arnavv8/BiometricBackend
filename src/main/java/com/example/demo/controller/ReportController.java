package com.example.demo.controller;

import com.example.demo.dto.ReportDTO;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ReportDTO> getSessionReport(@PathVariable String sessionId) {
        ReportDTO report = reportService.getSessionReport(sessionId);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
}
