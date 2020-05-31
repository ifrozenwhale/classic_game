package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * @author admin
 */
public class SnakeApp extends JFrame {
    /**
     * 构造函数
     */
    public SnakeApp() {
        initComponents();
        snakeMap.setText(scoreText);
    }

    private void startButtonActionPerformed(ActionEvent e) {
        snakeMap.start();

    }

    private void thisWindowClosing(WindowEvent e) {
        System.exit(0);
    }

    /**
     * 难度设置
     * @param e 事件
     */
    private void configButtonActionPerformed(ActionEvent e) {
        String s = JOptionPane.showInputDialog(null, "输入移动速度，整数（1 - 20）");
        int speed = Integer.parseInt(s);
        snakeMap.setSpeed(speed);
    }



    public static void main(String[] args) {
        SnakeApp snakeApp = new SnakeApp();
        snakeApp.setSize(800, 800);
        snakeApp.setVisible(true);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        toolBar1 = new JToolBar();
        startButton = new JButton();
        configButton = new JButton();
        toolBar2 = new JToolBar();
        scoreLabel = new JLabel();
        scoreText = new JLabel();
        snakeMap = new SnakeMap();

        //======== this ========
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

            //---- startButton ----
            startButton.setText("\u5f00\u59cb\u6e38\u620f");
            startButton.setFocusable(false);
            startButton.setBorderPainted(false);
            startButton.addActionListener(e -> startButtonActionPerformed(e));
            toolBar1.add(startButton);

            //---- configButton ----
            configButton.setText("\u8bbe\u7f6e\u96be\u5ea6");
            configButton.setFocusable(false);
            configButton.setBorderPainted(false);
            configButton.addActionListener(e -> configButtonActionPerformed(e));
            toolBar1.add(configButton);
        }
        contentPane.add(toolBar1, BorderLayout.SOUTH);

        //======== toolBar2 ========
        {

            //---- scoreLabel ----
            scoreLabel.setText("\u5f97\u5206");
            scoreLabel.setPreferredSize(new Dimension(40, 30));
            scoreLabel.setMaximumSize(new Dimension(50, 50));
            scoreLabel.setBorder(null);
            toolBar2.add(scoreLabel);

            //---- scoreText ----
            scoreText.setText("0");
            scoreText.setMaximumSize(new Dimension(50, 40));
            scoreText.setPreferredSize(new Dimension(40, 30));
            scoreText.setBorder(null);
            toolBar2.add(scoreText);
        }
        contentPane.add(toolBar2, BorderLayout.NORTH);
        contentPane.add(snakeMap, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

    }


    private JToolBar toolBar1;
    private JButton startButton;
    private JButton configButton;
    private JToolBar toolBar2;
    private JLabel scoreLabel;
    private JLabel scoreText;
    private SnakeMap snakeMap;
}
