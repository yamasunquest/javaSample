package top.sunquest.thread;

import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolOperation {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int count = 0;
            for (;;){
                log.info("---- 1>" + count++);
            }
        },"t1");

        System.out.println("log out");
        ThreadPoolExecutor tpe;
        //WorkQueue wq = new BlockingQueue<String>();
//        tpe = new ThreadPoolExecutor(3,5,10, TimeUnit.MILLISECONDS,wq);
    }


}
