package simstation;

import mvc.*;
import java.awt.Point;
import java.util.*;

public abstract class World extends Model {
    public static final int SIZE = 250;

    protected int clock;
    protected int alive;
    protected ObserverAgent observer;
    protected ArrayList<Agent> agents;
    protected transient boolean running;

    public World() {
        agents = new ArrayList<Agent>();
        clock = 0;
        alive = 0;
        observer = new ObserverAgent();
        observer.setWorld(this);
        running = false;
    }

    public void addAgent(Agent a) {
        a.setWorld(this);
        agents.add(a);
    }

    public Iterator<Agent> iterator() {
        return agents.iterator();
    }

    public int getPopulation() {
        return agents.size();
    }

    public int getClock() {
        return clock;
    }

    public synchronized void updateStatistics() {
        clock++;
        alive = 0;
        for (Agent agent : agents) {
            if (!agent.stopped) {
                alive++;
            }
        }
        changed();
    }

    public Agent getNeighbor(Agent requestingAgent, int radius) {
        if (agents.size() <= 1) return null;

        int startIndex = new Random().nextInt(agents.size());
        int currentIndex = startIndex;

        do {
            Agent candidate = agents.get(currentIndex);
            if (candidate != requestingAgent) {
                int dx = Math.abs(candidate.getX() - requestingAgent.getX());
                int dy = Math.abs(candidate.getY() - requestingAgent.getY());

                // Check for wrap-around distances
                dx = Math.min(dx, SIZE - dx);
                dy = Math.min(dy, SIZE - dy);

                if (dx <= radius && dy <= radius) {
                    return candidate;
                }
            }

            currentIndex = (currentIndex + 1) % agents.size();
        } while (currentIndex != startIndex);

        return null;
    }

    // Called when an agent moves
    public void changed(String agentName, Point oldPoint, Point newPoint) {
        setUnsavedChanges(true);
        notifySubscribers();
    }

    // When model changes
    protected void changed() {
        setUnsavedChanges(true);
        notifySubscribers();
    }

    // Abstract method to populate the world
    public abstract void populate();

    // Thread control methods
    public void startAgents() {
        if (!running) {
            running = true;
            populate();
            for (Agent agent : agents) {
                agent.start();
            }
            observer.start();
            changed();
        }
    }

    public void stopAgents() {
        if (running) {
            running = false;
            for (Agent agent : agents) {
                agent.stop();
            }
            agents.clear();
            observer.stop();
            changed();
        }
    }

    public void suspendAgents() {
        if (running) {
            for (Agent agent : agents) {
                agent.suspend();
            }
            observer.suspend();
            changed();
        }
    }

    public void resumeAgents() {
        if (running) {
            for (Agent agent : agents) {
                agent.resume();
            }
            observer.resume();
            changed();
        }
    }

    public String[] getStats() {
        String[] stats = new String[2];
        stats[0] = "#agents = " + agents.size();
        stats[1] = "clock = " + clock;
        return stats;
    }
}