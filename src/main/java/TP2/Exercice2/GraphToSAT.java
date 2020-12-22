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

    public void resolution(Graph graph, int k) throws IOException {
        this.reduction(graph,k);

        File file = new File(source);

        boolean result = MiniSat.run(file);

        if (result) System.out.println("Pour k = "+k+", la solution est satisfiable \n");
        else System.out.println("Pour k = "+k+", la solution est insatisfiable \n");
    }

    public void reduction(Graph graph, int k) throws IOException {
        System.out.println("Reduction");

        int nbSommet = graph.getMatrice().length;

        ArrayList<String> lignes = new ArrayList<>();

        //Première lignes
        lignes.add("p cnf " + nbSommet +" "+ graph.getNbEdge());


        // Ajout des cluases pour chaque arc
        for (int i = 0; i<nbSommet;i++) {
            for (int j = i+1; j<nbSommet;j++) {
                if (graph.getMatrice()[i][j] == 1) {
                    lignes.add("-"+(i+1) +" -"+ (j+1) + " 0");
                }
            }
        }


        int tailleClause = nbSommet - (k-1);

        // Ajout des clauses en fonction de k
        StringBuilder currentBuilder = new StringBuilder();
        for (int i = 0; i < nbSommet ; i++) {
            StringBuilder nextBuilder = new StringBuilder(currentBuilder);

            //Ajout du sommet  actuel
            nextBuilder.append(i+1).append(" ");
            //Si la taille de la clause a été atteinte
            if (tailleClause > 1)
                recursion(i,tailleClause, nbSommet, nextBuilder, lignes);
            else {
                nextBuilder.append("0");
                lignes.add(nextBuilder.toString());
            }
        }

        Files.write(fichier, lignes);
    }

    public void recursion(int x, int tailleClause, int nbSommet, StringBuilder currentBuilder, ArrayList<String> lignes) {
        tailleClause -= 1;

        for (int i = x+1; i < nbSommet ; i++) {
            StringBuilder nextBuilder = new StringBuilder(currentBuilder);

            //Ajout du sommet  actuel
            nextBuilder.append(i + 1).append(" ");
            //Si la taille de la clause a été atteinte
            if (tailleClause > 1)
                recursion(i,tailleClause, nbSommet,nextBuilder, lignes);
            else {
                nextBuilder.append("0");
                lignes.add(nextBuilder.toString());
            }
        }
    }
}
