package nio;

import lombok.val;
import nio.handle.ServerHandleSample;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerChannelSample {

    /**
     * buffer 的分散和聚合
     * 相当于一个公司（应用程序）-> 部门（ServerSocketChannel）-> 职工（SocketChannel）
     * 部门（ServerSocketChannel）在一个地方接受请求，接收到请求后，分配给 职工（SocketChannel）去做
     * 部门 由管理部（Selector）进行管理
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 客户端必须建立相对应的 SocketChannel（Socket）来与服务器建立连接
        // 服务器接受到客户端的连接受，再生成一个Socket或者SocketChannel与此客户端通信
        // 建立一个接受客户请求的服务

        // 1 服务器必须先建立 ServerSocketChannel（ServerSocket） 来等待客户端的连接
        // serverSocketChannel 初始化
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        ssc.socket().bind(inetSocketAddress);

        // 2 将NIO通道选绑定到择器（向管理部报备）,当然绑定后分配的主键为skey
        Selector selector = Selector.open();
        SelectionKey skewy = ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println(skewy);

        // 3 开始对接入通道进行管理
        while (true) {
            // 4 查看当前发生的事件个数，此处是阻塞方法
            int n = selector.select();
            System.out.println("当前选择器内的事件数量（socket number）：" + n);

            // 4.1 如果没有 socket ，继续等待
            if (n == 0) continue;

            // 4.2 通道内事件的集合
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

            //遍历事件的集合中的每个事件
            while (ite.hasNext()) {
            SelectionKey key = ite.next();
                // 5.1 accept：服务端接收客户端连接事件，对应值为SelectionKey.OP_ACCEPT(16)
                if (key.isAcceptable()) {
                    // 5.2 部门（ServerSocketChannel）接受这个链接请求
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    // 5.3 部门招聘出一个职工（SocketChannel）来接待这个客户的请求
                    SocketChannel clntChan = serverSocketChannel.accept();
                    clntChan.configureBlocking(false);
                    // 5.4 客户端信道注册到选择器上，指定该信道key值的属性为OP_READ，同时为该信道指定关联的附件
                    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(100));
                    System.out.println("事件：建立链接");
                    System.out.println("connection from：" + clntChan);
                }

                // 5.1 read：读事件，对应值为SelectionKey.OP_READ(1)
                if (key.isReadable()){
                    try{
                        System.out.println("事件：接受数据");
                        ServerHandleSample.readHandler((SocketChannel)key.channel());
                    }catch (Exception ex){
                        // 异常的的优雅退出
                        key.cancel();
                        key.channel().close();
                        ite.remove();
                        continue;
                    }
                }

                // 5.1 write：写事件，对应值为SelectionKey.OP_WRITE(4)
                if (key.isWritable() && key.isValid()){

                }

                // 5.1 connect：客户端连接服务端事件，对应值为SelectionKey.OP_CONNECT(8)
                if (key.isConnectable()) {
                    System.out.println("isConnectable = true");
                }

                // 6 清除事件，避免重复处理
                ite.remove();
            }
        }
    }
}