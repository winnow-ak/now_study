package winnow.ak.study.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class MessageEvent extends ApplicationEvent {
    private String message;


    public MessageEvent(String message) {
        super(message);
        this.message = message;
    }

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
