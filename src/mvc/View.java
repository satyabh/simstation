package mvc;

import tools.Subscriber;
import javax.swing.*;
import java.awt.*;

public abstract class View extends JPanel implements Subscriber {
    protected Model model;

    public View(Model model) {
        this.model = model;
    }

    public abstract void update();

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
    }

    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        update();
    }
}
