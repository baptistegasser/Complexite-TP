package TP2.Exercice2;

import TP2.Exercice1.FNCSolver;
import TP2.minisat.MiniSat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class GraphToSAT {
    String source = "src/main/java/TP2/Exercice2/formule.txt";
    Path fichier = Paths.get(source);

    String inputSource = "src/main/java/TP2/Exercice2/input.txt";
    Path inputFichier = Paths.get(inputSource);

    public GraphToSAT() throws IOException {
    }

    /**
     * Resolution by MiniSat the 3-Sat problem
     *
     * @param graph the graph
     * @param k the desired empty zone size
     * @throws IOException
     */
    public void resolutionByMiniSat(Graph graph, int k) throws IOException {
        this.reduction(graph,k);

        File file = new File(source);

        boolean result = MiniSat.run(file);

        if (result) System.out.println("Pour k = "+k+", avec MiniSat, la solution est satisfiable \n");
        else System.out.println("Pour k = "+k+", avec MiniSat, la solution est insatisfiable \n");
    }

    /**
     * Resolution by brute force the 3-Sat problem
     *
     * @param graph the graph
     * @param k the desired empty zone size
     * @throws IOException
     */
    public void resolutionBruteForce(Graph graph, int k) throws IOException {
        this.reduction(graph,k);

        int nbVertices = graph.getMatrice().length;

         if (writeInput(-1, k, nbVertices, new StringBuilder())) {
             System.out.println("Pour k="+k+", avec la methode brut force, la solution est satisfiable \n");
         } else System.out.println("Pour k="+k+", avec la methode brut force, la solution est insatisfiable \n");
    }

    /**
     * Test for all combination of value
     *
     * @param x the current index
     * @param k the desired empty zone size
     * @param nbVertices number of vertices
     * @param currentBuilder string builder
     * @throws IOException
     */
    public boolean writeInput(int x, int k, int nbVertices, StringBuilder currentBuilder) throws IOException {
        int i = x+1;
        StringBuilder nextBuilder = new StringBuilder(currentBuilder);

        if (i <= nbVertices) {
            if (i == nbVertices) {
                Files.write(inputFichier, Collections.singleton(nextBuilder.toString()));
                return FNCSolver.solve("Exercice2\\formule.txt", "Exercice2\\input.txt", false);
            } else if (k > 0) {
                int nbVal = k - 1;
                nextBuilder.append(i + 1).append(" ");
                if (writeInput(i, nbVal, nbVertices, nextBuilder)) return true;

                // Test with negative only if it's worth it
                if ((nbVertices-(i+1)) > nbVal) {
                    StringBuilder nextNegativeBuilder = new StringBuilder(currentBuilder);
                    nextNegativeBuilder.append("-").append(i + 1).append(" ");
                    return writeInput(i, nbVal + 1, nbVertices, nextNegativeBuilder);
                }
            } else {
                nextBuilder.append("-").append(i + 1).append(" ");
                return writeInput(i, k, nbVertices, nextBuilder);
            }
        }
        return false;
    }

    /**
     * Reduction a graph to 3-Sat problem
     *
     * @param graph the graph
     * @param k the desired empty zone size
     * @throws IOException
     */
    public void reduction(Graph graph, int k) throws IOException {
        int nbVertices = graph.getMatrice().length;
        int clauseSize = nbVertices - (k-1);

        ArrayList<String> lines = new ArrayList<>();

        int nbClause = (int) (graph.getNbEdge() + binomi(nbVertices, clauseSize));

        //First line
        lines.add("p cnf " + nbVertices +" "+ nbClause);


        // Add clause for each edge
        for (int i = 0; i<nbVertices;i++) {
            for (int j = i+1; j<nbVertices;j++) {
                if (graph.getMatrice()[i][j] == 1) {
                    lines.add("-"+(i+1) +" -"+ (j+1) + " 0");
                }
            }
        }


        // Add clause in function k
        StringBuilder currentBuilder = new StringBuilder();
        for (int i = 0; i < nbVertices ; i++) {
            StringBuilder nextBuilder = new StringBuilder(currentBuilder);

            // Add current vertice
            nextBuilder.append(i+1).append(" ");
            // If clause size is reached
            if (clauseSize > 1)
                writeNextValue(i,clauseSize, nbVertices, nextBuilder, lines);
            else {
                nextBuilder.append("0");
                lines.add(nextBuilder.toString());
            }
        }

        Files.write(fichier, lines);
    }

    /**
     * Write clauses for take value in function of k
     *
     * @param x the current index
     * @param clauseSize the current size of clause
     * @param nbVertices the number of vertices
     * @param currentBuilder The string builder
     * @param lines The list of clause
     */
    public void writeNextValue(int x, int clauseSize, int nbVertices, StringBuilder currentBuilder, ArrayList<String> lines) {
        clauseSize -= 1;

        for (int i = x+1; i < nbVertices ; i++) {
            StringBuilder nextBuilder = new StringBuilder(currentBuilder);

            // Add current vertice
            nextBuilder.append(i + 1).append(" ");
            // If clause size is reached
            if (clauseSize > 1)
                writeNextValue(i,clauseSize, nbVertices,nextBuilder, lines);
            else {
                nextBuilder.append("0");
                lines.add(nextBuilder.toString());
            }
        }
    }

    /**
     * Take this function from https://stackoverflow.com/questions/36925730/java-calculating-binomial-coefficient
     *
     * Calculate the binomial coefficient between two variables
     * @param n first variable
     * @param k second variable
     * @return the binomial coefficient
     */
    static long binomi(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }
}
