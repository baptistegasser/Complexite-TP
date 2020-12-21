package TP2.Exercice3;

import TP2.minisat.MiniSat;
import TP2.minisat.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Use multiple sudoku grid to show that our program is
 * able to detect valid (solvable) sudoku grids.
 *
 * @see SudokuToSAT for the convertion to a SAT problem
 */
public class Main {
    /**
     * Write only: don't run the SAT solver.
     */
    private static boolean writeOnly = false;
    /**
     * Max number of cased randomly filled by {@link #randomGrid(int)}.
     */
    private final static double RANDOM_PERCENT = 50;

    public static void main(String[] args) throws Exception {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        if (argsList.contains("--example")) {
            example();
        } else if (argsList.contains("--help")) {
            usage();
        } else if (args.length >= 1) {
            if (argsList.contains("--write-only")) {
                writeOnly = true;
                argsList.remove("--write-only");
            }
            parseAndRun(argsList);
        } else {
            System.err.println("Invalid usage, please use --help for help.");
            System.exit(1);
        }
    }

    /**
     * Parse the argument and run the program.
     *
     * @param args the argument list.
     */
    private static void parseAndRun(List<String> args) throws Exception {
        int n;
        try {
            n = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.err.println("Invalid argument, n must be an int");
            System.exit(2);
            return; // dumb return after exit because java is dumb.
        }
        int[][] grid = null;
        String path = null;
        if (args.size() == 2) {
            String arg1 = args.get(1).trim();
            if (arg1.startsWith("[")) {
                grid = parseGrid(n, arg1);
            } else {
                path = arg1;
            }
        } else if (args.size() >= 2) {
            path = args.get(args.size() - 1);

            String s = "";
            for (int i = 1; i < args.size() - 1; ++i) {
                s += args.get(i);
            }
            grid = parseGrid(n, s);
        }

        if (grid == null) {
            System.out.println("Generating a random grid as none was provided");
            grid = randomGrid(n);
        }

        run(n, grid, path);
    }

    /**
     * Run the program.
     *
     * @param n the grid size.
     * @param grid the grid.
     * @param pathToFile the path to the file used to store SAT.
     */
    private static void run(int n, int[][] grid, String pathToFile) throws Exception {
        System.out.println("Converting following sudoku to sat: ");
        printSudoku(n, grid);

        SudokuToSAT converter = new SudokuToSAT(n, grid);
        File file = getFile(pathToFile);
        converter.toSAT(file);

        // Run minisat if not --write-only and writing to a file
        if (!writeOnly && file != null) {
            boolean solvable = MiniSat.run(file);
            System.out.println("This sudoku grid is " + (solvable ? "" : "NOT ") + "solvable.");
        }
    }

    /**
     * Get a file matching the path.
     * If needed:
     * - create the target file
     * - create the target file's parent folders
     *
     * @param pathToFile the path to the file
     * @return a {@link File} instance representing the file or null if path is null
     */
    private static File getFile(String pathToFile) throws IOException {
        if (pathToFile == null) {
            return null;
        } else {
            Path path = Path.of(pathToFile);

            // Create file and parent dirs if needed
            if (!Files.exists(path)) {
                if (path.getParent() != null && !Files.exists(path.getParent())) {
                    path.getParent().toFile().mkdirs();
                }
                Files.createFile(path);
            } else if (Files.isDirectory(path)) {
                System.err.println("Warning: given path is a dir, a file will be created inside it.");
                path = Path.of(path.toString(), "SudokuToSAT" + new Date().toString().replaceAll("\\s+", "") + ".sat");
                Files.createFile(path);
            }

            return path.toFile();
        }
    }

    /**
     * Parse a grid from a string.
     *
     * @param n the size of the grid.
     * @param s the string to parse.
     * @return the matching grid instance.
     */
    private static int[][] parseGrid(int n, String s) {
        int[][] grid = new int[n * n][n * n];

        s = s.replaceAll("\\s+", "");
        Pattern r = Pattern.compile("\\[([0-9]+,)*[0-9]+]");
        if (!r.matcher(s).matches()) {
            System.err.println("Failed to parse the given grid");
            System.exit(2);
        }
        s = s.replaceAll("\\[", "").replaceAll("]", "");
        String[] split = s.split(",");
        assert split.length == n * n * n * n;

        int i = 0;
        for (int x = 0; x < n * n; ++x) {
            for (int y = 0; y < n * n; ++y) {
                grid[x][y] = Integer.parseInt(split[i]);
                i += 1;
            }
        }

        return grid;
    }

    /**
     * Create a random grid with a max percentage of filled cells.
     * A case might be filled with an empty value, technically not filling it.
     *
     * @param n the the grid size.
     * @return the new grid.
     * @see #RANDOM_PERCENT
     */
    private static int[][] randomGrid(int n) {
        int size = n * n;
        int[][] grid = new int[size][size];

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int attempt = (int) ((size * size / 100d) * RANDOM_PERCENT);
        for (; attempt > 0; attempt--) {
            grid[rnd.nextInt(size)][rnd.nextInt(size)] = rnd.nextInt(size + 1);
        }

        return grid;
    }

    /**
     * Print a sudoku grid.
     *
     * @param n the grid size.
     * @param grid the grid, duh.
     */
    private static void printSudoku(int n, int[][] grid) {
        int size = n * n;
        int max = 0;
        for (int[] line : grid) {
            for (int val : line) {
                if (max < val) {
                    max = val;
                }
            }
        }

        int intSize = String.valueOf(max).length();
        DecimalFormat format = new DecimalFormat("#".repeat(intSize));
        String top = "-".repeat(size * intSize + size - 1 + 2);
        System.out.println(top);

        StringBuilder sb;
        for (int[] line : grid) {
            sb = new StringBuilder("|");
            for (int val : line) sb.append(format.format(val)).append("|");
            System.out.println(sb.toString());
        }

        System.out.println(top);
    }

    /**
     * Convert a sudoku grid to a string value.
     *
     * @param grid the grid.
     * @return the string value.
     */
    private static String gridToString(int[][] grid) {
        StringBuilder builder = new StringBuilder("[");

        int max = grid.length - 1;
        for (int i = 0; i < grid.length; i++) {
            String line = Arrays.toString(grid[i]).replace("[", "").replace("]", "");
            builder.append(line);
            if (i < max) {
                builder.append(",");
            }
        }

        return builder.append("]").toString();
    }

    /**
     * Display usage and quit.
     */
    private static void usage() {
        System.out.println("Usage: java Main n [sudoku grid] [path] [options]");
        System.out.println("Convert a Sudoku problem of size n to a SAT problem.");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  n            the size of the sudoku grid.");
        System.out.print("  sudoku grid  the sudoku grid to convert to sat problem.");
        System.out.println("               the format is: [1,0,0,1,4,...,2], space should be avoided but are tolerated.");
        System.out.println("  path         the path to use to store the sat problem.");
        System.out.println();
        System.out.println("Mandatory arguments to long options are mandatory for short options too.\n");
        System.out.println("  --help        display this help and exit.");
        System.out.println("  --examples    display an example and exit.");
        System.out.print("  --write-only  only write the sat file and leave.");
        System.out.println("                This option require the wanted path to be set.");

        System.exit(0);
    }

    /**
     * Display examples and quit.
     * Run with --example argument
     */
    private static void example() throws Exception {
        int n = 2;
        int[][] valid = new int[n * n][n * n];
        valid[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        valid[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        valid[2] = new int[]{3, 1, 2, 4}; // 31  32  33  34
        valid[3] = new int[]{2, 4, 3, 0}; // 41  42  43  44

        int[][] invalid = new int[n * n][n * n];

        invalid[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        invalid[1] = new int[]{4, 3, 0, 0}; // 21  22  23  24
        invalid[2] = new int[]{3, 0, 1, 4}; // 31  32  33  34
        invalid[3] = new int[]{0, 4, 2, 0}; // 41  42  43  44

        int[][] invalid_2 = new int[n * n][n * n];
        invalid_2[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        invalid_2[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        invalid_2[2] = new int[]{3, 1, 0, 4}; // 31  32  33  34
        invalid_2[3] = new int[]{2, 4, 2, 0}; // 41  42  43  44

        int[][] invalid_3 = new int[n * n][n * n];
        invalid_3[0] = new int[]{0, 1, 4, 3}; // 11  12  13  14
        invalid_3[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        invalid_3[2] = new int[]{3, 1, 2, 4}; // 31  32  33  34
        invalid_3[3] = new int[]{2, 4, 3, 0}; // 41  42  43  44

        //MiniSat.DEBUG = true;
        // Create temp file
        File tmpFile = Util.CreateTmpFile();

        System.out.println("Running some fixed examples.");
        System.out.println("Example 1 is valid, the three other are not.");

        System.out.println("###################################\n");
        main(new String[]{String.valueOf(n), gridToString(valid), tmpFile.getAbsolutePath()});

        System.out.println("###################################\n");
        main(new String[]{String.valueOf(n), gridToString(invalid), tmpFile.getAbsolutePath()});

        System.out.println("###################################\n");
        main(new String[]{String.valueOf(n), gridToString(invalid_2), tmpFile.getAbsolutePath()});

        System.out.println("###################################\n");
        main(new String[]{String.valueOf(n), gridToString(invalid_3), tmpFile.getAbsolutePath()});

        System.exit(0);
    }
}
