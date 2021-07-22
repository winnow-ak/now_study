package winnow.ak.study.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Winyu
 * @Date: 2021/6/17
 */
public class ThreadState extends Thread {

    String name;
    @Override
    public void run(){
        System.out.println(name);
    }

    public static void main(String[] args) throws Exception {

//        new Thread(new ThreadState()).run();
        new Thread(new ThreadState()).start();

//        ThreadState.waiting();
//        ThreadState.timed_waiting();
//        ThreadState.join();

        //yieldTest
//        for (int i = 0; i < 50; i++) {
//            if (i < 10) {
//                //让出cpu
//                new Thread(new yieldTest(String.valueOf(i), true)).start();
//                continue;
//            }
//            new Thread(new yieldTest(String.valueOf(i), false)).start();
//        }
        //
    }

    public static void waiting() throws Exception {
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        while (true) {
            Thread.sleep(1000);
            System.out.println(thread.getState());
        }
        // WAITING
    }

    public static void timed_waiting() throws Exception {
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        while (true) {
            Thread.sleep(1000);
            System.out.println(thread.getState());
        }
        // TIMED_WAITING
    }

    private static volatile Map<String, AtomicInteger> count = new ConcurrentHashMap<>();

    static class yieldTest implements Runnable {
        private String name;
        private boolean isYield;
        public yieldTest(String name, boolean isYield) {
            this.name = name;
            this.isYield = isYield;
        }
        @Override
        public void run() {
            long l = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                if (isYield) {
                    Thread.yield();
                }
                AtomicInteger atomicInteger = count.get(name);
                if (null == atomicInteger) {
                    count.put(name, new AtomicInteger(1));
                    continue;
                }
                atomicInteger.addAndGet(1);
                count.put(name, atomicInteger);
            }
            System.out.println("线程编号：" + name + " 执行完成耗时：" + (System.currentTimeMillis() - l) + " (毫秒)" + (isYield ? "让出CPU----------------------" : "不让CPU"));
        }
    }

    public static void join1() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("thread before");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("thread after");
        });
        thread.start();
        System.out.println("main begin！");
        thread.join();
        System.out.println("main end！");

    }



}
