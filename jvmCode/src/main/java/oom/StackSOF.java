package oom;

/**
 * 栈溢出演示代码
 * -verbose:gc -Xss160k -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhuwei/work/dumpData.hprof -XX:+PrintGCDetails
 * -Xss 每个线程分配的内存大小
 */

public class StackSOF {

private int stackLength = -1;


    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String args[]){
        StackSOF ssf = new StackSOF();

        try{
            // 嵌套入栈，栈的深度不断提升
            ssf.stackLeak();
        }catch (Throwable ex){
            System.out.println(ssf.stackLength);
            throw ex;
        }
    }
}
