package SimStation;

import mvc.Model;

import java.util.Random;

public class Field extends Model {
    private static final int GRID_SIZE = 10;
    private static final int PERCENT_MINED = 5;

    private final Cell[][] minefield;
    private GameState gameState = GameState.RUNNING;
    private int x = 0;
    private int y = 0;

    public enum GameState {
        RUNNING,
        FAIL,
        SUCCESS
    }

    public Field() {
        minefield = generateMinefield();
        setPosition(0, 0);
    }

    // Assumes unwinnable grid is possible
    // Top left and bottom right are never mined
    private Cell[][] generateMinefield() {
        Cell[][] minefield = new Cell[GRID_SIZE][GRID_SIZE];
        Random rand = new Random();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (row == 0 && col == 0 || successCondition(row, col)) {
                    minefield[row][col] = new Cell(false);
                    continue;
                }
                boolean mineDecision = rand.nextInt(100) < PERCENT_MINED;
                minefield[row][col] = new Cell(mineDecision);
            }
        }
        return minefield;
    }

    public void setPosition(int x, int y) {
        if (this.gameState != GameState.RUNNING) {
            throw new MoveException(MoveException.Type.DISABLED);
        }
        if (!isValidCell(x, y)) {
            throw new MoveException(MoveException.Type.INVALID);
        }

        updateCell(x, y);
        this.x = x;
        this.y = y;

        if (isMinedCell(x, y)) {
            this.gameState = GameState.FAIL;
            notifySubscribers(); // show the "Play Again" button and redraw view
            throw new MoveException(MoveException.Type.LOSS);
        }
        if (successCondition(x, y)) {
            this.gameState = GameState.SUCCESS;
            notifySubscribers(); // show the "Play Again" button and redraw view
            throw new MoveException(MoveException.Type.WIN);
        }
    }

    private void updateCell(int x, int y) {
        int val = 0;
        val += isMinedCell(x - 1, y + 1) ? 1 : 0;
        val += isMinedCell(x - 1, y) ? 1 : 0;
        val += isMinedCell(x - 1, y - 1) ? 1 : 0;
        val += isMinedCell(x, y + 1) ? 1 : 0;
        val += isMinedCell(x, y - 1) ? 1 : 0;
        val += isMinedCell(x + 1, y + 1) ? 1 : 0;
        val += isMinedCell(x + 1, y) ? 1 : 0;
        val += isMinedCell(x + 1, y - 1) ? 1 : 0;
        minefield[y][x].setVisible(true);
        minefield[y][x].setAdjacent(val);
        System.out.println("*");
        notifySubscribers();
    }

    private boolean isMinedCell(int x, int y) {
        if (!isValidCell(x, y)) {
            return false;
        }
        return minefield[y][x].isMined();
    }

    private boolean successCondition(int x, int y) {
        return x == GRID_SIZE - 1 && y == GRID_SIZE - 1;
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell[][] getMinefield() {
         return minefield;
    }

    public GameState getGameState() {
        return gameState;
    }
}