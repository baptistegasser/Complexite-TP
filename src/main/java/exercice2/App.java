package exercice2;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        int[][] matriceTest = MatriceTest.matrice3;

        Graph graph = new Graph(matriceTest);

        //graph.show();

        //Question 1
        int[] zoneAChercher = new int[]{2,4};

        if (graph.zoneVide(zoneAChercher)) {
            System.out.println(Arrays.toString(zoneAChercher) + " : Zone vide");
        } else System.out.println(Arrays.toString(zoneAChercher) + " : Zone non-vide");

        long start = System.currentTimeMillis();

        //Question 2
        System.out.println("Une zone vide maximal : "+ graph.zoneVideMaximal());

        for (int i = 0; i<5000;i++) {
            graph.zoneVideMaximal();
        }

        long end = System.currentTimeMillis();

        System.out.println("Time for Q2 : "+(end-start)+"ms");

        //Question 3

        long start2 = System.currentTimeMillis();

        System.out.println("Une zone vide maximum (Methode complète) : "+ graph.zoneVideMaximumCom());

        for (int i = 0; i<5000;i++) {
            graph.zoneVideMaximumCom();
        }

        long end2 = System.currentTimeMillis();
        System.out.println("Time for Q3 : "+(end2-start2)+"ms");

        //Question 4

        long start3 = System.currentTimeMillis();

        System.out.println("Une zone vide maximum (Methode incomplète) : "+ graph.zoneVideMaximumInc());

        for (int i = 0; i<5000;i++) {
            graph.zoneVideMaximumInc();
        }

        long end3 = System.currentTimeMillis();
        System.out.println("Time for Q4 : "+(end3-start3)+"ms");
    }
}
