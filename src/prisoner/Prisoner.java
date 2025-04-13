package prisoner;

import simstation.Agent;
import simstation.Heading;
import simstation.MobileAgent;

public class Prisoner extends MobileAgent {
    private int fitness;
    private boolean partnerCheated;
    private CooperationStrategy strategy;
    
    public Prisoner(String name, CooperationStrategy strategy) {
        super();
        this.name = name;
        this.fitness = 0;
        this.partnerCheated = false;
        this.strategy = strategy;
    }
    
    public boolean cooperate() {
        return strategy.cooperate(this);
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public boolean getPartnerCheated() {
        return partnerCheated;
    }
    
    @Override
    public void update() {
        Agent neighbor = world.getNeighbor(this, 10);
        
        // If neighbor is found and is a Prisoner, play the game
        if (neighbor != null && neighbor instanceof Prisoner) {
            Prisoner partner = (Prisoner) neighbor;
            
            boolean myDecision = cooperate();
            boolean partnerDecision = partner.cooperate();

            updateFitness(myDecision, partnerDecision);
            partner.updateFitness(partnerDecision, myDecision);
        }
        
        // Move randomly
        heading = Heading.random();
        move(1);
    }
    
    private void updateFitness(boolean myDecision, boolean partnerDecision) {
        if (myDecision && partnerDecision) {
            fitness += 3;
        } else if (!myDecision && partnerDecision) {
            fitness += 5;
        } else if (myDecision && !partnerDecision) {
            // I cooperate, partner cheats (0,5)
            fitness += 0;
        } else {
            // Both cheat (1,1)
            fitness += 1;
        }
        partnerCheated = !partnerDecision;
    }
}