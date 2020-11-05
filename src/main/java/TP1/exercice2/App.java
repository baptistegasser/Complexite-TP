package TP1.exercice2;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        int[][] matriceTest = MatriceTest.matrice3;

        Graph graph = new Graph(matriceTest);

        //graph.show();

        //Question 1
        int[] zoneAChercher = new int[]{1,5};

        if (graph.zoneVide(zoneAChercher)) {
            System.out.println(Arrays.toString(zoneAChercher) + " : Zone vide");
        } else System.out.println(Arrays.toString(zoneAChercher) + " : Zone non-vide");

        //Question 2
        // Valeur pour les test de temps d'exécution
        int nbExe = 500000;

        System.out.println("Une zone vide maximal : "+ graph.zoneVideMaximal());

        long start = System.currentTimeMillis();

        for (int i = 0; i<nbExe;i++) {
            graph.zoneVideMaximal();
        }

        long end = System.currentTimeMillis();

        System.out.println("Temps pour "+nbExe+" itération de Q2 : "+(end-start)+"ms");

        //Question 3

        System.out.println("Une zone vide maximum (Methode complète) : "+ graph.zoneVideMaximumCom());

        long start2 = System.currentTimeMillis();

        for (int i = 0; i<nbExe;i++) {
            graph.zoneVideMaximumCom();
        }

        long end2 = System.currentTimeMillis();
        System.out.println("Temps pour "+nbExe+" itération de Q3 : "+(end2-start2)+"ms");

        //Question 4

        System.out.println("Une zone vide maximum (Methode incomplète) : "+ graph.zoneVideMaximumInc());

        long start3 = System.currentTimeMillis();

        for (int i = 0; i<nbExe;i++) {
            graph.zoneVideMaximumInc();
        }

        long end3 = System.currentTimeMillis();
        System.out.println("Temps pour "+nbExe+" itération de Q4 : "+(end3-start3)+"ms");
    }
}
