package sensitiveWord.response;

/**
 * 返回错误代码
 */
public enum BaseCodeEnum implements ErrorDescBase{
    // 通用错误
    OK("0", "success"), ERROR("103001", "服务器内部错误"),
    ERROR_103103("103002", "参数错误"),
    ERROR_404("404", "对应请求不存在"),
    ERROR_400("400", "请求不合法"),
    ERROR_403("403", "禁止访问"),
    ERROR_401("401","没有权限");

    private String code;
    private String message;

    BaseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
