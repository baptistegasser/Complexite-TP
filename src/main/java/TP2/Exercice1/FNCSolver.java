package TP2.Exercice1;

import java.util.ArrayList;

/**
 * Cette classe permet de savoir si une formume FNC est satisfiable
 */
public class FNCSolver {

    int nbTermes;

    /**
     * Liste des clauses, qui contiennent des Pair<numTerme,valeur>
     */
    ArrayList<ArrayList<Pair<Integer, Boolean>>> fncList;

    /**
     * Valeur des termes passé en paramètre dans le .txt
     */
    ArrayList<Boolean> valueOfTerms;

    /**
     * Méthode génerale permettant de traiter une formule FNC : lecture de fichier et résolution
     */
    public void solve () {
        FNCReader fileReader = new FNCReader("C:\\Users\\lucco\\IdeaProjects\\Complexite-TP\\src\\main\\java\\TP2\\Exercice1\\data.cnf");
        fileReader.ReadFile();
        this.fncList = fileReader.getFncList();
        this.nbTermes = fileReader.getNbTermes();
        valueOfTerms = fileReader.getValueOfTerms();
        System.out.println("\nLa FNC est " + isSat());
    }

    /**
     * Vérifie si la FNC est SAT avec les listes de la classe.
     */
    public boolean isSat () {
        // Pour chaque clause
        for (ArrayList<Pair<Integer, Boolean>> currentList: fncList) {
            // On vérifie si au moins une valeur est à vrai
            boolean isClauseGood = false;
            // Pour chaque termes de la clause
            for (Pair<Integer, Boolean> currentPair: currentList) {
                int xindice = currentPair.getKey();
                //Car liste de taille termes - 1
                boolean termValue = valueOfTerms.get(xindice - 1);
                // Dans les deux cas suivant, un terme est à vrai : la clause est validée
                if(currentPair.getValue() && termValue) {
                    isClauseGood = true;
                    break;
                }
                else if(currentPair.getValue() == Boolean.FALSE && !termValue) {
                    isClauseGood = true;
                    break;
                }
            }
            // La clause n'a pas été passé à vrai car tout les termes sont faux : la FNC est fausse
            if(!isClauseGood) return false;
        }
        // On arrive ici si toutes les clauses ont été vraies une fois
        return true;
    }

    public static void main(String[] args)  {
        FNCSolver fncSolver = new FNCSolver();
        fncSolver.solve();
    }
}
