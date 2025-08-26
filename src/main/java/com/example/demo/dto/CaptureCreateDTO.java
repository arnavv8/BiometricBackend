package com.example.demo.dto;

import com.example.demo.enumeration.FingerPosition;

public class CaptureCreateDTO {

    private String sessionId; // Accept UUID as string
    private FingerPosition fingerPosition;
    private float blurScore;
    private String brightnessLevel;
    private boolean focusLocked;
    private boolean livenessDetected;
    private String imageUrl;

    public CaptureCreateDTO() {}

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
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
}
