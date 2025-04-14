package prisoner;

import mvc.AppPanel;
import simstation.WorldPanel;

public class PrisonerDilemmaSimulation {
    
    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new PrisonerDilemmaFactory());
        panel.display();
    }
}