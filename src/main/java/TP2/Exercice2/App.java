package TP2.Exercice2;

import TP2.minisat.MiniSat;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(MatriceTest.matrice3);

        GraphToSAT resolver = new GraphToSAT();

        long start1 = System.nanoTime();

        resolver.resolutionByMiniSat(graph, 4);

        long end1 = System.nanoTime();

        System.out.println("Time for resolution with MiniSat : "+  (float)(end1-start1)/1_000_000+"ms");

        long start2 = System.nanoTime();

        resolver.resolutionBruteForce(graph, 4);

        long end2 = System.nanoTime();

        System.out.println("Time for resolution with Brute Force : "+  (float)(end2-start2)/1_000_000+"ms");
    }
}
