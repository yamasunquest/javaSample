package top.sunquest.hik;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

public class GetCameraPreviewURL {
    public static String GetCameraPreviewURL() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "192.168.11.200:443"; // 平台的ip端口
        ArtemisConfig.appKey = "24338637";  // 密钥appkey
        ArtemisConfig.appSecret = "205TajuFyE8NnFapI2By";// 密钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/video/v1/cameras/previewURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        // 电瓶车
        //jsonBody.put("cameraIndexCode", "430ce69c2bed4d988746c2d506b40cc9");
        // 人脸抓拍
        jsonBody.put("cameraIndexCode", "4a26765814c648de9a2ec7944bba9723");
        // 高空抛物
        //jsonBody.put("cameraIndexCode", "b51b5a048bf8470e9569128e814fb3a3");
        // 异常行为摄像头一
        //jsonBody.put("cameraIndexCode", "f4eae344bb3e48108f98fdb837285ed8");
        // 异常行为摄像头二
        //jsonBody.put("cameraIndexCode", "9f4afcc573234a629d1d53f2775348a8");

        jsonBody.put("streamType", 0);
        jsonBody.put("protocol", "rtsp");
        jsonBody.put("transmode", 0);
        jsonBody.put("expand", "streamform=rtp");

        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        return result;
    }

    public static void main(String[] args) {
        String result = GetCameraPreviewURL();
        System.out.println("result结果示例: " + result);
    }

}
