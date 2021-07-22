package winnow.ak.study.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Winyu
 * @Date: 2021/6/18
 */
public class ThreadPoolExcetorDemo {
    private static final int core = 3;
    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("download_task_" + r);
                t.setDaemon(true);
                return t;
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(core,
                0,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),threadFactory);

        int count = 0;
        while (true) {
            Thread.sleep(200);
            for (int i = 0; i < 9; i++) {
                executor.execute(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("终止。。。。。。");
                        e.printStackTrace();
                    }
                    System.out.println("------------core:\t" + executor.getCorePoolSize() + "\tactive:\t" + executor.getActiveCount() + "\tmax:\t" + executor.getMaximumPoolSize() + "\t queue" + executor.getQueue().size());
                });
            }


            count++;
            if (count == 10) {
                executor.setCorePoolSize(2);
                executor.setMaximumPoolSize(9);
                System.out.println("----------------------------------------");
            }
            if (count == 15) {
                executor.shutdown();
//                executor.shutdownNow();
                System.out.println("=============================================");
                break;
            }
        }



        System.out.println("end------------core:\t" + executor.getCorePoolSize() + "\tactive:\t" + executor.getActiveCount() + "\tmax:\t" + executor.getMaximumPoolSize());

//        Thread.currentThread().join();
    }
}
