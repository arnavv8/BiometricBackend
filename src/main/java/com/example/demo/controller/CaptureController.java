package com.example.demo.controller;

import com.example.demo.dto.CaptureDTO;
import com.example.demo.entity.Capture;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.CaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/captures")
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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCapture(
            @RequestParam(SESSION_ID) String sessionId,
            @RequestPart("image") MultipartFile imageFile) {

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
                            ", userId=" + capture.getSession().getUser().getUserId()
            );
            return ResponseEntity.ok(new CaptureDTO(capture));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing capture: " + ex.getMessage());
        }
    }

    @GetMapping("/{captureId}")
    public ResponseEntity<?> getCaptureById(@PathVariable String captureId) {
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCapturesByUserId(@PathVariable String userId) {
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

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getCapturesBySessionId(@PathVariable String sessionId) {
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

}
