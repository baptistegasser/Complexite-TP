package Exercice1;
import java.math.BigInteger;
import java.util.ArrayList;

public class IterativFibonacci {

    // Valeurs de base (pas besoin de les calculer)
    static BigInteger F0 = new BigInteger("0");
    static BigInteger F1 = new BigInteger("1");
    // Liste de Fibonacci
    static ArrayList<BigInteger> list = new ArrayList<>();

    public static BigInteger getFibonacci (int indice) {
        // Utile seulement lors du calcul de la moyenne de temps de traitement
        list.clear();
        // On ne calcule pas les premiers indices, on retourne directement la valeur
        if(indice == 0) return new BigInteger("0");
        if(indice == 1 || indice == 2) return new BigInteger("1");

        // On les ajoute à la liste F0 et F1 pour les calculs suivants
        list.add(0, F0);
        list.add(1,F1);

        // Boucle pour calculer la valeur de Fn (O(n))
        for(int i = 2; i < indice + 1; i++) {
            // Calcul de la valeur (Fn = Fn-1 + fn-2)
            list.add(i, list.get(i-1).add(list.get(i-2)));
        }
        return list.get(indice);
    }

    public static void main (String[] args) {
        int indice = 50_000;
        long avgTime = 0;
        int nbIter = 50;
        System.out.println("Calcul " + nbIter + " fois pour la " + indice + " eme valeur de la suite de Fibonacci");

        for (int i = 0; i < nbIter; i++) {
            long lStartTime = System.nanoTime();
            getFibonacci(indice);
            long lEndTime = System.nanoTime();
            long time = lEndTime - lStartTime;
            avgTime += time;
        }
        System.out.println("Temps moyen " + ((avgTime/1_000_000)/nbIter) + " ms");
    }
}
