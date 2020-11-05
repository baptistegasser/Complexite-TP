package TP2.Exercice2;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Resolver {
    Path fichier = Paths.get("src/main/java/TP2/Exercice2/formule.txt");

    public Resolver() throws IOException {
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
