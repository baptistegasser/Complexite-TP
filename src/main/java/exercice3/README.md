# Simulateur de machine de Turing
Ce packet contient un simulateur simple de Machines de Turing.

Lire dans un autre langage : [Français](README.md), [English](README.en.md).


## Avertissement
Le simulateur n'offre pas de garantie sur la finition ou non d'une machine, si on passe la définition d'une machine bouclant
à l'infinie, le simulateur ne s'arrêtera pas de lui-même.


## Compilation
Un fichier make permet de facilement compiler le programme.
- `make compile` pour compiler les fichiers sources.
- `make jar` pour compiler les fichiers sources et créer un jar nommer `Simulator.jar`.
- `make clean` pour nettoyer les fichiers crées lors de la compilation.


## Utilisation
Le simulateur prend en paramètres deux options `/chemin/vers/def.turing` qui est le chemin vers le fichier contenant la
definition de la machine et `mot_entre` qui sera le mot lu par cette machine. 

On peut lancer notre programme de 2 manières.

**Avec le jar**
```shell
java -jar Simulator.jar /chemin/vers/def.turing mot_entre
```

**En exécutant les fichiers .class**
```shell
# Dans src/main/java/exercice3
java -cp .. exercice3.Main /chemin/vers/def.turing mot_entre

# Dans src/main/java/exercice3
java exercice3.Main /chemin/vers/def.turing mot_entre
```


## Le format du fichier
Le fichier contentant la définition d'une machine de Turing est dans un format créer spécialement pour notre programme, on
pourra utiliser l'extension `.turing` pour ces fichiers même si cela n'affecte en rien le programme.

Le format supporte le saut de ligne et les lignes commençant par `#` comme commentaires.

L'ordre des paramètres est important, en effet il faut définir l'alphabet afin de valider les autres paramètres utilisant
un caractère qui devra donc être supporter par la machine.

De même on devra définir la liste des états valide en premier afin de valide les autres paramètres utilisant un état.

Les différents paramètres sont les suivant :
- **description**: ce que fait cette machine
- **alphabet**: liste des caractères reconnus par la machine, séparés par des virgules
- **blank**: le caractère blanc, doit faire partie de l'alphabet
- **states**: liste d'états possibles séparés par des virgules
- **init_state**: l'état initial de la machine (doit être un état possible)
- **final_states**: liste des états considérés comme finaux séparés par des virgules (doivent être un état possible)
- multiple lignes définissant les règles de transitions au format `q0 r w q1 Dir` avec
    - `q0` est l'état dans lequel on peut appliquer cette règle
    - `r` est le caractère à lire sur le ruban pour appliquer cette règle
    - `w` est le caractère à écrire sur le ruban
    - `q1` est le nouvel état de la machine
    - `Dir` est la direction dans laquelle bouge la tête de lecture du ruban, `R` ou `L` uniquement.

Le dossier [examples](examples) contient quelques example de définition de machines valides.
