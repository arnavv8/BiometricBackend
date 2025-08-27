package com.example.demo.controller;

import com.example.demo.dto.CaptureDTO;
import com.example.demo.entity.Capture;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.CaptureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/captures")
@Tag(name = "Captures", description = "Endpoints for managing fingerprint captures")
public class CaptureController {

    private static final String SESSION_ID = "sessionId";

    @Autowired
    private CaptureService captureService;

    @Autowired
    private AuditLogService auditLogService;

    private UUID parseUuid(String rawId) throws IllegalArgumentException {
        String formatted = rawId.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
        );
        return UUID.fromString(formatted);
    }

    @Operation(summary = "Upload a capture image", description = "Uploads a fingerprint capture and calculates blur/brightness")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capture successfully uploaded"),
            @ApiResponse(responseCode = "400", description = "Invalid sessionId format"),
            @ApiResponse(responseCode = "500", description = "Error processing capture")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCapture(
            @Parameter(description = "UUID of the session") @RequestParam(SESSION_ID) String sessionId,
            @Parameter(description = "Image file for the fingerprint capture") @RequestPart("image") MultipartFile imageFile) {

        UUID sessionUuid;
        try {
            sessionUuid = parseUuid(sessionId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid sessionId format: " + sessionId);
        }

        try {
            Capture capture = captureService.processAndSaveCapture(imageFile, sessionUuid);
            auditLogService.createAuditLog(
                    "CAPTURE_ADDED",
                    "Capture added: captureId=" + capture.getCaptureId() +
                            ", sessionId=" + capture.getSession().getSessionId() +
                            ", userId=" + capture.getSession().getUser().getUserId() +
                            ", deviceId=" + capture.getSession().getDeviceId()
            );
            return ResponseEntity.ok(new CaptureDTO(capture));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing capture: " + ex.getMessage());
        }
    }

    @Operation(summary = "Fetch a capture by ID", description = "Retrieve a single capture using its unique captureId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capture retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid captureId format"),
            @ApiResponse(responseCode = "404", description = "Capture not found")
    })
    @GetMapping("/{captureId}")
    public ResponseEntity<?> getCaptureById(
            @Parameter(description = "UUID of the capture") @PathVariable String captureId) {

        UUID uuid;
        try {
            uuid = parseUuid(captureId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid captureId format: " + captureId);
        }

        Capture capture = captureService.getCaptureById(uuid);
        if (capture == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Capture not found with ID: " + captureId);
        }

        return ResponseEntity.ok(new CaptureDTO(capture));
    }

    @Operation(summary = "Fetch captures by userId", description = "Retrieve all captures for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Captures retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid userId format"),
            @ApiResponse(responseCode = "404", description = "No captures found for the user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCapturesByUserId(
            @Parameter(description = "UUID of the user") @PathVariable String userId) {

        UUID uuid;
        try {
            uuid = parseUuid(userId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid userId format: " + userId);
        }

        List<CaptureDTO> captures = captureService.getCapturesByUserId(uuid);

        if (captures == null || captures.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No captures found for userId: " + userId);
        }

        return ResponseEntity.ok(captures);
    }

    @Operation(summary = "Fetch captures by sessionId", description = "Retrieve all captures for a specific session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Captures retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sessionId format"),
            @ApiResponse(responseCode = "404", description = "No captures found for the session")
    })
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getCapturesBySessionId(
            @Parameter(description = "UUID of the session") @PathVariable String sessionId) {

        UUID uuid;
        try {
            uuid = parseUuid(sessionId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid sessionId format: " + sessionId);
        }

        List<CaptureDTO> captures = captureService.getCapturesBySessionId(uuid);

        if (captures == null || captures.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No captures found for sessionId: " + sessionId);
        }

        return ResponseEntity.ok(captures);
    }

    @Operation(summary = "Fetch captures by deviceId", description = "Retrieve all captures for a specific device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Captures retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No captures found for the device")
    })
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<?> getCapturesByDeviceId(
            @Parameter(description = "Device ID of the capturing device") @PathVariable String deviceId) {

        List<CaptureDTO> captures = captureService.getCapturesByDeviceId(deviceId);

        if (captures == null || captures.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No captures found for deviceId: " + deviceId);
        }

        return ResponseEntity.ok(captures);
    }
}
