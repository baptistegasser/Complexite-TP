package TP2.exercice3;

import TP2.minisat.MiniSat;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Fuck implements Closeable {
    private File tmp;
    private BufferedWriter bw;

    private final int n;
    private final int max;
    private final int[][] grid;

    private final HashSet<String> vars = new HashSet<>();
    private final List<String> clauses = new ArrayList<>();

    public Fuck(int n, int[][] grid) {
        this.n = n;
        this.max = n * n;
        this.grid = grid;
    }

    public boolean isSolvable() throws IOException {
        tmp = new File("/home/baptiste/Documents/TPs/tp 1 comple/src/main/resources/manual_2.txt");
        bw = new BufferedWriter(new FileWriter(tmp));
        generateSat();
        bw.close();
        boolean result = MiniSat.run(tmp);
        tmp.delete();
        return result;
    }

    private void generateSat() throws IOException {
        validCell();
        validGroupCell();
        validColumnAndLine();

        writeLine(String.format("p cnf %d %d", vars.size(), clauses.size()));
        for (String clause : clauses) {
            writeLine(clause);
        }
    }

    private void validCell() {
        for (int x = 1; x <= max; ++x) {
            for (int y = 1; y <= max; ++y) {
                if (grid[x - 1][y - 1] != 0) {
                    clauses.add(var(grid[x - 1][y - 1], x, y) + " 0");
                } else {
                    for (int v = 1; v <= max; ++v) {
                        String var = var(v, x, y);
                        clauses.add(String.format("%s -%s 0", var, var));
                    }
                }
            }
        }
    }

    private void validGroupCell() {
        for (int a = 1; a < max; a += n) {
            for (int b = 1; b < max; b += n) {
                String[] group = new String[max];
                int i = 0;
                for (int x = a; x < a + n; ++x) {
                    for (int y = b; y < b + n; ++y) {
                        group[i++] = x + "" + y;
                    }
                }
                isValid(group);
            }
        }
    }

    private void validColumnAndLine() {
        for (int x = 1; x <= max; ++x) {
            String[] line = new String[max];
            String[] column = new String[max];
            for (int y = 1; y <= max; ++y) {
                line[y - 1] = x + "" + y;
                column[y - 1] = y + "" + x;
            }
            isValid(line);
            isValid(column);
        }
    }

    private void isValid(String[] cells) {
        for (int v = 1; v <= max; ++v) {
            for (int i = 0; i < cells.length - 1; ++i) {
                String s1 = cells[i];
                for (int j = i + 1; j < cells.length; ++j) {
                    String s2 = cells[j];
                    clauses.add(String.format("-%s -%s 0", var(v, s1), var(v, s2)));
                }
            }
        }
    }

    private String var(int v, int x, int y) {
        String s = v + "" + x + "" + y;
        vars.add(s);
        return s;
    }

    private String var(int v, String cell) {
        String s = v + cell;
        vars.add(s);
        return s;
    }

    private void writeLine(String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    @Override
    public void close() throws IOException {
        bw.close();
        tmp.delete();
    }
}
