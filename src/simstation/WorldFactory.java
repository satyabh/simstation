package simstation;

import mvc.*;
import mvc.Command;
import mvc.Utilities;

public class WorldFactory implements AppFactory {
    @Override
    public Model makeModel() {
        return new World();
    }

    @Override
    public View makeView(Model model) {
        return new WorldView((World) model);
    }

    @Override
    public String getTitle() {
        return "simstation";
    }

    @Override
    public String getAbout() {
        return "Team 5, 2025. All rights reserved.";
    }

    @Override
    public String getHelp() {
        return Utilities.buildMultilineString(
                "Save: Save minefield to file",
                "Save As: Save minefield to new file",
                "Open: Open new minefield from file",
                "Quit: Quit the program",
                "Edit: Move in the specified direction"
        );
    }

    @Override
    public String[] getEditCommands() {
        return new String[]{""};
    }

    @Override
    public Command makeEditCommand(String name, Model model) {
        return null;
    }
}
