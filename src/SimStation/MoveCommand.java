package SimStation;

import mvc.Model;
import tools.Command;

public class MoveCommand extends Command {
    private final Heading heading;

    public MoveCommand(Model model, Heading heading) {
        super(model);
        this.heading = heading;
    }

    public enum Heading {
        NW,
        N,
        NE,
        W,
        E,
        SW,
        S,
        SE
    }

    @Override
    public void execute() {
        if (!(model instanceof Field field)) {
            return;
        }
        int x = field.getX();
        int y = field.getY();
        switch (heading) {
            case NW -> field.setPosition(x - 1, y - 1);
            case N -> field.setPosition(x, y - 1);
            case NE -> field.setPosition(x + 1, y - 1);
            case W -> field.setPosition(x - 1, y);
            case E -> field.setPosition(x + 1, y);
            case SW -> field.setPosition(x - 1, y + 1);
            case S -> field.setPosition(x, y + 1);
            case SE -> field.setPosition(x + 1, y + 1);
        }
    }
}
