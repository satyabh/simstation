package mvc;

public interface AppFactory {
    Model makeModel();

    View makeView(Model model);

    String getTitle();

    String getAbout();

    String getHelp();

    String[] getEditCommands();

    Command makeEditCommand(Model model, String name, Object source);
}
