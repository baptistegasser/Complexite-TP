package TP2.minisat;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Util {

    /**
     * @return a temporary file that will be deleted with the virtual machine.
     */
    public static File CreateTmpFile() {
        try {
            File tmp = File.createTempFile(new Date().toString(), null);
            tmp.deleteOnExit();
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create tmp file");
        }
    }
}
