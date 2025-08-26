package com.example.demo.controller;

import com.example.demo.dto.SessionCreateRequestDTO;
import com.example.demo.dto.SessionDTO;
import com.example.demo.entity.Session;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AuditLogService auditLogService; // Inject instance here

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionCreateRequestDTO requestDTO) {
        // Pass the DTO to service; service handles UUID conversion
        Session session = sessionService.createSession(requestDTO);

        // Call on the injected instance
        auditLogService.createAuditLog(
            "CREATE_SESSION",
            "Session created: sessionId=" + session.getSessionId() +
            ", userId=" + session.getUser() +
            ", deviceId=" + session.getDeviceId()
        );

        return ResponseEntity.ok(new SessionDTO(session));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable String sessionId) {
        String formatted = sessionId.replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
            "$1-$2-$3-$4-$5"
        );
        UUID uuid = UUID.fromString(formatted);

        Session session = sessionService.getSessionById(uuid);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new SessionDTO(session));  // now includes captures
    }


}
