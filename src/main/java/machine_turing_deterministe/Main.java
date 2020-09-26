package machine_turing_deterministe;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import machine_turing_deterministe.turing.TuringMachine;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        for (String s : args) {
            if (s.equals("--help")) {
                showUsage();
                System.exit(0);
            }
        }
        if (args.length != 2) {
            showUsage();
            System.exit(1);
        }

        final String pathToFile = args[0];
        final String word = args[1];

        TuringMachine turingMachine = null;
        try (FileReader fr = new FileReader(pathToFile)) {
            turingMachine = new Gson().fromJson(fr, TuringMachine.class);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to find the give file");
            System.exit(2);
        } catch (JsonParseException e) {
            System.err.println("Failed to parse the json file");
            System.exit(2);
        }

        System.out.println("Initializing Turing Machine");
        turingMachine.init(word);
        System.out.println("Running");
        turingMachine.run();
        System.out.printf("The final state is '%s'%n", turingMachine.getCurrentState());
    }

    private static void showUsage() {
        final String usage = "Usage: java Main FILE WORD\n" +
                "Simulate a Turing machine on a given word and display the final state achieved.\n" +
                "\n" +
                "Options:\n" +
                " --help     display this help and exit\n" +
                "\n" +
                "Arguments:\n" +
                "The FILE argument is the path to a json containing the definition of a Turing machine.\n" +
                "The WORD argument is the word to feed to the Turing machine.\n" +
                "\n" +
                "Exit status:\n" +
                " 0  if OK,\n" +
                " 1  if minor problems (e.g., malformed arguments),\n" +
                " 2  if serious trouble (e.g., cannot parse the json).\n";
        System.out.println(usage);
    }
}
