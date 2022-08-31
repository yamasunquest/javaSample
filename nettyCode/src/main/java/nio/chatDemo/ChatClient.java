package nio.chatDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class ChatClient {
    private final String  HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel sc;
    private String userName;

    public ChatClient(){
        try{
            // 初始化客户端
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST,PORT);

            // 客户端连接服务器
            // 2：等待服务链接上
            if(!sc.connect(inetSocketAddress)){
                while (!sc.finishConnect()){
                }
            }

            Random random = new Random();
            userName = String.valueOf(random.nextInt(100));
            //this.sendMessage("u:" + userName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 客户端发送消息
     * @param msg
     */
    public void sendMessage(String msg){
        try {

            sc.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端接收到消息
     */
    public void receiveMessage(){
        try {
            int n = selector.select();

            if (n == 0)
                return;

            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

            while (ite.hasNext()){
                SelectionKey key = ite.next();

                //接收到消息
                if(key.isReadable()){
                    SocketChannel sc =  (SocketChannel)key.channel();
                    ByteBuffer bf = ByteBuffer.allocate(16);
                    while (true){
                        int count = sc.read(bf);

                        if(count > 0){
                            bf.flip();
                            System.out.print(new String(bf.array(),bf.position(),bf.limit(),"utf-8"));
                            bf.clear();
                        }else{
                            break;
                        }
                    }
                    System.out.println("");
                }


                ite.remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();

        new Thread(() -> {
            while (true){
                chatClient.receiveMessage();
                try {
                    Thread.currentThread().sleep(3000);
                }catch (Exception ex){

                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendMessage(s);

        }


    }
}
