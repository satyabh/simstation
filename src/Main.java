import simstation.WorldPanel;
import simstation.WorldFactory;

public class Main {
    public static void main(String[] args) {
        WorldPanel app = new WorldPanel(new WorldFactory());
        app.display();
    }
}