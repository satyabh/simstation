package greed;

import simstation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Meadow extends World {
    public static int waitPenalty = 5;
    public static int moveEnergy = 10;
    public static int numCows = 50;
    public static int dim = SIZE / Patch.patchSize;
    public static final int WAIT_PENALTY = 1;

    public Patch getPatch(int xc, int yc) {
        for (Agent agent : this.agents) {
            if (
                agent instanceof Patch && 
                agent.getX() <= xc &&
                agent.getX() + Patch.patchSize >= xc &&
                agent.getY() <= yc &&
                agent.getY() + Patch.patchSize >= yc
            ) return (Patch) agent;
        }
        return null;
    }

    public void populate() {
        // Patches
        for(int r = 0; r < SIZE; r += Patch.patchSize) {
            for (int c = 0; c < SIZE; c += Patch.patchSize) {
                Patch patch = new Patch(100);
                patch.setPosition(c, r);
                addAgent(patch);
            }
        }

        // Cows
        List<Agent> copyOfPatchesAgents = new ArrayList<>(this.agents);
        Collections.shuffle(copyOfPatchesAgents);
        for (int i = 0; i < numCows && i < copyOfPatchesAgents.size(); i++) {
            Agent a = copyOfPatchesAgents.get(i);

            Cow cow = new Cow(100);
            cow.setPosition(a.getX(), a.getY());
            addAgent(cow);
        }
    }

    public void setMoveEnergy(int value) {
        moveEnergy = value;
        changed();
    }
    public void SetGrowbackRate(int value) {
        Patch.growBackRate = value;
        changed();
    }
    public void setGreed(int value) {
        Cow.greediness = value;
        changed();
    }

    private double totalPatchesCount() {
        double totalPatchesCount = 0;
        for(Agent a: agents) if (a instanceof Patch) totalPatchesCount++;
        return totalPatchesCount;
    }

    private double totalPatchesAlive() {
        double totalPatchesAlive = 0;
        for(Agent a: agents) if (a instanceof Patch && ((Patch)a).energy > 0) totalPatchesAlive++;
        return totalPatchesAlive;
    }

    private double totalCowsCount() {
        double totalCowsCount = 0;
        for(Agent a: agents) if (a instanceof Cow) totalCowsCount++;
        return totalCowsCount;
    }

    private double totalCowsAlive() {
        double totalCowsAlive = 0;
        for(Agent a: agents) if (a instanceof Cow && ((Cow)a).energy > 0) totalCowsAlive++;
        return totalCowsAlive;
    }

    public ArrayList<String> getStats() {
        String[] stats = new String[5];
        stats[0] = "#Patches alive = " + this.totalPatchesAlive();
        stats[1] = "#Cows alive = " + this.totalCowsAlive();
        stats[2] = "#clock = " + this.clock;
        stats[3] = "% Cows alive = " + String.format("%.2f", totalCowsAlive() / totalCowsCount() * 100);
        stats[4] = "% Patches alive = " + String.format("%.2f", totalPatchesAlive() / totalPatchesCount() * 100);
        return new ArrayList<>(Arrays.asList(stats));
    }
}
