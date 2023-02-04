package org.example.filemover;

import lombok.AllArgsConstructor;
import org.example.segregator.JarFileSegregator;
import org.example.segregator.XmlFileSegregator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@AllArgsConstructor
public class FileMover {
    private String homePath;
    private JarFileSegregator jarFileSegregator;
    private XmlFileSegregator xmlFileSegregator;

    public void moveFilesContinuously() throws IOException {
        while (true) {
            File homeDirectory = new File(homePath);
            if (!homeDirectory.exists()) {
                System.out.println(homeDirectory.getName() + " directory created");
                homeDirectory.mkdirs();
            }
            File[] files = homeDirectory.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.getName().endsWith(".jar")) {
                    jarFileSegregator.moveFile(file);
                } else if (file.getName().endsWith(".xml")) {
                    xmlFileSegregator.moveFile(file);
                }
            }
            writeCountFile();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeCountFile() {
        try {
            File file = new File("HOME/count.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write("Total Files Transferred: " + (jarFileSegregator.getDevCount() + jarFileSegregator.getTestCount() + xmlFileSegregator.getDevCount()) + "\n");
            writer.write("Files Transferred to DEV: " + (jarFileSegregator.getDevCount() + xmlFileSegregator.getDevCount()) + "\n");
            writer.write("Files Transferred to TEST: " + jarFileSegregator.getTestCount() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing the count file.");
            throw new RuntimeException(e);
        }
    }
}



