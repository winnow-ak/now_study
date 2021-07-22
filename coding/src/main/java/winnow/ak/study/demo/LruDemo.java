package winnow.ak.study.demo;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Winyu
 * @Date: 2021/6/21
 */
public class LruDemo<K, V> extends LinkedHashMap<K, V> {

    private int cacheSize;

    public LruDemo(int cacheSize) {
        //构造函数一定要放在第一行  //那个f如果不加  就是double类型，然后该构造没有该类型的入参。 然后最为关键的就是那个入参 true
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        //重写LinkedHashMap原方法 //临界条件不能有等于，否则会让缓存尺寸小1
        return size() > cacheSize;
    }

    public static void main(String[] args) {
        LruDemo<Integer,String> cache = new LruDemo<Integer,String>(4);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "two");
        cache.put(6, "three");

        Iterator<Map.Entry<Integer,String>> it = cache.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, String> entry = it.next();
            Integer key = entry.getKey();
            System.out.print("Key:\t"+key);
            String Value = entry.getValue();  //这个无需打印...
            System.out.println();
        }
    }
}
