package oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出演示代码
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhuwei/work/dumpData.hprof -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -Xms 初始堆，-Xmx 最大堆，-Xmn 年轻代，-XX:SurvivorRatio Eden区与Survivor区比例
 *
 */
public class HeapOOM {

    static class OOMObject{}
    public static void main(String args[]){
        List<OOMObject> oomlist = new ArrayList<OOMObject>();

        // 循环创建对象，直到堆中无可用内存
        while (true){
            oomlist.add(new OOMObject());
        }
    }
}
