package Exercice1;
import java.math.BigInteger;

class MatrixFibonacci
{
    // Nombre d'appels à power
    static long countFiboCall = 0;

    // Matrice à élever ^n
    static BigInteger[][] Multiply = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};

    static BigInteger fibo(int n)
    {
        // Matrice initiale de Fibonnaci
        BigInteger[][] Fib = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},{new BigInteger("1"),new BigInteger("0")}};

        // Pour les valeurs de base (f0,f1,f2) on retourne simplement les valeurs
        if (n == 0) return new BigInteger("0");
        if(n == 1 || n == 2) return new BigInteger("1");

        // Calcul de la valeur
        // n - 1 fois car on récupère ensuite la valeur Fn+1 ; on économise ainsi un calcul
        power(Fib, n-1);

        /*
        On retourne la valeur d'indice 0,0 car nous avons :
        | Fn + 1 ; Fn |
        | Fn ; Fn + 1 |
        Si nous avions passé n en paramètre à power, il aurait fallu récupérer [0][1]
         */

        return Fib[0][0];
    }



    static void power(BigInteger[][] Fib, int n)
    {
        countFiboCall++;
        // Fin de la récursivité
        if (n == 1) return;

        /*
        |1 ; 1|^n = | Fn + 1 ; Fn | ; n étant l'indice souhaité
        |1 ; 0|     |Fn ; Fn + 1 |
         */

        power(Fib, n/2);
        // M^n = M^n/2 * M^n/2
        multiplyMatrix(Fib, Fib);

        // Dans le cas où n n'est pas pair, on multiplie par la matrice  [1,1][1,0]
        if (n%2 != 0) {
            multiplyMatrix(Fib, Multiply);
        }
    }

    static void multiplyMatrix(BigInteger[][] Fib, BigInteger[][] M)
    {
        /*
        Multiplication de deux matrices
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

    public static void main (String args[])
    {
        int indice = 50_000;
        long avgTime = 0;
        int nbIter = 1;
        System.out.println("Calcul " + nbIter + " fois pour la " + indice + " eme valeur de la suite de Fibonacci");

        for (int i = 0; i < nbIter; i++) {
            long lStartTime = System.nanoTime();

            // Indice souhaité
            System.out.println(fibo(indice));

            long lEndTime = System.nanoTime();
            long time = lEndTime - lStartTime;
            avgTime += time;
        }
        System.out.println(countFiboCall + " appels à power()");
        System.out.println("Temps moyen " + ((avgTime/1_000_000)/nbIter) + " ms");

    }
}


