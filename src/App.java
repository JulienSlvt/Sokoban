import ViewController.MF;

public class App {
    public static void main(String[] args) throws Exception {
        String[] nomsGrilles =
        {
            "src\\ViewController\\Cartes\\tutorial1.map",
            "src\\ViewController\\Cartes\\tutorial2.map",
            "src\\ViewController\\Cartes\\tutorial3.map",
            "src\\ViewController\\Cartes\\tutorial4.map",
            "src\\ViewController\\Cartes\\tutorial5.map",
            "src\\ViewController\\Cartes\\tutorial6.map",
            "src\\ViewController\\Cartes\\tutorial7.map",
            "src\\ViewController\\Cartes\\tutorial8.map",
            "src\\ViewController\\Cartes\\niveau1.map",
        };
        MF mf = new MF(nomsGrilles);
        mf.setVisible(true);
    }
}
