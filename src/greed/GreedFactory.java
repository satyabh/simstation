package greed;

import javax.swing.JSlider;

import mvc.Command;
import mvc.Model;
import mvc.View;
import simstation.World;
import simstation.WorldFactory;

class GreedFactory extends WorldFactory {
    public Model makeModel() { return new Meadow(); }
    public String getTitle() { return "Greed"; }
    public View makeView(Model model) {
        return new GreedView((World)model);
    }
    public String[] getEditCommands() {
        return new String[] { "Start", "Pause", "Resume", "Stop", "Stats", "Greed", "Grow back rate", "Move Energy"};
    }
    public Command makeEditCommand(Model model, String type,  Object source) {
        Command cmmd = super.makeEditCommand(model, type, source);
        if (cmmd == null) {
            if (type.equals("Greed")) {
                cmmd = new SetGreed(model);
                if (source instanceof JSlider) {
                    ((SetGreed)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Grow back rate")) {
                cmmd = new SetGrowbackRate(model);
                if (source instanceof JSlider) {
                    ((SetGrowbackRate)cmmd).value = ((JSlider)source).getValue();
                }
            } else if (type.equals("Move Energy")) {
                cmmd = new SetMoveEnergy(model);
                if (source instanceof JSlider) {
                   // ((SetMoveEnergy)cmmd).value = ((JSlider)source).getValue();
                }
            }
        }
        return cmmd;
    }
}