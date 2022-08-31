package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(6666).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();

        ServerBootstrap b = new ServerBootstrap();
        // 使用一个 nio 的时间轮训组
        b.group(new NioEventLoopGroup())
                // 对应的 channel 也是 nio
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });

        ChannelFuture f = b.bind().sync();
        f.channel().closeFuture().sync();
    }
}
