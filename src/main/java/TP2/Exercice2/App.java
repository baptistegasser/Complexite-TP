package TP2.Exercice2;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(MatriceTest.matrice2);

        Resolver resolver = new Resolver();

        resolver.reduction(graph);
    }
}
