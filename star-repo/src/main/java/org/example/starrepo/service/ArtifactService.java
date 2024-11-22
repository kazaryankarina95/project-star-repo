package org.example.starrepo.service;

import org.example.starrepo.requests.ArtifactRequests;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
@Component
public class ArtifactService {

    private final ArtifactRequests artifactRequests;

    public ArtifactService(ArtifactRequests artifactRequests) {
        this.artifactRequests = artifactRequests;
    }

    // Business logic for downloading the artifact
    public ResponseEntity<FileSystemResource> downloadArtifact(String filename) {
        // Check if the file exists
        Path filePath = artifactRequests.read(filename);

        if (filePath != null && filename.endsWith(".jar")) {
            return buildFileResponse(filePath.toFile());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Business logic for uploading the artifact
    public String uploadArtifact(File file) {
        if (file.exists() && file.isFile() && file.getName().endsWith(".jar")) {
            return artifactRequests.create(file);
        } else {
            return "Invalid file or file path.";
        }
    }

    // Business logic for updating the artifact
    public String updateArtifact(File file) {
        if (file.exists() && file.isFile() && file.getName().endsWith(".jar")) {
            return artifactRequests.update(file);
        } else {
            return "Invalid file or file path.";
        }
    }

    // Business logic for deleting the artifact
    public String deleteArtifact(String filename) {
        if (filename.endsWith(".jar")) {
            return artifactRequests.delete(filename);
        } else {
            return "Invalid filename.";
        }
    }

    // Helper method to build the file response for downloading
    private ResponseEntity<FileSystemResource> buildFileResponse(File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(file));
    }
}

