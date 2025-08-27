package com.example.demo.dto;

import com.example.demo.entity.Capture;
import com.example.demo.enumeration.FingerPosition;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Data Transfer Object representing a fingerprint capture")
public class CaptureDTO {

    @Schema(description = "Unique ID of the capture", example = "2012783fc95c4805bb610573a2df56d2")
    private UUID captureId;

    @Schema(description = "UUID of the session", example = "2012783fc95c4805bb610573a2df56d2")
    private UUID sessionId;

    @Schema(description = "Finger position of the capture", example = "THUMB")
    private FingerPosition fingerPosition;

    @Schema(description = "Blur score of the captured image", example = "120.5")
    private float blurScore;

    @Schema(description = "Brightness level of the captured image", example = "NORMAL")
    private String brightnessLevel;

    @Schema(description = "Indicates whether focus is locked", example = "true")
    private boolean focusLocked;

    @Schema(description = "Indicates whether liveness was detected", example = "true")
    private boolean livenessDetected;

    @Schema(description = "Public URL of the uploaded image", example = "http://localhost:8080/uploads/image.jpg")
    private String imageUrl;

    @Schema(description = "Timestamp when the capture was taken", example = "2025-08-26T12:34:56.789Z")
    private Instant capturedAt;

    public CaptureDTO() {}

    public CaptureDTO(Capture capture) {
        this.captureId = capture.getCaptureId();
        this.sessionId = capture.getSession().getSessionId(); // Only sessionId, avoids recursion
        this.fingerPosition = capture.getFingerPosition();
        this.blurScore = capture.getBlurScore();
        this.brightnessLevel = capture.getBrightnessLevel();
        this.focusLocked = capture.isFocusLocked();
        this.livenessDetected = capture.isLivenessDetected();
        this.imageUrl = capture.getImageUrl();
        this.capturedAt = capture.getCapturedAt();
    }

    // Getters and Setters
    public UUID getCaptureId() { return captureId; }
    public void setCaptureId(UUID captureId) { this.captureId = captureId; }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }

    public FingerPosition getFingerPosition() { return fingerPosition; }
    public void setFingerPosition(FingerPosition fingerPosition) { this.fingerPosition = fingerPosition; }

    public float getBlurScore() { return blurScore; }
    public void setBlurScore(float blurScore) { this.blurScore = blurScore; }

    public String getBrightnessLevel() { return brightnessLevel; }
    public void setBrightnessLevel(String brightnessLevel) { this.brightnessLevel = brightnessLevel; }

    public boolean isFocusLocked() { return focusLocked; }
    public void setFocusLocked(boolean focusLocked) { this.focusLocked = focusLocked; }

    public boolean isLivenessDetected() { return livenessDetected; }
    public void setLivenessDetected(boolean livenessDetected) { this.livenessDetected = livenessDetected; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Instant getCapturedAt() { return capturedAt; }
    public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }
}
