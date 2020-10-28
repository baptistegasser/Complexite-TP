# Turing machine simulator
This package contains a simple Turing machine simulator.

Read in another language : [English](README.en.md), [Français](README.md).

## Warning
This simulator offer no guaranty that a given machine will stop or not, if we feed it a definition that will loop forether,
the simulator will not stop by himself.


## Compilation
A make file allow to easily compile the program.
- `make compile` to compile source file.
- `make jar` to compiler source file and create a jar named `Simulator.jar`.
- `make clean` to clean file created during compilation.


## Usage
The simulator take in entry two arguments `/path/to/file` which is the path to the file containing the definition of our
machine and `entry_word` which will be the word read by the machine.

The program can be run with two command.

**With the jar**
```shell
java -jar Simulator.jar /chemin/vers/def.turing mot_entre
```

**Running the .class files**
```shell
# Inside src/main/java/exercice3
java -cp .. exercice3.Main /chemin/vers/def.turing mot_entre

# Inside src/main/java/exercice3
java exercice3.Main /chemin/vers/def.turing mot_entre
```


## Le format du fichier
The file containing the definition of a Turing machine use a format created specially for the simulator, we can name the 
files using this format with the extension `.turing` but it won't affect the simulator if we don't.

The format support skipping line en comments line starting with `#`.

The order of the parameters is important, the alphabet and valid state lists must be declared before any other parameters 
using a char or state, so they can be checked, preventing definition with rule concerning invalid states in example.

The parameters are as follow :
- **description**: what this machine does
- **alphabet**: list of char recognized by this machine, separated by commas
- **blank**: the blank char, must be part of the alphabet
- **states**: list of possible states, separated by commas
- **init_state**: the initial state of the machine, must be a valid state
- **final_states**: list of states considered as final/acceptable, must be valid states
- multiple lines defining transitions rules like `q0 r w q1 Dir` with
    - `q0` the machine's state in which the rule can be applied
    - `r` the char to read on the tape to apply this rule
    - `w` the char to write on the tape
    - `q1` the new machine's state
    - `Dir` the direction in which the tape's head must move, `R` or `L` only.

The folder [examples](examples) contain some example of valid machines definitions.
