package TP2.Exercice1;


import javafx.util.Pair;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FNCSolver {
    int nbTermes;
    ArrayList<ArrayList<Pair<Integer, Boolean>>> fncList;
    ArrayList<Boolean> valueOfTerms = new ArrayList<>();


    /**
     * Méthode qui permet de dire si la FNC est SAT
     */
    public void solve () {
        FNCReader fileReader = new FNCReader("C:\\Users\\lucco\\IdeaProjects\\Complexite-TP\\src\\main\\java\\TP2\\Exercice1\\data.cnf");
        fileReader.ReadFile();
        this.fncList = fileReader.getFncList();
        this.nbTermes = fileReader.getNbTermes();
        termsValues();
        System.out.println("La FNC est " + isSat());
    }

    /**
     * Permet de créer la liste des valeurs des termes (vrai ou faux)
     */
    public void termsValues() {
        // On initialise tout à faux
        for (int i = 0; i < nbTermes; i++) {
            valueOfTerms.add(Boolean.FALSE);
        }
        // On récupère la valeur pour chaque terme de l'utilisateur
        for (int i = 0; i < nbTermes; i++) {
            int val = 2;
            // Tant que l'utilisateur n'a pas dit si xi était vrai ou faux, on demande
            while(val != 1 && val != 0) {
                System.out.println("Entrez la valeur (0 ou 1) pour x" + (i + 1));
                Scanner scan = new Scanner(System.in);
                val = scan.nextInt();
                if(val != 1 && val != 0) System.out.println("Erreur, valeur 1 ou 0 requise");
            }
            // On change la valeur à vrai
            if(val == 1) valueOfTerms.set(i, Boolean.TRUE);
        }
        // Affichage des valeurs
        for (int i = 0; i < nbTermes; i++) {
            System.out.println("Valeur de x" + (i+1) + " :" + valueOfTerms.get(i));
        }
    }

    // Vérifie si la FNC est SAT
    public boolean isSat () {
        // Pour chaque clause
        for (ArrayList<Pair<Integer, Boolean>> currentList: fncList) {
            // On vérifie si au moins une valeur est à vrai
            boolean isClauseGood = false;
            for (Pair<Integer, Boolean> currentPair: currentList) {
                int xindice = currentPair.getKey();
                //car list de taille termes - 1
                boolean termValue = valueOfTerms.get(xindice - 1);
                // Dans les deux cas suivant, une clause est à vrai : la clause est validée
                if(currentPair.getValue() && termValue) {
                    isClauseGood = true;
                    break;
                }
                else if(currentPair.getValue() == Boolean.FALSE && !termValue) {
                    isClauseGood = true;
                    break;
                }
            }
            // La clause n'a pas été passé à vrai : return false
            if(!isClauseGood) return false;
        }
        return true;
    }


}
