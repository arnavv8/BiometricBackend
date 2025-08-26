package com.example.demo.service;

import com.example.demo.dto.SessionCreateRequestDTO;
import com.example.demo.entity.Session;
import com.example.demo.entity.User;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Session createSession(SessionCreateRequestDTO dto) {
        // Convert compact UUID string to proper UUID
        String standardUUID = toStandardUUID(dto.getUserId());
        UUID userUuid = UUID.fromString(standardUUID);

        User user = userRepository.findById(userUuid)
                  .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Session session = new Session();
        session.setUser(user);  // link entity
        session.setDeviceId(dto.getDeviceId());
        session.setCreatedAt(Instant.now());

        return sessionRepository.save(session);
    }

    public Session getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    // Helper to insert dashes into compact UUID
    private String toStandardUUID(String compactUUID) {
        return compactUUID.replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
            "$1-$2-$3-$4-$5"
        );
    }
}
