package exercice2;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        int[][] matriceTest = MatriceTest.matrice2;

        Graph graph = new Graph(matriceTest);

        graph.show();

        System.out.println("nbEdge : " + graph.getVoisin(3));


        //Question 1
        int[] zoneAChercher = new int[]{1};

        if (graph.zoneVide(zoneAChercher)) {
            System.out.println(Arrays.toString(zoneAChercher) + " : Zone vide");
        } else System.out.println(Arrays.toString(zoneAChercher) + " : Zone non-vide");

        //Question 2
        System.out.println("Zone vide opt : "+ graph.zoneVideMaximal());
    }
}
