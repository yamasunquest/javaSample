package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelSample {

    public static void main(String[] args){
        // channel 读取操作
//        try {
//            read(new File("/Users/zhuwei/Desktop/1.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // channel 写入操作
//        try {
//            write(new File("/Users/zhuwei/Desktop/1.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 内存映射文件
        try {
            memoryFile(new File("/Users/zhuwei/Desktop/1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取 channel 数据，写入到内存
     * @param file
     * @throws IOException
     */
    static void read(File file) throws IOException {
        //建立文件读取
        FileInputStream fi = new FileInputStream(file);
        //绑定文件读取和channel
        FileChannel fc = fi.getChannel();
        //建立 buffer
        ByteBuffer buffer = ByteBuffer.allocate(8);

        // 读取 channel 内容，放到 buffer 中
        int readCount = 0;
        while (true){
            // channel 读取内容到 buff
            buffer.clear();
            if(-1 == fc.read(buffer))
                break;

            //buffer 输出内容
            buffer.flip();
            System.out.println("当前读取次数：" + readCount ++);
            System.out.println("当前读取内容：" + new String(buffer.array(), buffer.position(), buffer.limit(), "utf-8"));
        }

        fi.close();
    }

    /**
     * 读取内存数据，写入到 channel
     * @param file
     * @throws FileNotFoundException
     */
    static void write(File file) throws IOException {
        // 建立文件输出
        FileOutputStream fo = new FileOutputStream(file);
        //绑定 channel 和文件输出
        FileChannel fc = fo.getChannel();
        //建立 buffer
        ByteBuffer buffer = ByteBuffer.allocate(8);
        String  s = "who is sunquest，\r\n sunquest is king";
        int offset = 0;
        int mod = s.getBytes().length % buffer.capacity();

        // buffer 写入 channel 中
        while(offset < s.getBytes().length){
            // 将内存数据读入到 buffer
            buffer.clear();
            buffer.put(s.getBytes("utf-8"),
                    offset
                    ,offset + buffer.capacity() > s.getBytes().length?mod:buffer.capacity());

            //  channel 读取 buffer 中的数据
            buffer.flip();
            fc.write(buffer);

            offset += buffer.capacity();
        }

        fo.close();
    }

    /**
     * 大文件的内存操作
     * 零拷贝
     *
     */
    static void memoryFile(File file) throws IOException {
        // 建立文件 randomAccessFile
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");

        //绑定 randomAccessFile 和 channel
        FileChannel fileChannel = randomAccessFile.getChannel();

        //设定内存映射
        // 操作模式 FileChannel.MapMode.READ_WRITE 读写模式
        // position 代表开始读取的位置
        // size 映射的大小，在这个范围内操作
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,randomAccessFile.length());
        for(int i = 0;i < "凯".getBytes().length;i++){
            mappedByteBuffer.put(10+ i,"凯".getBytes()[i]);
        }

        mappedByteBuffer.put("牛".getBytes()); // 注意是字节，一定要转换成字节才行，这里对镜像的修改直接生效到节点文件中了！

//        fileChannel.position(randomAccessFile.length()); // 定位到文件末尾
//        mappedByteBuffer.clear(); // 注意！一定要是clear，将limit定位到capacity！如果是flip则当前limit才在第二个字符位置！
//        fileChannel.write(mappedByteBuffer);

        randomAccessFile.close();
    }

}
