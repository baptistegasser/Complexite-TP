package machine_turing_deterministe.turing;

/**
 * A Turing machine simulator
 */
public class TuringMachine {
    /**
     * The description of what this machine does.
     */
    private final String description;

    /**
     * The reserved char used to represent blank word, end of tape...
     */
    private final char blankWord;
    /**
     * The list of valid character supported by this machine.
     */
    private final char[] alphabet;

    /**
     * The list of possible states.
     */
    private final String[] states;
    /**
     * The initial state in which the machine is.
     */
    private final String initialState;
    /**
     * The current state in which the machine is.
     */
    private String currentState;

    /**
     * The rules used to transition from one state to another.
     */
    private final Rule[] rules;

    /**
     * The machine tape.
     */
    private Tape tape;

    public TuringMachine(String description, char blankWord, char[] alphabet, String[] states, String initialState, Rule[] rules) {
        this.description = description;

        this.alphabet = alphabet;
        this.blankWord = blankWord;

        this.states = states;
        this.initialState = initialState;
        this.rules = rules;
    }

    /**
     * Prepare the machine before a run.
     *
     * @param word the word to put on the tape
     */
    public void init(String word) {
        this.currentState = initialState;
        this.tape = new Tape(blankWord, word);
    }

    /**
     * Start the machine.
     * It will run step by step, until the machine can't find a rules to apply.
     */
    public void run() {
        System.out.println("Running: " + this.description + "; with '" + tape.word + "' as entry word.\n");

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
}
