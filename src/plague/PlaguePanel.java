package plague;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Arrays;

import simstation.WorldPanel;

class PlaguePanel extends WorldPanel implements ChangeListener {
    JPanel panelButtons = new JPanel();
    JSlider[] sliders = new JSlider[4];
    JButton FatalBtn = new JButton();

    public PlaguePanel(PlagueFactory factory) {
        super(factory);

        panelButtons.setLayout(new GridLayout(6, 1));
        panelButtons.setOpaque(false);

        sliders[0] = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        sliders[0].setMinorTickSpacing(1);
        sliders[0].setMajorTickSpacing(3);
        sliders[0].setLabelTable(sliders[0].createStandardLabels(10));
        
        sliders[1] = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        sliders[1].setMinorTickSpacing(1);
        sliders[1].setMajorTickSpacing(3);
        sliders[1].setLabelTable(sliders[1].createStandardLabels(10));

        sliders[2] = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        sliders[2].setMinorTickSpacing(1);
        sliders[2].setMajorTickSpacing(3);
        sliders[2].setLabelTable(sliders[2].createStandardLabels(20));
        sliders[2].setPreferredSize(new Dimension(420, 50));

        sliders[3] = new JSlider(JSlider.HORIZONTAL, 0, 500, 200);
        sliders[3].setMinorTickSpacing(1);
        sliders[3].setMajorTickSpacing(3);
        sliders[3].setLabelTable(sliders[3].createStandardLabels(50));
        sliders[3].setPreferredSize(new Dimension(420, 50));

        FatalBtn = new JButton("Not Fatal");
        
        sliders[0].addChangeListener(this);
        sliders[1].addChangeListener(this);
        sliders[2].addChangeListener(this);
        sliders[3].addChangeListener(this);
        FatalBtn.addActionListener(this);

        // Sliders
        int sliderLabelsSize = factory.getEditCommands().length;
        String[] sliderLabels = Arrays.copyOfRange(factory.getEditCommands(), sliderLabelsSize - 5, sliderLabelsSize);
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
        // Fatal btn
        JPanel fatalBtnWrapper = new JPanel();
        fatalBtnWrapper.setLayout(new BorderLayout());
        fatalBtnWrapper.setOpaque(false);

        JPanel ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(FatalBtn);
        fatalBtnWrapper.add(ppp, BorderLayout.CENTER);
        panelButtons.add(fatalBtnWrapper);

        controlPanel.add(panelButtons, BorderLayout.CENTER);
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sliders[0]) {
            Plague.INITIAL_INFECTED = sliders[0].getValue();
        }
        if (e.getSource() == sliders[1]) {
            ((Plague)model).setVirulence(sliders[1].getValue());
        }
        if (e.getSource() == sliders[2]) {
            Plague.INITIAL_POPULATION_SIZE = sliders[2].getValue();
        }
        if (e.getSource() == sliders[3]) {
            ((Plague)model).SetRecoveryTime(sliders[3].getValue());
        }
        if (e.getSource() == "Not Fatal") {
            ((Plague)model).triggerFatal();
        }
        
        ((Plague)model).changed();
    }

    public void update() {
        sliders[0].setValue(Plague.INITIAL_INFECTED);
        sliders[1].setValue(Plague.VIRULENCE);
        sliders[2].setValue(Plague.INITIAL_POPULATION_SIZE);
        sliders[3].setValue(Plague.RECOVERY_FATALITY_TIME);
        repaint();
    }
}