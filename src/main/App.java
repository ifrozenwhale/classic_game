/*
 * Created by JFormDesigner on Fri May 08 09:56:10 CST 2020
 */

package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brainrain
 */
public class App extends JFrame {
    /**
     * 设置JOptionPane 的样式
     */
    public void setUIAppearence() {
        UIManager uiManager = new UIManager();
        uiManager.put("OptionPane.background", Color.WHITE);
        uiManager.put("Panel.background", Color.WHITE); //Panel.background和OptionPane.background共同来改变JOptionPane的背景颜色
        uiManager.put("OptionPane.buttonFont", new Font("微软雅黑", Font.BOLD, 12));//OptionPane.buttonFont用于设置按钮的背景颜色
    }

    /**
     * 无参构造函数
     */
    public App() {
        setUIAppearence();
        initComponents();
        chessboard.setLabel(currentUser);
    }

    /**
     * 开始游戏按键监听
     * @param e 事件
     */
    private void startButtonActionPerformed(ActionEvent e) {
        chessboard.startGame();
    }

    /**
     * 投降事件监听
     * @param e 事件
     */
    private void giveUpButtonActionPerformed(ActionEvent e) {
        chessboard.giveUp();
    }

    /**
     * 窗口关闭
     * @param e
     */
    private void thisWindowClosing(WindowEvent e) {
        JOptionPane.showMessageDialog(null, "你退出了游戏");
        System.exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        toolBar1 = new JToolBar();
        separator7 = new JSeparator();
        startButton = new JButton();
        separator5 = new JSeparator();
        giveUpButton = new JButton();
        separator6 = new JSeparator();
        toolBar2 = new JToolBar();
        separator3 = new JSeparator();
        currLabel = new JLabel();
        separator2 = new JSeparator();
        currentUser = new JLabel();
        separator4 = new JSeparator();
        chessboard = new ChessBoard();

        //======== this ========
        setMinimumSize(new Dimension(550, 600));
        setName("frame");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== toolBar1 ========
        {
            toolBar1.setBorder(null);
            toolBar1.setMinimumSize(new Dimension(210, 120));
            toolBar1.setMaximumSize(new Dimension(410, 150));
            toolBar1.setPreferredSize(new Dimension(162, 30));
            toolBar1.setEnabled(false);
            toolBar1.setMargin(new Insets(15, 0, 0, 0));

            //---- separator7 ----
            separator7.setBorder(null);
            toolBar1.add(separator7);

            //---- startButton ----
            startButton.setText("\u5f00\u59cb\u6e38\u620f");
            startButton.setBorder(null);
            startButton.setMinimumSize(new Dimension(100, 60));
            startButton.setMaximumSize(new Dimension(200, 100));
            startButton.setBackground(new Color(197, 91, 22));
            startButton.setForeground(new Color(222, 221, 221));
            startButton.setFont(new Font("Arial Unicode MS", startButton.getFont().getStyle(), startButton.getFont().getSize() + 1));
            startButton.setPreferredSize(new Dimension(200, 24));
            startButton.addActionListener(e -> startButtonActionPerformed(e));
            toolBar1.add(startButton);

            //---- separator5 ----
            separator5.setBorder(null);
            toolBar1.add(separator5);

            //---- giveUpButton ----
            giveUpButton.setText("\u6295\u964d");
            giveUpButton.setBorder(null);
            giveUpButton.setMaximumSize(new Dimension(200, 100));
            giveUpButton.setMinimumSize(new Dimension(100, 60));
            giveUpButton.setBackground(new Color(197, 91, 22));
            giveUpButton.setForeground(new Color(222, 221, 221));
            giveUpButton.setFont(new Font("Arial Unicode MS", giveUpButton.getFont().getStyle(), giveUpButton.getFont().getSize() + 1));
            giveUpButton.setPreferredSize(new Dimension(200, 24));
            giveUpButton.addActionListener(e -> giveUpButtonActionPerformed(e));
            toolBar1.add(giveUpButton);

            //---- separator6 ----
            separator6.setBorder(null);
            toolBar1.add(separator6);
        }
        contentPane.add(toolBar1, BorderLayout.SOUTH);

        //======== toolBar2 ========
        {
            toolBar2.setBorder(null);
            toolBar2.add(separator3);

            //---- currLabel ----
            currLabel.setText("\u5f53\u524d");
            currLabel.setMaximumSize(new Dimension(50, 50));
            currLabel.setPreferredSize(new Dimension(50, 30));
            currLabel.setBorder(null);
            currLabel.setFont(new Font("Arial Unicode MS", currLabel.getFont().getStyle(), currLabel.getFont().getSize() + 1));
            currLabel.setHorizontalAlignment(SwingConstants.CENTER);
            toolBar2.add(currLabel);
            toolBar2.add(separator2);

            //---- currentUser ----
            currentUser.setText("\u9ed1\u68cb");
            currentUser.setMaximumSize(new Dimension(100, 50));
            currentUser.setPreferredSize(new Dimension(100, 35));
            currentUser.setBorder(null);
            currentUser.setBackground(Color.black);
            currentUser.setForeground(Color.white);
            currentUser.setFont(new Font("Arial Unicode MS", currentUser.getFont().getStyle(), currentUser.getFont().getSize() + 1));
            currentUser.setHorizontalAlignment(SwingConstants.CENTER);
            currentUser.setMinimumSize(new Dimension(100, 18));
            currentUser.setOpaque(true);
            currentUser.setHorizontalTextPosition(SwingConstants.CENTER);
            toolBar2.add(currentUser);
            toolBar2.add(separator4);
        }
        contentPane.add(toolBar2, BorderLayout.NORTH);
        contentPane.add(chessboard, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JToolBar toolBar1;
    private JSeparator separator7;
    private JButton startButton;
    private JSeparator separator5;
    private JButton giveUpButton;
    private JSeparator separator6;
    private JToolBar toolBar2;
    private JSeparator separator3;
    private JLabel currLabel;
    private JSeparator separator2;
    private JLabel currentUser;
    private JSeparator separator4;
    private ChessBoard chessboard;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    private class ChessBoard extends JPanel {
        private boolean start = false;
        private JLabel label;
        private int SPAN = 30;
        private int W = 16;
        private int H = 16;
        private Point[][] points;
        private List<Point> blackPoints;
        private List<Point> whitePoints;

        private PointType currentGo = PointType.BLACK;

        public void startGame() {
            start = true;
        }

        public void setLabel(JLabel label) {
            this.label = label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 1; i <= W; i++) {
                g.setColor(ChessColor.LINE);
                g.drawLine(SPAN, i * SPAN, (W) * SPAN, i * SPAN);
            }
            for (int j = 1; j <= H; j++) {
                g.setColor(ChessColor.LINE);
                g.drawLine(j * SPAN, SPAN, j * SPAN, (H) * SPAN);
            }
            if (whitePoints != null) {
                for (Point p : whitePoints) {
                    p.draw((Graphics2D) g);
                }
            }
            if (blackPoints != null) {
                for (Point p : blackPoints) {
                    p.draw((Graphics2D) g);
                }
            }


        }

        /**
         * 初始化黑棋和白棋的各自容器
         */
        public void initPoint() {
            blackPoints = new ArrayList<>();
            whitePoints = new ArrayList<>();
        }

        /**
         * 初始化棋盘底盘
         */
        public void initBoard() {
            setBackground(ChessColor.MAP);
            points = new Point[H + 1][W + 1];

            for (int i = 1; i <= H; i++) {
                for (int j = 1; j <= W; j++) {
                    points[i][j] = new Point(i, j, Color.BLACK, SPAN);
                }
            }

        }

        /**
         * 判断是否在边界内部
         * @param x 位置x
         * @param y 位置y
         * @return 是或否
         */
        private boolean inBorder(int x, int y) {
            return x <= H && x > 0 && y <= W && y > 0;
        }

        /**
         * 判断是否胜利
         * @return 是或否
         */
        private boolean win() {
            System.out.println(currentGo);
            List<Point> pointList = null;
            if (currentGo == PointType.BLACK) {
                pointList = blackPoints;
            } else {
                pointList = whitePoints;
            }
            int cnt;
            for (Point p : pointList) {
                int x = p.getX();
                int y = p.getY();
                // 纵向
                cnt = 0;
                for (int i = -4; i <= 4; i++) {
                    int nx = x + i;
                    if (!inBorder(nx, y)) {
                        continue;
                    }
                    if (points[nx][y].getType() == currentGo) {
                        cnt++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt >= 5) {
                        return true;
                    }
                }
                // 横向
                cnt = 0;
                for (int i = -4; i <= 4; i++) {
                    int ny = y + i;
                    if (!inBorder(x, ny)) {
                        continue;
                    }
                    if (points[x][ny].getType() == currentGo) {
                        cnt++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt >= 5) {
                        return true;
                    }
                }

                // 左上 - 右下 对角线
                cnt = 0;
                for (int i = -4; i <= 4; i++) {
                    int nx = x + i, ny = y + i;
                    if (!inBorder(nx, ny)) {
                        continue;
                    }
                    if (points[nx][ny].getType() == currentGo) {
                        cnt++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt >= 5) {
                        return true;
                    }
                }
                // 右上 - 左下 对角线
                cnt = 0;
                for (int i = -4; i <= 4; i++) {
                    int nx = x - i, ny = y + i;
                    if (!inBorder(nx, ny)) {
                        continue;
                    }
                    if (points[nx][ny].getType() == currentGo) {
                        cnt++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt >= 5) {
                        return true;
                    }
                }

            }

            return false;
        }

        /**
         * 构造函数
         */
        private ChessBoard() {
            initPoint();
            initBoard();
            initComponents();
        }

        /**
         * 得到被点击的位置棋子
         * @param x 点击的绝对位置x
         * @param y 点击的绝对位置y
         * @return 点击范围内棋盘棋子
         */
        private Point getClickPoint(int x, int y) {
            for (int i = 1; i <= H; i++) {
                for (int j = 1; j <= W; j++) {
                    Point p = points[i][j];
                    if (p.getType() != PointType.NULL) {
                        continue;
                    }
                    int px = p.getOx(), py = p.getOy(), r = p.getD() / 2;
                    if (x < px + r && x >= px - r && y < py + r && y >= py - r) {
                        return p;
                    }
                }
            }
            return null;
        }

        /**
         * 鼠标点击事件监听相应
         * @param e 事件
         */
        private void chessBoardMouseClicked(MouseEvent e) {
            if (!start) {
                JOptionPane.showMessageDialog(null, "请点击开始游戏");
                return;
            }
            int x = e.getX();
            int y = e.getY();
            Point p = getClickPoint(x, y);
            if (p == null) return;

            if (currentGo == PointType.BLACK) {
                blackPoints.add(p);
                p.setType(PointType.BLACK);
                p.setColor(ChessColor.BLACK);
            } else {
                whitePoints.add(p);
                p.setType(PointType.WHITE);
                p.setColor(ChessColor.WHITE);
            }
            repaint();
            if (win()) {
                String info = null;
                if (currentGo == PointType.BLACK) info = "黑棋你赢啦";
                else info = "白棋你赢啦";
                JOptionPane.showMessageDialog(null, info);
                start = false;
                return;
            }
            if (currentGo == PointType.BLACK) {
                currentGo = PointType.WHITE;
                currentUser.setBackground(ChessColor.WHITE);
                currentUser.setForeground(ChessColor.BLACK);
                currentUser.setText("轮到白棋啦");
            } else {
                currentGo = PointType.BLACK;
                currentUser.setBackground(ChessColor.BLACK);
                currentUser.setForeground(ChessColor.WHITE);
                currentUser.setText("轮到黑棋啦");

            }
        }

        /**
         * 投降
         */
        public void giveUp() {
            if (currentGo == PointType.BLACK) {
                JOptionPane.showMessageDialog(null, "黑棋投降啦");
            } else {
                JOptionPane.showMessageDialog(null, "白旗投降案例");
            }
            start = false;
            return;
        }

        private void initComponents() {
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

            //======== this ========
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chessBoardMouseClicked(e);
                }
            });
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
            // JFormDesigner - End of component initialization  //GEN-END:initComponents
        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        // JFormDesigner - End of variables declaration  //GEN-END:variables
    }

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
}
