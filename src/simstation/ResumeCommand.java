package simstation;

import mvc.*;

public class ResumeCommand extends Command {
    public ResumeCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World)model;
        world.resumeAgents();
    }
}