package com.example.demo.dto;

public class ReportDTO {

    private int totalCaptures;
    private double averageBlurScore;
    private int brightCaptures;
    private int darkCaptures;
    private int perfectCaptures;
    private double livenessPassRate;

    public ReportDTO() {
    }

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

    public int getTotalCaptures() {
        return totalCaptures;
    }

    public void setTotalCaptures(int totalCaptures) {
        this.totalCaptures = totalCaptures;
    }

    public double getAverageBlurScore() {
        return averageBlurScore;
    }

    public void setAverageBlurScore(double averageBlurScore) {
        this.averageBlurScore = averageBlurScore;
    }

    public int getBrightCaptures() {
        return brightCaptures;
    }

    public void setBrightCaptures(int brightCaptures) {
        this.brightCaptures = brightCaptures;
    }

    public int getDarkCaptures() {
        return darkCaptures;
    }

    public void setDarkCaptures(int darkCaptures) {
        this.darkCaptures = darkCaptures;
    }

    public int getPerfectCaptures() {
        return perfectCaptures;
    }

    public void setPerfectCaptures(int perfectCaptures) {
        this.perfectCaptures = perfectCaptures;
    }

    public double getLivenessPassRate() {
        return livenessPassRate;
    }

    public void setLivenessPassRate(double livenessPassRate) {
        this.livenessPassRate = livenessPassRate;
    }
}
