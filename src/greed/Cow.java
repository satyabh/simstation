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
            // cow attempts to lock patch by passing itself
            boolean locked = curPatch.tryLock(this);
            if (!locked) {
                // patch was unavailable, cow looks to move
                if (this.energy >= Meadow.moveEnergy) {
                    this.energy -= Meadow.moveEnergy;
                    heading = Heading.random();
                    move(Patch.patchSize);
                    return; // skip eating for this tick
                } else {
                    // cow did not have enough energy to move, forced to wait
                    curPatch.waitForLock(this); // join the queue
                    if (this.energy <= 0) {
                        // starves from waiting too long
                        stop();
                        return;
                    }
                }
            }
            curPatch.eatMe(this, greediness);
            curPatch.unlock(this); // makes available for other cows
        }
        /* Movement */ 
        // Spend moveEnergy if possible to move to new patch
        if (this.energy >= Meadow.moveEnergy) {
            this.energy -= Meadow.moveEnergy;
            heading = Heading.random();
            move(Patch.patchSize);
            // Snap to patch
            if (this.getX() % Patch.patchSize != 0 ) this.xc = curPatch.getX();
            if (this.getY() % Patch.patchSize != 0 ) this.yc = curPatch.getY();
        } else {
            // cow does not have energy to move
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.loseEnergy(Meadow.WAIT_PENALTY);
        }
        if (this.energy <= 0) {
            stop();
        }
    }

    public void loseEnergy(int amount) {
        this.energy -= amount;
    }
}
