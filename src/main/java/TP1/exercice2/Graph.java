package TP1.exercice2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {
    int[][] matrice;

    public Graph(int[][] matrice) {
        this.matrice = matrice;
    }

    /**
     * Vérifie si la la liste de sommet passée en paramètre est une zone vide
     * @param listSommet liste de sommet à vérifier
     * @return vrai si listSommet est une zone vide, faux sinon
     */
    public boolean zoneVide(int[] listSommet) {
        if (listSommet.length == 0) return false; //Si la liste est vide
        if (listSommet.length == 1) { // Si on cherche sur un seul sommet
            return !haveEdge(listSommet[0] - 1, listSommet[0] - 1); // On test avec lui même
        }

        for (int sommet = 0; sommet<listSommet.length; sommet++) {
            for (int sommetACompare = sommet; sommetACompare < listSommet.length; sommetACompare++) {
                if (haveEdge(listSommet[sommet] - 1, listSommet[sommetACompare] - 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retourne une liste de sommet formant une zone vide maximal
     * @return
     */
    public ArrayList<Integer> zoneVideMaximal() {
        return parcours(null);
    }

    /**
     * Retourne une liste de sommet formant une zone vide maximum (methode complète)
     * @return
     */
    public ArrayList<Integer> zoneVideMaximumCom() {
        ArrayList<Integer> list = (ArrayList<Integer>) triSommetByNbedge();
        return parcours(list);
    }

    /**
     * Parcours la liste de sommets du graph et retourne une zone vide
     * @param list liste à parcourir
     * @return une liste de sommet formant une zone vide
     */
    public ArrayList<Integer> parcours(ArrayList<Integer> list) {
        //Init list de visited
        ArrayList<Integer> returnList = new ArrayList<>();
        ArrayList<Boolean> listVisited = new ArrayList<>();
        //Si la list n'est pas initialisé, on la rempli avec les sommets dans l'ordre croissant
        if (list==null) {
            list = new ArrayList<>();
            for (int i = 0; i<matrice.length;i++){
                listVisited.add(true);
                list.add(i);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                listVisited.add(true);
            }
        }

        //Parcours
        for (Integer sommet : list) {
            //Si il n'est pas marqué
            if (listVisited.get(sommet)) {
                //On parcours tous ces voisins pour les marquer
                ArrayList<Integer> listVoisin = getVoisin(sommet);
                for (Integer voisin : listVoisin) {
                    listVisited.set(voisin, false);
                }
            }
            //Si il n'a pas été marqué (permet des gérer les boucles)
            if (listVisited.get(sommet)) {
                returnList.add(sommet+1);
            }
        }

        return returnList;
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

        //Init
        for (int i = 0; i<matrice.length;i++){
            list.add(i);
            listVisitedParcours.add(true);
            listVisited.add(false);
        }

        for (Integer sommet : list) {
            //On parcours les voisins
            for (Integer voisin : list) {
                if (matrice[sommet][voisin] == 1) {
                    //si il a déjà été pris
                    if (listVisited.get(voisin)) {
                        listVisitedParcours.set(sommet, false);
                        break;
                    } // si on la déjà visité mais pas pris
                    else if (listVisitedParcours.get(voisin)) {
                        //Si mon voisin à moins de voisin que moi
                        if (getNbEdge(voisin) < getNbEdge(sommet)) {
                            listVisitedParcours.set(sommet, false);
                            break;
                        }
                    }
                }
            }

            if (listVisitedParcours.get(sommet)) {
                listVisited.set(sommet, true);
                listVisitedParcours.set(sommet, false);
            }

            //On rempli la liste de retour
            if (listVisited.get(sommet)) {
                returnList.add(sommet+1);
            }
        }

        return returnList;
    }

    /**
     *  Retourne la liste de sommet triè en fonction du nombre de voisin décroissant
     */
    public List<Integer> triSommetByNbedge() {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i<matrice.length;i++) {
            m.put(i,getNbEdge(i));
        }

        Stream<Integer> resultStream = m.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        List<Integer> result = resultStream.collect(Collectors.toList());

        return result;
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
