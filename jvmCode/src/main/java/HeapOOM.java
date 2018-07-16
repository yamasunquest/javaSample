import java.util.ArrayList;
import java.util.List;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhuwei/work/dumpData -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */

public class HeapOOM {

    static class OOMObject{}

    public static void main(String args[]){
        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true){
            list.add(new OOMObject());
        }
    }
}
