package main;


import javax.swing.*;
import java.awt.*;

public class MineCell extends JButton {
    int px; // 在地图中的位置x
    int py; // 在地图中的位置y
    int num; // 周边8个单元格的地雷的数目
    boolean mine = false; // 是否有地雷
    CellType cellType = CellType.EMPTY; // 标记状态
    // static ImageIcon imageIcon = IconCreate.change(new ImageIcon("src/res/盘子.png"), 5, 5);


    public MineCell(int px, int py) {
        this.px = px;
        this.py = py;
        num = 0;
        setBackground(ColorType.EMPTY);

        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }


}
