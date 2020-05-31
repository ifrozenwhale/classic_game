import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 计算数据model类
public class Compute {

    private String expression; // 输入的计算表达式

    public Compute(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * 比较运算符的优先级
     * @param sa 前者
     * @param sb 后者
     * @return 前者优先于后者 则返回True, 否则返回False
     */
    private boolean primaryThan(String  sa, String  sb){
        char a = sa.charAt(0); // String 转化为char
        char b = sb.charAt(0);
        if(b == '(') return true; // (优先级最高
        if(b == ')') return false; // 优先级最低
        return (a == '*' || a == '/') && (b == '+' || b == '-'); // 乘除 优先级 高于 加减
    }

    /**
     * 判断是否是数值(整数 小数 负数)
     * @param s 表达式字符串
     * @return 是数值则返回True
     */
    private boolean isValue(String s){
        Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*"); // 正则表达式 匹配 小数点和数字
        Matcher isNum = pattern.matcher(s);
        return isNum.matches();
    }

    /**
     * 计算后缀两个元素
     * @param a 数a
     * @param b 数b
     * @param s 运算符号
     * @return 结果
     */
    private double calculate(double a, double b, String s){
        char c = s.charAt(0);
        double ans;
        switch (c){
            case '+': ans = a + b; break;
            case '-': ans = a - b; break;
            case '*': ans = a * b; break;
            case '/': ans = a / b; break;
            default: ans = 0; break;
        }
        return ans;
    }

    /**
     * 计算后缀表达式
     * @param ss 后缀表达式
     * @return 答案
     * @throws Exception 计算异常
     */
    private double execute(List<String> ss) throws Exception{
        Stack<String> stack = new Stack<>();
        for(String s : ss){
//            System.out.println(s);
            if(isValue(s)){
                stack.push(s);
            }else{
                double a = Double.parseDouble(stack.pop());
                double b = Double.parseDouble(stack.pop());
                double c = calculate(b, a, s);
                stack.push(String.valueOf(c));
            }
        }
        return Double.parseDouble(stack.pop());
    }

    /**
     * 预处理
     * 分离数字和操作符
     * @param tmp 原始字符串
     * @return 分隔后的字符串列表
     * @throws Exception 计算异常
     */
    private List<String> preProcess(String tmp) throws Exception{
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean split = false;
        // 预处理 - 在开头
        if(tmp.charAt(0) == '-'){
            tmp = '0' + tmp;
        }
        for(int i = 0; i < tmp.length(); i++){
            char c = tmp.charAt(i);
            if((c < '0' || c > '9') && c != '.'){ // 如果是字符 但是字符不是小数点
                if(i > 0){
                    if(tmp.charAt(i-1) == '('){ // 如果前一个是左括号, 这说明是 负数, 如(-42) 直接入栈
                        sb.append(c); // 将 - 入数字串
                        continue;
                    }
                }

                if(sb.length() > 0){ // 剩余的入栈, 然后清空
                    list.add(String.valueOf(sb));
                    sb.setLength(0);
                }

                list.add(String.valueOf(c));
            }else{
                sb.append(c);
            }
        }
        // 将剩余的入栈
        if(sb.length() > 0) list.add(String.valueOf(sb));
        return list;
    }

    /**
     * 中缀表达式转换为后缀表达式
     * @param list 分隔后的中缀表达式
     * @return 后缀表达式列表
     * @throws Exception 计算异常
     */
    private List<String> transInfixSuffix(List<String> list) throws Exception{
        List<String> s = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for(String c : list){
            if(isValue(c)){
                s.add(c);
                continue;
            }
            if(stack.isEmpty()){ // 栈空 直接入栈
                stack.push(c);
                continue;
            }
            if(c.equals("(")){ // 左括号直接入栈
                stack.push(c);
                continue;
            }
            if(c.equals(")")){ // 右括号需要和左括号进行匹配
                do {
                    String top = stack.pop(); // 弹栈
                    if(top.equals("(")) break; // 直到遇到左括号
                    s.add(top); // 将其加入串
                }while (!stack.isEmpty()); // 栈不能为空
                continue;
            }
            if (primaryThan(c, stack.peek())){
                stack.push(c);
                continue;
            }
            s.add(stack.pop());
            stack.push(c);
        }
        while(!stack.isEmpty()){ // 剩余的元素全部弹栈 入串
            s.add(stack.pop());
        }
        return s;
    }

    /**
     * 计算函数
     * @return 计算结果
     * @throws Exception
     */
    public String run() throws Exception{

        // String tmp = "-(12*(-2)/(2-(-2)))/5";
        List<String> list = preProcess(expression); // 预处理

        // 输出 查看
        for(String ss : list){
            System.out.print(ss + " ");
        }
        System.out.println();

        // 中缀转后缀
        List<String> s = transInfixSuffix(list);

        // 输出查看
        for(String c : s){
            System.out.print(c + " ");
        }
        System.out.println();

        // 计算并返回
        return String.valueOf(execute(s));
    }

    public static void main(String[] args) {
        // test
    }
}
