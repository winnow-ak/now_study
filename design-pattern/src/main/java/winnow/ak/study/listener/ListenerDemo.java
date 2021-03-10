package winnow.ak.study.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class ListenerDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        context.publishEvent(new MessageEvent("明天放假了"));

    }
}
