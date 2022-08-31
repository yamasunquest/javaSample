package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientChannelSample {
    public static void main(String args[] ) throws IOException {

        // 1：初始化一个连接
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",6666);

        // 2：等待服务链接上
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){

            }
        }

        // 3：发送数据
        String sendMessage = "helloSunkai!";
        ByteBuffer byteBuffer = ByteBuffer.wrap(sendMessage.getBytes());
        socketChannel.write(byteBuffer);
    }

}
