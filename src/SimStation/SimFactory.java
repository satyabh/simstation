package SimStation;

import mvc.*;
import tools.Command;
import tools.Utilities;

public class SimFactory implements AppFactory {
    @Override
    public Model makeModel() {
        return new Field();
    }

    @Override
    public View makeView(Model model) {
        return new SimView((Field) model);
    }

    @Override
    public String getTitle() {
        return "MineField";
    }

    @Override
    public String getAbout() {
        return "Team 1, 2025. All rights reserved.";
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
        return new String[]{"NW", "N", "NE", "W", "E", "SW", "S", "SE"};
    }

    @Override
    public Command makeEditCommand(String name, Model model) {
        return switch (name) {
            case "NW" -> new MoveCommand(model, MoveCommand.Heading.NW);
            case "N" -> new MoveCommand(model, MoveCommand.Heading.N);
            case "NE" -> new MoveCommand(model, MoveCommand.Heading.NE);
            case "W" -> new MoveCommand(model, MoveCommand.Heading.W);
            case "E" -> new MoveCommand(model, MoveCommand.Heading.E);
            case "SW" -> new MoveCommand(model, MoveCommand.Heading.SW);
            case "S" -> new MoveCommand(model, MoveCommand.Heading.S);
            case "SE" -> new MoveCommand(model, MoveCommand.Heading.SE);
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
