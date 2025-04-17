package greed;

import simstation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class Meadow extends World {
    // Centralized simulation parameters
    public static volatile int MOVE_ENERGY = 10;         // Energy cost for moving
    public static volatile int COW_GREEDINESS = 25;       // How much energy a cow eats
    public static volatile int PATCH_GROWBACK_RATE = 1;
    public static int MAX_ENERGY = 100;             // Energy growth per update for a patch
    public static int WAIT_PENALTY = 1;             // Energy cost for waiting
    public static int numCows = 50;
    public static int PATCH_SIZE = 12;               // Dimensions of each patch

    // Returns the patch that covers the given coordinates
    public Patch getPatch(int xc, int yc) {
        for (Agent agent : this.agents) {
            if (agent instanceof Patch) {
                // Check if (xc, yc) falls within the patch's area
                if (agent.getX() <= xc &&
                        agent.getX() + PATCH_SIZE >= xc &&
                        agent.getY() <= yc &&
                        agent.getY() + PATCH_SIZE >= yc) {
                    return (Patch) agent;
                }
            }
        }
        return null;
    }

    @Override
    public void populate() {
        // Create patches arranged in a grid covering the world
        for (int r = 0; r < SIZE; r += PATCH_SIZE) {
            for (int c = 0; c < SIZE; c += PATCH_SIZE) {
                Patch patch = new Patch(MAX_ENERGY);
                patch.setPosition(c, r);
                addAgent(patch);
            }
        }

        // Create cows and randomly assign them to patches
        List<Agent> copyOfPatches = new ArrayList<>(this.agents);
        Collections.shuffle(copyOfPatches);
        for (int i = 0; i < numCows && i < copyOfPatches.size(); i++) {
            Agent a = copyOfPatches.get(i);
            Cow cow = new Cow(100);
            cow.setPosition(a.getX(), a.getY());
            addAgent(cow);
        }
    }

    // Setters for simulation variables, used by commands or sliders
    public void setMoveEnergy(int value) {
        MOVE_ENERGY = value;
        changed();
    }

    public void setGreed(int value) {
        COW_GREEDINESS = value;
        changed();
    }

    public void setGrowbackRate(int value) {
        PATCH_GROWBACK_RATE = value;
        changed();
    }

    // Statistics methods for reporting current simulation states
    private double totalPatchesCount() {
        double count = 0;
        for (Agent a : agents) {
            if (a instanceof Patch)
                count++;
        }
        return count;
    }

    private double totalPatchesAlive() {
        double count = 0;
        for (Agent a : agents) {
            if (a instanceof Patch && ((Patch)a).getEnergy() > 0)
                count++;
        }
        return count;
    }

    private double totalCowsCount() {
        double count = 0;
        for (Agent a : agents) {
            if (a instanceof Cow)
                count++;
        }
        return count;
    }

    private double totalCowsAlive() {
        double count = 0;
        for (Agent a : agents) {
            if (a instanceof Cow && ((Cow)a).energy > 0)
                count++;
        }
        return count;
    }

    public ArrayList<String> getStats() {
        String[] stats = new String[5];
        stats[0] = "#Patches alive = " + totalPatchesAlive();
        stats[1] = "#Cows alive = " + totalCowsAlive();
        stats[2] = "#clock = " + clock;
        stats[3] = "% Cows alive = " + String.format("%.2f", totalCowsAlive() / totalCowsCount() * 100);
        stats[4] = "% Patches alive = " + String.format("%.2f", totalPatchesAlive() / totalPatchesCount() * 100);
        return new ArrayList<>(Arrays.asList(stats));
    }
}
