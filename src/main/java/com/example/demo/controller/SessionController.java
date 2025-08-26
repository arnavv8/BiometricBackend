package com.example.demo.controller;

import com.example.demo.dto.SessionCreateRequestDTO;
import com.example.demo.dto.SessionDTO;
import com.example.demo.entity.Session;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionCreateRequestDTO requestDTO) {
        Session session = sessionService.createSession(requestDTO);

        auditLogService.createAuditLog(
            "CREATE_SESSION",
            "Session created: sessionId=" + session.getSessionId() +
            ", userId=" + session.getUser() +
            ", deviceId=" + session.getDeviceId()
        );

        return ResponseEntity.ok(new SessionDTO(session));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        UUID uuid;
        try {
            // Insert hyphens if missing and convert to UUID
            String formatted = sessionId.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
            );
            uuid = UUID.fromString(formatted);
        } catch (IllegalArgumentException ex) {
            // Invalid UUID format
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid sessionId format: " + sessionId);
        }

        Session session = sessionService.getSessionById(uuid);
        if (session == null) {
            // Session not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Session not found with ID: " + sessionId);
        }

        return ResponseEntity.ok(new SessionDTO(session));
    }
}
