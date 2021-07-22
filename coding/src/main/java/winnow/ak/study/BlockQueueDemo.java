package winnow.ak.study;

import org.assertj.core.util.Lists;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Winyu
 * @Date: 2021/6/18
 */
public class BlockQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue(2);

        //put 阻塞
//        queue.put(1);
//        queue.put(2);
//        queue.put(3);
        // offer timeout 阻塞
//        queue.offer(1);
//        queue.offer(2);
//        queue.offer(3,10,TimeUnit.SECONDS);
        //take 阻塞
//        queue.take();

        // poll timie out 阻塞
//        queue.poll();
//        queue.poll(5,TimeUnit.SECONDS);
//
//        queue.add(1);
//        queue.add(2);
//        queue.remove();
        queue.put(1);
        queue.put(2);
        queue.remove(1);

        System.out.println(queue.size());
    }
}
