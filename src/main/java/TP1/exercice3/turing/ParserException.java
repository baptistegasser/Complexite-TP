package TP1.exercice3.turing;

/**
 * Class representing an error during parsing
 */
public class ParserException extends RuntimeException {
    public ParserException(int line, String message) {
        super(String.format("At line %d: %s", line, message));
    }
}
