package com.example.demo.service;

import com.example.demo.dto.ReportDTO;
import com.example.demo.entity.Session;
import com.example.demo.entity.Capture;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.CaptureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CaptureRepository captureRepository;

    public ReportDTO getSessionReport(UUID sessionId) {
        // Fetch session first to ensure it exists
        Session session = sessionRepository.findById(sessionId).orElse(null);
        if (session == null) return null;

        // Fetch all captures for the session
        List<Capture> captures = captureRepository.findAllBySessionSessionId(sessionId);
        if (captures.isEmpty()) return new ReportDTO(0, 0, 0, 0, 0, 0);

        int totalCaptures = captures.size();
        double sumBlur = 0;
        int brightCaptures = 0;
        int darkCaptures = 0;
        int perfectCaptures = 0;
        int livenessPassCount = 0;

        for (Capture c : captures) {
            sumBlur += c.getBlurScore();
            if ("BRIGHT".equalsIgnoreCase(c.getBrightnessLevel())) brightCaptures++;
            if ("DARK".equalsIgnoreCase(c.getBrightnessLevel())) darkCaptures++;
            if (c.getBlurScore() < 0.1 && c.isFocusLocked() && c.isLivenessDetected()) perfectCaptures++;
            if (c.isLivenessDetected()) livenessPassCount++;
        }

        double averageBlur = sumBlur / totalCaptures;
        double livenessPassRate = (livenessPassCount * 100.0) / totalCaptures;

        return new ReportDTO(totalCaptures, averageBlur, brightCaptures, darkCaptures, perfectCaptures, livenessPassRate);
    }
}
