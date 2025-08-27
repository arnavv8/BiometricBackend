package com.example.demo.dto;

import com.example.demo.enumeration.FingerPosition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating a capture record")
public class CaptureCreateDTO {

    @Schema(description = "UUID of the session", example = "2012783fc95c4805bb610573a2df56d2")
    private String sessionId;

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

    public CaptureCreateDTO() {}

    // Getters and Setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

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
}
