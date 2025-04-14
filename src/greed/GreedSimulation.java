package greed;

import mvc.AppPanel;

class GreedSimulation {
        public static void main(String[] args) {
        AppPanel panel = new GreedPanel(new GreedFactory());

        panel.display();
    }
}