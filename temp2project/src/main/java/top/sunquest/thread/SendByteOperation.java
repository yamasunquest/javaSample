package top.sunquest.thread;

import okio.ByteString;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SendByteOperation {
    private static ExecutorService es  = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException, InterruptedException {

        WakeupThread wakeupThread = new WakeupThread();
        WakeupThread wakeupThread2 = new WakeupThread();
        WakeupThread wakeupThread3 = new WakeupThread();


        PipedInputStream in = wakeupThread.getInputPiped();
        PipedOutputStream out = new PipedOutputStream();
        out.connect(in);

        // 线程加入队列
        es.submit(wakeupThread);

        File file=new File("/Users/sunkai/Desktop/audio", "vad测试数据1.wav");
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        byte[] buf = new byte[3200];
        int length = 0;
        //循环读取文件内容，输入流中将最多buf.length个字节的数据读入一个buf数组中,返回类型是读取到的字节数。
        //当文件读取到结尾时返回 -1,循环结束。
        while((length = fis.read(buf)) != -1){
            Thread.sleep(10);
            out.write(buf);
            System.out.printf("文件发送：" + length);
        }

        System.out.printf("hello");
    }
}

class WakeupThread implements Callable<String>{
    //输入管道
    private PipedInputStream inputPiped = new PipedInputStream();

    public PipedInputStream getInputPiped() {
        return inputPiped;
    }

    @Override
    public String call() throws Exception {
        byte[] buf = new byte[1024];
        int length = 0;

        while(true){
            try {
                // 接收消息，并且打印
                if((length = inputPiped.read(buf)) !=-1){
                    System.out.println("接收到：" + length);
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}