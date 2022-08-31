package jdtast;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;


public class astSample {

    public static void main(String[] args) {
        int i = 0001;
        int j = 10;

        int cc = 1 + 2;
        cc = Math.abs(1);

        for(int ii = 0;i <10;i++){

        }

        i = (10 + 3)*2 ;
        System.out.println("hello");
        Math.abs(-6);

        "sss".substring(Math.abs(-2),i);

        int m = i;
        int k = Math.abs(i);

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "json");

        if("a".equals("B")){
            System.out.println("hello");
        }else if(10 > 9){
            System.out.println("hello");
            System.out.println("hello");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public void getSample(){

    }


}
