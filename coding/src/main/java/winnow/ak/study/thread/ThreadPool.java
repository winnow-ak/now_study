package winnow.ak.study.thread;

import java.util.concurrent.*;

/**
 * @Author: Winyu
 * @Date: 2021/5/21
 */
public class ThreadPool implements Runnable {
    public static void main(String[] args) {

//        ThreadPoolExecutor fixedThreadPool1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        ThreadPoolExecutor singleThreadExecutor =(ThreadPoolExecutor) Executors.newSingleThreadExecutor();
//        singleThreadExecutor.setCorePoolSize(5);
        ThreadPool demo = new ThreadPool();
        for (int i = 0; i < 5; i++) {
            singleThreadExecutor.submit(demo);
        }
        singleThreadExecutor.shutdown();
        while (true) {
            if (singleThreadExecutor.isTerminated()) {
                System.out.println("fixedThreadPool1 结束");
                break;
            }
        }
        ThreadPoolExecutor fixedThreadPool3 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        fixedThreadPool3.setCorePoolSize(3);
        for (int i = 0; i < 5; i++) {
            fixedThreadPool3.submit(demo);
        }
        fixedThreadPool3.shutdown();
    }


    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("子线程" + threadName + " 开启");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            System.out.println(threadName + "异常");
        } finally {
            System.out.println("子线程" + threadName + " 结束");
        }
    }
}
