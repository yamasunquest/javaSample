package top.sunquest.logic2result;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class logicReversePolish {
    public static void main(String[] args) {
        try{

            boolean a = true;
            boolean b = true;
            boolean c = true;
            boolean d = true;
            boolean e = true;

            if(((1 + 2) > 3) || b && (c && ( d || e ) )){

            }

            //String logicExpression = "a and b or c and d";
            //String logicExpression = "( a and b ) and ( c and d )";
            //String logicExpression = "( a or b ) and ( c or d )";
            String logicExpression = "( 1 + 2 > 3 ) and a or b and ( c and ( d or e ) ) and f";

            List<String> rpnList = transToRPN(logicExpression);

            System.out.println(JSON.toJSONString(rpnList));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 运算符优先级
     * 数字大，优先级高
     */
    static Map<String,Integer> LogicPriority = new HashMap<>(4);
    static{
        LogicPriority.put("(",0);
        LogicPriority.put(")",0);
        LogicPriority.put("or",1);
        LogicPriority.put("and",2);
        LogicPriority.put(">",3);
        LogicPriority.put("<",3);
        LogicPriority.put("+",4);
        LogicPriority.put("-",4);
        LogicPriority.put("*",5);
        LogicPriority.put("/",5);
    }

    /**
     * 转换成逆波兰表达式
     * @param logicExpression
     * @return
     */
    public static Stack<String> transToRPN(String logicExpression){
        //逆波兰栈
        Stack<String> RPNStack = new Stack<>();
        //逻辑符栈
        Stack<String> operStack = new Stack<>();

        //分离字符串
        String[] array = logicExpression.split(" ");
        for(int t  = 0;t < array.length; t++){
            String temp = array[t];
            //过滤空
            if (temp.equals(" ")){
                continue;
            }

            //逻辑符压栈
            if(LogicPriority.containsKey(temp)){
                //逻辑符栈是空的，逻辑符压栈
                if (operStack.isEmpty()){
                    operStack.push(temp);
                    continue;
                }

                //左括号"("压栈
                String topOper = operStack.peek();
                if (temp.equals("(")){
                    //如果是左括号，直接放入逻辑符栈
                    operStack.push(temp);
                    continue;
                }

                //如果是右括号")"，则逻辑符栈依次出栈，压栈逆波兰，直到出栈运算符为左括号，并舍弃左括号
                if (temp.equals(")")){
                    while (!operStack.peek().toString().equals("(")){
                        RPNStack.push(operStack.pop());
                    }
                    operStack.pop();
                    continue;
                }

                //当前逻辑符的优先级小于栈顶逻辑符优先级，压栈逆波兰
                if (LogicPriority.get(temp) <= LogicPriority.get(topOper)) {
                    do {
                        RPNStack.push(operStack.pop());
                    } while (!operStack.isEmpty() && LogicPriority.get(operStack.peek()) >= LogicPriority.get(temp));
                }

                //当前逻辑符的优先级大于（等于）栈顶逻辑符优先级，压栈逻辑符
                operStack.push(temp);
                continue;
            }

            //字符压栈
            RPNStack.push(temp);

        }

        //逻辑符栈中的逻辑符，则依次出栈，压入逆波兰栈
        while(!operStack.isEmpty()){
            RPNStack.push(operStack.pop());
        }

        return RPNStack;
    }
}

class logicObject {
    logicObject left;
    logicObject right;
    String operator;
}