package oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区（永久带、非堆）溢出
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 * 1.8 之后，舍弃了 perm ，由 metaspace 来进行类信息维护
 * -verbose:gc -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhuwei/work/dumpData.hprof -XX:+PrintGCDetails
 *
 */
public class MethodOOM {
    public static void main(String[] args) {

        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,objects);
                }
            });

            enhancer.create();
        }
    }

    static class OOMObject{

    }

}
