import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;

/**
 * @author user
 */
public class CalculatorApp extends JFrame {
    Compute compute;
    StringBuilder s;
    public CalculatorApp() {
        initUI();
        s = new StringBuilder();
        compute = new Compute("");
    }

    /**
     * 数字按键响应
     * @param e 事件
     */
    private void buttonValueActionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object.equals(button0)){
            s.append("0");
        }else if (object.equals(button1)){
            s.append("1");
        }else if (object.equals(button2)){
            s.append("2");
        }else if (object.equals(button3)){
            s.append("3");
        }else if (object.equals(button4)){
            s.append("4");
        }else if (object.equals(button5)){
            s.append("5");
        }else if (object.equals(button6)){
            s.append("6");
        }else if (object.equals(button7)){
            s.append("7");
        }else if (object.equals(button8)){
            s.append("8");
        }else if (object.equals(button9)) {
            s.append("9");
        } else if (object.equals(buttonDot)){
            s.append(".");
        }else if (object.equals(buttonLbra)){
            s.append("(");
        }else if (object.equals(buttonRbra)){
            s.append(")");
        }
        updateView(); // 更新显示
    }

    /**
     * 清除响应
     * @param e
     */
    private void buttonClearActionPerformed(ActionEvent e) {
        Object object = e.getSource(); // 判断来源
        if(object.equals(buttonDel)){
            s.deleteCharAt(s.length()-1); // 删除一个字符
        }else if(object.equals(buttonAc)){
            s.setLength(0);
        }
        updateView(); // 更新显示
    }

    /**
     * 更新显示
     */
    private void updateView(){
        text.setText(String.valueOf(s)); // 设置文本框
    }

    /**
     * 运算响应
     * @param e 事件
     */
    private void buttonCalActionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object.equals(buttonMul)){
            s.append("*");
        }else if (object.equals(buttonSub)){
            s.append("-");
        }else if (object.equals(buttonDiv)){
            s.append("/");
        }else if (object.equals(buttonPlus)){
            s.append("+");
        }else if (object.equals(buttonCal)) {
            compute.setExpression(text.getText());
            s.setLength(0);
            try {
                s.append(compute.run());
            } catch (Exception ex) {
                compute.setExpression("");
                s.setLength(0);
                s.append("表达式有误"); // 出现异常
            }
        }
        updateView(); // 更新显示
    }

    /**
     * 关闭窗口
     * @param e
     */
    private void thisWindowClosing(WindowEvent e) {
        System.exit(0);
    }

    private void setButtonSetting(JButton button, String text, int type){

        button.setText(text);
        button.setBorderPainted(false);
        if(type == 0){
            button.setBackground(new Color(30, 99, 44));
            button.setForeground(new Color(228, 228, 228));
            button.addActionListener(e -> buttonValueActionPerformed(e));
        }else if(type == 1){
            button.setBackground(new Color(125, 129, 126));
            button.setForeground(new Color(228, 228, 228));
            button.addActionListener(e -> buttonValueActionPerformed(e));
        }else if(type == 2){
            button.setBackground(new Color(14, 124, 66));
            button.setForeground(new Color(228, 228, 228));
            button.addActionListener(e -> buttonCalActionPerformed(e));
        }else {
            button.setBackground(new Color(74, 79, 75));
            button.setForeground(new Color(228, 228, 228));
            button.addActionListener(e -> buttonClearActionPerformed(e));
        }




        button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 19));
        button.setPreferredSize(new Dimension(70, 70));
    }

    /**
     * 设置GUI
     */
    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        dialogPane = new JPanel();
        contentPanel = new JPanel();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();
        buttonLbra = new JButton();
        buttonRbra = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        buttonMul = new JButton();
        buttonDiv = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        buttonPlus = new JButton();
        buttonSub = new JButton();
        button0 = new JButton();
        buttonDot = new JButton();
        buttonDel = new JButton();
        buttonAc = new JButton();
        buttonCal = new JButton();
        text = new JTextField();


        setTitle("Calculator");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        dialogPane.setLayout(new BorderLayout(0, 7));

        //======== contentPanel ========

        contentPanel.setLayout(new GridLayout(4, 5, 5, 5));


        // 7 8 9
        setButtonSetting(button7, "7", 0);
        contentPanel.add(button7);
        setButtonSetting(button8, "8", 0);
        contentPanel.add(button8);
        setButtonSetting(button9, "9", 0);
        contentPanel.add(button9);

        // (
        setButtonSetting(buttonLbra, "(", 1);
        contentPanel.add(buttonLbra);
        // )
        setButtonSetting(buttonRbra, ")", 1);
        contentPanel.add(buttonRbra);



        // 4 6 6
        setButtonSetting(button4, "4", 0);
        contentPanel.add(button4);
        setButtonSetting(button5, "5", 0);
        contentPanel.add(button5);
        setButtonSetting(button6, "6", 0);
        contentPanel.add(button6);

        // * /
        setButtonSetting(buttonMul, "*", 2);
        contentPanel.add(buttonMul);
        setButtonSetting(buttonDiv, "/", 2);
        contentPanel.add(buttonDiv);


        // 1 2 3
        setButtonSetting(button1, "1", 0);
        contentPanel.add(button1);
        setButtonSetting(button2, "2", 0);
        contentPanel.add(button2);
        setButtonSetting(button3, "3", 0);
        contentPanel.add(button3);


        // + -
        setButtonSetting(buttonPlus, "+", 2);
        contentPanel.add(buttonPlus);
        setButtonSetting(buttonSub, "-", 2);
        contentPanel.add(buttonSub);

        // 0 .
        setButtonSetting(button0, "0", 0);
        contentPanel.add(button0);
        setButtonSetting(buttonDot, ".", 0);
        contentPanel.add(buttonDot);

        // del ac
        setButtonSetting(buttonDel, "DEL", 3);
        contentPanel.add(buttonDel);
        setButtonSetting(buttonAc, "AC", 3);
        contentPanel.add(buttonAc);

        // calculate
        setButtonSetting(buttonCal, "=", 2);
        contentPanel.add(buttonCal);

        dialogPane.add(contentPanel, BorderLayout.CENTER);

        // text
        text.setBorder(null);
        text.setPreferredSize(new Dimension(64, 41));
        text.setFont(new Font("Console", Font.PLAIN,13));
        dialogPane.add(text, BorderLayout.NORTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

    }

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton buttonLbra;
    private JButton buttonRbra;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton buttonMul;
    private JButton buttonDiv;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton buttonPlus;
    private JButton buttonSub;
    private JButton button0;
    private JButton buttonDot;
    private JButton buttonDel;
    private JButton buttonAc;
    private JButton buttonCal;
    private JTextField text;

    public static void main(String[] args) {
        CalculatorApp app = new CalculatorApp();
        app.setVisible(true);
    }
}
