package tools;

import mvc.Model;

public abstract class Command {
    protected final Model model;

    public Command(Model model) {
        this.model = model;
    }

    public abstract void execute();
}
