package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Report summary for a session")
public class ReportDTO {

    @Schema(description = "Total number of captures in the session", example = "25")
    private int totalCaptures;

    @Schema(description = "Average blur score of all captures", example = "123.45")
    private double averageBlurScore;

    @Schema(description = "Number of captures classified as bright", example = "10")
    private int brightCaptures;

    @Schema(description = "Number of captures classified as dark", example = "5")
    private int darkCaptures;

    @Schema(description = "Number of captures considered perfect quality", example = "8")
    private int perfectCaptures;

    @Schema(description = "Percentage of captures passing liveness detection", example = "92.5")
    private double livenessPassRate;

    public ReportDTO() {}

    public ReportDTO(int totalCaptures, double averageBlurScore, int brightCaptures,
                     int darkCaptures, int perfectCaptures, double livenessPassRate) {
        this.totalCaptures = totalCaptures;
        this.averageBlurScore = averageBlurScore;
        this.brightCaptures = brightCaptures;
        this.darkCaptures = darkCaptures;
        this.perfectCaptures = perfectCaptures;
        this.livenessPassRate = livenessPassRate;
    }

    // Getters and setters
    public int getTotalCaptures() { return totalCaptures; }
    public void setTotalCaptures(int totalCaptures) { this.totalCaptures = totalCaptures; }

    public double getAverageBlurScore() { return averageBlurScore; }
    public void setAverageBlurScore(double averageBlurScore) { this.averageBlurScore = averageBlurScore; }

    public int getBrightCaptures() { return brightCaptures; }
    public void setBrightCaptures(int brightCaptures) { this.brightCaptures = brightCaptures; }

    public int getDarkCaptures() { return darkCaptures; }
    public void setDarkCaptures(int darkCaptures) { this.darkCaptures = darkCaptures; }

    public int getPerfectCaptures() { return perfectCaptures; }
    public void setPerfectCaptures(int perfectCaptures) { this.perfectCaptures = perfectCaptures; }

    public double getLivenessPassRate() { return livenessPassRate; }
    public void setLivenessPassRate(double livenessPassRate) { this.livenessPassRate = livenessPassRate; }
}
