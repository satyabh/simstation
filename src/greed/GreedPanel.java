package greed;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Arrays;

import simstation.WorldPanel;

class GreedPanel extends WorldPanel implements ChangeListener {
    JPanel panelButtons = new JPanel();
    JSlider[] sliders = new JSlider[3];
    JButton FatalBtn = new JButton();

    public GreedPanel(GreedFactory factory) {
        super(factory);

        panelButtons.setLayout(new GridLayout(6, 1));
        panelButtons.setOpaque(false);

        sliders[0] = new JSlider(JSlider.HORIZONTAL, 0, 100, Meadow.COW_GREEDINESS);
        sliders[0].setLabelTable(sliders[0].createStandardLabels(10));
        
        sliders[1] = new JSlider(JSlider.HORIZONTAL, 0, 10, Meadow.PATCH_GROWBACK_RATE);
        sliders[1].setMajorTickSpacing(3);
        sliders[1].setLabelTable(sliders[1].createStandardLabels(2));

        sliders[2] = new JSlider(JSlider.HORIZONTAL, 0, 50, Meadow.MOVE_ENERGY);
        sliders[2].setMajorTickSpacing(3);
        sliders[2].setLabelTable(sliders[2].createStandardLabels(10));
        
        sliders[0].addChangeListener(this);
        sliders[1].addChangeListener(this);
        sliders[2].addChangeListener(this);

        // Sliders
        int sliderLabelsSize = factory.getEditCommands().length;
        String[] sliderLabels = Arrays.copyOfRange(factory.getEditCommands(), sliderLabelsSize - 3, sliderLabelsSize);
        for (int i = 0; i < sliders.length; i++) {
            sliders[i].setPaintTicks(true);
            sliders[i].setPaintLabels(true);

            JPanel pp = new JPanel();
            pp.setLayout(new BorderLayout());
            pp.setOpaque(false);
    
            JPanel ppp = new JPanel();
            ppp.setOpaque(false);
            ppp.add(new JLabel(sliderLabels[i]));
            pp.add(ppp, BorderLayout.NORTH);
    
            ppp = new JPanel();
            ppp.setOpaque(false);
            ppp.add(sliders[i]);
            pp.add(ppp, BorderLayout.CENTER);
            panelButtons.add(pp);
        }

        controlPanel.add(panelButtons, BorderLayout.CENTER);
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sliders[0]) {
            ((Meadow)model).setGreed(sliders[1].getValue());
        }
        if (e.getSource() == sliders[1]) {
            ((Meadow)model).setGrowbackRate(sliders[1].getValue());
        }
        if (e.getSource() == sliders[2]) {
            ((Meadow)model).setMoveEnergy(sliders[2].getValue());
        }
        
        ((Meadow)model).changed();
    }

    public void update() {
        sliders[0].setValue(Meadow.COW_GREEDINESS);
        sliders[1].setValue(Meadow.PATCH_GROWBACK_RATE);
        sliders[2].setValue(Meadow.COW_GREEDINESS);
        repaint();
    }
}