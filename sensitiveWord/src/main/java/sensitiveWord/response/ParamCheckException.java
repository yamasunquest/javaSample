package sensitiveWord.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ParamCheckException extends IllegalArgumentException {

    /**
     * 异常说明
     */
    private String causeInfo;

    /**
     * 异常参数名（多个使用,分隔）
     */
    private String paramName;
}