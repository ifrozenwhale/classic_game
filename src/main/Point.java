package main;

import java.awt.*;

public class Point {
    /**
     * 索引位置 x
     */
    private int x;
    /**
     * 索引位置 y
     */
    private int y;
    /**
     * 宽度 d
     */
    private int d;
    /**
     * 圆心 ox
     */
    private int ox;
    /**
     * 圆心 oy
     */
    private int oy;
    /**
     * 左上角绝对位置 abx
     */
    private int abx;
    /**
     * 左上角绝对位置 aby
     */
    private int aby;
    /**
     * 单元格类型
     */
    private PointType type = PointType.NULL;

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    public int getD() {
        return d;
    }

    public int getAbx() {
        return abx;
    }

    public int getAby() {
        return aby;
    }

    private Color color = Color.BLACK;

    public void draw(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, (RenderingHints.VALUE_TEXT_ANTIALIAS_GASP));
        graphics2D.setColor(color);
        graphics2D.fillOval(abx, aby, d - 1, d - 1);
    }

    public int getOx() {
        return ox;
    }

    public int getOy() {
        return oy;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", d=" + d +
                ", ox=" + ox +
                ", oy=" + oy +
                ", abx=" + abx +
                ", aby=" + aby +
                ", type=" + type +
                ", color=" + color +
                '}';
    }

    public Point(int x, int y, Color color, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.color = color;
        abx = x * d - d / 2;
        aby = y * d - d / 2;
        ox = x * d;
        oy = y * d;
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
}
