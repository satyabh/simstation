package prisoner;

public class TitForTatStrategy implements CooperationStrategy {
    @Override
    public boolean cooperate(Prisoner prisoner) {
        return !prisoner.getPartnerCheated();
    }
}