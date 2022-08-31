package sensitiveWord.response;

/**
 * Result 的 fail 方法接收实现该接口的枚举作为参数
 */
public interface ErrorDescBase {

	/**
	 * 获取错误码
	 * @return
	 */
	String getCode();

	/**
	 * 获取错误描述信息
	 * @return
	 */
	String getMessage();
}
