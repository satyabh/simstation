package prisoner;

import simstation.Agent;
import simstation.World;
import java.util.ArrayList;

public class PrisonerDilemmaWorld extends World {
    
    @Override
    public void populate() {
        // Create 10 prisoners of each strategy type
        for (int i = 0; i < 10; i++) {
            addAgent(new Prisoner("Cheat-" + i, new AlwaysCheatStrategy()));
            addAgent(new Prisoner("Coop-" + i, new AlwaysCooperateStrategy()));
            addAgent(new Prisoner("Random-" + i, new RandomlyCooperateStrategy()));
            addAgent(new Prisoner("TitForTat-" + i, new TitForTatStrategy()));
        }
    }
    
    @Override
    public ArrayList<String> getStats() {
        ArrayList<String> stats = super.getStats();
        
        // Calculate average fitness for each strategy
        int cheatTotal = 0, cheatCount = 0;
        int coopTotal = 0, coopCount = 0;
        int randomTotal = 0, randomCount = 0;
        int titForTatTotal = 0, titForTatCount = 0;
        
        for (Agent agent : agents) {
            if (agent instanceof Prisoner) {
                Prisoner prisoner = (Prisoner) agent;
                String name = prisoner.getName();
                
                if (name.startsWith("Cheat")) {
                    cheatTotal += prisoner.getFitness();
                    cheatCount++;
                } else if (name.startsWith("Coop")) {
                    coopTotal += prisoner.getFitness();
                    coopCount++;
                } else if (name.startsWith("Random")) {
                    randomTotal += prisoner.getFitness();
                    randomCount++;
                } else if (name.startsWith("TitForTat")) {
                    titForTatTotal += prisoner.getFitness();
                    titForTatCount++;
                }
            }
        }
        
        // Add strategy statistics to stats list
        if (cheatCount > 0) stats.add("Always Cheat avg fitness: " + (cheatTotal / cheatCount));
        if (coopCount > 0) stats.add("Always Cooperate avg fitness: " + (coopTotal / coopCount));
        if (randomCount > 0) stats.add("Random Cooperate avg fitness: " + (randomTotal / randomCount));
        if (titForTatCount > 0) stats.add("Tit For Tat avg fitness: " + (titForTatTotal / titForTatCount));
        
        return stats;
    }
}