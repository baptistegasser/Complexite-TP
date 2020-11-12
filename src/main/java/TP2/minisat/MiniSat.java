package TP2.minisat;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

public class MiniSat {
    private static boolean satisfiable = false;
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean run(File file) {
        String binName;

        if (OS.contains("win")) {
            binName = "MiniSat_v1.14_cygwin";
        } else if (OS.contains("nux") || OS.contains("nix") || OS.contains("aix")) {
            binName = "MiniSat_v1.14_linux";
        } else {
            throw new UnsupportedOperationException("Your OS is not supported by provided MiniSat binaries. Please use Windows or Linux");
        }

        URL url = MiniSat.class.getClassLoader().getResource(binName);
        if (url == null) {
            throw new RuntimeException(String.format("Failed to find %s in resources dir", binName));
        }

        String binary = Path.of(url.getFile()).toString().replaceAll("%20", " ");

        try {
            Process ps = new ProcessBuilder(Arrays.asList(binary, file.getAbsolutePath())).start();

            new Thread(new ErrorStreamConsumer(ps.getErrorStream())).start();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("SATISFIABLE")) {
                    satisfiable = true;
                }
            }

            return satisfiable;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ErrorStreamConsumer implements Runnable {
        private final InputStream is;

        private ErrorStreamConsumer(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            char[] cbuf = new char[255];
            int l;

            try (InputStreamReader ir = new InputStreamReader(is)) {
                while (true) {
                    l = ir.read(cbuf);
                    if (l == -1) {
                        break;
                    } else if (l > 0) {
                        System.err.println(cbuf);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
