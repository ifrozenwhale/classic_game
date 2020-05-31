package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MineFrame extends JFrame {
    private MineMap mineMap;
    private JToolBar toolBar1;
    private JButton button1;
    private JSeparator separator1;
    private JButton button2;
    private JSeparator separator2;
    private JButton button3;
    private JToolBar toolBar2;
    private JLabel labelRest;
    private JTextField restMines;

    private JLabel labelTime;
    private JTextField costTime;
    private JSeparator separator3;
    private JButton configButton;
    private long startTime = 0;
    private TimeThread timeThread;


    public MineFrame() {
        setLayout(new BorderLayout()); // BorderLayout布局

        toolBar1 = new JToolBar();
        button1 = new JButton();
        separator1 = new JSeparator();
        button2 = new JButton();
        separator2 = new JSeparator();
        button3 = new JButton();
        toolBar2 = new JToolBar();
        labelRest = new JLabel();
        restMines = new JTextField();

        labelTime = new JLabel();
        costTime = new JTextField();
        separator3 = new JSeparator();
        configButton = new JButton();

        toolBar1.setBorder(null);
        toolBar1.setBackground(new Color(229, 243, 255));

        //---- button1 ----
        button1.setText("\u5f00\u59cb\u6e38\u620f");
        button1.setBorder(null);
        button1.setPreferredSize(new Dimension(88, 44));
        button1.setBackground(new Color(229, 243, 255));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineMap.setStart(true);
                long startTime =  System.currentTimeMillis();
                if(timeThread == null){
                    timeThread = new TimeThread(costTime, startTime);
                    new Thread(timeThread).start();
                    mineMap.setTimeThread(timeThread);
                }


            }
        });
        toolBar1.add(button1);

        //---- separator1 ----
        separator1.setBorder(null);
        separator1.setBackground(new Color(234, 240, 251));
        toolBar1.add(separator1);

        //---- button2 ----
        button2.setText("\u67e5\u770b\u63d0\u793a");
        button2.setBorder(null);
        button2.setPreferredSize(new Dimension(88, 44));
        button2.setBackground(new Color(229, 243, 255));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineMap.setTip(true);
            }
        });
        toolBar1.add(button2);


        //---- separator2 ----
        separator2.setBorder(null);
        separator2.setBackground(new Color(229, 243, 255));
        toolBar1.add(separator2);

        //---- button3 ----
        button3.setText("重开一局");
        button3.setBorder(null);
        button3.setPreferredSize(new Dimension(88, 44));
        button3.setBackground(new Color(229, 243, 255));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineMap.reStart();
                timeThread.setStartTime(System.currentTimeMillis());
                timeThread.setStart(true);
                mineMap.setTimeThread(timeThread);
            }
        });
        toolBar1.add(button3);

        separator3.setBorder(null);
        separator3.setBackground(new Color(229, 243, 255));
        toolBar1.add(separator3);
        configButton.setText("设置难度");
        configButton.setBorder(null);
        configButton.setPreferredSize(new Dimension(88, 44));
        configButton.setBackground(new Color(229, 243, 255));
        configButton.addActionListener(new ActionListener() { // 添加事件监听
            @Override
            public void actionPerformed(ActionEvent e) {

                String s2 = JOptionPane.showInputDialog(null, "请输入地图尺寸(宽 高)，例如(10 10)");
                if(s2 == null) return;
                String s = JOptionPane.showInputDialog(null, "请输入地雷数");
                if(s == null) return;
                try{
                    String[] strings = s2.split(" ");
                    int sw = Integer.parseInt(strings[0]);
                    int sh = Integer.parseInt(strings[1]);
                    int num = Integer.parseInt(s);
                    mineMap.setW(sw); // 设置w
                    mineMap.setH(sh); // 设置h
                    mineMap.setNumMine(num); // 设置地雷数

                    mineMap.initCell(); // 初始化
                    mineMap.initMine();
                }catch (Exception exp){
                    // JOptionPane.showMessageDialog(null, "请合法输入");
                    System.out.println(exp);
                }
                setVisible(true);
            }
        });
        toolBar1.add(configButton);


        add(toolBar1, BorderLayout.SOUTH);

        //======== toolBar2 ========

        toolBar2.setBorder(null);

        //---- labelRest ----
        labelRest.setText("\u5269\u4f59\u5730\u96f7\u6570");
        labelRest.setBorder(null);
        labelRest.setPreferredSize(new Dimension(80, 30));
        labelRest.setHorizontalTextPosition(SwingConstants.CENTER);
        labelRest.setMaximumSize(new Dimension(100, 17));
        labelRest.setMinimumSize(new Dimension(100, 17));
        toolBar2.add(labelRest);

        //---- restMines ----
        restMines.setBorder(null);
        restMines.setEditable(false);
        toolBar2.add(restMines);



        //---- labelTime ----
        labelTime.setText("\u65f6\u95f4");
        labelTime.setBorder(null);
        labelTime.setPreferredSize(new Dimension(60, 27));
        labelTime.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar2.add(labelTime);

        //---- costTime ----
        costTime.setBorder(null);
        costTime.setEditable(false);
        toolBar2.add(costTime);


        add(toolBar2, BorderLayout.NORTH);


        mineMap = new MineMap(10, 10, restMines);
        // setSize(new Dimension(800, 800));

        add(mineMap, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "确认退出？");
                if(i == JOptionPane.YES_OPTION){
                    if(timeThread != null){
                        timeThread.setStart(false);
                    }
                    System.exit(0);
                }

            }
        });
        pack();

        setLocationRelativeTo(getOwner());
    }
    public static void main(String[] args) {
        MineFrame mineFrame = new MineFrame();
        mineFrame.setVisible(true);
    }
}

