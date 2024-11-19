package org.example.starrepo.requests;

import org.example.starrepo.config.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArtifactRequests implements CrudInterface {

    private final Path repositoryPath;

    public ArtifactRequests() throws IOException {
        String repoPath = Config.getProperties("repository.path");
        repositoryPath = Paths.get(repoPath);
        if (Files.notExists(repositoryPath)) {
            Files.createDirectories(repositoryPath);
        }
    }

    @Override
    public String create(Object obj) {
        if (obj instanceof File) {
            File file = (File) obj;
            try {
                Path destinationFile = repositoryPath.resolve(file.getName()).normalize().toAbsolutePath();
                Files.copy(file.toPath(), destinationFile);
                return "File uploaded successfully.";
            } catch (IOException e) {
                return "Failed to upload file: " + e.getMessage();
            }
        }
        return "Invalid object type. Expected File.";
    }

    @Override
    public Path read(String id) {
        Path filePath = repositoryPath.resolve(id).normalize().toAbsolutePath();
        if (Files.exists(filePath)) {
            return filePath;
        }
        return null;
    }

    @Override
    public String update(Object obj) {
        if (obj instanceof File) {
            File file = (File) obj;
            try {
                Path destinationFile = repositoryPath.resolve(file.getName()).normalize().toAbsolutePath();
                if (Files.exists(destinationFile)) {
                    Files.copy(file.toPath(), destinationFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    return "File updated successfully.";
                } else {
                    return "File not found for update.";
                }
            } catch (IOException e) {
                return "Failed to update file: " + e.getMessage();
            }
        }
        return "Invalid object type. Expected File.";
    }

    @Override
    public String delete(String id) {
        Path filePath = repositoryPath.resolve(id).normalize().toAbsolutePath();
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return "File deleted successfully.";
            } else {
                return "File not found for deletion.";
            }
        } catch (IOException e) {
            return "Failed to delete file: " + e.getMessage();
        }
    }
}
