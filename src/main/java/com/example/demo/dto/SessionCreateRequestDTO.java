package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to create a new session")
public class SessionCreateRequestDTO {

    @Schema(description = "User ID of the session owner (compact UUID as string)", 
            example = "2012783fc95c4805bb610573a2df56d2", required = true)
    private String userId;

    @Schema(description = "Device ID from which the session is initiated", 
            example = "DEVICE123", required = true)
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
