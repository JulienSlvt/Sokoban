package Model;

import javax.swing.ImageIcon;

public class Joueur extends Entite {

    public Joueur(Point p){
        super(new ImageIcon("src\\ViewController\\Assets\\Character-Vide.png"), p);
    }

    @Override
    public ImageIcon getIconBackground(Grille grille) {
        Case c = grille.tab_Cases[point.x][point.y];
        if (c instanceof Glace)
            return new ImageIcon("src\\ViewController\\Assets\\Character-Glace.png");
        
        // On traite le cas où la texture arrière n'est pas traitée au dessus
        return new ImageIcon("src\\ViewController\\Assets\\Character-Vide.png");
    }

    
}
