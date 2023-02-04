package org.example.segregator;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;


@Getter
@RequiredArgsConstructor
public class JarFileSegregator implements FileSegregator {

    @NonNull
    private final String devDirectory;
    @NonNull
    private final String testDirectory;
    private int devCount = 0;
    private int testCount = 0;

    @Override
    public void moveFile(File file) throws IOException {
        Path source = file.toPath();
        BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        long creationTime = attributes.creationTime().toMillis();
        Path target = creationTime % 2 == 0 ? Paths.get(devDirectory, file.getName())
                : Paths.get(testDirectory, file.getName());
        try {
            Files.createDirectories(target.getParent());
            System.out.println(target.getParent() + " directory created");
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved file " + file.getName() + " to " + target.getParent().getFileName());
            incrementCount(target.getParent().getFileName().toString());
        } catch (IOException e) {
            System.out.println("Failed to move file " + file.getName() + " to " + target.getParent().getFileName() + e);
        }
    }

    private void incrementCount(String directory) {
        if (directory.equals(devDirectory)) {
            devCount++;
        } else if (directory.equals(testDirectory)) {
            testCount++;
        }
    }
}
