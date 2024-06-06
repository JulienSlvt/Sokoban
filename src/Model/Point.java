package Model;

public class Point {

    public int x;
    public int y;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point getDirection(Direction direction){
        switch (direction) {
            case Haut:
                return new Point(x, y-1);
            case Droite:
                return new Point(x+1, y);
            case Bas:
                return new Point(x, y+1);
            case Gauche:
                return new Point(x-1, y);
            default:
            return new Point(x, y);
        }
    }

}
