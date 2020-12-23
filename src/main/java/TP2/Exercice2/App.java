package TP2.Exercice2;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws IOException {
        int nbVertices = 10;
        int edgePercentage = 20;
        int[][] matrices = new int[nbVertices][nbVertices];



        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
                int n = (int)(Math.random() * 100);
                if (n > edgePercentage) {
                    matrices[i][j] = 0;
                } else matrices[i][j] = 1;
            }
        }

        Graph graph = new Graph(matrices);

        GraphToSAT resolver = new GraphToSAT();

        int k = 1;

        compareTime(graph, resolver, k);

        k = 2;

        compareTime(graph, resolver, k);

        k = 3;

        compareTime(graph, resolver, k);

        k = 4;

        compareTime(graph, resolver, k);

        k = 5;

        compareTime(graph, resolver, k);

        k = 6;

        compareTime(graph, resolver, k);

        k = 7;

        compareTime(graph, resolver, k);

        k = 8;

        compareTime(graph, resolver, k);

        k = 9;

        compareTime(graph, resolver, k);

        k = 10;

        compareTime(graph, resolver, k);
    }

    private static void compareTime(Graph graph, GraphToSAT resolver, int k) throws IOException {
        long start1 = System.nanoTime();

        resolver.resolutionByMiniSat(graph, k);

        long end1 = System.nanoTime();

        System.out.println("Temps de resolution pour MiniSat : "+  (float)(end1-start1)/1_000_000+"ms\n");

        long start2 = System.nanoTime();

        resolver.resolutionBruteForce(graph, k);

        long end2 = System.nanoTime();

        System.out.println("Temps de resolution pour force brut : "+  (float)(end2-start2)/1_000_000+"ms\n");
    }
}
