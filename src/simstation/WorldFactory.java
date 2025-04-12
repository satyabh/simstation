package simstation;

import mvc.*;

public abstract class WorldFactory implements AppFactory {
    public abstract Model makeModel();

    public View makeView(Model model) {
        return new WorldView((World)model);
    }

    @Override
    public String[] getEditCommands() {
        return new String[] {"Start", "Suspend", "Resume", "Stop", "Stats"};
    }

    @Override
    public Command makeEditCommand(Model model, String name, Object source) {
        World world = (World)model;
        switch(name) {
            case "Start": return new StartCommand(world);
            case "Stop": return new StopCommand(world);
            case "Suspend": return new SuspendCommand(world);
            case "Resume": return new ResumeCommand(world);
            case "Stats": return new StatsCommand(world);
            default: return null;
        }
    }

    @Override
    public String getTitle() {
        return "SimStation";
    }

    @Override
    public String getHelp() {
        return "Start: Starts the simulation\n" +
                "Stop: Stops the simulation\n" +
                "Suspend: Pauses the simulation\n" +
                "Resume: Resumes the simulation\n" +
                "Stats: Shows statistics";
    }

    @Override
    public String getAbout() {
        return "SimStation v2.0\nAuthor: Your Name";
    }
}