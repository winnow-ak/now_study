package winnow.ak.study.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * @Author: Winyu
 * @Date: 2020/12/20
 */
public class User implements Observer {

    private String userName;

    public User(String userName) {
        this.userName = userName;
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("用户：【" + userName  + "】收到消息：" + arg);
    }
}
