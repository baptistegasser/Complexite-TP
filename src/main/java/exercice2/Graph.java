package exercice2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class Graph {
    int[][] matrice;

    public Graph(int[][] matrice) {
        this.matrice = matrice;
    }

    /**
     * Retourne une liste de sommet formant une zone vide maximal
     * @return
     */
    public ArrayList<Integer> zoneVideMaximal() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i<matrice.length;i++) list.add(i);

        return parcours(list);
    }

    /**
     * Retourne une liste de sommet formant une zone vide maximum (methode complète)
     * @return
     */
    public ArrayList<Integer> zoneVideMaximumCom() {
        ArrayList<Integer> list = triSommetByNbedge();
        return parcours(list);
    }

    /**
     * Retourne une liste de sommet formant une zone vide maximum (methode incomplète)
     * @return
     */
    public ArrayList<Integer> zoneVideMaximumInc() {
        ArrayList<Integer> returnList = new ArrayList<>();
        ArrayList<Boolean> listVisited = new ArrayList<>();
        ArrayList<Boolean> listVisitedParcours = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i<matrice.length;i++){
            list.add(i);
            listVisitedParcours.add(true);
            listVisited.add(false);
        }

        for (int sommet = 0; sommet<list.size(); sommet++) {
            ArrayList<Integer> listVoisin = getVoisin(sommet);
            for (int voisin = 0; voisin<listVoisin.size(); voisin++) {
                if (listVisited.get(listVoisin.get(voisin))) {
                    listVisitedParcours.set(sommet, false);
                    break;
                }
                else if (listVisitedParcours.get(listVoisin.get(voisin))) {
                    if (getNbEdge(listVoisin.get(voisin)) < getNbEdge(sommet)) {
                        listVisitedParcours.set(sommet, false);
                        break;
                    }
                }
            }
            if (listVisitedParcours.get(sommet)) {
                listVisited.set(sommet, true);
                listVisitedParcours.set(sommet, false);
            }
        }

        for (int i = 0; i<listVisited.size(); i++) {
            if (listVisited.get(i)) returnList.add(i+1);
        }

        return returnList;
    }

    public ArrayList<Integer> parcours(ArrayList<Integer> list) {
        //Init list de visited
        ArrayList<Boolean> listVisited = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            listVisited.add(true);
        }

        for (Integer i : list) {
            //Si il n'est pas viré
            if (listVisited.get(i)) {
                //On parcours tous ces voisins pour les virer
                ArrayList<Integer> listVoisin = getVoisin(i);
                for (Integer voisin : listVoisin) {
                    listVisited.set(voisin, false);
                }
            }
        }

        //On rempli la liste de sortie
        ArrayList<Integer> returnList = new ArrayList<>();
        for (int i = 0; i<listVisited.size(); i++) {
            if (listVisited.get(i)) returnList.add(i+1);
        }

        return returnList;
    }


    /**
     *  Retourne la liste de sommet triè en fonction du nombre de voisin décroissant
     */
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

        return new ArrayList<>(result.keySet());
    }


    /**
     * Vérifie si la la liste de sommet passée en paramètre est une zone vide
     * @param listSommet
     * @return
     */
    public boolean zoneVide(int[] listSommet) {
        if (listSommet.length == 0) return false; //Si la liste est vide

        for (int sommmet = 0; sommmet<listSommet.length; sommmet++) {
            if (listSommet.length == 1) { // Si on cherche sur un seul sommet
                return !haveEdge(listSommet[sommmet] - 1, listSommet[sommmet] - 1); // On test avec lui même
            } else { // Si on cherche entre plusieurs sommets
                for (int sommetACompare = sommmet+1; sommetACompare < listSommet.length; sommetACompare++) {
                    if (haveEdge(listSommet[sommmet] - 1, listSommet[sommetACompare] - 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Permet de savoir s'il y a un arc entre deux sommets
     * @param i le premier sommet
     * @param j le deuxième sommet
     * @return si il existe un arc
     */
    public boolean haveEdge(int i, int j) {
        return matrice[i][j] == 1;
    }

    /**
     * Retourne tous les sommets voisins d'un somment
     */
    public ArrayList<Integer> getVoisin(int sommet) {
        ArrayList<Integer> listVoisin = new ArrayList<>();
        for (int i =0; i<matrice.length; i++) {
            if (matrice[i][sommet] == 1) listVoisin.add(i);
        }
        return listVoisin;
    }

    /**
     * Retourne le nombre de sommet sortant d'un sommet
     */
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
