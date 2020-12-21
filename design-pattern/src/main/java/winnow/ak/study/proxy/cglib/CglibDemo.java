package winnow.ak.study.proxy.cglib;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class CglibDemo {
    public static void main(String[] args) {
        CglibService cglibService = new CglibService();
        CglibProxy cglibProxy = new CglibProxy();
        CglibService instance = (CglibService) cglibProxy.getInstance(cglibService);
        instance.buyHouse("123");
    }
}
