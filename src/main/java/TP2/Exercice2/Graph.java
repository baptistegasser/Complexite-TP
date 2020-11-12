package TP2.Exercice2;

public class Graph {
    int[][] matrice;

    public Graph(int[][] matrice) {
        this.matrice = matrice;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public int getNbEdge() {
        int nbEdge = 0;
        for (int i = 0; i<matrice.length;i++) {
            for (int j = i+1; j<matrice.length;j++) {
                if (matrice[i][j] == 1) nbEdge+=1;
            }
        }
        return nbEdge;
    }
}
