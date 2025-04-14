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
