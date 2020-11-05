package TP1.exercice3.turing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class charged to parse a file containing a Turing machine definition.
 */
public class Parser {
    private final String filePath;
    private TuringMachine machine = null;
    private int currentLine;

    /**
     * @param filePath the path to the file containing the definition
     */
    public Parser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Parse and check the definition from the file pointed by {@link #filePath}.
     *
     * @return the turing machine instance matching the definition
     * @throws ParserException if the definition is invalid / malformed
     */
    public TuringMachine parse() throws ParserException {
        if (machine != null) {
            return machine;
        }

        machine = new TuringMachine();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            this.currentLine = 1;
            while ((line = br.readLine()) != null) {
                parseLine(line);
                currentLine++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to find the given file");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Failed to read the given file");
            System.exit(2);
        }

        return machine;
    }

    /**
     * Method called on every line to redirect to the correct parsing method depending one the line.
     *
     * @param line the line to parse
     */
    private void parseLine(String line) {
        // Ignore comments and line jumps
        if (line.startsWith("#") || line.isBlank()) {
            return;
        }

        if (line.startsWith("description:")) {
            parseDescription(line);
        } else if (line.startsWith("states:")) {
            parseStates(line);
        } else if (line.startsWith("init_state:")) {
            parseInitState(line);
        } else if (line.startsWith("final_states:")) {
            parseFinalStates(line);
        } else if (line.startsWith("alphabet:")) {
            parseAlphabet(line);
        } else if (line.startsWith("blank:")) {
            parseBlank(line);
        } else {
            parseTransitionRule(line);
        }
    }

    /**
     * Parse a line containing the machine description.
     *
     * @param line the line to parse
     */
    private void parseDescription(String line) {
        // Clean the line
        String description = line.replace("description:", "");
        if (description.startsWith(" ")) {
            description = description.replaceFirst(" ", "");
        }
        this.machine.description = description;
    }


    /**
     * Parse a line containing the machine valid states.
     *
     * @param line the line to parse
     */
    private void parseStates(String line) {
        // Remove prefix and blank chars
        String states = line.replace("states:", "").replaceAll("\\s", "");
        this.machine.states = states.split(",");
    }

    /**
     * Parse a line containing the machine initial state.
     *
     * @param line the line to parse
     */
    private void parseInitState(String line) {
        String initState = line.replace("init_state:", "").replaceAll("\\s", "");

        if (!Util.arrayContain(machine.states, initState)) {
            throwParseException("Initial state is not in the states list or defined before the states list");
        } else {
            this.machine.initialState = initState;
        }
    }

    /**
     * Parse a line containing the machine final states.
     *
     * @param line the line to parse
     */
    private void parseFinalStates(String line) {
        // Remove prefix and blank chars
        String statesStr = line.replace("final_states:", "").replaceAll("\\s", "");
        String[] finalStates = statesStr.split(",");

        if (machine.states.length == 0) {
            throwParseException("Can't define final states before states list");
        } else if (finalStates.length == 0) {
            throwParseException("No final state defined.");
        }

        for (String s : finalStates) {
            if (!Util.arrayContain(machine.states, s)) {
                throwParseException(String.format("Final state '%s' is not defined in the state list", s));
            }
        }

        this.machine.finalStates = finalStates;
    }

    /**
     * Parse a line containing the machine recognized alphabet.
     *
     * @param line the line to parse
     */
    private void parseAlphabet(String line) {
        String clean = line.replace("alphabet:", "").replaceAll("\\s", "");
        String[] alphabetStrings = clean.split(",");

        char[] alphabet = new char[alphabetStrings.length];
        int i = 0;
        for (String s : alphabetStrings) {
            if (s.length() == 0) {
                alphabet[i] = ' ';
            } else if (s.length() == 1) {
                alphabet[i] = s.toCharArray()[0];
            } else {
                throwParseException("Alphabet can contain char only, not strings.");
            }
            i++;
        }
        this.machine.alphabet = alphabet;
    }

    /**
     * Parse a line containing the machine blank word.
     *
     * @param line the line to parse
     */
    private void parseBlank(String line) {
        String clean = line.replace("blank:", "").replaceAll("\\s", "");
        char blank = ' ';
        switch (clean.length()) {
            case 0:
                blank = ' ';
                break;
            case 1:
                blank = clean.toCharArray()[0];
                break;
            default:
                throwParseException("Blank must be a char, not a string.");
        }

        if (!Util.charArrayContain(machine.alphabet, blank)) {
            throwParseException("The blank word must be in the alphabet of the machine.");
        }

        this.machine.blankWord = blank;
    }

    /**
     * Parse a transition rule with the following format: "state r w nextState, R|L"
     * where 'a' and 'w' are respectively the char to read and the char to write.
     *
     * @param line the line to parse
     */
    private void parseTransitionRule(String line) {
        String[] split = line.split(" ");

        if (split.length != 5) {
            throwParseException("Invalid number of argument for rule.");
        }
        if (this.machine.states.length == 0) {
            throwParseException("Can't defined a rule before the states list");
        }

        String state = split[0];
        if (!Util.arrayContain(machine.states, state)) {
            throwParseException("The rule's state is not a valid state.");
        }

        char read = ' ';
        int l = split[1].length();
        if (l == 1) {
            read = split[1].toCharArray()[0];
        } else if (l > 1) {
            throwParseException("The rule char to read should be a char not a string.");
        }
        if (!Util.charArrayContain(machine.alphabet, read)) {
            throwParseException("Rule defined an invalid char to read.");
        }

        char write = ' ';
        l = split[2].length();
        if (l == 1) {
            write = split[2].toCharArray()[0];
        } else if (l > 1) {
            throwParseException("The rule char to read should be a char not a string.");
        }
        if (!Util.charArrayContain(machine.alphabet, write)) {
            throwParseException("Rule defined an invalid char to write.");
        }

        String nextState = split[3];
        if (!Util.arrayContain(machine.states, nextState)) {
            throwParseException("The rule's next state is not a valid state.");
        }

        Dir dir = null;
        switch (split[4]) {
            case "R":
                dir = Dir.RIGHT;
                break;
            case "L":
                dir = Dir.LEFT;
                break;
            default:
                throwParseException("Invalid rule direction, must be 'R' or 'L'.");
        }

        this.machine.rules.add(new Rule(state, read, write, nextState, dir));
    }

    /**
     * utility function that build exception by automatically passing the current line the parser is
     * reading.
     * Allow for cleaner exception throwing.
     *
     * @param message the exception's message
     */
    private void throwParseException(String message) throws ParserException {
        throw new ParserException(currentLine, message);
    }
}
