package greed;

import simstation.Agent;

public class Patch extends Agent {
    private int energy;

    public Patch(int initialEnergy) {
        super();
        this.energy = initialEnergy;
    }

    public synchronized void eatMe(Cow cow, int amt) {
        while (energy < amt) {
            cow.loseEnergy(Meadow.WAIT_PENALTY);
            if (cow.energy <= 0) {
                cow.stop();
                return;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        energy -= amt;
        cow.energy += amt;
        if (energy < 0) {
            energy = 0;
        }
        notifyAll();
    }

    @Override
    public synchronized void update() {
        // Regrow only if patch is still “alive”
        if (energy > 0 && energy < Meadow.MAX_ENERGY) {
            energy += Meadow.PATCH_GROWBACK_RATE;
            if (energy > Meadow.MAX_ENERGY) {
                energy = Meadow.MAX_ENERGY;
            }
        }
        // Notify all waiting cows that energy may have changed
        notifyAll();
    }

    public synchronized int getEnergy() {
        return energy;
    }

}
