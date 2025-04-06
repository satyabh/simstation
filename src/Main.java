import SimStation.SimPanel;
import SimStation.SimFactory;

public class Main {
    public static void main(String[] args) {
        SimPanel app = new SimPanel(new SimFactory());
        app.display();
    }
}