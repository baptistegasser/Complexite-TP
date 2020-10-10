package exercice2;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        int[][] matriceTest = MatriceTest.matrice2;

        Graph graph = new Graph(matriceTest);

        //graph.show();

        //Question 1
        int[] zoneAChercher = new int[]{2,4};

        if (graph.zoneVide(zoneAChercher)) {
            System.out.println(Arrays.toString(zoneAChercher) + " : Zone vide");
        } else System.out.println(Arrays.toString(zoneAChercher) + " : Zone non-vide");

        //Question 2
        System.out.println("Une zone vide maximal : "+ graph.zoneVideMaximal(false));

        //Question 3 & 4
        System.out.println("Une zone vide maximum : "+ graph.zoneVideMaximal(true));
    }
}
