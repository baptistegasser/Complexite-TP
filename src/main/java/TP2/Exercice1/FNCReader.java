package TP2.Exercice1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FNCReader {
    private String name;
    int nbClauses = 0;
    int nbTermes = 0;

    public FNCReader(String name) {
        this.name = name;
    }

    public void ReadFile () {
        try {
            File myObj = new File(name);
            Scanner myReader = new Scanner(myObj);
            fileParams(myReader);
            if( nbClauses == 0) {
                System.out.println("Fichier non valide. Merci de revoir la première ligne du fichier");
                return;
            }
            readClauses(myReader);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void fileParams(Scanner scanner) {
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

    public void readClauses(Scanner scanner) {
        while(scanner.hasNextLine()) {
            String data = scanner.nextLine();
            System.out.println(data);
        }
    }
}
