package TP2.Exercice1;

import java.util.ArrayList;

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
        valueOfTerms = fileReader.getValueOfTerms();
        System.out.println("\nLa FNC est " + isSat());
    }

    /**
     * Vérifie si la FNC est SAT avec les listes données.
     */
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

    public static void main(String[] args)  {
        FNCSolver fncSolver = new FNCSolver();
        fncSolver.solve();
    }
}
