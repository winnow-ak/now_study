package winnow.ak.study.juc;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Winyu
 * @Date: 2021/7/17
 */
public class CountDownLatchTest {

    public static List<Integer> getBatch(Integer count){
        List<Integer> result = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            result.add(i);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<Integer> result = Lists.newArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        List<Integer> batch = getBatch(20);
                        result.addAll(batch);
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(result.size());
    }
}
