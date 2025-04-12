package simstation;

import mvc.*;

public class SuspendCommand extends Command {
    public SuspendCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World)model;
        world.suspendAgents();
    }
}