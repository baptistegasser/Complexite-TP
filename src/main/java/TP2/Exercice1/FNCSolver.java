package TP2.Exercice1;


import javafx.util.Pair;

import java.io.FileReader;
import java.util.ArrayList;

public class FNCSolver {
    int nbTermes;
    ArrayList<ArrayList<Pair<Integer, Boolean>>> fncList;


    public void solve () {
        FNCReader fileReader = new FNCReader("C:\\Users\\lucco\\IdeaProjects\\Complexite-TP\\src\\main\\java\\TP2\\Exercice1\\data.cnf");
        fileReader.ReadFile();
        this.fncList = fileReader.getFncList();
        this.nbTermes = fileReader.getNbTermes();
    }

    public void termsValues() {

    }


}
