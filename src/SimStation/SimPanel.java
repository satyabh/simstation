package SimStation;

import mvc.AppFactory;
import mvc.AppPanel;
import mvc.Model;
import tools.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimPanel extends AppPanel {
    private final JButton playAgainButton = new JButton("Play Again");

    public SimPanel(AppFactory factory) {
        super(factory);
        createButtons(this);
        model.subscribe(this);
    }

    @Override
    public void update() {
        Field field = (Field) model;
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    protected void createButtons(ActionListener listener) {
        JPanel p = new JPanel();
        p.setBackground(Color.PINK);

        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        JButton resume = new JButton("Resume");
        JButton stop = new JButton("Stop");
        JButton stats = new JButton("Stats");
        p.add(start);
        p.add(pause);
        p.add(resume);
        p.add(stop);
        p.add(stats);
        start.addActionListener(listener);
        pause.addActionListener(listener);
        resume.addActionListener(listener);
        stop.addActionListener(listener);
        stats.addActionListener(listener);
        controlPanel.add(p);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        try {
            switch (cmmd) {
                case "Save" -> Utilities.save(model, false);
                case "Save As" -> Utilities.save(model, true);
                case "Open" -> {
                    Model newModel = Utilities.open(model);
                    if (newModel != null) {
                        replaceModel(newModel);
                    }
                }
                case "New" -> {
                    Utilities.saveChanges(model);
                    replaceModel(factory.makeModel());
                }
                case "Quit" -> {
                    Utilities.saveChanges(model);
                    System.exit(0);
                }
                case "About" -> Utilities.inform(factory.getAbout());
                case "Help" -> Utilities.inform(factory.getHelp());
                default -> {
                    factory.makeEditCommand(cmmd, model).execute();
                    view.update();
                }
            }
        }
        catch (Exception exception) {
            String message;
            Utilities.inform(exception.getMessage());
        }
    }

    public void replaceModel(Model newModel) {
        // Unsubscribe and update the model
        model.unsubscribe(this);
        this.model = newModel;
        newModel.subscribe(this);
        view.setModel(newModel);
        newModel.notifySubscribers();

        // Rebuild the control panel so that new action listeners are created
        controlPanel.removeAll();
        createButtons(this);
        controlPanel.revalidate();
        controlPanel.repaint();
    }
}