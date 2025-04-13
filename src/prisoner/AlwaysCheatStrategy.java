package prisoner;

public class AlwaysCheatStrategy implements CooperationStrategy {
    @Override
    public boolean cooperate(Prisoner prisoner) {
        return false;
    }
}