package greed;

import mvc.Command;
import mvc.Model;
import mvc.Utilities;

class SetGreed extends Command {
    Integer value = null;

    public SetGreed(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Greed = ?");
            value = Integer.valueOf(response);
        }
        ((Meadow)model).setGreed(value);
    }
}

class SetGrowbackRate extends Command {
    Integer value = null;

    public SetGrowbackRate(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Grow back rate = ?");
            value = Integer.valueOf(response);
        }
        ((Meadow)model).SetGrowbackRate(value);
    }
}

class SetMoveEnergy extends Command {
    Integer value = null;

    public SetMoveEnergy(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Move Energy = ?");
            value = Integer.valueOf(response);
        }
        ((Meadow)model).setMoveEnergy(value);
    }
}