package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.ImageIcon;

@SuppressWarnings("deprecation")
public class Grille extends Observable {
    int L;
    int H;
    Case[][] tab_Cases;
    Map<Entite, Point> map;

    Point[] objectifs;
    int nbObjectifs = 0;

    Point[] portails;
    int nbPortails = 0;

    Point bouton = null;
    Point[] barrières;
    int nbBarrières = 0;



    /**
     * Constructeur de la classe Grille.
     * @param L Largeur de la grille
     * @param H Hauteur de la grille
     */
    public Grille(int L, int H){
        this.L = L;
        this.H = H;
        this.objectifs = new Point[L*H];
        this.portails = new Point[2];
        this.barrières = new Point[L*H];

        tab_Cases = new Case[L][H];
        map = new HashMap<>();

        for (int j = 0; j < H; j++){
            for (int i = 0; i < L; i++){
                if (i == 0 || i == L - 1 || j == 0 || j == H - 1){
                    tab_Cases[i][j] = new Mur();
                } else {
                    tab_Cases[i][j] = new Vide();

                }
            }
        }
    }

    /**
     * Affiche les objectifs.
     */
    public void afficherObjectifs(){
        System.out.println(objectifs);
    }

    /**
     * Ajoute une entité à une position donnée.
     * @param entite L'entité à ajouter
     * @param p La position de l'entité
     */
    public void addEntity(Entite entite, Point p){
        setEntity(entite.getPoint(), entite);
        tab_Cases[entite.getPoint().x][entite.getPoint().y].setCollision(true);
    }

    /**
     * Vérifie si le jeu est complété.
     * @return true si le jeu est complété, sinon false
     */
    public boolean isCompleted(){
        for (int i = 0; i < nbObjectifs; i++) {
            Entite entite = tab_Cases[objectifs[i].x][objectifs[i].y].getEntite();
            if (!(entite instanceof Caisse)){
                return false;
            } else {
                Caisse caisse = (Caisse)entite;
                if (caisse.color != Color.Black){
                    Point p = new Point(objectifs[i].x, objectifs[i].y);
                    Objectif obj = (Objectif)tab_Cases[p.x][p.y];
                    Caisse caisse2 = (Caisse)getEntity(p);
                    if (obj.getColor() != caisse2.getColor()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Définit une case à une position donnée.
     * @param c La case à définir
     * @param p La position de la case
     */
    public void setCase(Case c, Point p){
        if (c instanceof Objectif){
            objectifs[nbObjectifs] = p;
            nbObjectifs++;
        }

        if (c instanceof Portail){
            portails[nbPortails] = p;
            nbPortails++;
        }

        if (c instanceof Bouton){
            this.bouton = p;
        }

        if (c instanceof Barrière){
            barrières[nbBarrières] = p;
            nbBarrières++;
        }
        tab_Cases[p.x][p.y] = c;
    }

    /**
     * Définit l'état de collision d'une case à une position donnée.
     * @param p La position de la case
     * @param etat L'état de collision
     */
	public void setCollision(Point p, boolean etat){
        tab_Cases[p.x][p.y].setCollision(etat);
    }

    /**
     * Obtient l'état de collision d'une case à une position donnée.
     * @param p La position de la case
     * @return true si la case est en collision, sinon false
     */
    public Boolean getCollision(Point p){
        return tab_Cases[p.x][p.y].isCollider();
    }

    /**
     * Obtient l'icône d'une case à une position donnée.
     * @param p La position de la case
     * @return L'icône de la case
     */
    public ImageIcon getIcon(Point p){
        return tab_Cases[p.x][p.y].getIcon();
    }

    /**
     * Définit une entité à une position donnée.
     * @param p La position de l'entité
     * @param entite L'entité à définir
     */
    public void setEntity(Point p, Entite entite){
        tab_Cases[p.x][p.y].setEntite(entite);
        map.put(entite, p);
    }

    /**
     * Obtient une entité à une position donnée.
     * @param p La position de l'entité
     * @return L'entité à la position donnée
     */
    public Entite getEntity(Point p){
        return tab_Cases[p.x][p.y].getEntite();
    }

    /**
     * Obtient l'icône d'une entité à une position donnée.
     * @param p La position de l'entité
     * @return L'icône de l'entité
     */
    public ImageIcon getEntityIcon(Point p){
        if (this.getEntity(p) != null){
            if (tab_Cases[p.x][p.y] instanceof Objectif && getEntity(p) instanceof Caisse){
                Objectif obj = (Objectif)tab_Cases[p.x][p.y];
                Caisse caisse = (Caisse)getEntity(p);
                if (obj.getColor() == caisse.getColor()){
                    return getEntity(p).getFinishIcon();
                }
            }
        }
        return getEntity(p).getIconBackground(this);
    }

    /**
     * Déplace une entité dans une direction donnée.
     * @param direction La direction du déplacement
     * @param entite L'entité à déplacer
     */
    public void moveEntite(Direction direction, Entite entite){
        move(direction, entite);
        setChanged();
        notifyObservers(this);
    }

    /**
     * Déplace une entité sur la glace dans une direction donnée.
     * @param direction La direction du déplacement
     * @param entite L'entité à déplacer
     */
    public void moveEntiteSurGlace(Direction direction, Entite entite){
        Point p = entite.getDirectionPoint(direction);

        while (tab_Cases[p.x][p.y] instanceof Glace 
        && !tab_Cases[p.x][p.y].isCollider()) {
            move(direction, entite);
            p = entite.getDirectionPoint(direction);
        }
        if (!tab_Cases[p.x][p.y].isCollider()){
            if (entite instanceof Joueur){
                TryMove(direction, (Joueur)entite);
            } else {
                TryMoveEntite(direction, entite);
            }
        }

        setChanged();
        notifyObservers(this);
    }

    /**
     * Obtient la direction dans le sens horaire à partir d'une direction donnée.
     * @param dir La direction initiale
     * @return La direction dans le sens horaire
     */
    Direction getDirectionSensHoraire(Direction dir) {
        switch (dir) {
            case Haut:
                return Direction.Droite;

            case Droite:
                return Direction.Bas;

            case Bas:
                return Direction.Gauche;

            case Gauche:
                return Direction.Haut;

            default:
                return Direction.Haut;

        }
    }

    /**
     * Vérifie si une case est libre pour un déplacement dans une direction donnée.
     * @param p La position de la case
     * @param direction La direction du déplacement
     * @return true si la case est libre, sinon false
     */
    public boolean isCaseFree(Point p, Direction direction){
        return ((tab_Cases[p.x][p.y].getEntite() == null 
        || (tab_Cases[p.getDirection(direction).x][p.getDirection(direction).y].getEntite() == null && !(tab_Cases[p.getDirection(direction).x][p.getDirection(direction).y] instanceof Mur))) 
        && !(tab_Cases[p.x][p.y] instanceof Mur));
    }

    /**
     * Vérifie si une case est libre pour une entité.
     * @param p La position de la case
     * @param direction La direction du déplacement
     * @return true si la case est libre, sinon false
     */
    public boolean isCaseFreeEntite(Point p, Direction direction){
        return (tab_Cases[p.x][p.y].getEntite() == null && !(tab_Cases[p.x][p.y] instanceof Mur));
    }

    /**
     * Déplace un joueur à travers un portail dans une direction donnée.
     * @param direction La direction du déplacement
     * @param joueur Le joueur à déplacer
     */
    public void movePortailPlayer(Direction direction, Joueur joueur){
        Point p = joueur.getDirectionPoint(direction);
        if (nbPortails != 2){
            return;
        }

        Direction dir = direction;
        if (p.x == portails[0].x && p.y == portails[0].y){
            for (int index = 0; index < 4; index++) {
                Point p2 = portails[1].getDirection(dir);
                if (isCaseFree(p2,dir)){
                    Point portail = new Point(portails[1].x, portails[1].y);
                    tab_Cases[joueur.getPoint().x][joueur.getPoint().y].setEntite(null);

                    tab_Cases[portail.x][portail.y].setEntite(joueur);
                    joueur.setPoint(portail);

                    TryMove(dir, joueur);
                    setChanged();
                    notifyObservers(this);
                    return;
                }
                dir = getDirectionSensHoraire(dir);
            }
        }

        else {
            for (int index = 0; index < 4; index++) {
                Point p2 = portails[0].getDirection(dir);
                if (isCaseFree(p2,dir)){
                    Point portail = new Point(portails[0].x, portails[0].y);
                    tab_Cases[joueur.getPoint().x][joueur.getPoint().y].setEntite(null);

                    tab_Cases[portail.x][portail.y].setEntite(joueur);
                    joueur.setPoint(portail);

                    TryMove(dir, joueur);
                    setChanged();
                    notifyObservers(this);
                    return;
                }
                dir = getDirectionSensHoraire(dir);
            }
        }
        move(direction, joueur);
        setChanged();
        notifyObservers(this);
    }

    /**
     * Déplace une entité à travers un portail dans une direction donnée.
     * @param direction La direction du déplacement
     * @param entite L'entité à déplacer
     */
    public void movePortailEntity(Direction direction, Entite entite){
        Point p = entite.getDirectionPoint(direction);
        if (nbPortails != 2){
            return;
        }

        Direction dir = direction;
        if (p.x == portails[0].x && p.y == portails[0].y){
            for (int index = 0; index < 4; index++) {
                Point p2 = portails[1].getDirection(dir);
                if (isCaseFree(p2,dir)){
                    setCollision(entite.getPoint(), false);
                    Point portail = new Point(portails[1].x, portails[1].y);
                    tab_Cases[entite.getPoint().x][entite.getPoint().y].setEntite(null);

                    tab_Cases[portail.x][portail.y].setEntite(entite);
                    entite.setPoint(portail);

                    TryMoveEntite(dir, entite);
                    return;
                }
                dir = getDirectionSensHoraire(dir);
            }
        } else {
            for (int index = 0; index < 4; index++) {
                Point p2 = portails[0].getDirection(dir);
                if (isCaseFreeEntite(p2,dir)){
                    Point portail = new Point(portails[0].x, portails[0].y);
                    tab_Cases[entite.getPoint().x][entite.getPoint().y].setEntite(null);

                    tab_Cases[portail.x][portail.y].setEntite(entite);
                    entite.setPoint(portail);

                    TryMoveEntite(dir, entite);
                    return;
                }
                dir = getDirectionSensHoraire(dir);
            }
        }
        move(direction, entite);
        setChanged();
        notifyObservers(this);
    }

    /**
     * Tente de déplacer un joueur dans une direction donnée.
     * @param direction La direction du déplacement
     * @param joueur Le joueur à déplacer
     * @return true si le déplacement est réussi, sinon false
     */
    public boolean TryMove(Direction direction, Joueur joueur){
        Point p = joueur.getDirectionPoint(direction);
        
        // On ne veut pas que le joueur puisse pousser de caisse sur la glace
        if (tab_Cases[joueur.getPoint().x][joueur.getPoint().y] instanceof Glace && tab_Cases[p.x][p.y].isCollider()){
            return false;
        }

        if (tab_Cases[p.x][p.y] instanceof Glace){
            if (!tab_Cases[p.x][p.y].isCollider()){
                moveEntiteSurGlace(direction, joueur);
                updateBarrieres();
                return true;
            }
        }

        if (tab_Cases[p.x][p.y] instanceof Portail){
            if (!tab_Cases[p.x][p.y].isCollider()){
                movePortailPlayer(direction, joueur);
                updateBarrieres();
                return true;
            }
        }

        if (!tab_Cases[p.x][p.y].isCollider()){
            moveEntite(direction, joueur);
        } else {
            Entite tempEntite = tab_Cases[p.x][p.y].getEntite();
            if (tempEntite != null){
                if (TryMoveEntite(direction, tempEntite)){
                    moveEntite(direction, joueur);
                    updateBarrieres();
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Tente de déplacer une entité dans une direction donnée.
     * @param direction La direction du déplacement
     * @param entite L'entité à déplacer
     * @return true si le déplacement est réussi, sinon false
     */
    public boolean TryMoveEntite(Direction direction, Entite entite){
        Point p = entite.getDirectionPoint(direction);

            if (tab_Cases[p.x][p.y] instanceof Glace){
                if (!tab_Cases[p.x][p.y].isCollider()){
                    setCollision(entite.getPoint(), false);
                    moveEntiteSurGlace(direction, entite);
                    setCollision(entite.getPoint(), true);
                    return true;
                }
            }

            if (tab_Cases[p.x][p.y] instanceof Portail){
                if (!tab_Cases[p.x][p.y].isCollider()){
                    setCollision(entite.getPoint(), false);
                    movePortailEntity(direction, entite);
                    setCollision(entite.getPoint(), true);
                    return true;
                }
            }
            
            if (!tab_Cases[p.x][p.y].isCollider()){
                setCollision(entite.getPoint(), false);
                moveEntite(direction, entite);
                setCollision(entite.getPoint(), true);
                return true;
            }
        return false;
    }

    /**
     * Déplace une entité dans une direction donnée.
     * @param d La direction du déplacement
     * @param entite L'entité à déplacer
     */
    public void move(Direction d, Entite entite){
        setEntity(entite.getPoint(), null);
        switch (d) {
            case Haut:
                entite.point.y--;
                break;
            case Bas:
                entite.point.y++;
                break;
            case Gauche:
                entite.point.x--;
                break;
            case Droite:
                entite.point.x++;
                break;
        }
        setEntity(entite.getPoint(), entite);
    }

    /**
     * Met à jour l'état des barrières.
     */
    public void updateBarrieres(){
        if (nbBarrières != 0){
            if (tab_Cases[bouton.x][bouton.y].getEntite() != null){
                for (int i = 0; i < nbBarrières; i++){
                    tab_Cases[barrières[i].x][barrières[i].y].setCollision(false);
                    tab_Cases[barrières[i].x][barrières[i].y].setIcon(new ImageIcon("src\\ViewController\\Assets\\Fence-Ouverte.png"));
                }
            } else {
                for (int i = 0; i < nbBarrières; i++){
                    tab_Cases[barrières[i].x][barrières[i].y].setCollision(true);
                    tab_Cases[barrières[i].x][barrières[i].y].setIcon(new ImageIcon("src\\ViewController\\Assets\\Fence-Fermée.png"));
                }
            }
        setChanged();
        notifyObservers(this);
        }
    }
}
