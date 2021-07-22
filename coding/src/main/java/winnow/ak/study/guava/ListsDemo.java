package winnow.ak.study.guava;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: Winyu
 * @Date: 2021/3/25
 */
public class ListsDemo {

    //分隔
    private void partition() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        List<List<Integer>> partition = Lists.partition(list, 2);

        System.out.println(partition.size());
    }


}
