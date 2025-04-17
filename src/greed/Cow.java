// Cow.java
package greed;

import simstation.Heading;
import simstation.MobileAgent;

public class Cow extends MobileAgent {
    int energy;

    public Cow(int initialEnergy) {
        super();
        this.energy = initialEnergy;
    }

    @Override
    public void update() {
        Meadow meadow = (Meadow) this.world;
        Patch cur = meadow.getPatch(this.xc, this.yc);  // assume non-null

        if (cur != null) {
            // If patch can't satisfy greed and cow can move, move on
            if (cur.getEnergy() < Meadow.COW_GREEDINESS
                    && this.energy >= Meadow.MOVE_ENERGY) {
                this.energy -= Meadow.MOVE_ENERGY;
                heading = Heading.random();
                move(Meadow.PATCH_SIZE);
            }
            else {
                // otherwise, just call eatMe and let the patch block/wait as needed
                cur.eatMe(this, Meadow.COW_GREEDINESS);
            }
        }

        // death check
        if (this.energy <= 0) stop();
    }
    public void loseEnergy(int amount) {
        energy -= amount;
        if (energy < 0) {
            energy = 0;
        }
        if (energy == 0) {
            stop();
        }
    }

}