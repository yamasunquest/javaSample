package nio.handle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ServerHandleSample {

    public static void readHandler(SocketChannel sc) throws IOException {
        // 1 buffer 的分散和聚合
        ByteBuffer[] echoBuffer = new ByteBuffer[2];
        echoBuffer[0] = ByteBuffer.allocate(2);
        echoBuffer[1] = ByteBuffer.allocate(3);
        long readNum = 0;

        // 2 读取通讯通道数据
        while (true){
            Arrays.asList(echoBuffer).forEach(buffer -> buffer.clear());
            // 2 可以放置 buffer 数组，channel 顺序放置到 buffer 中
            long l = sc.read(echoBuffer);
            // 如果读取到的是 -1，客户端已经主动断开连接
            if(-1 == l) throw new IOException("连接断开");
            if(0 == l) continue;

//            readNum +=l;
//            if(readNum > 5)
//                break;

            // 3 查看 buffer 的输出内容
            Arrays.asList(echoBuffer)
                    .forEach(buffer -> {
                        buffer.flip();
                        System.out.println("position:" + buffer.position() + ",limit:" + buffer.limit());
                        try {
                            System.out.println(new String(buffer.array(), buffer.position(), buffer.limit(), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

}
