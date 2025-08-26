package com.example.demo.dto;

import com.example.demo.entity.Session;
//import com.example.demo.entity.Capture;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SessionDTO {

    private UUID sessionId;
    private UUID userId;
    private String userName;
    private String userEmail;
    private String deviceId;
    private String createdAt;
    private List<CaptureDTO> captures;

    public SessionDTO(Session session) {
        this.sessionId = session.getSessionId();
        this.userId = session.getUser().getUserId();
        this.userName = session.getUser().getName();
        this.userEmail = session.getUser().getEmail();
        this.deviceId = session.getDeviceId();
        this.createdAt = session.getCreatedAt().toString();

        // Map captures to DTOs
        if (session.getCaptures() != null) {
            this.captures = session.getCaptures()
                                   .stream()
                                   .map(CaptureDTO::new)
                                   .collect(Collectors.toList());
        }
    }

    // Getters
    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<CaptureDTO> getCaptures() {
        return captures;
    }
}
