package plague;

import javax.swing.JSlider;

import mvc.Command;
import mvc.Model;
import mvc.View;
import simstation.World;
import simstation.WorldFactory;

class PlagueFactory extends WorldFactory {
    public Model makeModel() { return new Plague(); }
    public String getTitle() { return "Plague"; }
    public View makeView(Model model) {
        return new PlagueView((World)model);
    }
    public String[] getEditCommands() {
        return new String[] { "Start", "Pause", "Resume", "Stop", "Stats", "Initial % infected", "Infection Probability", "Initial Population Size", "Fatality/Recovery Time", "Resistance", "Toggle Fatal"};
    }
    public Command makeEditCommand(Model model, String type,  Object source) {
        Command cmmd = super.makeEditCommand(model, type, source);
        if (cmmd == null) {
            if (type.equals("Initial % infected")) {
                cmmd = new SetInitialInfected(model);
                if (source instanceof JSlider) {
                    ((SetInitialInfected)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Initial Population Size")) {
                cmmd = new SetPopulation(model);
                if (source instanceof JSlider) {
                    ((SetPopulation)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Infection Probability")) {
                cmmd = new setVirulence(model);
                if (source instanceof JSlider) {
                   //((setVirulence)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Fatality/Recovery Time")) {
                cmmd = new SetRecoveryTime(model);
                if (source instanceof JSlider) {
                   //((SetRecoveryTime)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Resistance")) {
                cmmd = new setResistance(model);
            } else if (type.equals("Fatal") || type.equals("Not Fatal") || type.equals("Toggle Fatal")) {
                cmmd = new TriggerFatal(model);
            }
        }
        return cmmd;
    }
}