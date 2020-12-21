package winnow.ak.study.proxy.processor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class BeanProcessorDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        SmsService smsService = (SmsService) applicationContext.getBean("smsServiceImpl");
//        System.out.println(smsService);
        //通过修改SmsProxy 限制条件 达到代理增加效果
        smsService.send("18112345678","恭喜你中奖了");
        // 必须手动调用 容器销毁的方法 --- web服务器tomcat，自动调用容器销毁
//        applicationContext.close();
    }
}
