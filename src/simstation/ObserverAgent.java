package simstation;

public class ObserverAgent extends Agent {
    private static final int STATS_INTERVAL = 100; // Update stats every 100ms

    public ObserverAgent() {
        super();
        name = "Observer";
    }

    @Override
    public void update() {
        try {
            Thread.sleep(STATS_INTERVAL);
            if (world != null) {
                world.updateStatistics();
            }
        } catch (InterruptedException e) {
            onInterrupted();
        }
    }
}