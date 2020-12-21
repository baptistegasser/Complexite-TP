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

    public void resolution(Graph graph) throws IOException {
        this.reduction(graph);

        File file = new File(source);

        boolean result = MiniSat.run(file);

        if (result) System.out.println("Satisfiable");
        else System.out.println("Insatisfiable");
    }

    public void reduction(Graph graph) throws IOException {
        System.out.println("Reduction");

        ArrayList<String> lignes = new ArrayList<>();

        lignes.add("p cnf " + graph.getMatrice().length +" "+ graph.getNbEdge());

        for (int i = 0; i<graph.getMatrice().length;i++) {
            for (int j = i+1; j<graph.getMatrice().length;j++) {
                if (graph.getMatrice()[i][j] == 1) {
                    lignes.add(i +" "+ j);
                }
            }
        }

        Files.write(fichier, lignes);
    }
}
