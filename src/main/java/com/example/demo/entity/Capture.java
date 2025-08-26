package com.example.demo.entity;

import com.example.demo.enumeration.FingerPosition;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "captures")
public class Capture {

    @Id
    @GeneratedValue
    @Column(name = "capture_id", columnDefinition = "BINARY(16)")
    private UUID captureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Enumerated(EnumType.STRING)
    @Column(name = "finger_position", nullable = false)
    private FingerPosition fingerPosition;

    @Column(name = "blur_score", nullable = false)
    private float blurScore;

    @Column(name = "brightness_level", nullable = false)
    private String brightnessLevel;

    @Column(name = "focus_locked", nullable = false)
    private boolean focusLocked;

    @Column(name = "liveness_detected", nullable = false)
    private boolean livenessDetected;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    // Getters and setters

    public UUID getCaptureId() {
        return captureId;
    }

    public void setCaptureId(UUID captureId) {
        this.captureId = captureId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
