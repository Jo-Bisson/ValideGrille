Markdown

# Validation et Transposition de Grilles de Sudoku (Java)

Programme Java développé dans le cadre d'un cours de programmation (INF 1220, Université TÉLUQ) permettant de charger, valider et transposer des grilles de Sudoku 9×9 à partir de fichiers texte.

## Fonctionnalités principales

1. **Chargement de la grille :** Lecture d'un fichier `.txt` situé dans le répertoire `data/` contenant une suite de triplets `xyz` séparés par des espaces (`x` : indice de ligne de 0 à 8, `y` : indice de colonne de 0 à 8, `z` : valeur entière de 1 à 9).

2. **Validation matricielle :** Vérification de l'unicité et de la présence des chiffres de 1 à 9 sur chacune des 9 lignes et 9 colonnes (sans contrainte de sous-grilles 3×3). Lève une exception personnalisée (`GrilleInvalideException`) en cas d'anomalie.

3. **Transposition et exportation :** Calcul et affichage à la console de la matrice transposée, avec option d'enregistrement dans un nouveau fichier texte dans `data/`.

## Structure du projet

```text
.
├── src/
│   └── ValideGrille.java   # Code source principal et gestion des exceptions
├── data/
│   └── partie1.txt         # Fichiers d'entrée et grilles transposées sauvegardées
└── README.md               # Documentation du projet.


## Consignes pour le test du programme

Le fichier Partie1.txt comprend une suite de triplets valides et le programme peut transposer la matrice correctement.

Le fichier Triplets3.txt comprend aussi des triplets valides mais d'une matrice non symétrique et fonctionne correctement.

Le fichiers Triplet4.txt et Triplets5.txt comprend différents types d'erreurs dans la matrice, qui devraient être signalées par le programme avec les éléments erronés.



