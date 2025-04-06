package mvc;

import tools.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AppPanel extends JPanel implements Subscriber, ActionListener {
    public static final int FRAME_WIDTH = 500;
    public static final int FRAME_HEIGHT = 300;

    protected final AppFactory factory;
    protected final JFrame frame;
    protected final JPanel controlPanel;
    protected final View view;
    protected Model model;

    public AppPanel(AppFactory factory) {
        this.factory = factory;
        model = factory.makeModel();
        view = factory.makeView(model);
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY);
        controlPanel.setLayout(new GridLayout(1, 2));

        this.setLayout(new GridLayout(1, 2));
        this.add(controlPanel);
        this.add(view);

        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void update() {

    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Save As", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        try {
            switch (cmmd) {
                case "Save" -> Utilities.save(model, false);
                case "Save As" -> Utilities.save(model, true);
                case "Open" -> {
                    Model newModel = Utilities.open(model);
                    if (newModel != null) {
                        setModel(newModel);
                    }
                }
                case "New" -> {
                    Utilities.saveChanges(model);
                    setModel(factory.makeModel());
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

        } catch (Exception ex) {
            Utilities.error(ex);
        }
    }

    private void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        view.setModel(this.model);
        model.notifySubscribers();
    }
}