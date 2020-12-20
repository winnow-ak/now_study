package winnow.ak.study.singleton;

import java.io.Serializable;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class Singleton implements Serializable {
    private volatile static Singleton singleton;
    public Singleton (){}

    public static  Singleton getInstance(){
        if (singleton ==null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = singleton;
                }
            }
        }
        return singleton;
    }

    /**
     * why 从内存读出而组装的对象破坏了单例的规则. 单例是要求一个JVM中只有一个类对象的, 而现在通过反序列化,一个新的对象克隆了出来.
     * how 这样当JVM从内存中反序列化地"组装"一个新对象时,就会自动调用这个 readResolve方法来返回我们指定好的对象了, 单例规则也就得到了保证.
     * @return
     */
    private Object readResolve(){
        return singleton;
    }

}
