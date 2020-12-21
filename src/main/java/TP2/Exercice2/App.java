package TP2.Exercice2;

import TP2.minisat.MiniSat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(MatriceTest.matrice2);

        GraphToSAT resolver = new GraphToSAT();

        //resolver.resolution(graph);

        String testSource = "src/main/resources/test.sat";
        File file = new File(testSource);

        boolean res = MiniSat.run(file);

        if (res) System.out.println("Test Sat");
        else System.out.println("test Insat");
    }
}
