package simstation;

import mvc.*;

import java.util.ArrayList;

public class StatsCommand extends Command {
    public StatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World)model;
        ArrayList<String> stats = world.getStats();
        Utilities.inform(String.join("\n", stats));
    }
}