package org.example.segregator;

import java.io.File;
import java.io.IOException;

public interface FileSegregator {
    void moveFile(File file) throws IOException;
}
