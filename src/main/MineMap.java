package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MineMap extends JPanel {
    private int w;
    private int h;
    private int numMine = 10;
    private MineCell[][] mineCells;
    private boolean tip = false;
    private JTextField textField;
    private boolean start = false;
    private TimeThread timeThread;
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getNumMine() {
        return numMine;
    }

    /**
     * 设置地雷数
     * @param numMine 地雷数
     */
    public void setNumMine(int numMine) {
        if (numMine >= w * h){
            JOptionPane.showMessageDialog(null, "地雷数太多");
            return;
        }
        this.numMine = numMine;
    }

    /**
     * 初始化单元格
     */
    public void initCell(){
        setLayout(new GridLayout(w, h));
        setSize(new Dimension(20 + w * 20,20 + h * 20)); // 设置尺寸
        mineCells = new MineCell[w][h];
        // init default
        for (int x = 0; x < h; x++){
            for (int y = 0; y < w; y++){
                mineCells[x][y] = new MineCell(x, y);
                mineCells[x][y].setPreferredSize(new Dimension(20, 20));
                mineCells[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        isClicked(e);
                    }
                });
                add(mineCells[x][y]);
            }
        }
        repaint();
    }

    /**
     * 构造函数
     * @param w 宽
     * @param h 高
     * @param restWines 剩余地雷显示框
     */
    public MineMap(int w, int h, JTextField restWines) {
        this.w = w;
        this.h = h;
        this.textField = restWines;

        setPreferredSize(new Dimension(500, 500));
    }

    /**
     * 计算九宫格内地雷数
     */
    private void setNumAround(){
        int i, j, k, num, x, y;
        for (i = 0; i < h; i++) {
            for (j = 0; j < w; j++) {
                num = 0;
                if (!mineCells[i][j].isMine()) {
                    for (x = i - 1; x <= i + 1; x++) {
                        for (y = j - 1; y <= j + 1; y++) {
                            if (x >= 0 && x < w && y >= 0 && y < h && mineCells[x][y].isMine())  //判断数组是否发生越界以及是否有雷
                                num++;
                        }
                    }
                    mineCells[i][j].setNum(num);
                }
            }
        }
    }

    /**
     * 展开安全区域
     * @param i
     * @param j
     */
    public void extendSafeZone(int i, int j) {//用以展开安全区域
        if(mineCells[i][j].getNum() == 0){
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    if (x >= 0 && x < w && y >= 0 && y < h && mineCells[x][y].getCellType() == CellType.EMPTY){
                        mineCells[x][y].setCellType(CellType.SAFETY);
                        if (mineCells[x][y].getNum() == 0) {
                            setSafetyUI(mineCells[x][y]);
                            extendSafeZone(x, y);//判断是否越界、是否已经展开、是否为雷区{
                        } else{
                            setBorderUI(mineCells[x][y]);
                        }
                    }
                }
            }
        }else if(mineCells[i][j].getCellType() == CellType.EMPTY){
            mineCells[i][j].setCellType(CellType.SAFETY);
            setBorderUI(mineCells[i][j]);
        }else{
            System.out.println(mineCells[i][j].getCellType());
            System.out.println("what?");
        }
    }

    /**
     * 设置安全与区UI
     * @param mineCell
     */
    private void setSafetyUI(MineCell mineCell){
        mineCell.setText(mineCell.getNum() + "");
        mineCell.setForeground(Color.lightGray);
        mineCell.setBackground(ColorType.SAFETY);
    }

    private void setBorderUI(MineCell mineCell){
        mineCell.setText(mineCell.getNum() + "");
        mineCell.setForeground(Color.lightGray);

        int num = mineCell.getNum();
        Color color;
        switch (num){
            case 1: color = ColorType.ONE; break;
            case 2: color = ColorType.TWO; break;
            case 3: color = ColorType.THREE; break;
            case 4: color = ColorType.FOUR; break;
            case 5: color = ColorType.FIVE; break;
            case 6: color = ColorType.SIX; break;
            case 7: color = ColorType.SEVEN; break;
            case 8: color = ColorType.NIGHT; break;
            default: color = ColorType.SAFETY;
        }
        mineCell.setBackground(color);
    }

    private void setFlagUI(MineCell mineCell){
        mineCell.setText("$");
        mineCell.setForeground(Color.lightGray);
        mineCell.setBackground(ColorType.FLAG);
    }

    private void setEmptyUI(MineCell mineCell){
        mineCell.setText("");
        mineCell.setBackground(ColorType.EMPTY);
    }

    private void setUnknownUI(MineCell mineCell){
        mineCell.setText("?");
        mineCell.setForeground(Color.lightGray);
        mineCell.setBackground(ColorType.UNSURE);
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    private void isClicked(MouseEvent e){
        // 如果未开始游戏
        if(!start){
            JOptionPane.showMessageDialog(null, "请点击开始游戏");
            return;
        }
        MineCell mineCell = (MineCell) e.getSource();
        System.out.println(mineCell.getCellType());
        OperationType op = null;
        // 设置操作
        if(tip){
            if(mineCell.isMine()){
                JOptionPane.showMessageDialog(null, "有雷");
            }else{
                JOptionPane.showMessageDialog(null, "安全");
            }
            tip = false;
            return;
        }
        // 根据状态判断
        if(e.getButton() == MouseEvent.BUTTON1) op = OperationType.BOMB;
        else if(e.getButton() == MouseEvent.BUTTON3){
            switch (mineCell.getCellType()){
                case EMPTY: op = OperationType.FLAG;break;
                case FLAG: op = OperationType.UNSURE; break;
                case UNKNOWN: op = OperationType.CANCEL; break;
            }
        }
        if(op == null) return;
        switch (op){
            case BOMB:
                if(mineCell.isMine()){
                    JOptionPane.showMessageDialog(null, "你输掉了");
                    showAll();
                    if(timeThread != null){
                        timeThread.setStart(false);
                        System.out.println("end");
                    }
                    return;
                }else if(mineCell.getCellType() == CellType.EMPTY){
                    extendSafeZone(mineCell.getPx(), mineCell.getPy());
                }else{
                    System.out.println(mineCell.getCellType());
                }
                break;
            case FLAG: setFlagUI(mineCell); mineCell.setCellType(CellType.FLAG);break;
            case UNSURE: setUnknownUI(mineCell); mineCell.setCellType(CellType.UNKNOWN);break;
            case CANCEL: setEmptyUI(mineCell); mineCell.setCellType(CellType.EMPTY);break;
        }
        updateRestText();

        if(win()){
            JOptionPane.showMessageDialog(null, "你赢了");
            showAll();
            if(timeThread != null){
                timeThread.setStart(false);
                System.out.println("end");
            }

        }
    }

    private void updateRestText() {
        int cnt = 0;
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                MineCell mineCell = mineCells[i][j];
                if(mineCell.getCellType() == CellType.FLAG){
                    cnt++;
                }
            }
        }
        textField.setText(numMine - cnt + "");
    }

    public void setTimeThread(TimeThread timeThread) {
        this.timeThread = timeThread;
    }

    private void showAll() {
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                MineCell mineCell = mineCells[i][j];
                if(mineCell.isMine()){
                    mineCell.setBackground(ColorType.MINE);
                }else if(mineCell.getNum() == 0){
                    setSafetyUI(mineCell);
                }else{
                    setBorderUI(mineCell);
                }
            }
        }
    }

    private boolean win(){
        int cnt = 0;
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(mineCells[i][j].getCellType() == CellType.SAFETY){
                    cnt++;
                }
            }
        }
        System.out.println(cnt + " " + numMine + " = " + cnt + numMine);
        return cnt + numMine == h * w;

    }

    public void initMine(){
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                setEmptyUI(mineCells[i][j]);
                mineCells[i][j].setCellType(CellType.EMPTY);
                mineCells[i][j].setNum(0);
            }
        }

        for(int i = 0; i < numMine; i++){
            Random random = new Random();
            int xw = random.nextInt(w);
            int xh = random.nextInt(h);
            if(mineCells[xw][xh].isMine()){
                i--;
            }else{
                mineCells[xw][xh].setMine(true);
//                mineCells[xw][xh].setBackground(ColorType.MINE); // 显示地雷
            }
        }
        setNumAround();
    }

    public void setTip(boolean b) {
        tip = b;
    }

    public void reStart() {
        initMine();
        updateRestText();
    }
}