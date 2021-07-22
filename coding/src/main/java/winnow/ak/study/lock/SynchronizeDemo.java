package winnow.ak.study.lock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Winyu
 * @Date: 2021/5/26
 */
public class SynchronizeDemo {
    private static Thread numThread, letterThread;

    public static void main(String[] args) throws InterruptedException {

        letterThread = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                System.out.print((char) ('A'+i) + "|");
                LockSupport.unpark(numThread);
                LockSupport.park();
            }
        }, "letterThread");
        numThread = new Thread(() -> {
            for (int i = 1; i <= 26; i++) {
                LockSupport.park();
                System.out.print(i + " ");
                LockSupport.unpark(letterThread);
            }
        }, "numThread");
        numThread.start();
        letterThread.start();
    }
}