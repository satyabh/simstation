package prisoner;

public class AlwaysCooperateStrategy implements CooperationStrategy {
    @Override
    public boolean cooperate(Prisoner prisoner) {
        return true;
    }
}