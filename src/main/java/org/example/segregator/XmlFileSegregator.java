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

@Getter
@RequiredArgsConstructor
public class XmlFileSegregator implements FileSegregator {
    @NonNull
    private final String devDir;
    private int devCount = 0;

    @Override
    public void moveFile(File file) throws IOException {
        Path source = file.toPath();
        Path target = Paths.get("DEV", file.getName());
        try {
            Files.createDirectories(target.getParent());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            devCount++;
            System.out.println("Moved file " + file.getName() + " to DEV");
        } catch (IOException e) {
            System.out.println("Failed to move file " + file.getName() + " to DEV " + e );
            throw e;
        }
    }
}
