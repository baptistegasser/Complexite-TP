package TP2.Exercice1;

/**
 * Création d'une classe pair, contenant deux objets
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {

    /**
     * Objets contenus dans la Pair
     */
    private final K element0;
    private final V element1;

    /**
     * Créé une pair
     * @param element0 Element 0
     * @param element1 Element 1
     * @param <K> Type de l'élement 0
     * @param <V> Type de l'élement 1
     * @return la nouvelle Pair
     */
    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    /**
     * Constructeur avec les deux élements
     */
    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    /**
     * Récupère la clé
     * @return la première valeur
     */
    public K getKey() {
        return element0;
    }

    /**
     * Récupère la valeur
     * @return la seconde valeur
     */
    public V getValue() {
        return element1;
    }

    /**
     * Affichage de l'objet
     * @return element0=element1
     */
    @Override
    public String toString() {
        return element0 + "=" + element1;
    }
}