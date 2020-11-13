package TP2.Exercice3;

import TP2.minisat.MiniSat;
import TP2.minisat.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SudokuToSat {
    private final int n;
    private final int max;
    private final int[][] grid;

    private final HashSet<String> vars = new HashSet<>();

    /**
     * Create an instance capable of telling if a sudoku grid is solvable.
     *
     * @param n the size of a group of cell
     * @param grid the grid of size n*n containing number between 0 and n*n where 0 is an empty cell (no value set)
     */
    public SudokuToSat(int n, int[][] grid) {
        this.n = n;
        this.max = n * n;
        this.grid = grid;
    }

    /**
     * @return true if the sudoku {@link #grid} is solvable
     */
    public boolean isSolvable() {
        // Generate the clauses
        final ArrayList<String> clauses = generateClauses();

        // Create temp file
        File tmp = Util.CreateTmpFile();

        // Write them to a file
        writeSatToFile(tmp, clauses);

        // Test if solvable with Minisat
        boolean result = MiniSat.run(tmp);

        // Delete temp file
        tmp.delete();

        return result;
    }

    /**
     * @return the list of clauses representing this sudoku problem for sat solving.
     */
    private ArrayList<String> generateClauses() {
        final ArrayList<String> clauses = new ArrayList<>();
        clauses.addAll(cellClauses());
        clauses.addAll(groupCellsClauses());
        clauses.addAll(columnAndLineClauses());
        return clauses;
    }

    /**
     * Write a sat problem to a file.
     * format is :
     * p cnf 3 1
     * 1 -2 4 0
     *
     * @param file the file to write to
     * @param clauses the clauses to write
     */
    private void writeSatToFile(File file, ArrayList<String> clauses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            String cnf = String.format("p cnf %d %d", vars.size(), clauses.size());
            writeLine(bw, cnf);

            for (String clause : clauses) {
                writeClauseLine(bw, clause);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the constraint that a cell must have a value.
     *
     * @return the clauses corresponding to the constraint
     */
    private ArrayList<String> cellClauses() {
        final ArrayList<String> cellClauses = new ArrayList<>();

        for (int x = 1; x <= max; ++x) {
            for (int y = 1; y <= max; ++y) {
                int v = grid[x - 1][y - 1];
                // If the cell have a value, add a clause with the exact value
                if (v != 0) {
                    cellClauses.add(var(v, x, y));
                    continue;
                }

                // Create clauses to assert that a cell have only one value
                //     * One clause with all valid values to assert their is at least one value
                //     * A clause for each value to assert the cell either take the value or don't take it, not both
                StringBuilder validValues = new StringBuilder();
                for (v = 1; v <= max; ++v) {
                    String var = var(v, x, y);
                    cellClauses.add(String.format("%s -%s", var, var));
                    validValues.append(var).append(" ");
                }
                cellClauses.add(validValues.toString());
            }
        }

        return cellClauses;
    }

    /**
     * Add the constraint that a group of n*n cells must contain only one instance of possible values.
     *
     * @return the clauses corresponding to the constraint
     */
    private ArrayList<String> groupCellsClauses() {
        final ArrayList<String> groupCellsClauses = new ArrayList<>();

        for (int a = 1; a < max; a += n) {
            for (int b = 1; b < max; b += n) {
                int i = 0;
                String[] groupCells = new String[max];
                for (int x = a; x < a + n; ++x) {
                    for (int y = b; y < b + n; ++y) {
                        groupCells[i++] = cell(x, y);
                    }
                }
                groupCellsClauses.addAll(noDuplicateCell(groupCells));
            }
        }

        return groupCellsClauses;
    }

    /**
     * Add the constraint that a line or column of n*n cells must contain only one instance of possible values.
     *
     * @return the clauses corresponding to the constraint
     */
    private ArrayList<String> columnAndLineClauses() {
        final ArrayList<String> columnAndLineClauses = new ArrayList<>();

        for (int x = 1; x <= max; ++x) {
            String[] lineCells = new String[max];
            String[] columnCells = new String[max];
            for (int y = 1; y <= max; ++y) {
                lineCells[y - 1] = cell(x, y);
                columnCells[y - 1] = cell(y, x);
            }
            columnAndLineClauses.addAll(noDuplicateCell(lineCells));
            columnAndLineClauses.addAll(noDuplicateCell(columnCells));
        }

        return columnAndLineClauses;
    }

    /**
     * Add the constraint that for a given list of cells, no cell
     * contain a duplicate value present in another cell.
     *
     * @param cells the cells where we don't want duplicate
     * @return the clauses corresponding to the constraint
     */
    private ArrayList<String> noDuplicateCell(String[] cells) {
        final ArrayList<String> noDuplicateClauses = new ArrayList<>();

        // For each possible values and for each cell of pos i, create
        // clauses as (-cell[i] V -cell[i+1]) ^ (-cell[i] V -cell[i+2]) ...
        for (int v = 1; v <= max; ++v) {
            for (int i = 0; i < cells.length - 1; ++i) {
                String s1 = cells[i];
                for (int j = i + 1; j < cells.length; ++j) {
                    String s2 = cells[j];
                    noDuplicateClauses.add(String.format("-%s -%s", var(v, s1), var(v, s2)));
                }
            }
        }

        return noDuplicateClauses;
    }

    /**
     * Create a var representing a cell and it's value and store it inside the vars list.
     *
     * @param v the value of the cell
     * @param x the x pos of the cell
     * @param y the y pos of the cell
     * @return a string representation of the var
     */
    private String var(int v, int x, int y) {
        return var(v, cell(x, y));
    }

    /**
     * Create a var representing a cell and it's value and store it inside the vars list.
     *
     * @param v    the value of the cell
     * @param cell the position of the cell {@link #cell(int, int)}
     * @return a string representation of the var
     */
    private String var(int v, String cell) {
        String s = v + cell;
        vars.add(s);
        return s;
    }

    /**
     * Create a cell from it's position.
     *
     * @param x the x pos of the cell
     * @param y the y pos of the cell
     * @return a string representation of the cell
     */
    private String cell(int x, int y) {
        return x + "" + y;
    }

    /**
     * Write a string and jump to next line.
     *
     * @param bw the writer to write the string to
     * @param s  the string to write
     * @throws IOException writing might fail
     */
    private void writeLine(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    /**
     * Write a clause and jump to next line.
     * A clause is of form "1 3 -4".
     * Writing it will result in "1 3 -4 0\n".
     *
     * @param bw     the writer to write the string to
     * @param clause the clause to write
     * @throws IOException writing might fail
     * @see #writeLine(BufferedWriter, String) for the actual function writing the line
     */
    private void writeClauseLine(BufferedWriter bw, String clause) throws IOException {
        writeLine(bw, clause + " 0");
    }
}
