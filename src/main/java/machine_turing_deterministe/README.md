# Simulateur de machine de turing
Ce packet contient un simulateur simple de Machines de Turing.

Lire dans un autre langage : [Français](README.md), [English](README.en.md).

## Avertissement
Ce simulateur n'effectue **aucune vérification sur la definition** de la machine de Turing fournis.

Ainsi, si l'on passe une définition créant une machine qui ne s'arrête jamais, le simulateur ne s'arrêtera pas non plus,
on peut également passé des définitions invalides (ex : règle pour passé à un état non définis dans la liste des états possible)

## Example d'utilisation du programme
On exécutera notre machine avec la commande `java Main /chemin/vers/definition.json mot_entre`

On pourra afficher l'aide avec la commande `java Main --help`

On pourra afficher un example de configuration avec la commande `java Main --example`

## Le fichier de définition
Le fichier contentant la définition d'une machine de turing est au format json.

Le format est le suivant :
- **description** : ce que fait cette machine
- **alphabet** : les characters reconnus par cette machine
- **blankWord** : le mot blanc
- **states** : la liste des états reconnus par cette machine
- **initialState** : l'état initial dans lequel se trouve la machine
- **rules** : la liste des règles de transitions permettant le passage d'un état à l'autre
    - **state** : l'état dans lequel doit être la machine pour appliquer cette règle
    - **read** : le character à lire sur le ruban pour appliquer cette règle
    - **write** : le character à écrire sur le ruban
    - **nextState** : le prochain état où la machine se trouvera
    - **moveDir** : la direction dans laquelle la tête du ruban doit allez (uniquement `LEFT` ou `RIGHT`)

Example de définition (avec liste de règles tronquée) :
```json
{
  "description": "Ma machine de Turing",
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
