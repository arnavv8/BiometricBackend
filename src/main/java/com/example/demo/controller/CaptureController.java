package com.example.demo.controller;

import com.example.demo.dto.CaptureDTO;
import com.example.demo.entity.Capture;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.CaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/captures")
public class CaptureController {

    @Autowired
    private CaptureService captureService;

    @Autowired
    private AuditLogService auditLogService;

    private UUID parseUuid(String rawId) {
    	String formatted = rawId.replaceFirst(
    		    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
    		    "$1-$2-$3-$4-$5"
    		);
        return UUID.fromString(formatted);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CaptureDTO> uploadCapture(
            @RequestParam("sessionId") String sessionId,
            @RequestPart("image") MultipartFile imageFile) throws Exception {

        UUID sessionUuid = parseUuid(sessionId);
        Capture capture = captureService.processAndSaveCapture(imageFile, sessionUuid);

        auditLogService.createAuditLog(
            "CAPTURE_ADDED",
            "Capture added: captureId=" + capture.getCaptureId() +
            ", sessionId=" + capture.getSession().getSessionId() +
            ", userId=" + capture.getSession().getUser().getUserId()
        );

        return ResponseEntity.ok(new CaptureDTO(capture));
    }

    @GetMapping("/{captureId}")
    public ResponseEntity<CaptureDTO> getCaptureById(@PathVariable String captureId) {
        Capture capture = captureService.getCaptureById(parseUuid(captureId));
        if (capture == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new CaptureDTO(capture));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CaptureDTO>> getCapturesByUserId(@PathVariable String userId) {
        List<CaptureDTO> captures = captureService.getCapturesByUserId(parseUuid(userId));
        return ResponseEntity.ok(captures);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<CaptureDTO>> getCapturesBySessionId(@PathVariable String sessionId) {
        List<CaptureDTO> captures = captureService.getCapturesBySessionId(parseUuid(sessionId));
        return ResponseEntity.ok(captures);
    }
}
