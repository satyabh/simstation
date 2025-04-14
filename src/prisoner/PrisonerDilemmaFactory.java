package prisoner;

import mvc.*;
import simstation.World;
import simstation.WorldFactory;

public class PrisonerDilemmaFactory extends WorldFactory {
    
    @Override
    public Model makeModel() {
        return new PrisonerDilemmaWorld();
    }
    
    @Override
    public View makeView(Model model) {
        return new PrisonerDilemmaView((World)model);
    }
    
    @Override
    public String getTitle() {
        return "The Prisoner's Dilemma Tournament";
    }
    
    @Override
    public String getHelp() {
        return super.getHelp() + 
                "\n\nPrisoner's Dilemma Tournament:\n" +
                "Each prisoner plays games with random neighbors and earns fitness points.\n" +
                "Strategies are: Cheat, Cooperate, Tit-for-tat, and randomly cooperate.";
    }
    
    @Override
    public String getAbout() {
        return "Prisoner's Dilemma Tournament v1.0\n" +
                "Used to study the evolution of altruistic behavior in social networks.";
    }
}