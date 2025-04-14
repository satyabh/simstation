package plague;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import mvc.AppPanel;

public class PlagueSimulation {
        public static void main(String[] args) {
        AppPanel panel = new PlaguePanel(new PlagueFactory());

        panel.display();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.pack();
        frame.setResizable(false);
    }
}
