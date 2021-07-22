package winnow.ak.study.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Winyu
 * @Date: 2021/6/21
 */
public class RandomWeight {
    public static final Map<String, Integer> WEIGHT_LIST = new HashMap<String, Integer>();

    static {
        // 权重之和为50
        WEIGHT_LIST.put("A", 10);
        WEIGHT_LIST.put("B", 40);
        WEIGHT_LIST.put("C", 20);
        WEIGHT_LIST.put("D", 30);
    }

    public static String getServer() {
        int totalWeight = 0;
        Object[] weights = RandomWeight.WEIGHT_LIST.values().toArray();
        for (int i = 0; i < weights.length; i++) {
            Integer weight = (Integer) weights[i];
            totalWeight += weight;
        }

        int randomPos = new Random().nextInt(totalWeight);
        for (String ip : RandomWeight.WEIGHT_LIST.keySet()) {
            Integer value = RandomWeight.WEIGHT_LIST.get(ip);
            if (randomPos < value) {
                return ip;
            }
            randomPos = randomPos - value;
        }

        return (String) RandomWeight.WEIGHT_LIST.keySet().toArray()[new java.util.Random().nextInt(RandomWeight.WEIGHT_LIST.size())];
    }

    public static void main(String[] args) {

        // 连续调用10次
        Map<String, Integer> countAtom = new HashMap<String, Integer>();
        for (int i = 0; i < 5; i++) {
            String node = RandomWeight.getServer();
            if (countAtom.containsKey(node)) {
                countAtom.put(node, countAtom.get(node) + 1);
            } else {
                countAtom.put(node, 1);
            }
        }
        System.out.println("概率统计：");
        for (String id : countAtom.keySet()) {
            System.out.println(" " + id + " 出现了 " + countAtom.get(id) + " 次");
        }
    }
}
