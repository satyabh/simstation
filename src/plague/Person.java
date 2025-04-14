package plague;

import simstation.Heading;
import simstation.MobileAgent;

class Person extends MobileAgent {
    boolean infected;
    int infectedStartClock;
    boolean dead = false;

    public Person(boolean infected) {
        super();
        this.infected = infected;
        if (this.infected) this.infectedStartClock = 0;
    }

    public void update() {
        Person neighbor = (Person) world.getNeighbor(this, 10);

        // Infect neighbor
        if (this.infected && neighbor != null && !neighbor.isInfected()) {
            if (Math.random() < (Plague.VIRULENCE / 100.0)) { // infection attempts to infect person
                if (Math.random() > Plague.RESISTANCE / 100.0) { // person has a second chance, usually low
                    ((Person)neighbor).setInfected(true);
                    ((Person)neighbor).setInfectedStartClock(world.getClock());
                }
            }
        }

        // Fatality / Recovery
        if (this.infected && this.world.getClock() >= this.infectedStartClock + Plague.RECOVERY_FATALITY_TIME) {
            this.setInfected(false);
            if (Plague.FATAL) {
                this.dead = true;
                this.stop(); 
            }
        }

        heading = Heading.random();
        move(4);
    }

    public boolean isInfected() { return infected; }
    public void setInfected(boolean infected) { this.infected = infected; }
    private void setInfectedStartClock(int clock) { this.infectedStartClock = clock; }
}