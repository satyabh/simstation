package greed;

import simstation.Agent;

class Patch extends Agent {
    private Cow currentCow = null; // cow occupying the patch
    int energy;
    public static int growBackRate = 1;
    public static int patchSize = 12;

    public Patch(int energy) {
        super();
        this.energy = energy;
    }

    // Visiting cow attempts to lock patch if unoccupied
    public synchronized boolean tryLock(Cow cow) {
        if (currentCow == null) { // Change: check for null means the patch is free.
            currentCow = cow;
            return true;
        }
        return false;
    }

    // If visiting cow has no energy to move, they wait at the patch for currentCow to unlock()
    public synchronized void waitForLock(Cow cow) {
        while (currentCow != null) {
            try {
                wait(100);
                cow.loseEnergy(Meadow.WAIT_PENALTY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        currentCow = cow;
    }

    public synchronized void unlock(Cow cow) {
        if (currentCow == cow) {
            currentCow = null;
            notifyAll();
        }
    }

    public synchronized void eatMe(Cow cow, int amt) {
        // Assuming cow only eats patch if the patch has enough energy
        if (this.energy >= amt) {
            // if the patch has enough to satisfy cow's greed, cow consumes exactly the amount of energy
            this.energy -= amt;
            cow.energy += amt;
        } else {
            // cow consumes all available energy in the patch
            cow.energy += this.energy;
            this.energy = 0;
        }
        if (this.energy < 0) {
            // can never be negative
            this.energy = 0;
        }
    }

    @Override
    public void update() {
        if (energy <= 0) {
            this.stop();
            return;
        }
        // Energy grow-back
        if (!this.stopped && this.energy < 100) {
            this.energy += growBackRate;
            if (this.energy > 100) {
                this.energy = 100;
            }
        }
    }
}