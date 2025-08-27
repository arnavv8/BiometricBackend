package com.example.demo.dto;

import com.example.demo.entity.Session;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Schema(description = "Data Transfer Object representing a session and its captures")
public class SessionDTO {

    @Schema(description = "Unique identifier of the session", example = "2012783fc95c4805bb610573a2df56d2")
    private UUID sessionId;

    @Schema(description = "User ID associated with the session", example = "2012783fc95c4805bb610573a2df56d2")
    private UUID userId;

    @Schema(description = "Name of the user", example = "John Doe")
    private String userName;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String userEmail;

    @Schema(description = "Device ID from which the session was created", example = "DEVICE123")
    private String deviceId;

    @Schema(description = "Session creation timestamp", example = "2025-08-26T18:14:23Z")
    private String createdAt;

    @Schema(description = "List of captures associated with this session")
    private List<CaptureDTO> captures;

    public SessionDTO(Session session) {
        this.sessionId = session.getSessionId();
        this.userId = session.getUser().getUserId();
        this.userName = session.getUser().getName();
        this.userEmail = session.getUser().getEmail();
        this.deviceId = session.getDeviceId();
        this.createdAt = session.getCreatedAt().toString();

        if (session.getCaptures() != null) {
            this.captures = session.getCaptures()
                                   .stream()
                                   .map(CaptureDTO::new)
                                   .collect(Collectors.toList());
        }
    }

    // Getters
    public UUID getSessionId() { return sessionId; }
    public UUID getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getDeviceId() { return deviceId; }
    public String getCreatedAt() { return createdAt; }
    public List<CaptureDTO> getCaptures() { return captures; }
}
