package simstation;
import mvc.Model;

public class World extends Model {
    static int SIZE = 500;
    public int clock = 0;
    public int alive = 0;

    public void addAgent(Agent a) {}
    public void startAgents() {}
    public void stopAgents() {}
    public void pauseAgents() {}
    public void resumeAgents() {}
    private void populate() {}
    public String getStatus() { return null; }
    private void updateStatistics() {}
    public Agent getNeighbor(Agent caller, int radius) { return null; }
}
