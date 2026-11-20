import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ValideGrille {

    // Dossier contenant les fichiers textes
    private static final String DOSSIER_DATA = "data";

    // Exception interne pour éviter tout conflit de nom
    public static class GrilleInvalideException extends Exception {
        public GrilleInvalideException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        int[][] grille = new int[9][9];
        Scanner clavier = new Scanner(System.in);

        // 1. DEMANDE DU NOM DU FICHIER SOURCE
        System.out.print("Entrez le nom du fichier texte contenant la grille initiale (ex: grille.txt) : ");
        String nomFichierSource = clavier.nextLine().trim();
        
        if (!nomFichierSource.toLowerCase().endsWith(".txt")) {
            nomFichierSource += ".txt";
        }

        // Recherche dans le sous-répertoire data/
        File fichierSource = new File(DOSSIER_DATA, nomFichierSource);

        // 2. LECTURE ET VÉRIFICATION DES VALEURS INDIVIDUELLES
        try (Scanner fileScanner = new Scanner(fichierSource)) {
            while (fileScanner.hasNext()) {
                String triplet = fileScanner.next();
                if (triplet.length() == 3) {
                    int ligne = Character.getNumericValue(triplet.charAt(0));
                    int colonne = Character.getNumericValue(triplet.charAt(1));
                    int valeur = Character.getNumericValue(triplet.charAt(2));

                    if (ligne < 0 || ligne > 8 || colonne < 0 || colonne > 8) {
                        throw new GrilleInvalideException(
                            String.format("Indices hors limites dans le triplet '%s'. Les coordonnées doivent être entre 0 et 8.", triplet)
                        );
                    }

                    if (valeur < 1 || valeur > 9) {
                        throw new GrilleInvalideException(
                            String.format("Valeur incorrecte dans le triplet '%s'. Le chiffre doit être compris entre 1 et 9.", triplet)
                        );
                    }

                    grille[ligne][colonne] = valeur;
                } else {
                    throw new GrilleInvalideException("Format de triplet invalide détecté : '" + triplet + "'. Attendu : 3 chiffres.");
                }
            }
            
            System.out.println("\n--- Grille initiale chargée.");
            afficherGrille(grille);
            
            // 3. Vérification des doublons ou valeurs manquantes.
            System.out.println("\n--- Validation de la grille.");
            validerGrille(grille);
            System.out.println("✅ La grille initiale est valide.");
            
            // 4. TRANSPOSITION ET AFFICHAGE
            System.out.println("\n--- Affichage de la grille transposée.");
            afficherMatriceTransposee(grille);
            
            // 5. INTERACTION UTILISATEUR : SAUVEGARDE
            proposerSauvegarde(clavier, grille);

        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Le fichier '" + fichierSource.getPath() + "' est introuvable. Vérifiez son emplacement.");
        } catch (GrilleInvalideException e) {
            System.err.println("\n🛑 Erreur de validation : " + e.getMessage());
        } finally {
            clavier.close();
        }
    }

    public static void validerGrille(int[][] grille) throws GrilleInvalideException {
        // Vérification des Lignes
        for (int i = 0; i < 9; i++) {
            Set<Integer> vus = new HashSet<>();
            List<Integer> doublons = new ArrayList<>();
            List<Integer> manquants = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                int val = grille[i][j];
                if (vus.contains(val)) {
                    doublons.add(val);
                } else {
                    vus.add(val);
                }
            }

            if (!doublons.isEmpty()) {
                for (int num = 1; num <= 9; num++) {
                    if (!vus.contains(num)) {
                        manquants.add(num);
                    }
                }
                throw new GrilleInvalideException(
                    String.format("Ligne %d : Doublon(s) %s. Chiffre(s) manquant(s) %s.", i + 1, doublons, manquants)
                );
            }
        }

        // Vérification des Colonnes
        for (int j = 0; j < 9; j++) {
            Set<Integer> vus = new HashSet<>();
            List<Integer> doublons = new ArrayList<>();
            List<Integer> manquants = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                int val = grille[i][j];
                if (vus.contains(val)) {
                    doublons.add(val);
                } else {
                    vus.add(val);
                }
            }

            if (!doublons.isEmpty()) {
                for (int num = 1; num <= 9; num++) {
                    if (!vus.contains(num)) {
                        manquants.add(num);
                    }
                }
                throw new GrilleInvalideException(
                    String.format("Colonne %d : Doublon(s) %s. Chiffre(s) manquant(s) %s.", j + 1, doublons, manquants)
                );
            }
        }
    }

    public static void afficherMatriceTransposee(int[][] grille) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grille[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void proposerSauvegarde(Scanner clavier, int[][] grille) {
        System.out.print("\nVoulez-vous sauvegarder la grille transposée dans un fichier ? (O/N) : ");
        String reponse = clavier.nextLine().trim().toUpperCase();

        if (reponse.equals("O") || reponse.equals("OUI")) {
            System.out.print("Entrez le nom du fichier de destination (ex: transposee.txt) : ");
            String nomFichierCible = clavier.nextLine().trim();

            if (!nomFichierCible.toLowerCase().endsWith(".txt")) {
                nomFichierCible += ".txt";
            }

            File dossierData = new File(DOSSIER_DATA);
            if (!dossierData.exists()) {
                dossierData.mkdirs();
            }

            File fichierCible = new File(dossierData, nomFichierCible);

            try (PrintWriter writer = new PrintWriter(fichierCible)) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        writer.print(grille[j][i] + " ");
                    }
                    writer.println();
                }
                System.out.println("La grille transposée a été sauvegardée avec succès dans : " + fichierCible.getPath());
            } catch (FileNotFoundException e) {
                System.err.println("Erreur impossible de créer ou d'écrire dans le fichier : " + fichierCible.getPath());
            }
        } else {
            System.out.println("Sauvegarde annulée.");
        }
    }

    private static void afficherGrille(int[][] grille) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
    }
}