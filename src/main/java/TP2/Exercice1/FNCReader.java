package TP2.Exercice1;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FNCReader {
    private Scanner scanner;
    private String name;
    int nbClauses = 0;
    int nbTermes = 0;
    ArrayList<ArrayList<Pair<Integer, Boolean>>> fncList;

    public FNCReader(String name) {
        this.name = name;
        fncList = new ArrayList<>();
    }

    /**
     * Lecture du fichier donné en paramètre au constructeur
     */
    public void ReadFile () {
        try {
            File myObj = new File(name);
            scanner = new Scanner(myObj);
            fileParams();
            if( nbClauses == 0) {
                System.out.println("Fichier non valide. Merci de revoir la première ligne du fichier");
                return;
            }
            readClauses();
            for (ArrayList list: fncList) {
                System.out.println(list);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Vérifie si a première ligne du fichier contient bien le terme "p cnf"
     * Vérifie également que le nombre de clauses et de termes sont bien indiqués
     */
    public void fileParams() {
        if(scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String [] params = data.split(" ");
            if(params[0].equals("p") || params[1].equals("cnf") ) {
                if(Integer.parseInt(params[2]) > 0 && Integer.parseInt(params[3]) > 0) {
                    System.out.println("Fichier CNF Valide trouvé");
                    nbTermes = Integer.parseInt(params[2]);
                    nbClauses = Integer.parseInt(params[3]);
                    System.out.println("Nombre de termes : " + nbTermes);
                    System.out.println("Nombre de clauses : " + nbClauses);
                }
            }
        }
    }

    /**
     * Ajoute toutes les clauses du fichier à une liste
     */
    public void readClauses() {
        try {
            // On créé le nombre de listes nécessaires ( = nbClauses)
            for (int i = 0; i < nbClauses; i++) {
                fncList.add(new ArrayList<>());
            }

            //Lecture de chaque clauses
            int i = 0;
            while (scanner.hasNextLine()) {
                if(i > nbClauses - 1) throw new UnsupportedOperationException("Trop de clauses dans votre fichier !");
                String data = scanner.nextLine();
                String[] splited = data.split(" ");
                // On traite chaque terme
                for (String str : splited) {
                    // Fin de la clause
                    if (Integer.parseInt(str) == 0) break;
                    Integer value;
                    Boolean currentBool = Boolean.TRUE;
                    // Si le terme est faux
                    if (str.contains("-")) {
                        currentBool = Boolean.FALSE;
                        value = Integer.parseInt(String.valueOf(str.charAt(1)));
                    } else value = Integer.parseInt(str);
                    if(value > nbTermes ) throw new UnsupportedOperationException("Valeur du terme trop élevé");
                    // On l'ajoute à la liste de la clause courrante
                    fncList.get(i).add(new Pair<>(value, currentBool));
                }
                i++;
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Un problème est survenu lors de la construction de la liste de clauses");
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Pair<Integer, Boolean>>> getFncList() {
        return fncList;
    }

    public int getNbTermes() {
        return nbTermes;
    }
}
