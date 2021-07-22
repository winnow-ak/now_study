package winnow.ak.study.letcode;

import java.util.*;

/**
 * @Author: Winyu
 * @Date: 2021/6/21
 */
public class leetCode {
    public static void main(String[] args) {
//        printChar();
        List<List<Integer>> result = combine(3, 2);
        result.forEach(l -> {
            System.out.println(result);
        });


        MergeTowList.Node node1 = new MergeTowList.Node(1);
        MergeTowList.Node node2 = new MergeTowList.Node(2);
        MergeTowList.Node node3 = new MergeTowList.Node(3);
        MergeTowList.Node node4 = new MergeTowList.Node(4);
        MergeTowList.Node node5 = new MergeTowList.Node(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node1;

        boolean a = hasCycle1(node1);
        boolean b = hasCycle(node1);
        System.out.println(a);
        System.out.println(b);
    }

    public static void printChar() {
        //字符串输入输出
        //输入：aaaccbb
        //输出：3a2c2b
        Map<String, Integer> map = new LinkedHashMap<>();
        String str = "aaacccbb";
        System.out.println(str.indexOf("a"));
        char[] chars = str.toCharArray();
        for (char c : chars) {
            String s = String.valueOf(c);
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.print(entry.getValue() + entry.getKey());
        }
    }

    // 1-n 之间的数。k个数的全部组合
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<Integer>();
        dfs(n, k, 1, result, temp);
        return result;
    }

    private static void dfs(int n, int k, int next,
                            List<List<Integer>> result, List<Integer> temp) {
        //达到三个数放到结果里
        if (temp.size() == k) {
            result.add(new ArrayList(temp));
            return;
        }
        //1 -n 循环
        for (int i = next; i < n + 1; i++) {
            temp.add(i);
            dfs(n, k, i + 1, result, temp);
            temp.remove(temp.size() - 1);
        }

    }


    // 方法1
    public int binarySearch(int[] dataset, int data, int beginIndex, int endIndex) {
// 如果查找的数要比开始索引的数据要小或者是比结束索引的书要大，或者开始查找的索引值大于结束的索引值返回-1没有查到
        int midIndex = (beginIndex + endIndex) / 2;
        if (data < dataset[beginIndex] || data > dataset[endIndex] || beginIndex > endIndex) {
            return -1;
        }
        if (data < dataset[midIndex]) {
            return binarySearch(dataset, data, beginIndex, midIndex - 1);
        } else if (data > dataset[midIndex]) {
            return binarySearch(dataset, data, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }


    //链表存在环
    public static boolean hasCycle(MergeTowList.Node head) {
        Set<MergeTowList.Node> hash = new HashSet<>();  //创建一个哈希表，要会方法
        int a = 0;
        while (head != null) {
            if (hash.contains(head)) {  //判断是否存在
                System.out.println("循环次数：" + a);
                return true;
            } else {
                hash.add(head);
            }
            a++;
            head = head.next;  //继续向下走
        }

        return false;
    }

    //链表存在环
    public static boolean hasCycle1(MergeTowList.Node head) {
        MergeTowList.Node slow = head.next;
        MergeTowList.Node fast = slow.next;
        while (slow != null && fast != null) {
            if (fast == slow) {
                return true;
            }
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        return false;
    }
}
