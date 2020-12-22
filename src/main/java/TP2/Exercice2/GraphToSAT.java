package TP2.Exercice2;

import TP2.minisat.MiniSat;
import TP2.minisat.Util;

import javax.swing.*;
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

    public GraphToSAT() throws IOException {
    }

    /**
     * Resolution by MiniSat the 3-Sat problem
     *
     * @param graph the graph
     * @param k the desired empty zone size
     * @throws IOException
     */
    public void resolution(Graph graph, int k) throws IOException {
        this.reduction(graph,k);

        File file = new File(source);

        boolean result = MiniSat.run(file);

        if (result) System.out.println("Pour k = "+k+", la solution est satisfiable \n");
        else System.out.println("Pour k = "+k+", la solution est insatisfiable \n");
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
                recursion(i,clauseSize, nbVertices, nextBuilder, lines);
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
    public void recursion(int x, int clauseSize, int nbVertices, StringBuilder currentBuilder, ArrayList<String> lines) {
        clauseSize -= 1;

        for (int i = x+1; i < nbVertices ; i++) {
            StringBuilder nextBuilder = new StringBuilder(currentBuilder);

            // Add current vertice
            nextBuilder.append(i + 1).append(" ");
            // If clause size is reached
            if (clauseSize > 1)
                recursion(i,clauseSize, nbVertices,nextBuilder, lines);
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
