package SimStation;

public class MoveException extends RuntimeException {
    public enum Type {INVALID, DISABLED, WIN, LOSS};
    private final Type type;
    public MoveException(Type type) {
        this.type = type;
    }
    public Type getType() {
        return type;
    }
}
