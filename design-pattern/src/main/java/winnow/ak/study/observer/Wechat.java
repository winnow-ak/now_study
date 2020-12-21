package winnow.ak.study.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 被观察者
 *
 * @Author: Winyu
 * @Date: 2020/12/20
 */
public class Wechat extends Observable {

    private String message;

    private List<Observer> list;

    public Wechat() {
        list = new ArrayList<Observer>();
    }

    @Override
    public void notifyObservers() {
        System.out.println("开始通知 notifyObservers");
        list.forEach(l -> {
            l.update(this, message);
        });
    }

    @Override
    public void notifyObservers(Object o) {
        System.out.println("========start notifyObservers start =======");
        list.forEach(l -> {
            l.update(this, o);
        });
        System.out.println("========end notifyObservers end =======");
    }

    @Override
    public void addObserver(Observer o) {
        list.add(o);

    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public void deleteObserver(Observer o) {
        list.remove(o);
    }


    public void sendMessage(String message) {
        if(!message.equals(this.message)){
            this.message = message;
            super.setChanged();
            this.notifyObservers();
        }
    }
}
