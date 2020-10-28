package machine_turing_deterministe;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import machine_turing_deterministe.turing.TuringMachine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class charge to load a Turing machine definition and run it.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // show program usage and exit
        if (Arrays.asList(args).contains("--help")) {
            displayUsage();
            System.exit(0);
        }

        // show example definition and exit
        if (Arrays.asList(args).contains("--example")) {
            displayExampleDefinition();
            System.exit(0);
        }

        // if not enough args, exit
        if (args.length != 2) {
            displayUsage();
            System.exit(1);
        }

        final String filePath = args[0];
        final String word = args[1];
        TuringMachine turingMachine = parseFile(filePath);

        displayWarning();

        turingMachine.init(word);
        turingMachine.run();
        System.out.printf("The final state is '%s'%n", turingMachine.getCurrentState());
    }

    /**
     * Parse a turing machine from a json file containing it's definition.
     *
     * @param filePath the path to the file
     * @return a turing machine instance matching the json definition
     */
    private static TuringMachine parseFile(String filePath) {
        TuringMachine turingMachine = null;

        try (FileReader fr = new FileReader(filePath)) {
            turingMachine = new Gson().fromJson(fr, TuringMachine.class);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to find the given file");
            System.exit(2);
        } catch (JsonParseException e) {
            System.err.println("Failed to parse the json from the given file");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Failed to read the given file");
            System.exit(2);
        }

        return turingMachine;
    }

    /**
     * Display how to use this program.
     */
    private static void displayUsage() {
        final String usage = "Usage: java Main FILE WORD\n" +
                "Simulate a Turing machine on a given word and display the final state achieved.\n" +
                "\n" +
                "Options:\n" +
                " --help        display this help and exit\n" +
                " --example     display an example machine's definition and exit\n" +
                "\n" +
                "Arguments:\n" +
                "The FILE argument is the path to the definition of a Turing machine stored as json (see --example).\n" +
                "The WORD argument is the word to feed to the Turing machine.\n" +
                "\n" +
                "Exit status:\n" +
                " 0  if OK,\n" +
                " 1  if minor problems (e.g., malformed arguments),\n" +
                " 2  if serious trouble (e.g., cannot parse the json).\n";
        System.out.println(usage);
    }

    /**
     * Display a file containing an exemple Turing machine definition.
     */
    private static void displayExampleDefinition() {
        try (BufferedReader br = new BufferedReader(new FileReader("example.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the example file.");
            e.printStackTrace();
            System.exit(3);
        }
    }

    /**
     * Display a warning.
     */
    private static void displayWarning() {
        System.out.println("~~ Warning ~~");
        System.out.println("This Turing machine simulator is distributed as is.");
        System.out.println("Their is no check and so no guaranty of the simulator stopping or working in case of a");
        System.out.println("bad/invalid definition file");
        System.out.println("~~ End Warning ~~");
    }
}
