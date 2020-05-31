package main;

import java.awt.*;

public class SnakeCell extends Cell{

    public SnakeCell() {
        super();
        x = 0;
        y = 0;
        color = GameColor.SNAKE;
    }

    @Override
    public void draw(Graphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.fillRect(x * wid, y * wid, wid, wid);
    }

    public SnakeCell(int x, int y) {
        super(x, y);
        color = GameColor.SNAKE;
    }

}
