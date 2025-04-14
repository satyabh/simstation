package greed;

import mvc.*;
import simstation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Cow extends MobileAgent {
    int energy;
    static int greediness = 25;

    public Cow(int energy) {
        super();
        this.energy = energy;
    }

    public void update() {
        /* Patch interaction */
        Patch curPatch = ((Meadow)this.world).getPatch(this.xc, this.yc);
        if (curPatch != null) {
            curPatch.eatMe(this, greediness);
        }

        /* Movement */ 
        // Spend moveEnergy if possible
        if (this.energy >= Meadow.moveEnergy) {
            this.energy -= Meadow.moveEnergy;
            heading = Heading.random();
            move(Patch.patchSize);
            // Snap to patch
            if (this.getX() % Patch.patchSize != 0 ) this.xc = curPatch.getX();
            if (this.getY() % Patch.patchSize != 0 ) this.yc = curPatch.getY();
        } 
        // Otherwise, as cow waits, slowly deplete its energy
        else this.energy--;

        if (energy <= 0) {
            this.stop();
            return;
        }
    }
}

class Patch extends Agent {
    int energy;
    public static int growBackRate = 1;
    public static int patchSize = 12;

    public Patch(int energy) {
        super();
        this.energy = energy;
    }

    public void update() {
        if (energy <= 0) {
            this.stop();
            return;
        }

        // Energy grow
        if (!this.stopped && this.energy < 100) {
            this.energy += growBackRate;
            if (this.energy > 100) this.energy = 100;
        }
    }

    public void eatMe(Cow cow, int amt) {
        // Assuming cow only eats patch if cow is hungry enough to eat amt
        if (this.energy >= amt && cow.energy >= (cow.energy - amt)) {
            this.energy -= amt;
            if (this.energy < 0 ) this.energy = 0;
            cow.energy += amt;
        }
    }
}

public class Meadow extends World {
    public static int waitPenalty = 5;
    public static int moveEnergy = 10;
    public static int numCows = 50;
    public static int dim = SIZE / Patch.patchSize;

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

    public static void main(String[] args) {
        AppPanel panel = new GreedPanel(new GreedFactory());

        panel.display();
        // JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        // frame.pack();
        // frame.setResizable(false);
    }
}
