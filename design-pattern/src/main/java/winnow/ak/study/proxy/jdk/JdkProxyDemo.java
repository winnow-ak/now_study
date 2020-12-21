package winnow.ak.study.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class JdkProxyDemo {
    public static void main(String[] args) {
        //被代理对象（目标对象）
        TargetService serviceA = new HeadhuntingA();
        serviceA.pushResume("target");
        //给目标对象创建代理对象
        TargetService serviceProxy = (TargetService) Proxy.newProxyInstance(
                serviceA.getClass().getClassLoader(),
                new Class[]{TargetService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(serviceA, args);
                    }
                });
         serviceProxy.pushResume("target");
        System.out.println("by proxy:" + serviceA.getClass() + "\n proxy:" + serviceProxy.getClass());

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");



//        ClassLoader loader,:指定当前目标对象使用类加载器,获取加载器的方法是固定的
//        Class<?>[] interfaces,:目标对象实现的接口的类型,使用泛型方式确认类型
//        InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法,会把当前执行目标对象的方法作为参数传入

    }
}
