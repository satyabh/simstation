package simstation;

import java.io.Serializable;
import java.util.Random;

public abstract class Agent implements Runnable, Serializable {
    private static final int DELAY = 20; // 20ms sleep time for smooth graphics

    protected String name;
    protected World world;
    protected int xc, yc; // coordinates in the world
    protected boolean suspended;
    protected boolean stopped;
    transient protected Thread myThread;

    public Agent() {
        this.suspended = false;
        this.stopped = false;
        Random rng = new Random();
        this.xc = rng.nextInt(World.SIZE);
        this.yc = rng.nextInt(World.SIZE);
        this.name = "Agent" + hashCode();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return xc;
    }

    public int getY() {
        return yc;
    }

    public void setPosition(int x, int y) {
        this.xc = x;
        this.yc = y;
    }

    // Thread control methods
    public synchronized void start() {
        myThread = new Thread(this);
        myThread.start();
    }

    public synchronized void suspend() {
        suspended = true;
    }

    public synchronized void resume() {
        suspended = false;
        notify();
    }

    public synchronized void stop() {
        stopped = true;
        if (suspended) {
            suspended = false;
            notify();
        }
    }

    // Hook methods that can be overridden
    protected void onStart() { }
    protected void onInterrupted() { }
    protected void onExit() { }

    @Override
    public void run() {
        onStart();
        while (!stopped) {
            try {
                synchronized(this) {
                    while (suspended) {
                        wait();
                    }
                }
                update();
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                onInterrupted();
            }
        }
        onExit();
    }

    // To be implemented by subclasses
    public abstract void update();
}