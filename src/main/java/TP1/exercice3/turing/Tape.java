package TP1.exercice3.turing;

import java.util.ArrayList;

/**
 * This represent the tape used by a turing machine.
 * It's able to move it's head, read and write at the head position.
 */
public class Tape {
    /**
     * The position of the head on the tape.
     */
    private int head;
    /**
     * The blank character (might be used when trying to read outside bound of the original word as the tape is infinite).
     */
    private final char blankWord;
    /**
     * The tape represented as a list of chars.
     */
    private final ArrayList<Character> characters;

    /**
     * @param blankWord the blank word used when extending the tape
     * @param word the initial value stored on the tape
     */
    public Tape(char blankWord, String word) {
        this.head = 0;
        this.blankWord = blankWord;
        this.characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            this.characters.add(c);
        }
    }

    /**
     * Move the tape's head one case to the right.
     */
    public void moveRight() {
        ++head;
        if (head >= characters.size()) {
            characters.add(blankWord);
        }
    }

    /**
     * Move the tape's head one case to the left.
     */
    public void moveLeft() {
        --head;
        if (head < 0) {
            characters.add(0, blankWord);
        }
    }

    /**
     * Write a character at the head position.
     *
     * @param c the char to write
     */
    public void write(char c) {
        characters.set(head, c);
    }

    /**
     * Read a character at the head position.
     *
     * @return the character at the head position
     */
    public char read() {
        return characters.get(head);
    }
}
