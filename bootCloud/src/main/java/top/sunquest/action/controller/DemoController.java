package top.sunquest.action.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoController {

    @GetMapping("/validate")
    public String validate(HttpServletResponse response, HttpServletRequest request) throws InterruptedException {
        return "sample";
    }

    @PostMapping("/skills")
    public String addIntent(@RequestBody JSONObject jsonParam) {

        System.out.println(jsonParam.toJSONString());

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "json");
        result.put("data", jsonParam);

        return result.toJSONString();
    }
}
