package exercice2;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        int[][] matriceTest = new int[][]{
                {0,1,0,0,0},
                {1,1,0,1,0},
                {0,0,0,0,0},
                {0,1,0,0,0},
                {0,0,0,0,0}};

        Graph graph = new Graph(matriceTest);

        graph.show();

        System.out.println("nbEdge : " + graph.getNbEdge(2-1));

        graph.zoneVideMaximal();

        int[] zoneAChercher = new int[]{1};

        if (graph.zoneVide(zoneAChercher)) {
            System.out.println(Arrays.toString(zoneAChercher) + " : Zone vide");
        } else System.out.println(Arrays.toString(zoneAChercher) + " : Zone non-vide");
    }
}
