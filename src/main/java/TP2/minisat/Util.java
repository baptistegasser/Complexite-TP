package TP2.minisat;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Util {
    public static File CreateTmpFile() {
        try {
            return File.createTempFile(new Date().toString(), null);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create tmp file");
        }
    }
}
