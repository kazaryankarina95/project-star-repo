package org.example.starrepo.controller;

import org.example.starrepo.service.ArtifactService;
import org.example.starrepo.config.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    // Endpoint to upload artifact
    @PostMapping("/upload")
    public ResponseEntity<String> uploadArtifact(@RequestParam("filePath") String filePath) {
        try {
            File file = new File(filePath);
            String result = artifactService.uploadArtifact(file);
            if (result.equals("File uploaded successfully.")) {
                return ResponseEntity.status(201).body(result);
            } else {
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    // Endpoint to download artifact
    @GetMapping("/download")
    public ResponseEntity<?> downloadArtifact(@RequestParam("filename") String filename) {
        try {
            return artifactService.downloadArtifact(filename);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while downloading file: " + e.getMessage());
        }
    }

    // Endpoint to update artifact
    @PutMapping("/update")
    public ResponseEntity<String> updateArtifact(@RequestParam("filePath") String filePath) {
        try {
            File file = new File(filePath);
            String result = artifactService.updateArtifact(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update file: " + e.getMessage());
        }
    }

    // Endpoint to delete artifact
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArtifact(@RequestParam("filename") String filename) {
        try {
            String result = artifactService.deleteArtifact(filename);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete file: " + e.getMessage());
        }
    }
}
