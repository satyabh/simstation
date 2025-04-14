package greed;

import simstation.Heading;
import simstation.MobileAgent;

class Cow extends MobileAgent {
    int energy;
    static int greediness = 25;

    public Cow(int energy) {
        super();
        this.energy = energy;
    }

    @Override
    public void update() {
        Meadow meadow = (Meadow) this.world;
        Patch curPatch = meadow.getPatch(this.xc, this.yc);
        if (curPatch != null) {
            // Cow attempts to lock patch by passing itself
            boolean locked = curPatch.tryLock(this);
            if (!locked) {
                // Patch was unavailable, cow looks to move if it has enough energy
                if (this.energy >= Meadow.moveEnergy) {
                    this.energy -= Meadow.moveEnergy;
                    heading = Heading.random();
                    move(Patch.patchSize);
                    return; // Skip eating for this tick
                } else {
                    // Cow does not have enough energy to move, so it waits
                    curPatch.waitForLock(this); // Join the wait
                    if (this.energy <= 0) {
                        // Starves from waiting too long
                        stop();
                        return;
                    }
                }
            }
            // Now the cow has obtained the patch's lock
            curPatch.eatMe(this, greediness);
            curPatch.unlock(this); // Release for other cows
        }
        /* Movement */
        // Spend moveEnergy if possible to move to a new patch
        if (this.energy >= Meadow.moveEnergy) {
            this.energy -= Meadow.moveEnergy;
            heading = Heading.random();
            move(Patch.patchSize);
            // Snap to patch alignment
            if (this.getX() % Patch.patchSize != 0)
                this.xc = curPatch.getX();
            if (this.getY() % Patch.patchSize != 0)
                this.yc = curPatch.getY();
        } else {
            // Cow does not have energy to move
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.loseEnergy(Meadow.WAIT_PENALTY);
        }
        if (this.energy <= 0) { // Corrected condition
            stop();
        }
    }

    public void loseEnergy(int amount) {
        this.energy -= amount;
    }
}