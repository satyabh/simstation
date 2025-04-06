package SimStation;

import java.io.Serializable;

public class Cell implements Serializable {
    private final boolean isMined;
    private boolean visible;
    private int adjacentCount;

    public Cell(boolean isMined) {
        this.isMined = isMined;
        this.visible = false;
    }

    public boolean isMined() {
        return isMined;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setAdjacent(int count) {
        this.adjacentCount = count;
    }

    public String getLook() {
        return visible ? String.valueOf(adjacentCount) : "?";
    }
}
