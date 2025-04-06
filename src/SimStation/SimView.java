package SimStation;

import mvc.*;

import java.awt.*;

public class SimView extends View {
    public SimView(Field field) {
        super(field);
        model.subscribe(this);
    }

    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        int y = 0;
        int width = getWidth()/10;
        int height = getHeight()/10;

        Field f = (Field) model;
        Cell[][] mineField = f.getMinefield();
        for (int r = 0; r < mineField.length; r++) {
            for (int c = 0; c < mineField[r].length; c++) {
                Cell cell = mineField[r][c];
                if (cell != null) {

                    // cell outlines
                    if (r == mineField.length - 1 && c == mineField[0].length - 1) {
                        g.setColor(Color.GREEN);
                    }
                    g.drawRect(x, y, width, height);

                    // current and visited cells
                    if (r == f.getY() && c == f.getX()) {
                        g.setColor(Color.ORANGE);
                        if (r == mineField.length - 1 && c == mineField[0].length - 1) {
                            g.setColor(Color.GREEN);
                        }
                        if (cell.isMined()) {
                            g.setColor(Color.RED);
                        }
                        g.fillRect(x, y, width, height);
                    }
                    else if (cell.isVisible()) {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(x, y, width, height);
                    }

                    // number of mines
                    g.setColor(Color.BLACK);
                    String text = cell.getLook();
                    Font font = new Font("Arial", Font.PLAIN, width / 2); // Adjust font size
                    g.setFont(font);
                    FontMetrics metrics = g.getFontMetrics(font);
                    int textWidth = metrics.stringWidth(text);
                    int textHeight = metrics.getHeight();
                    int textX = x + (width - textWidth) / 2;
                    int textY = y + (width - textHeight) / 2 + metrics.getAscent();
                    g.drawString(text, textX, textY);

                }
                else {
                    System.out.println(r + " " + c);
                }
                x += width;
            }
            x = 0;
            y += height;
        }
    }
}