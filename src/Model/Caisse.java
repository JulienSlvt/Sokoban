package Model;

import javax.swing.ImageIcon;

/**
 * La classe Caisse représente une entité de caisse dans le jeu Sokoban.
 */
public class Caisse extends Entite {

    Color color;
    ImageIcon finishIcon;

    /**
     * Constructeur de la classe Caisse.
     * @param p Le point où la caisse est placée.
     * @param color La couleur de la caisse.
     */
    public Caisse(Point p, Color color) {
        super(null, p);
        this.color = color;
        this.setIcon(getColorImage());
        this.finishIcon = getFinishImage();
    }

    /**
     * Obtient l'icône représentant la caisse lorsque le niveau est terminé.
     * @return L'icône représentant la caisse lorsqu'elle est placée sur un objectif.
     */
    @Override
    public ImageIcon getFinishIcon() {
        return finishIcon;
    }

    /**
     * Obtient la couleur de la caisse.
     * @return La couleur de la caisse.
     */
    public Color getColor(){
        return color;
    }

    /**
     * Obtient l'icône représentant la caisse lorsqu'elle est placée sur un objectif.
     * @return L'icône représentant la caisse lorsqu'elle est placée sur un objectif.
     */
    public ImageIcon getFinishImage() {
        switch (color) {
            case Beige:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Beige.png"));
            case Black:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Black.png"));
            case Blue:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Blue.png"));
            case Brown:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Brown.png"));
            case Gray:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Gray.png"));
            case Purple:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Purple.png"));
            case Red:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Red.png"));
            case Yellow:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Yellow.png"));
            default:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/CrateDark_Beige.png"));
        }
    }
    

    /**
     * Obtient l'icône représentant la caisse selon sa couleur.
     * @return L'icône représentant la caisse selon sa couleur.
     */
    public ImageIcon getColorImage(){
        switch (color) {
            case Beige:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige.png"));
            case Black:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Black.png"));
            case Blue:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Blue.png"));
            case Brown:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Brown.png"));
            case Gray:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Gray.png"));
            case Purple:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Purple.png"));
            case Red:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Red.png"));
            case Yellow:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Yellow.png"));
            default:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige.png"));
        }
    }

    /**
     * Obtient l'icône de fond de la caisse en fonction de la grille.
     * @param grille La grille du jeu.
     * @return L'icône de fond de la caisse en fonction de la grille.
     */
    @Override
    public ImageIcon getIconBackground(Grille grille) {
        Case c = grille.tab_Cases[point.x][point.y];
        if (c instanceof Glace)
            switch (color) {
                case Beige:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige-Glace.png"));
                case Black:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Black-Glace.png"));
                case Blue:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Blue-Glace.png"));
                case Brown:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Brown-Glace.png"));
                case Gray:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Gray-Glace.png"));
                case Purple:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Purple-Glace.png"));
                case Red:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Red-Glace.png"));
                case Yellow:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Yellow-Glace.png"));
                default:
                    return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige-Glace.png"));
            }
        
        // On traite le cas où la texture arrière n'est pas traitée au dessus
        switch (color) {
            case Beige:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige-Vide.png"));
            case Black:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Black-Vide.png"));
            case Blue:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Blue-Vide.png"));
            case Brown:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Brown-Vide.png"));
            case Gray:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Gray-Vide.png"));
            case Purple:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Purple-Vide.png"));
            case Red:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Red-Vide.png"));
            case Yellow:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Yellow-Vide.png"));
            default:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/Crate_Beige-Vide.png"));
        }
    }

}
