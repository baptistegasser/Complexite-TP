package exercice2;

public class Graph {
    int[][] matrice;

    public Graph(int[][] matrice) {
        this.matrice = matrice;
    }

    public boolean zoneVide(int[] listSommet) {
        int nbTour = 1;
        if (listSommet.length == 0) return false; //Si la liste est vide

        for (int sommmet = 0; sommmet<listSommet.length; sommmet++) {
            if (listSommet.length == 1) { // Si on cherche sur un seul sommet
                return !haveEdge(listSommet[sommmet] - 1, listSommet[sommmet] - 1); // On test avec lui même
            } else { // Si on cherche entre plusieurs sommets
                for (int sommetACompare = nbTour; sommetACompare < listSommet.length; sommetACompare++) {
                    if (haveEdge(listSommet[sommmet] - 1, listSommet[sommetACompare] - 1)) {
                        return false;
                    }
                }
            }
            nbTour += 1;
        }
        return true;
    }

    public boolean haveEdge(int i, int j) {
        return matrice[i][j] == 1;
    }

    public void show() {
        String res = "[";
        boolean fisrt = true;
        for (int i = 0; i< matrice.length; i++) {
            if (!fisrt) res += " ";fisrt = false;
            for (int y = 0; y< matrice.length; y++) {
                res += " " + matrice[i][y] + " ";
            }
            if (i== matrice.length-1) res += "]";
            res += "\n";
        }
        System.out.println(res);
    }
}
