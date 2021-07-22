package winnow.ak.study.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Winyu
 * @Date: 2021/5/18
 */
public class MapSource {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("a","a");
        map.get("a");

//        (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        String a= "a";
        int i = a.hashCode();
        System.out.println(i);
        System.out.println();

        AtomicInteger i1 = new AtomicInteger();
        i1.incrementAndGet();
    }
}
