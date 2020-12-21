package winnow.ak.study.proxy.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class SmsProxy implements BeanPostProcessor {
    private final String proxyMethodName = "send";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("进入前置条件");
        if (bean instanceof SmsService){
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!proxyMethodName.contains(method.getName())){
                        //防止加方法不限制
                        return method.invoke(bean,args);
                    }
                    //发送短信限制
                    //1. 同手机号一天只能发10次
                    // redis.incr("sms.limit.day");
                    if (false){
                        throw new Exception("今天手机号短信发送频率太快");
                    }
                    return method.invoke(bean,args);
                }
            };
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(),new Class[] {SmsService.class},handler);
        }
        return bean;
    }


}
