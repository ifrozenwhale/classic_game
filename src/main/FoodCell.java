package main;

import java.awt.*;

public class FoodCell extends Cell {

    public FoodCell(int x, int y) {
        super(x, y);
        color = GameColor.FOOD;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        // graphics2D.fillOval(wid * x + wid / 2, wid * y + wid / 2, wid, wid);
        graphics2D.fillRect(x * wid, y * wid, wid, wid);
    }
}
