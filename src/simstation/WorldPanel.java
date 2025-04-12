package simstation;

import mvc.*;

import javax.swing.*;
import java.awt.*;

public class WorldPanel extends AppPanel {
    public JPanel threadPanel = new JPanel();

    public WorldPanel(WorldFactory factory) {
        super(factory);
        threadPanel.setLayout(new GridLayout(1, 5));
        threadPanel.setOpaque(false);

        // Create buttons in thread panel
        JPanel p = new JPanel();
        p.setOpaque(false);
        JButton button = new JButton("Start");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Suspend");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Resume");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Stop");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Stats");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        // Add thread panel to control panel
        controlPanel.setLayout(new BorderLayout());
        p = new JPanel();
        p.setOpaque(false);
        p.add(threadPanel);
        controlPanel.add(p, BorderLayout.NORTH);
    }

    @Override
    public void setModel(Model m) {
        super.setModel(m);
        World w = (World)m;
        // No need to start threads here as it's handled by startAgents
    }

    @Override
    public void update() {
        view.repaint();
    }
}