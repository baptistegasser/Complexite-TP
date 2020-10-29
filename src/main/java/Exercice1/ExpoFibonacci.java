package Exercice1;


import java.math.BigInteger;
import java.util.Arrays;

class ExpoFibonacci {

    static int maxValue = 20000;
    static BigInteger[] f;

    // Returns n'th fibonacci number using
    // table f[]
    public static BigInteger fib(int n)
    {
        if (n == 0) return new BigInteger("0");
        if (n == 1 || n == 2) return new BigInteger("1");
         /*
        -If n is even then k = n/2:
        F(n) = [2*F(k-1) + F(k)]*F(k)

        If n is odd then k = (n + 1)/2
        F(n) = F(k)*F(k) + F(k-1)*F(k-1)
        */
        int k;
        if(n%2 == 0) {
            k = n/2;
            return ((fib(k-1).multiply(new BigInteger("2"))).add(fib(k))).multiply(fib(k));
        }
        else {
            k = (n+1)/2;
            return ((fib(k).multiply(fib(k))).add(fib(k-1).multiply(fib(k-1))));
        }

    }

    /* Driver program to test above function */
    public static void main(String[] args)
    {
        int n = 1000;
        f= new BigInteger[maxValue];
        long lStartTime = System.nanoTime();

        System.out.println(fib(n));
        long lEndTime = System.nanoTime();
        long time = lEndTime - lStartTime;
        System.out.println("Temps d'execution : " + time/1000000 + " ms");
    }
}

