package nio.chatDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChatServer {

    //选择器
    private Selector selector;
    //建立通道
    private ServerSocketChannel ssc;
    //服务端口
    private static int serverPort = 6667;

    public ChatServer(){
        try{
            // 初始化服务(实例化、非阻塞、绑端口)
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(serverPort));

            // 注册选择器
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listen();
    }

    public void listen(){
        try{
            //
            while (true){
                int n = selector.select();
                if (n == 0) continue;

                Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
                while (ite.hasNext()){
                    SelectionKey key = ite.next();

                    //接收到链接请求
                    if(key.isAcceptable()){
                        // 初始化请求链接
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);

                        //发送上线通知
                        sendMessage(sc.getRemoteAddress() + ":上线了",sc);
                    }

                    if(key.isReadable()){
                        String msg = receiveMessage(key);

                        // 发送给特定用户
                        if(msg.startsWith("u:")){
                            String userID = msg.substring(msg.indexOf(":"),msg.lastIndexOf(":"));
                            sendMessage(msg,(SocketChannel) key.channel(),findChannel(userID));
                        }else{
                            // 群发
                            sendMessage(msg,(SocketChannel) key.channel());
                        }

                    }

                    ite.remove();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 接受消息处理
     * @param key
     * @return
     */
    private String receiveMessage(SelectionKey key){
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        StringBuffer sb =  new StringBuffer();

        try{
            while(true){
                int count =  sc.read(byteBuffer);

                // -1 时，客户端主动断开
                if(-1 == count) throw new IOException("连接断开");

                if (count > 0 ){
                    byteBuffer.flip();
                    sb.append(new String(byteBuffer.array(),byteBuffer.position(),byteBuffer.limit(),"utf-8"));
                    byteBuffer.clear();
                }else{
                    break;
                }
            }

            System.out.println("recive message" + sb.toString());
            return sb.toString();
        }catch (Exception ex){
            try{
                // 用户下线，安全退出
                System.out.println(((SocketChannel) key.channel()).getRemoteAddress() + "：离线了。");
                key.cancel();
                sc.close();
            }catch (Exception ex1){
                ex1.printStackTrace();
            }
        }

        return "";
    }

    /**
     * 群发消息
     * @param msg
     * @param self
     */
    private void sendMessage(String msg,SocketChannel self){
        for(SelectionKey key:selector.keys()){
            if (!(key.channel() instanceof SocketChannel) || key.channel() == self)
                continue;

            SocketChannel destChannel = (SocketChannel)key.channel();
            sendMessage(msg,self,destChannel);
        }
    }

    /**
     * 发给消息，给具体客户端
     * @param msg
     * @param self
     * @param dest
     */
    private void sendMessage(String msg,SocketChannel self,SocketChannel dest){
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
            dest.write(byteBuffer);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private SocketChannel findChannel(String dest){
        SocketChannel sc;

        try {
            for(SelectionKey key:selector.keys()){
                if (!(key.channel() instanceof SocketChannel))
                    continue;

                String address = ((SocketChannel) key.channel()).getRemoteAddress().toString();
                if(address.endsWith(dest)){
                    return (SocketChannel)key.channel();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
