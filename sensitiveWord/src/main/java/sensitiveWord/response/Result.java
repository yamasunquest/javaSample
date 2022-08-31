package sensitiveWord.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map.Entry;

/**
 * 请求返回
 */
@Data
@Slf4j
public class Result implements Serializable {

    private static final String PARAM_DELIMITER = ", ";
    private static final String MESSAGE_PARAM_DELIMITER = ": ";
    private static final String URL_PARAM_DELIMITER = "&";
    private static final String EQUAL = "=";
    private static final int BUFFER_SIZE = 1024;

    private static final long serialVersionUID = 1L;
    private String code;
    private String detailCode;
    private String message;
    private Object data;

    private static Result result(String code, String detailCode, String message, Object data) {
        Result rtnData = new Result();
        rtnData.setCode(code);
        rtnData.setDetailCode(detailCode);
        rtnData.setMessage(message);
        rtnData.setData(data);
        return rtnData;
    }

    public boolean isOk(){
        return BaseCodeEnum.OK.getCode().equals(this.code);
    }

    public static Result ok(String code, String message, Object data) {
        return result(code, "", message, data);
    }
    public static Result ok(Object result) {
        return ok(BaseCodeEnum.OK.getCode(), BaseCodeEnum.OK.getMessage(), result);
    }
    public static Result ok() {
        return ok(null);
    }
    public static Result fail(String code, String detailCode, String message) {
        return result(code, detailCode, message, null);
    }
    public static Result fail(String code, String detailCode, String message, Object data) {
        return result(code, detailCode, message, data);
    }
    public static Result fail(String code, String message, Object data) {
        return result(code, "", message, data);
    }
    public static Result fail(String code, String message) {
        return fail(code, "", message);
    }
    public static Result fail() {
        return fail(BaseCodeEnum.ERROR.getCode(), BaseCodeEnum.ERROR.getMessage());
    }
    public static Result fail(ErrorDescBase error) {
        return fail(error.getCode(), error.getMessage());
    }

    /**
     * 返回业务逻辑错误的同时，添加触发的请求参数相关日志
     * @param code 错误代号
     * @param message 错误信息
     * @return
     */
    public static Result failWithLog(String code, String message) {
        Result result = Result.fail(code, message);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return result;
        }
        HttpServletRequest request = attributes.getRequest();
        if (null == request) {
            return result;
        }
        String remoteHost = request.getRemoteHost();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        String body;
        try {
            ServletInputStream servletInputStream = request.getInputStream();
            StringBuilder content = new StringBuilder();
            byte[] buffer = new byte[BUFFER_SIZE];
            int lens = -1;
            while ((lens = servletInputStream.read(buffer)) > 0) {
                content.append(new String(buffer, 0, lens, Charset.forName("UTF-8")));
            }
            body = content.toString();
        } catch (IOException e) {
            return result;
        }

        StringBuilder params = new StringBuilder();
        for(Entry<String, String[]> entry : request.getParameterMap().entrySet()){
            params.append(entry.getKey());
            params.append(EQUAL);
            params.append(String.join(PARAM_DELIMITER, entry.getValue()));
            params.append(URL_PARAM_DELIMITER);
        }
        log.info("User [{},{},{}] from [{}] [{}] [{}] failure, message [{}], Param[{}],Body[{}]",
                remoteHost, method,url, message, params.toString(), body);
        return result;
    }

    /**
     * 返回业务逻辑错误的同时，添加触发的请求参数相关日志
     */
    public static Result failWithLog(ErrorDescBase error) {
        return failWithLog(error.getCode(), error.getMessage());
    }

    /**
     * 常用数据校验异常
     */
    private static final String INVALID_PARAM = "parameter is invalid";
    private static final String MISS_PARAM = "parameter is missing";
    private static final String INVALID_FORMAT = "parameter is invalid format";
    private static final String INVALID_NULL = "parameter is null";

    /**
     * 参数非法
     */
    public static ParamCheckException invalidParam(){
        return new ParamCheckException(INVALID_PARAM, null);
    }
    public static ParamCheckException invalidParam(String... parameterNames){
        return new ParamCheckException(INVALID_PARAM, String.join(PARAM_DELIMITER, parameterNames));
    }

    /**
     * 缺少必要的参数
     */
    public static ParamCheckException missParam(){
        return new ParamCheckException(MISS_PARAM, null);
    }
    public static ParamCheckException missParam(String... parameterNames){
        return new ParamCheckException(MISS_PARAM, String.join(PARAM_DELIMITER, parameterNames));
    }

    /**
     * 参数格式错误
     */
    public static ParamCheckException invalidFormat(){
        return new ParamCheckException(INVALID_FORMAT, null);
    }
    public static ParamCheckException invalidFormat(String... parameterNames){
        return new ParamCheckException(INVALID_FORMAT, String.join(PARAM_DELIMITER, parameterNames));
    }
    /**
     * 参数值为空
     */
    public static ParamCheckException invalidNull(){
        return new ParamCheckException(INVALID_NULL, null);
    }
    public static ParamCheckException invalidNull(String... parameterNames){
        return new ParamCheckException(INVALID_NULL, String.join(PARAM_DELIMITER, parameterNames));
    }
}
