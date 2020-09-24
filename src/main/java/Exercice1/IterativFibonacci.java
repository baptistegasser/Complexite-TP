package Exercice1;
import java.math.BigInteger;
import java.util.ArrayList;

public class IterativFibonacci {

    // Valeur de base (pas besoin de les calculer)
    BigInteger F0;
    BigInteger F1;
    // Liste de Fibonacci
    ArrayList<BigInteger> list = new ArrayList<>();
    // Indice cible
    int maxIndice;

    public IterativFibonacci(int maxIndice) {
        F0 = new BigInteger("0");
        F1 = new BigInteger("1");
        this.maxIndice = maxIndice;
    }

    public ArrayList<BigInteger> getFibonacci () {

        list.add(0, F0);
        list.add(1,F1);
        for(int i = 2; i < maxIndice + 1; i++) {
            // Calcul de la valeur (Fn = Fn-1 + fn-2)
            list.add(i, list.get(i-1).add(list.get(i-2)));
        }

        return list;
    }

    public static void main (String[] args) {
        IterativFibonacci iterativFibonacci = new IterativFibonacci(1000);
        ArrayList<BigInteger> result = iterativFibonacci.getFibonacci();
        int i = 0;
        for (BigInteger value : result) {
            System.out.println("F" + i + " = " + value + "\n");
            i++;
        }
    }
}
