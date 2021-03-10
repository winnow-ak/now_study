package winnow.ak.study.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.security.MessageDigest;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class MessageListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) applicationEvent;
            System.out.println("监听到MessageEvent事件，message:" + messageEvent.getMessage());
        }
    }
}
