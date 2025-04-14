package greed;

import mvc.AppPanel;

import javax.swing.*;

class GreedSimulation {
        public static void main(String[] args) {
        AppPanel panel = new GreedPanel(new GreedFactory());

        panel.display();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.pack();
        frame.setResizable(false);
    }
}