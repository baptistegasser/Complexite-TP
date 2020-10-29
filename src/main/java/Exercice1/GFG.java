package Exercice1;
import java.math.BigInteger;

class GFG
{
    static BigInteger fibo(int n)
    {
        // Matrice initiale 
        BigInteger[][] Fib = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};
        if (n == 0) return new BigInteger("0");
        if(n == 1 || n == 2) return new BigInteger("1");

        // Calcul de la valeur
        power(Fib, n-1);

        /*
        On retourne la valeur d'indice 0,0 car nous avons :
        | Fn + 1 ; Fn |
        | Fn ; Fn + 1 |
         */

        return Fib[0][0];
    }

    static void multiplyMatrix(BigInteger Fib[][], BigInteger M[][])
    {
        /*
        Multiplication de la matrice Fib et M
         */
        BigInteger fn11 =  Fib[0][0].multiply(M[0][0]).add(Fib[0][1].multiply(M[1][0]));
        BigInteger fn1 =  Fib[0][0].multiply(M[0][1]).add(Fib[0][1].multiply(M[1][1]));
        BigInteger fn2 =  Fib[1][0].multiply(M[0][0]).add(Fib[1][1].multiply(M[1][0]));
        BigInteger fn12 =  Fib[1][0].multiply(M[0][1]).add(Fib[1][1].multiply(M[1][1]));

        // Mise à jour de la matrice de fibonacci, fn11 et fn12 étant fn+1 (les valeurs qui nous intéressent)
        Fib[0][0] = fn11;
        Fib[0][1] = fn1;
        Fib[1][0] = fn2;
        Fib[1][1] = fn12;
    }

    static void power(BigInteger F[][], int n)
    {

        BigInteger[][] Multiply = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};

        power(F, n/2);
        multiplyMatrix(F, F);

        if (n%2 != 0) multiplyMatrix(F, Multiply);
    }

    public static void main (String args[])
    {
        System.gc();
        long lStartTime = System.nanoTime();

        int n = 8;
        System.out.println(fibo(n));

        long lEndTime = System.nanoTime();
        long time = lEndTime - lStartTime;
        System.out.println("Temps d'execution : " + time/1000000 + " ms");
    }
}


