package TP1.Exercice1;
import java.math.BigInteger;

public class RecursivFibonacci {

    // Compteur pour le nombre d'appels à fibo()
    static long countFiboCall = 0;

    public static BigInteger fibo (int indice) {
        countFiboCall++;

        if(indice==0) return new BigInteger("0");
        if(indice ==1) return new BigInteger("1");

        // récursivité
        return fibo(indice-1).add(fibo(indice-2));
    }

    public static void main (String[] args) {
        // Vide la mémoire pour éviter un StackOverFlow du à la récursivité.
        System.gc();
        int indice = 40;
        long avgTime = 0;
        int nbIter = 1;
        System.out.println("Calcul " + nbIter + " fois pour la " + indice + " eme valeur de la suite de Fibonacci");

        for (int i = 0; i < nbIter; i++) {
            long lStartTime = System.nanoTime();

            fibo(indice);

            long lEndTime = System.nanoTime();
            long time = lEndTime - lStartTime;
            avgTime += time;
        }
        System.out.println(countFiboCall + " appels à fibo() effectués");
        System.out.println("Temps moyen " + ((avgTime/1_000_000)/nbIter) + " ms");

    }
}
