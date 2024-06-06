package ViewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Barrière;
import Model.Bouton;
import Model.Caisse;
import Model.Color;
import Model.Direction;
import Model.Entite;
import Model.Glace;
import Model.Grille;
import Model.Joueur;
import Model.Mur;
import Model.Objectif;
import Model.Point;
import Model.Portail;

@SuppressWarnings("deprecation")
public class MF extends JFrame implements Observer{
    int L = 10;
    int H = 10;
    JPanel[][] tabC = new JPanel[L][H];
    Grille grille;
    int indiceGrille = 0;

    Joueur joueur;

    JButton boutonFin;
    JButton boutonRestart;
    JPanel jp;
    JPanel jpC;
    private String[] nomsGrilles;


    /**
     * Constructeur de la classe MF. Initialise l'interface utilisateur et charge la première grille.
     * @param nomsGrilles Un tableau de noms de fichiers contenant les grilles de jeu.
     */
    public MF(String[] nomsGrilles){

        this.nomsGrilles = nomsGrilles;
        // On lit une grille
        this.grille = lireGrille(false);
        jp = new JPanel(new BorderLayout());
        jpC = new JPanel(new GridLayout(L, H));

        // Initialisation des éléments du tableau tabC avec des JPanel
        // Ajout des éléments du tableau à jpC
        for (int j = 0; j < H; j++) {
            for (int i = 0; i < L; i++) {
                // Créer un nouveau JPanel pour chaque cellule de la grille
                tabC[i][j] = new JPanel();
                tabC[i][j].setPreferredSize(new Dimension(64, 64)); // Redéfinir la taille à 64x64 pixels
        
                // Créer la première JLabel avec l'icône de la grille pour cette cellule
                JLabel bottomLabel = new JLabel(grille.getIcon(new Point(i, j)));
                bottomLabel.setPreferredSize(new Dimension(64, 64)); // Redéfinir la taille à 64x64 pixels
                tabC[i][j].setLayout(new BorderLayout());
                tabC[i][j].add(bottomLabel, BorderLayout.CENTER);
        
                // Vérifier si une entité est associée à cette position dans la grille
                Entite entity = grille.getEntity(new Point(i, j));
                if (entity != null) {
                    // Créer la deuxième JLabel pour afficher l'entité sur la deuxième couche
                    JLabel topLabel = new JLabel(entity.getIconBackground(grille));
                    tabC[i][j].setLayout(new BorderLayout());
                    tabC[i][j].add(topLabel, BorderLayout.CENTER);
                }
        
                // Ajouter le JPanel à jpC
                jpC.add(tabC[i][j]);
            }
        }

        jp.add(jpC, BorderLayout.CENTER);
        add(jp);

        // Configurer la fenêtre JFrame
        setTitle("Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        grille.TryMove(Direction.Haut, joueur);
                        break;
                    case KeyEvent.VK_DOWN:
                        grille.TryMove(Direction.Bas, joueur);
                        break;
                    case KeyEvent.VK_LEFT:
                        grille.TryMove(Direction.Gauche, joueur);
                        break;
                    case KeyEvent.VK_RIGHT:
                        grille.TryMove(Direction.Droite, joueur);
                        break;
                }
                // On met à jour les barrières
                grille.updateBarrieres();
            }
        });

        boutonFin = new JButton("Niveau terminé !");
        boutonFin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grille = lireGrille(false);
                if (grille == null){
                    JLabel labelFinDuJeu = new JLabel("Fin du jeu");

                    // Ajoutez le JLabel à la fenêtre principale (jp)
                    jp.add(labelFinDuJeu, BorderLayout.SOUTH);
                    jp.remove(boutonFin);
                    revalidate();
                    repaint();
                } else {
                    nouvelleGrille();
                }
            }
        });


        boutonRestart = new JButton("Restart");
        boutonRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grille = lireGrille(true);
                nouvelleGrille();
            }
        });
        jp.add(boutonRestart, BorderLayout.NORTH); // Ajout du bouton au haut du JPanel
    }
    
    /**
     * Met à jour l'affichage de la grille lorsque le modèle de données change.
     * @param o L'objet observable.
     * @param arg Un argument supplémentaire passé à la méthode notifyObservers.
     */
    @Override
    public void update(Observable o, Object arg) {
        // Remettre toutes les cases à leur couleur d'origine
        for (int j = 0; j < H; j++) {
            for (int i = 0; i < L; i++) {
                // Supprimer tous les composants de la cellule tabC[i][j]
                tabC[i][j].removeAll();

                JLabel bottomLabel = new JLabel(grille.getIcon(new Point(i, j)));
                tabC[i][j].setLayout(new BorderLayout());
                tabC[i][j].add(bottomLabel, BorderLayout.CENTER);

                // Vérifier si une entité est associée à cette position dans la grille
                Entite entity = grille.getEntity(new Point(i, j));
                if (entity != null) {
                    // Créer la deuxième JLabel pour afficher l'entité sur la deuxième couche
                    JLabel topLabel = new JLabel(grille.getEntityIcon(new Point(i, j)));
                    tabC[i][j].add(topLabel, BorderLayout.CENTER);
                }
            }
        }

        if (grille.isCompleted()){
            jp.remove(boutonRestart);
            jp.add(boutonFin, BorderLayout.SOUTH); // Ajout du bouton au bas du JPanel
        }
        revalidate();
        repaint();
    }

    /**
     * Lit une grille à partir d'un fichier.
     * @param restart Indique s'il faut redémarrer la grille actuelle.
     * @return La grille lue.
     */
    public Grille lireGrille(boolean restart){

        String nomFichier;

        if (restart){
            nomFichier = nomsGrilles[indiceGrille-1];
        } else {
            if (indiceGrille >= nomsGrilles.length){
                return null;
            }
            nomFichier = nomsGrilles[indiceGrille];
            indiceGrille++;
        }

        File fichier = new File(nomFichier);
        Scanner scanner;
        try {
            scanner = new Scanner(fichier);

            // Lire la première ligne pour obtenir la taille de la grille
            int taille = Integer.parseInt(scanner.nextLine().split("=")[1].trim());
            this.L = taille + 2;
            this.H = taille + 2;

            Grille grille = new Grille(this.L, this.H);
            
            // Lire les données du fichier et les stocker dans la grille
            int j = 0;
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] cases = ligne.split(" "); // Séparer chaque case
                for (int i = 0; i < cases.length; i++) {
                    Point p = new Point(i + 1, j + 1);
                    switch (cases[i]) {
                        case "V":
                            break;
                        case "M":
                            grille.setCase(new Mur(), p);
                            break;
                        case "G":
                            grille.setCase(new Glace(), p);
                            break;
                        // Boites
                        case "B":   // Boites qui ne comptent pas comme objectif
                            grille.addEntity(new Caisse(p, Color.Black), p);
                            break;
                        case "B-Beige":
                            grille.addEntity(new Caisse(p, Color.Beige), p);
                            break;
                        case "B-Blue":
                            grille.addEntity(new Caisse(p, Color.Blue), p);
                            break;
                        case "B-Brown":
                            grille.addEntity(new Caisse(p, Color.Brown), p);
                            break;
                        case "B-Gray":
                            grille.addEntity(new Caisse(p, Color.Gray), p);
                            break;
                        case "B-Purple":
                            grille.addEntity(new Caisse(p, Color.Purple), p);
                            break;
                        case "B-Red":
                            grille.addEntity(new Caisse(p, Color.Red), p);
                            break;
                        case "B-Yellow":
                            grille.addEntity(new Caisse(p, Color.Yellow), p);
                            break;

                        // Objectifs
                        case "O-Beige":
                            grille.setCase(new Objectif(Color.Beige), p);
                            break;
                        case "O-Blue":
                            grille.setCase(new Objectif(Color.Blue), p);
                            break;
                        case "O-Brown":
                            grille.setCase(new Objectif(Color.Brown), p);
                            break;
                        case "O-Gray":
                            grille.setCase(new Objectif(Color.Gray), p);
                            break;
                        case "O-Purple":
                            grille.setCase(new Objectif(Color.Purple), p);
                            break;
                        case "O-Red":
                            grille.setCase(new Objectif(Color.Red), p);
                            break;
                        case "O-Yellow":
                            grille.setCase(new Objectif(Color.Yellow), p);
                            break;

                        // Portails
                        case "P":
                            grille.setCase(new Portail(), p);
                            break;

                        // Bouton + Barrières
                        case "Bouton":
                            grille.setCase(new Bouton(), p);
                            break;
                        case "Barriere":
                            grille.setCase(new Barrière(), p);
                            break;
                        case "J":
                            this.joueur = new Joueur(p);
                            grille.setEntity(p, joueur);
                            break;
                        default:
                            break;
                    }
                }
                j++;
            }
            scanner.close();
            grille.afficherObjectifs();
            grille.addObserver(this);
            return grille;
        }
    catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Charge une nouvelle grille et met à jour l'affichage.
     */
    public void nouvelleGrille(){
        jp.remove(jpC);
        jp.remove(boutonFin);
        this.jpC = new JPanel(new GridLayout(L, H));

        // Initialisation des éléments du tableau tabC avec des JPanel
        // Ajout des éléments du tableau à jpC
        for (int j = 0; j < H; j++) {
            for (int i = 0; i < L; i++) {
                // Créer un nouveau JPanel pour chaque cellule de la grille
                tabC[i][j] = new JPanel();
                tabC[i][j].setPreferredSize(new Dimension(64, 64)); // Redéfinir la taille à 64x64 pixels
        
                // Créer la première JLabel avec l'icône de la grille pour cette cellule
                JLabel bottomLabel = new JLabel(grille.getIcon(new Point(i, j)));
                bottomLabel.setPreferredSize(new Dimension(64, 64)); // Redéfinir la taille à 64x64 pixels
                tabC[i][j].setLayout(new BorderLayout());
                tabC[i][j].add(bottomLabel, BorderLayout.CENTER);
        
                // Vérifier si une entité est associée à cette position dans la grille
                Entite entity = grille.getEntity(new Point(i, j));
                if (entity != null) {
                    // Créer la deuxième JLabel pour afficher l'entité sur la deuxième couche
                    JLabel topLabel = new JLabel(entity.getIconBackground(grille));
                    topLabel.setPreferredSize(new Dimension(64, 64)); // Redéfinir la taille à 64x64 pixels
                    tabC[i][j].setLayout(new BorderLayout());
                    tabC[i][j].add(topLabel, BorderLayout.CENTER);
                }
        
                // Ajouter le JPanel à jpC
                this.jpC.add(tabC[i][j]);
            }
        }

        jp.add(jpC, BorderLayout.CENTER);
        jp.add(boutonRestart, BorderLayout.NORTH);
        requestFocusInWindow();
        revalidate();
        repaint();

    }
}