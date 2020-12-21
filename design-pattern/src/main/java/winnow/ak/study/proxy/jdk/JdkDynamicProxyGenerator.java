package winnow.ak.study.proxy.jdk;

import winnow.ak.study.proxy.aop.IAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class JdkDynamicProxyGenerator {
    public static Object generatorJDKProxy(TargetService targetPoint, final IAspect aspect) {
        return Proxy.newProxyInstance(
                /**
                 *   委托类使用的类加载器
                 */
                targetPoint.getClass().getClassLoader(),
                /**
                 * 委托类实现的接口
                 */
                targetPoint.getClass().getInterfaces(),
                /**
                 * 生成的动态代理类关联的 执行处理器，代理我们的业务逻辑被生成的动态代理类回调
                 * 具体逻辑代码执行,返回值为方法执行结果, 在aop模型中，委托类的接口方法称为切点。
                 */
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {                        // 执行切面方法,对入参进行校验
                        boolean prepareAction = aspect.startTransaction(args);
                        if (prepareAction) {                           // 具体逻辑代码执行,返回值为方法执行结果
                            Object result = method.invoke(targetPoint, args);
                            aspect.endTrasaction();
                            return result;
                        } else {
                            throw new RuntimeException("args: " + Arrays.toString(args) + "不能为null ");
                        }
                    }
                });
    }
}
