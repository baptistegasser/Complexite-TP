package Exercice1;

import java.math.BigInteger;
import java.util.*;

class GFG {

    static BigInteger fib(BigInteger n) {
        double phi = (1 + Math.sqrt(5)) / 2;
        double d = Math.round(Math.pow(phi, n.doubleValue()))
                / Math.sqrt(5);
        return new BigInteger(String.valueOf(d));
    }

    // Driver Code
    public static void main(String[] args) {
        BigInteger n = new BigInteger("15500");
        System.out.println(fib(n));
    }
}
