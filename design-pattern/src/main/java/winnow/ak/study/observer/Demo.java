package winnow.ak.study.observer;

import java.util.Observer;

/**
 * @Author: Winyu
 * @Date: 2020/12/20
 */
public class Demo {
    public static void main(String[] args) {
        Wechat wechat = new Wechat();
        Observer u1 = new User("zhangsan");
        Observer u2 = new User("lisi");
        //添加观察者
        wechat.addObserver(u1);
        wechat.addObserver(u2);
        wechat.sendMessage("-----今天周五放假了----");
        //发送特定消息
//        wechat.notifyObservers("明天周末");
        //通过sendMessage 赋值发送
//        wechat.notifyObservers();
        //通知
        wechat.sendMessage("-----今天周五放假了----");
//        wechat.notifyObservers("明天周末");
        //删除观察者
        wechat.deleteObserver(u1);
//        wechat.sendMessage("====今天周五不放假了====");

        //通知
//        wechat.notifyObservers();
    }
}
