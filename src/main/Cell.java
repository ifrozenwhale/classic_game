package main;

import java.awt.*;

public abstract class Cell {
    protected int x;
    protected int y;
    protected int wid = 10;
    protected Color color;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Cell(){
        x = 0;
        y = 0;
        Color color = GameColor.BORDER;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw(Graphics2D graphics2D);
}
