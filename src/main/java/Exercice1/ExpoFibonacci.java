package Exercice1;


import java.math.BigInteger;

class ExpoFibonacci {

    public static BigInteger fibo (int n) {
        if (n==0) return new BigInteger("0");
        else if (n == 1) return new BigInteger("1");
        BigInteger fib [][] = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};
        calculate(fib,n-1);
        return fib[0][0];
    }

    static void multiply(BigInteger F[][], BigInteger M[][])
    {
        BigInteger x =   F[0][0].multiply(M[0][0]).add(F[0][1].multiply(M[1][0]));
        BigInteger y =  F[0][0].multiply(M[0][0]).add(F[0][1].multiply(M[1][1]));
        BigInteger z =  F[1][0].multiply(M[0][0]).add(F[1][1].multiply(M[1][0]));
        BigInteger w =   F[1][0].multiply(M[0][1]).add(F[1][1].multiply(M[1][1]));

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    public static void calculate (BigInteger fib [][], int iter) {
        int i;
        BigInteger M[][] = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};

        // n - 1 times multiply the matrix to {{1,0},{0,1}}
        for (i = 2; i <= iter; i++)
            multiply(fib, M);
    }

    public static void main (String[] args) {
        long lStartTime = System.nanoTime();
        
        System.out.println(fibo(15500));
        long lEndTime = System.nanoTime();
        long time = lEndTime - lStartTime;
        System.out.println("Temps d'execution : " + time/1000000 + " ms");
    }
}

