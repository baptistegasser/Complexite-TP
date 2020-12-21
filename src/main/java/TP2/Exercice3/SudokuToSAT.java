package TP2.Exercice3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class SudokuToSAT {
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
    public SudokuToSAT(int n, int[][] grid) {
        this.n = n;
        this.max = n * n;
        this.grid = grid;
    }

    /**
     * Convert this sudoku problem to sat and write it to a temporary file.
     *
     * @return a {@link File} instance representing the used file
     */
    public void toSAT() throws IOException {
        toSAT(null);
    }

    /**
     * Convert this sudoku problem to sat and write it to a file.
     *
     * @param file the file to write to
     * @return a {@link File} instance representing the used file
     */
    public void toSAT(File file) throws IOException {
        try  {
            BufferedWriter bw;
            if (file == null) {
                bw = new BufferedWriter(new OutputStreamWriter(System.out));
            } else {
                bw = new BufferedWriter(new FileWriter(file));
            }

            // Generate the clauses and write them to the output
            final ArrayList<String> clauses = generateClauses();
            writeSatToFile(bw, clauses);

            // Clean and return the used file
            bw.close();
        } catch (IOException ioe) {
            System.err.println("Failed due to I/O error.");
            ioe.printStackTrace();
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Write a sat problem to a file.
     *
     * @param bw the object to write to
     * @param clauses the clauses to write
     */
    private void writeSatToFile(BufferedWriter bw, ArrayList<String> clauses) throws IOException {
        String cnf = String.format("p cnf %d %d", vars.size(), clauses.size());
        bw.write(cnf);
        bw.newLine();

        for (String clause : clauses) {
            bw.write(clause + " 0");
            bw.newLine();
        }
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
}
