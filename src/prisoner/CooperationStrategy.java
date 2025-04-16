package prisoner;

import java.io.Serializable;

public interface CooperationStrategy extends Serializable {
    boolean cooperate(Prisoner prisoner);
}