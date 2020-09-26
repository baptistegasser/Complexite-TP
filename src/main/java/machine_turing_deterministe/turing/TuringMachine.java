package machine_turing_deterministe.turing;

/**
 * A Turing machine representation
 */
public class TuringMachine {
    private final char blankWord;
    private final char[] alphabet;

    private final String[] states;
    private final String initialState;
    private String currentState;

    private final Rule[] rules;

    private Tape tape;

    public TuringMachine(char blankWord, char[] alphabet, String[] states, String initialState, Rule[] rules) {
        this.states = states;
        this.initialState = initialState;
        this.rules = rules;

        this.alphabet = alphabet;
        this.blankWord = blankWord;
    }

    /**
     * Prepare the machine before a run
     *
     * @param word the word to put on the tape
     */
    public void init(String word) {
        this.currentState = initialState;
        this.tape = new Tape(blankWord, word);
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            flag = step();
        }
    }

    /**
     * Run the next step of the machine
     *
     * @return true if the step completed, false if the machine can't move further
     */
    public boolean step() {
        char read = tape.read();

        Rule r = null;
        for (Rule r1 : rules) {
            if (r1.state.equals(currentState) && r1.read == read) {
                r = r1;
                break;
            }
        }

        if (r == null) {
            return false;
        }

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
