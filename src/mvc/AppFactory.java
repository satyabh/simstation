package mvc;

import tools.Command;

public interface AppFactory {
    Model makeModel();

    View makeView(Model model);

    String getTitle();

    String getAbout();

    String getHelp();

    String[] getEditCommands();

    Command makeEditCommand(String name, Model model);
}
