package Exercice1;
import java.math.BigInteger;

public class RecursivFibonacci {

    public static BigInteger fibo (int maxValue, BigInteger F1 , BigInteger F0) {
        // Retourne la valeur de F0 et F1
        if (maxValue == 0) return F0;
        else if (maxValue == 1) return F1;
        else {
            maxValue = maxValue - 1;
        }
        return fibo(maxValue, (F1.add(F0)), F1);
    }

    public static void main (String[] args) {
        long lStartTime = System.nanoTime();
        System.out.println(fibo(16100,new BigInteger ("1"), new BigInteger("0")));
        long lEndTime = System.nanoTime();
        long time = lEndTime - lStartTime;
        System.out.println("Temps d'execution : " + time/1000000 + " ms");
    }
}
