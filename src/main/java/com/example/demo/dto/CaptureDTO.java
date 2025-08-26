package com.example.demo.dto;

import com.example.demo.entity.Capture;
import com.example.demo.enumeration.FingerPosition;

import java.time.Instant;
import java.util.UUID;

public class CaptureDTO {

    private UUID captureId;
    private UUID sessionId;
    private FingerPosition fingerPosition;
    private float blurScore;
    private String brightnessLevel;
    private boolean focusLocked;
    private boolean livenessDetected;
    private String imageUrl;
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
    public UUID getCaptureId() {
        return captureId;
    }

    public void setCaptureId(UUID captureId) {
        this.captureId = captureId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public FingerPosition getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(FingerPosition fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public float getBlurScore() {
        return blurScore;
    }

    public void setBlurScore(float blurScore) {
        this.blurScore = blurScore;
    }

    public String getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(String brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public boolean isFocusLocked() {
        return focusLocked;
    }

    public void setFocusLocked(boolean focusLocked) {
        this.focusLocked = focusLocked;
    }

    public boolean isLivenessDetected() {
        return livenessDetected;
    }

    public void setLivenessDetected(boolean livenessDetected) {
        this.livenessDetected = livenessDetected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(Instant capturedAt) {
        this.capturedAt = capturedAt;
    }
}
