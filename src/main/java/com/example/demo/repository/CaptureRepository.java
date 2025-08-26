package com.example.demo.repository;

import com.example.demo.entity.Capture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CaptureRepository extends JpaRepository<Capture, UUID> {
    List<Capture> findAllBySessionUserUserId(UUID userId);
    List<Capture> findAllBySessionSessionId(UUID sessionId);
}
