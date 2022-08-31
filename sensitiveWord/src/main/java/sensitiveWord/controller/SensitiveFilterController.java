package sensitiveWord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sensitiveWord.dao.LoadResources;
import sensitiveWord.data.SensitiveWord;
import sensitiveWord.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Set;

/**
 * 敏感词过滤
 */
@RestController
@RequestMapping(value = "/sensitive", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensitiveFilterController {
    @Autowired
    private LoadResources lr;

    @Autowired
    private SensitiveWord sw;

    @GetMapping("/contains")
    public Result contains(HttpServletResponse response, HttpServletRequest request) throws InterruptedException {
        return Result.ok("contains");
    }

    /**
     * 重新加载敏感词库
     * @return 是否加载成功
     */
    @GetMapping("/reload")
    public Result reload(){
        try {
            Set<String> sensitiveWord = lr.fromDiskPath();
            sw.rebuildModel(sensitiveWord);
        } catch (FileNotFoundException e) {
            return Result.fail("103100","sensitive word file that from disk load failed");
        }catch (Exception ex){
            return Result.fail();
        }

        return Result.ok("sensitive word reload success!");
    }


    /**
     * 敏感词
     * @return 是否加载成功
     */
    @GetMapping("/replaceSensitiveWord")
    public Result replaceSensitiveWord(@RequestParam(required = true) String txt){
        String replaceTxt;
        try {
            System.out.println(System.currentTimeMillis());
            replaceTxt = sw.replaceSensitiveWord(txt,"*");
            System.out.println(System.currentTimeMillis());

        }catch (Exception ex){
            return Result.fail();
        }

        return Result.ok(replaceTxt);
    }
}
