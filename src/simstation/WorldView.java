package simstation;

import mvc.*;

import java.awt.*;

public class WorldView extends View {
    public WorldView(World world) {
        super(world);
        model.subscribe(this);
    }

    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}