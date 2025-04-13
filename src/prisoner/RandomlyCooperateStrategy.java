package prisoner;

import mvc.Utilities;

public class RandomlyCooperateStrategy implements CooperationStrategy {
    @Override
    public boolean cooperate(Prisoner prisoner) {
        return Utilities.rng.nextBoolean();
    }
}