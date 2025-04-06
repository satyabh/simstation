package simstation;
import java.io.Serializable;

public class Agent implements Runnable, Serializable {
    int xc;
    int yc;
    private boolean paused = false;
    private boolean stopped = false;
    private String agentName;
    transient protected Thread myThread;
    @Override
    public void run() {

    }
    public void start() {}
    public void stop() {}
    public void pause() {}
    public void resume() {}
    public void update() {}
}
