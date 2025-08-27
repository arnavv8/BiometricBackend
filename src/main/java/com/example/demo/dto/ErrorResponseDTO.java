package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard error response")
public class ErrorResponseDTO {

    @Schema(description = "Error message describing what went wrong", example = "Invalid sessionId format")
    private String message;

    public ErrorResponseDTO() {}

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
