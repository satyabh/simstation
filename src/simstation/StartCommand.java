package simstation;

import mvc.*;

public class StartCommand extends Command {
    public StartCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World)model;
        world.stopAgents(); // Make sure everything is stopped first
        world.startAgents();
    }
}