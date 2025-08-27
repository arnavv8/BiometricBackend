package com.example.demo.service;

import com.example.demo.dto.CaptureDTO;
import com.example.demo.entity.Capture;
import com.example.demo.entity.Session;
import com.example.demo.enumeration.FingerPosition;
import com.example.demo.repository.CaptureRepository;
import com.example.demo.repository.SessionRepository;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CaptureService {

    static {
        nu.pattern.OpenCV.loadLocally(); // load native OpenCV binaries
    }

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Process the uploaded image, calculate blur & brightness,
     * and save Capture entity.
     */
    public Capture processAndSaveCapture(MultipartFile imageFile, UUID sessionUuid) throws Exception {
        // Find session
        Session session = sessionRepository.findById(sessionUuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sessionId"));

        // Save image (get both file path and public URL)
        SavedImage savedImage = saveImage(imageFile);
        File savedFile = new File(savedImage.getFilePath());

        // Load image with OpenCV (from local file path)
        Mat img = Imgcodecs.imread(savedFile.getAbsolutePath());
        if (img.empty()) {
            throw new IOException("Failed to read image with OpenCV: " + savedFile.getAbsolutePath());
        }

        // Calculate blur score
        double blurScore = calculateBlurScore(img);

        // Calculate brightness level
        String brightnessLevel = calculateBrightness(img);

        // Build Capture entity
        Capture capture = new Capture();
        capture.setSession(session);
        capture.setFingerPosition(FingerPosition.THUMB); // placeholder for now
        capture.setBlurScore((float) blurScore);
        capture.setBrightnessLevel(brightnessLevel);
        capture.setFocusLocked(blurScore > 100); // simple heuristic
        capture.setLivenessDetected(true); // stub for now
        capture.setImageUrl(savedImage.getUrl()); // store public URL in DB
        capture.setCapturedAt(Instant.now());

        return captureRepository.save(capture);
    }

    /**
     * Fetch a single capture by id.
     */
    public Capture getCaptureById(UUID captureId) {
        return captureRepository.findById(captureId).orElse(null);
    }

    /**
     * Fetch all captures for a given userId.
     */
    public List<CaptureDTO> getCapturesByUserId(UUID userId) {
        return captureRepository.findAllBySessionUserUserId(userId)
                .stream()
                .map(CaptureDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Fetch all captures for a given sessionId.
     */
    public List<CaptureDTO> getCapturesBySessionId(UUID sessionId) {
        return captureRepository.findAllBySessionSessionId(sessionId)
                .stream()
                .map(CaptureDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<CaptureDTO> getCapturesByDeviceId(String deviceId) {
        return captureRepository.findAllBySessionDeviceId(deviceId)
                .stream()
                .map(CaptureDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Small wrapper for saved image info (local path + public URL).
     */
    public static class SavedImage {
        private final String filePath; // local path for OpenCV
        private final String url;      // public URL for API

        public SavedImage(String filePath, String url) {
            this.filePath = filePath;
            this.url = url;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getUrl() {
            return url;
        }
    }

    /**
     * Save the uploaded image to disk and return both file path and URL.
     */
    private SavedImage saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // Public URL mapping (served via ResourceHandlerConfig)
        String url = "http://localhost:8080/uploads/" + fileName;

        return new SavedImage(filePath.toString(), url);
    }

    /**
     * Calculate blur score using variance of Laplacian.
     */
    private double calculateBlurScore(Mat image) {
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        Mat laplacian = new Mat();
        Imgproc.Laplacian(gray, laplacian, CvType.CV_64F);

        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(laplacian, mean, stddev);

        return Math.pow(stddev.get(0, 0)[0], 2); // variance
    }

    /**
     * Calculate brightness level based on mean intensity.
     */
    private String calculateBrightness(Mat image) {
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        Scalar mean = Core.mean(gray);
        double brightness = mean.val[0];

        if (brightness < 80) return "DARK";
        else if (brightness > 180) return "BRIGHT";
        else return "NORMAL";
    }
}
