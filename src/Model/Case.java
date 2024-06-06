package Model;

import javax.swing.ImageIcon;

public abstract class Case {

    protected boolean collision = false;
    Entite entite = null;
    private ImageIcon icon;
    
    /**
     * Constructeur de la classe Case.
     * @param imageIcon L'icône de la case.
     */
    public Case(ImageIcon imageIcon){
        this.icon = imageIcon;
    }

    /**
     * Définit l'icône de la case.
     * @param icon L'icône à définir.
     */
    public void setIcon(ImageIcon icon){
        this.icon = icon;
    }

    /**
     * Obtient l'icône de la case.
     * @return L'icône de la case.
     */
    public ImageIcon getIcon(){
        return this.icon;
    }

    /**
     * Vérifie si la case est un obstacle.
     * @return true si la case est un obstacle, sinon false.
     */
    public boolean isCollider(){
        return this.collision;
    }

    /**
     * Définit si la case est un obstacle.
     * @param bool Valeur booléenne indiquant si la case est un obstacle.
     */
    public void setCollision(boolean bool){
        this.collision = bool;
    }

    /**
     * Obtient l'entité associée à la case.
     * @return L'entité associée à la case, ou null si aucune entité n'est présente.
     */
    public Entite getEntite(){
        return entite;
    }

    /**
     * Définit l'entité associée à la case.
     * @param entite L'entité à associer à la case.
     */
    public void setEntite(Entite entite){
        this.entite = entite;
    }
}