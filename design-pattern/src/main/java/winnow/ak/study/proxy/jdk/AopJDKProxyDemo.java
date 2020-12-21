package winnow.ak.study.proxy.jdk;

import winnow.ak.study.proxy.aop.CustomAspect;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class AopJDKProxyDemo {
    public static void main(String[] args) {
        System.out.println("无代理前 调用方法 userService.saveUser 输出......");
        TargetService targetService = new HeadhuntingA();
        targetService.pushResume("1234567890");
        System.out.println("有代理后AOP 是怎么样的？ Proxy......");
        TargetService proxyUserService = (TargetService) JdkDynamicProxyGenerator.generatorJDKProxy(targetService, new CustomAspect());
        proxyUserService.pushResume("1234567890");        /** 制造异常,两个入参都是null   */
//        proxyUserService.pushResume(null);        /** 制造异常,两个入参都是null   */
    }
}
