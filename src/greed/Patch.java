package greed;

import simstation.Agent;

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