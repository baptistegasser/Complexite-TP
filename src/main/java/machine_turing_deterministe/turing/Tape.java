package machine_turing_deterministe.turing;

import java.util.ArrayList;

/**
 * This represent the tape used by a turing machine.
 * It's able to move it's head, read and write at the head position
 */
public class Tape {
    private int head;
    private final char blankWord;
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

    public void moveRight() {
        ++head;
        if (head >= characters.size()) {
            characters.add(blankWord);
        }
    }

    public void moveLeft() {
        --head;
        if (head < 0) {
            characters.add(0, blankWord);
        }
    }

    public void write(char c) {
        characters.set(head, c);
    }

    public char read() {
        return characters.get(head);
    }
}
