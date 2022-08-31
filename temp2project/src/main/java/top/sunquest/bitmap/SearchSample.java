package top.sunquest.bitmap;

import sun.reflect.annotation.ExceptionProxy;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

public class SearchSample {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(df.format(System.currentTimeMillis()));

        BigData bigData = new BigData();
//        byte[] arrayByte = {0b00000001, 0b00000010, 0b00000100, 0b00001000, 0b00010000, 0b00100000, 0b01000000, -0b10000000};
//        ByteBuffer bf =  ByteBuffer.allocate(12500000);
//        for(long i = 0L;i<99999999;i++)
//            bf.put((int)i/8,arrayByte[(int)i%8]);

        for(int i = 0;i<99999999;i++){
            long temp = 15300000000L + i;
            bigData.addData(temp);
        }
        System.out.println(df.format(System.currentTimeMillis()));

        for(int j =0;j<999999;j++)
            bigData.searchData(15370053910L);

        System.out.println(df.format(System.currentTimeMillis()));

        while (true)
            Thread.sleep(1000);
    }
}

class BigData{

    ByteBuffer[]  DATA_STORAGE =  new ByteBuffer[999];
    private final static byte[] ARRAY_BYTE = {0b00000001, 0b00000010, 0b00000100, 0b00001000, 0b00010000, 0b00100000, 0b01000000, -0b10000000};

    public BigData(){

    }

    public void initData(int index){

    }

    public void addData(long data) throws Exception {
        DataInfo dataInfo = new DataInfo(data);

        ByteBuffer bf = DATA_STORAGE[dataInfo.arrayIndex];
        if(bf ==  null){
            bf =  ByteBuffer.allocate(12500000);
            DATA_STORAGE[dataInfo.arrayIndex] = bf;
        }

        bf.put(dataInfo.byteIndex, (byte) (bf.get(dataInfo.byteIndex) | ARRAY_BYTE[dataInfo.bytePosition]));
    }

    public boolean searchData(long data) throws Exception {
        DataInfo dataInfo = new DataInfo(data);

        ByteBuffer bf = DATA_STORAGE[dataInfo.arrayIndex];
        if(bf ==  null){
            return false;
        }

        return ARRAY_BYTE[dataInfo.bytePosition] == (bf.get(dataInfo.byteIndex) & ARRAY_BYTE[dataInfo.bytePosition]);
    }

    public void delData(long data){

    }
}

class DataInfo{
    // 所在的存储分区（999 哪一个）
    int arrayIndex;
    // 需要存储的数据
    int number;

    // 存在哪个 byte 位
    int byteIndex;
    // 需要存储的数据
    int bytePosition;

    DataInfo(long data) throws Exception {
        if(data > 99999999999L)
            throw new Exception("数据过大");

        // 所在的存储分区（999 哪一个）
        arrayIndex = (int)(data/100000000);
        // 需要存储的数据
        number = (int)(data%100000000);

        // 存在哪个 byte 位
        byteIndex = number/8;
        // 存在哪个 bit 位
        bytePosition = number%8;
    }
}