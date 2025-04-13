package plague;

import mvc.Command;
import mvc.Model;
import mvc.Utilities;

class SetPopulation extends Command {
    Integer value = null;

    public SetPopulation(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Initial Population Size = ?");
            value = Integer.valueOf(response);
        }
        Plague.INITIAL_POPULATION_SIZE = value;
    }
}

class SetInitialInfected extends Command {
    Integer value = null;

    public SetInitialInfected(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Initial % Infected = ?");
            value = Integer.valueOf(response);
        }
        Plague.INITIAL_INFECTED = value;
    }
}

class setVirulence extends Command {
    Integer value = null;

    public setVirulence(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Infection Probability = ?");
            value = Integer.valueOf(response);
        }
        ((Plague)model).setVirulence(value);
    }
}

class setResistance extends Command {
    Integer value = null;

    public setResistance(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Resistance to infection = ?");
            value = Integer.valueOf(response);
        }
        ((Plague)model).setResistance(value);
    }
}

class SetRecoveryTime extends Command {
    Integer value = null;

    public SetRecoveryTime(Model m) { super(m); }

    public void execute() throws Exception {
        if (value == null) {
            String response = Utilities.ask("Infection Probability = ?");
            value = Integer.valueOf(response);
        }
        ((Plague)model).SetRecoveryTime(value);
    }
}

class TriggerFatal extends Command {
    public TriggerFatal(Model m) { super(m); }

    public void execute() throws Exception {
        ((Plague)model).triggerFatal();
    }
}
