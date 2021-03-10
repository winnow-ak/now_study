package winnow.ak.study.reids;

/**
 * @Author: Winyu
 * @Date: 2021/1/25
 */
public class RedssionTest {
    String LUA = "if(redis.call('exists',KEYS[1]) == 0 then redis.call('hset',KEYS[1],ARGV[2],1); redis.call('expire',KEYS[1],ARGV[1]); return nil;";
}
