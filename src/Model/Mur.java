package Model;

import javax.swing.ImageIcon;

public class Mur extends Case {

    public Mur() {
        super(new ImageIcon("src\\ViewController\\Assets\\Wall_Black.png"));
        setCollision(true);
    }

}
