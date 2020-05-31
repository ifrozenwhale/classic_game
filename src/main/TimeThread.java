package main;

import javax.swing.*;

// 计时器
public class TimeThread implements Runnable{
    private JTextField textField; // 显示框
    private long startTime = 0; // 开始时间
    private boolean start = true; // 是否启动
    public TimeThread(JTextField textField, long startTime) {
        this.textField = textField;
        this.startTime = startTime;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while (start){
            long endTime =  System.currentTimeMillis();
            long usedTime = (endTime - startTime) / 1000;
            textField.setText(usedTime + " 秒");
            try {
                Thread.sleep(1000); // 每隔1秒刷新一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

