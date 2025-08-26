package com.example.demo.dto;

public class SessionCreateRequestDTO {
    private String userId;   // accept compact UUID as string
    private String deviceId;

    public SessionCreateRequestDTO() {}

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
