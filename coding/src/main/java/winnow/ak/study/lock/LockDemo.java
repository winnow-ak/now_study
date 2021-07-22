package winnow.ak.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: Winyu
 * @Date: 2021/5/21
 */
public class LockDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);

        // 三个线程

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("A thread come in");
                try{ TimeUnit.SECONDS.sleep(3L); } catch (InterruptedException e) { e.printStackTrace(); }
            }finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("B thread come in");
                try{ TimeUnit.SECONDS.sleep(3L); } catch (InterruptedException e) { e.printStackTrace(); }
            }finally {
                lock.unlock();
            }
        },"B").start();


        new Thread(()->{
            lock.lock();
            try{
                System.out.println("C thread come in");
                try{ TimeUnit.SECONDS.sleep(3L); } catch (InterruptedException e) { e.printStackTrace(); }
            }finally {
                lock.unlock();
            }
        },"C").start();

    }
}
