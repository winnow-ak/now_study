package winnow.ak.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Winyu
 * @Date: 2021/6/28
 */
public class ThreadSequence {
    static class MyTask {
        private static ReentrantLock rl = new ReentrantLock();
        private static Condition conditionA = rl.newCondition();
        private static Condition conditionB = rl.newCondition();
        private static Condition conditionC = rl.newCondition();

        public void execute(String flag) {
            rl.lock();

            try {
                for (int i = 1 ; i <= 10 ; i++) {

                    if ("B".equals(flag)) {
                        System.out.println(Thread.currentThread().getName() + " - " + i);
                        conditionC.signal();
                        conditionB.await();
                    }

                    if ("C".equals(flag)) {
                        System.out.println(Thread.currentThread().getName() + " - " + i);
                        conditionA.signal();
                        conditionC.await();
                    }

                    if ("A".equals(flag)) {
                        System.out.println(Thread.currentThread().getName() + " - " + i);
                        conditionB.signal();
                        conditionA.await();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rl.unlock();
            }
        }
    }


    public static void main(String[] args) {
        final MyTask myTask = new MyTask();
        new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.execute("A");
            }
        }, "A").start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.execute("B");
            }
        }, "B").start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.execute("C");
            }
        }, "C").start();
    }
}
