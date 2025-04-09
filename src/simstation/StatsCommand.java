package simstation;

import mvc.*;

public class StatsCommand extends Command {
    public StatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World)model;
        String[] stats = world.getStats();
        Utilities.inform(stats);
    }
}