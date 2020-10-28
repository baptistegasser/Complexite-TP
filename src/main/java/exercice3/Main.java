package exercice3;

import exercice3.turing.Parser;
import exercice3.turing.ParserException;
import exercice3.turing.TuringMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.DecimalFormat;
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

        TuringMachine machine;
        try {
            machine = new Parser(filePath).parse();
        } catch (ParserException e) {
            System.err.printf("Failed to parse a Turing machine from file '%s':%n", filePath);
            System.err.println(e.getMessage());
            System.exit(2);
            throw new Exception(); // Hack: throw exception after System.exit() because compiler complain
        }

        System.out.println("Running: " + machine.description + ", with '" + word + "' as entry word.\n");
        long start = System.nanoTime();
        machine.run(word);
        long elapsed = System.nanoTime()-start;
        System.out.printf("The Turing machine ended in %d nanosecond which is ~%sms.%n", elapsed, new DecimalFormat("0.00").format(elapsed/1000000.0));
        System.out.println("The state is '" + machine.getCurrentState() + "', this is " + (machine.isInAcceptedState() ? "" : "not " ) + "an acceptable state.");
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
                "The FILE argument is the path to the definition of a Turing machine (see --example).\n" +
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
        System.out.println("######################################");
        System.out.println("# This is an example of a definition #");
        System.out.println("######################################\n");

        Path path = Path.of(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "), "exercice3", "examples", "binary_odd_even.turing");
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
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
}
