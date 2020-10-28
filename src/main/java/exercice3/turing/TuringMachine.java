package exercice3.turing;

import java.util.ArrayList;

/**
 * A Turing machine simulator
 */
public class TuringMachine {
    /**
     * The description of what this machine does.
     */
    public String description;

    /**
     * The reserved char used to represent blank word, end of tape...
     */
    public char blankWord;
    /**
     * The list of valid character supported by this machine.
     */
    public char[] alphabet;

    /**
     * The list of possible states.
     */
    public String[] states;
    /**
     * The initial state in which the machine is.
     */
    public String initialState;
    /**
     * The current state in which the machine is.
     */
    private String currentState;
    /**
     * The list of final states.
     */
    public String[] finalStates;

    /**
     * The rules used to transition from one state to another.
     */
    public ArrayList<Rule> rules;

    /**
     * The machine tape.
     */
    private Tape tape;

    /**
     * Default constructor
     */
    public TuringMachine() {
        this.states = new String[0];
        this.finalStates = new String[0];
        this.alphabet = new char[0];
        this.rules = new ArrayList<>();
    }

    /**
     * Initialize the Turing machine.
     *
     * @param word the word to place on the tape.
     */
    public void init(String word) {
        for (char c : word.toCharArray()) {
            if (!Util.charArrayContain(alphabet, c)) {
                throw new RuntimeException("The input word contain character not supported by the machine alphabet.");
            }
        }

        this.currentState = initialState;
        this.tape = new Tape(blankWord, word);
    }

    /**
     * Initialize and automatically run each {@link #step()} of the machine until it stop.
     * If you want to run step by step manually, call {@link #init} than {@link #step()} by hand.
     *
     * @param word the entry word on the tape
     */
    public void run(String word) {
        init(word);

        boolean flag = true;
        while (flag) {
            flag = step();
        }
    }

    /**
     * Run the next step of the machine.
     *
     * @return true if the step completed, false if the machine can't move further (no rule to apply)
     */
    public boolean step() {
        // read a char
        char read = tape.read();

        // find a rule that match our current state and the char that was read on the tape.
        Rule r = null;
        for (Rule r1 : rules) {
            if (r1.state.equals(currentState) && r1.read == read) {
                r = r1;
                break;
            }
        }

        // if no rule was found return false
        if (r == null) {
            return false;
        }

        // Write a char, move the tape's head and update the current state
        tape.write(r.write);
        if (r.moveDir == Dir.LEFT) {
            tape.moveLeft();
        } else {
            tape.moveRight();
        }
        this.currentState = r.nextState;

        return true;
    }

    /**
     * @return the machine's current state
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * @return true if the current state is a state representing acceptation (a final state)
     */
    public boolean isInAcceptedState() {
        for (String state : finalStates) {
            if (state.equals(currentState)) {
                return true;
            }
        }
        return false;
    }
}
