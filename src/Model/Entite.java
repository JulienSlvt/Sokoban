package Model;

import javax.swing.ImageIcon;


public abstract class Entite {

    protected ImageIcon icon;
    public Point point;

    /**
     * Constructeur de la classe Entite.
     * @param icon L'icône de l'entité.
     * @param point Le point de l'entité dans la grille.
     */
    public Entite(ImageIcon icon, Point point){
        this.icon = icon;
        this.point = point;
    }

    /**
     * Obtient l'icône de l'entité.
     * @return L'icône de l'entité.
     */
    public ImageIcon getIcon(){
        return this.icon;
    }

    /**
     * Définit l'icône de l'entité.
     * @param imageIcon L'icône à définir.
     */
    public void setIcon(ImageIcon imageIcon){
        this.icon = imageIcon;
    }

    /**
     * Obtient le point de l'entité dans la grille.
     * @return Le point de l'entité.
     */
    public Point getPoint(){
        return this.point;
    }

    /**
     * Obtient l'icône de fond de l'entité, utilisée pour afficher des entités superposées.
     * Par défaut, renvoie simplement l'icône de l'entité elle-même.
     * @param grille La grille de jeu.
     * @return L'icône de fond de l'entité.
     */
    public ImageIcon getIconBackground(Grille grille) {
        return getIcon();
    }

    /**
     * Obtient le point dans la direction spécifiée à partir du point actuel de l'entité.
     * @param direction La direction dans laquelle déplacer l'entité.
     * @return Le point dans la direction spécifiée.
     */
    public Point getDirectionPoint(Direction direction) {
        switch (direction) {
            case Droite:
                return new Point(point.x + 1, point.y);
            case Gauche:
                return new Point(point.x - 1, point.y);
            case Bas:
                return new Point(point.x, point.y + 1);
            case Haut:
                return new Point(point.x, point.y - 1);
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    /**
     * Définit le point de l'entité dans la grille.
     * @param p Le point à définir.
     */
    public void setPoint(Point p){
        this.point = p;
    }

    /**
     * Obtient l'icône de fin de jeu associée à l'entité.
     * Cette méthode est utilisée pour afficher une icône spécifique lorsqu'une entité termine une tâche.
     * Par défaut, renvoie null, ce qui signifie qu'aucune icône spécifique n'est associée à cette entité.
     * @return L'icône de fin de jeu de l'entité.
     */
    public ImageIcon getFinishIcon(){
        return null;
    }
}