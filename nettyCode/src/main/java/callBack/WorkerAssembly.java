package callBack;

import lombok.Data;

/**
 * 采用组合的思想实现
 */
@Data
public class WorkerAssembly {
    // 输入参数
    private Object input;
    // 工作者
    private Worker worker;
    // 回调
    private CallBack callBack;
}
