package winnow.ak.study.proxy.cglib;

import winnow.ak.study.proxy.aop.CustomAspect;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class AopCglibProxyDemo {
    public static void main(String[] args) {
        System.out.println("before Proxy......");
        CglibService cglibService = new CglibService();
        cglibService.buyHouse("sss");
        System.out.println("引入Cglib  Proxy代理库 后......");
        CglibService proxy = (CglibService) CglibProxyGenerator.generatorCglibProxy(cglibService, new CustomAspect());
        proxy.buyHouse("aaa");
    }
}
