package exercice3.turing;

/**
 * Representation of a transition rule used by the turing machine.
 */
public class Rule {
    public final String state;
    public final char read;
    public final char write;
    public final String nextState;
    public final Dir moveDir;

    public Rule(String state, char read, char write, String nextState, Dir moveDir) {
        this.state = state;
        this.read = read;
        this.write = write;
        this.nextState = nextState;
        this.moveDir = moveDir;
    }
}
