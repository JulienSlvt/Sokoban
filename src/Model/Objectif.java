package Model;

import javax.swing.ImageIcon;

public class Objectif extends Case {

    Color color;
    
    /**
     * Constructeur de la classe Objectif.
     * @param color La couleur de l'objectif.
     */
    public Objectif(Color color){
        super(null);
        this.color = color;
        this.setIcon(getColorImage());
    }

    /**
     * Obtient la couleur de l'objectif.
     * @return La couleur de l'objectif.
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Obtient l'icône de l'objectif basé sur sa couleur.
     * @return L'icône de l'objectif.
     */
    public ImageIcon getColorImage(){
        switch (color) {
            case Beige:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Beige.png"));
            case Black:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Black.png"));
            case Blue:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Blue.png"));
            case Brown:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Brown.png"));
            case Gray:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Gray.png"));
            case Purple:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Purple.png"));
            case Red:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Red.png"));
            case Yellow:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Yellow.png"));
            default:
                return new ImageIcon(Caisse.class.getResource("/ViewController/Assets/EndPoint_Beige.png"));
        }
    }
    
}