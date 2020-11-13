package TP2.Exercice1;

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
    ArrayList<Boolean> valueOfTerms;

    public FNCReader(String name) {
        this.name = name;
        fncList = new ArrayList<>();
        valueOfTerms  = new ArrayList<>();
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
            System.out.println("Liste des clauses : ");
            for (ArrayList<Pair<Integer, Boolean>> list: fncList) {
                System.out.println(list);
            }

            scanner.close();
            readTermes();
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
                    System.out.println("\nFichier CNF Valide trouvé");
                    nbTermes = Integer.parseInt(params[2]);
                    nbClauses = Integer.parseInt(params[3]);
                    System.out.println("Nombre de termes : " + nbTermes);
                    System.out.println("Nombre de clauses : " + nbClauses + "\n");
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

    /**
     * Permet de lire la valeur des termes à partir du fichier input.txt
     */
    public void readTermes () throws FileNotFoundException {

        // On initialise la valeur de chaque termes à TRUE
        for (int i = 0; i < nbTermes; i++) {
            valueOfTerms.add(Boolean.TRUE);
        }

        // Lecture du fichier
        File myObj = new File("C:\\Users\\lucco\\IdeaProjects\\Complexite-TP\\src\\main\\java\\TP2\\Exercice1\\input.txt");
        Scanner scanner = new Scanner(myObj);
        String data = scanner.nextLine();

        // On sépare chaque entrée
        String [] termsValue = data.split(" ");
        try {
            for (int i = 0; i < nbTermes; i++) {
                // Si le terme contient un "-", on passe de TRUE à FALSE
                if (termsValue[i].contains("-")) {
                    // Si le chiffre donné est faux
                    if (Integer.parseInt(String.valueOf(termsValue[i].charAt(1))) != (i + 1) || termsValue[i].length() > 2) throw new UnsupportedOperationException("Valeur de l'indice éronnée");
                    valueOfTerms.set(i, Boolean.FALSE);
                    continue;
                }
                // Si le chiffre donné est faux
                if(Integer.parseInt(termsValue[i]) != (i+1) ) throw new UnsupportedOperationException("Valeur de l'indice éronnée");
            }
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Une erreur est survenue");
            e.printStackTrace();
            System.exit(0);
        }

        // Affichage des valeurs
        System.out.println("\nValeur des clauses : ");
        for (int i = 0; i < nbTermes; i++) {
            System.out.println("x" + (i+1) + " : " + valueOfTerms.get(i));
        }

    }

    public ArrayList<ArrayList<Pair<Integer, Boolean>>> getFncList() {
        return fncList;
    }

    public int getNbTermes() {
        return nbTermes;
    }

    public ArrayList<Boolean> getValueOfTerms() {
        return valueOfTerms;
    }
}
