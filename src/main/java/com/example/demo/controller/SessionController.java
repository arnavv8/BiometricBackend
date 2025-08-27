package com.example.demo.controller;

import com.example.demo.dto.SessionCreateRequestDTO;
import com.example.demo.dto.SessionDTO;
import com.example.demo.entity.Session;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@Tag(name = "Sessions", description = "Endpoints for manging fingerprint capture sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AuditLogService auditLogService;

    @Operation(summary = "Create a new session", description = "Creates a contactless capture session for a user and device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping
    public ResponseEntity<SessionDTO> createSession(
            @Parameter(description = "Session creation request payload") @RequestBody SessionCreateRequestDTO requestDTO) {

        Session session = sessionService.createSession(requestDTO);

        auditLogService.createAuditLog(
            "CREATE_SESSION",
            "Session created: sessionId=" + session.getSessionId() +
            ", userId=" + session.getUser() +
            ", deviceId=" + session.getDeviceId()
        );

        return ResponseEntity.ok(new SessionDTO(session));
    }

    @Operation(summary = "Get session by ID", description = "Fetch details of a session by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sessionId format"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSession(
            @Parameter(description = "UUID of the session") @PathVariable String sessionId) {

        UUID uuid;
        try {
            // Insert hyphens if missing and convert to UUID
            String formatted = sessionId.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
            );
            uuid = UUID.fromString(formatted);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid sessionId format: " + sessionId);
        }

        Session session = sessionService.getSessionById(uuid);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Session not found with ID: " + sessionId);
        }

        return ResponseEntity.ok(new SessionDTO(session));
    }
}
