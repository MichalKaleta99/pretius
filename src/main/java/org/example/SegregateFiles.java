package org.example;

import org.example.segregator.XmlFileSegregator;
import org.example.segregator.JarFileSegregator;
import org.example.filemover.FileMover;

import java.io.IOException;

public class SegregateFiles {
    public static void main(String[] args) throws IOException {
        JarFileSegregator jarFileSegregator = new JarFileSegregator("DEV", "TEST");
        XmlFileSegregator xmlFileSegregator = new XmlFileSegregator("DEV");
        FileMover fileMover = new FileMover("HOME", jarFileSegregator, xmlFileSegregator);
        fileMover.moveFilesContinuously();
    }
}
