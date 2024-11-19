package org.example.starrepo.generators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarGenerator {

    public File generateJar(String baseDir, String jarName) throws IOException {
        Path jarFilePath = Paths.get(baseDir, jarName + ".jar");
        File jarFile = jarFilePath.toFile();

        try (FileOutputStream fos = new FileOutputStream(jarFile);
             JarOutputStream jos = new JarOutputStream(fos)) {

            // Example: Add a dummy class file to the JAR (can be extended as needed)
            String entryName = "com/example/DummyClass.class";
            jos.putNextEntry(new JarEntry(entryName));
            byte[] dummyClassContent = generateDummyClass();
            jos.write(dummyClassContent, 0, dummyClassContent.length);
            jos.closeEntry();
        }

        return jarFile;
    }

    private byte[] generateDummyClass() {
        // In a real scenario, this would be bytecode. For simplicity, just return some dummy content.
        return "public class DummyClass {}".getBytes();
    }
}
