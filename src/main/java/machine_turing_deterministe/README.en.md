# Turing machine simulator
This package contains a simple Turing machine simulator.

Read in another language : [English](README.en.md), [Français](README.md).

## Warning
This simulator **does not do any checks** on the Turing machine's definition.

It is therefore possible to give the definition of a machine that will nether stop, and the simulator won't stop either.
It's also possible to pass an invalid definition (ie : a rule to pass to a state not defined in the states list).

## Utilisation example
Run the machine with `java Main /path/to/definition.json entry_word`

Display the help message with `java Main --help`

Display an example of a json definition with `java Main --example`

## The definition file
The file containing the machine's definition must be written in json.

The format is as follows :
- **description** : what this machine does
- **alphabet** : characters recognized by this machine
- **blankWord** : the blank word
- **states** : the list of states recognized by this machine
- **initialState** : the initial state in which the machine is
- **rules** : the list of transitions rules defining how to pass from one state to another
    - **state** : the state in which the machine must be to apply this rule
    - **read** : the char to read on the tape to apply this rule
    - **write** : the char to write on the tape
    - **nextState** : the next state in which the machine will be
    - **moveDir** : the direction in which the tape's head must move (only `LEFT` or `RIGHT`)

Example of a definition (with rules list truncated) :
```json
{
  "description": "My Turing machine",
  "alphabet": [
    "a",
    "b"
  ],
  "blankWord": " ",
  "states": [
    "1",
    "2"
  ],
  "initialState": "1",
  "rules": [
    {
      "state": "1",
      "read": "a",
      "write": "b",
      "nextState": "2",
      "moveDir": "RIGHT"
    }
  // [...]
  ]
}
```
