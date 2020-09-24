package exercice2;

import java.nio.charset.IllegalCharsetNameException;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    int[][] matrice;

    public Graph(int[][] matrice) {
        this.matrice = matrice;
    }

    public void zoneVideMaximal() {
        ArrayList<Integer> listTrie = triSommetByNbedge();

        ArrayList<Boolean> listVisited = new ArrayList<>();
    }


    // Retourne la liste de sommet triè en fonction du nombre de voisin décroissant
    public ArrayList<Integer> triSommetByNbedge() {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i<matrice.length;i++) {
            m.put(i,getNbEdge(i));
        }
        Map<Integer, Integer> result = m.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return new ArrayList<>(result.values());
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

    public ArrayList<Integer> getVoisin(int sommet) {
        ArrayList<Integer> listVoisin = new ArrayList<>();
        for (int i =0; i<matrice.length; i++) {
            if (matrice[i][sommet] == 1) listVoisin.add(i);
        }
        return listVoisin;
    }

    public int getNbEdge(int sommet) {
        int nbEdge = 0;
        for (int i = 0; i<matrice.length;i++) {
            nbEdge += matrice[i][sommet];
        }
        return nbEdge;
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
