package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
public class SnakeMap extends JPanel {
    private int len = 5;
    private int score = 0;
    private int w = 50;
    private int h = 50;
    private int px = 10;
    private int startX = 5;
    private int startY = 5;
    private int speed = 10;
    private JLabel text;
    private FoodCell food;
    private List<SnakeCell> border = new ArrayList<>();
    private SnakeCell head;
    private Direction direction = Direction.DOWN;
    private SnakeCell[][] cells;
    private List<SnakeCell> cellList = new LinkedList<>();

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setText(JLabel text) {
        this.text = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制蛇
        for(SnakeCell snakeCell : cellList){
            snakeCell.draw((Graphics2D) g);
        }
        // 绘制边界
        for(SnakeCell snakeCell : border){
            snakeCell.draw((Graphics2D) g);
        }
        // 绘制食物
        food.draw((Graphics2D) g);
    }

    /**
     * 初始化地图
     * 生成单元格
     */
    private void initMap(){
        cells = new SnakeCell[h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                SnakeCell snakeCell = new SnakeCell(i, j);

                cells[i][j] = snakeCell;
            }
        }
    }

    /**
     * 初始化边界
     */
    private void initBorder(){
        for(int i = 0; i < w; i++){
            cells[i][0].setColor(GameColor.BORDER);
            cells[i][w-1].setColor(GameColor.BORDER);
            border.add(cells[i][0]);
            border.add(cells[i][w-1]);
        }
        for(int j = 0; j < h; j++){
            cells[0][j].setColor(GameColor.BORDER);
            cells[w-1][j].setColor(GameColor.BORDER);
            border.add(cells[0][j]);
            border.add(cells[w-1][j]);
        }

    }

    /**
     * 初始化蛇,长度为5
     */
    private void initSnake(){

        for(int i = len-1; i >= 0; i--){
            SnakeCell snakeCell = cells[startX][startY+i];
            snakeCell.setColor(GameColor.SNAKE);
            cellList.add(snakeCell);
        }
        head = cellList.get(0);
        head.setColor(GameColor.HEAD);
    }

    /**
     * 初始化食物
     */
    private void initFood(){
        Random random = new Random();
        boolean flag = true;
        while(flag){
            int fx = random.nextInt(w-1);
            int fy = random.nextInt(h-1);
            for(SnakeCell snakeCell : cellList){
                if(fx == snakeCell.getX() && fy == snakeCell.getY()){
                    flag = false;
                    break;
                }
            }
            for(SnakeCell snakeCell : border){
                if(fx == snakeCell.getX() && fy == snakeCell.getY()){
                    flag = false;
                    break;
                }
            }
            if(flag){
                food = new FoodCell(fx, fy);
                flag = false;
            }else{
                flag = true;
            }
        }
    }

    /**
     * 判断是否吃掉了自己!
     * @return 吃掉了自己 true
     */
    private boolean eatSelf(){
        boolean skip = true;
        for(SnakeCell snakeCell : cellList){
            if(skip){
                skip = false;
                continue;
            }
            if(head.getX() == snakeCell.getX() && head.getY() == snakeCell.getY()){
                return true;
            }
        }
        return false;
    }

    public SnakeMap() {
        initComponents();
        initMap();
        initBorder();
        initSnake();
        initFood();
        setFocusable(true);                //画布获取焦
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(direction != Direction.DOWN){
                            direction = Direction.UP;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction != Direction.UP){
                            direction = Direction.DOWN;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(direction != Direction.RIGHT){
                            direction = Direction.LEFT;
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if(direction != Direction.LEFT){
                            direction = Direction.RIGHT;
                        }
                        break;
                }
            }
        });
        repaint();
    }
    private void initComponents() {


        //======== this ========
        setLayout(null);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }

    }

    /**
     * 开始游戏
     */
    public void start() {
        MoveThread moveThread = new MoveThread();
        moveThread.setLive(true);
        new Thread(moveThread).start();

    }

    /**
     * 判断是否出界
     * @return 出界 true
     */
    private boolean outBorder(){
        return head.getX() >= w-1 || head.getX() <= 0 || head.getY() <= 0 || head.getY() >= h-1;
    }

    /**
     * 判断是否吃掉了食物
     * @return 吃掉了 true
     */
    private boolean eatFood(){
        return head.getX() == food.getX() && head.getY() == food.getY();
    }

    /**
     * 内部线程类 移动
     */
    class MoveThread implements Runnable{
        private boolean live = false;

        public boolean isLive() {
            return live;
        }

        public void setLive(boolean live) {
            this.live = live;
        }

        @Override
        public void run() {
            while (live){
                head.setColor(GameColor.SNAKE);
                // 判断前进方向和前进位置
                int hx = head.getX();
                int hy = head.getY();
                switch (direction){
                    case UP: hy--; break;
                    case DOWN: hy++; break;
                    case LEFT: hx--; break;
                    case RIGHT: hx++; break;
                }
                SnakeCell snakeCell = cells[hx][hy];
                // 在下一个位置添加一个结点
                cellList.add(0, snakeCell);
                // 如果吃掉了食物 长度边长 得分
                if(eatFood()){
                    len++;
                    score += 2;
                    initFood(); // 重新放置食物
                }else{
                    cellList.remove(len); // 没吃掉食物就把末尾的删除
                }
                head = snakeCell;
                head.setColor(GameColor.HEAD);
                repaint();
                text.setText(score + "");
                if(outBorder()){
                    JOptionPane.showMessageDialog(null, "你撞墙了, 得分是 " + score);
                    live = false;
                }else if(eatSelf()){
                    JOptionPane.showMessageDialog(null, "吃掉了自己, 得分是 " + score);
                    live = false;
                }

                try {
                    Thread.sleep(1000 / speed); // 移动速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
    }
}
