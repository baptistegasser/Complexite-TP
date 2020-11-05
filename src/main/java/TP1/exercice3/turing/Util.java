package TP1.exercice3.turing;

/**
 * Class containing utility functions
 */
public class Util {
    /**
     * Simple function to test if an array contain an element.
     * Complexity of O(n) at worse (where n is the length of array)
     *
     * @param array the array to test
     * @param e the element searched
     * @param <T> generic type to test on any kind of array
     * @return true if e is in the array false otherwise
     */
    public static  <T> boolean arrayContain(T[] array, T e) {
        for (T e1 : array) {
            if (e1.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simple function to test if a char array contain an element.
     * @see #arrayContain(Object[], Object)
     */
    public static boolean charArrayContain(char[] array, char e) {
        for (char e1 : array) {
            if (e1 == e) {
                return true;
            }
        }
        return false;
    }
}
