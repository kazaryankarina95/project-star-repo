package org.example.reposervice.repo;

import org.example.reposervice.repo.config.Config;
import org.example.reposervice.repo.requests.ArtifactRequests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@RestController
@RequestMapping("/artifacts")
public class StartRepoApplication {

    private final Path repositoryPath;
    private final ArtifactRequests artifactRequests;

    public StartRepoApplication() throws IOException {
        // Initialize repository path from config
        String repoPath = Config.getProperties("repository.path");
        repositoryPath = Paths.get(repoPath);
        if (Files.notExists(repositoryPath)) {
            Files.createDirectories(repositoryPath);
        }
        artifactRequests = new ArtifactRequests();
    }

    public static void main(String[] args) {
        SpringApplication.run(StartRepoApplication.class, args);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadArtifact(@RequestParam("filePath") String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile() && filePath.endsWith(".jar")) {
                String result = artifactRequests.create(file);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file or file path.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadArtifact(@RequestParam("filename") String filename) {
        Path filePath = repositoryPath.resolve(filename).normalize().toAbsolutePath();
        if (Files.exists(filePath) && filename.endsWith(".jar")) {
            File file = filePath.toFile();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(file));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateArtifact(@RequestParam("filePath") String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile() && filePath.endsWith(".jar")) {
                String result = artifactRequests.update(file);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file or file path.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArtifact(@RequestParam("filename") String filename) {
        try {
            if (filename.endsWith(".jar")) {
                String result = artifactRequests.delete(filename);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid filename.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: " + e.getMessage());
        }
    }

    // Endpoint to serve the JAR file directly
    // Also, read about MVC spring
    // take a look how Maven will deal with these files (.jar)
    // spring beans - check spring doc. (don't place anything into one application file. But create Controller class and place there logic that serve web logic)
    @GetMapping("/")
    public ResponseEntity<?> viewJarFile() {
        // Specify the JAR file you want to serve
        File jarFile = repositoryPath.resolve("MvnProjectToBePackaged-1.0-SNAPSHOT.jar").toFile();
        if (jarFile.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + jarFile.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(jarFile));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JAR file not found");
        }
    }
}
