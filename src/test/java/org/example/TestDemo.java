package org.example;


import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class TestDemo {
    @Test
    public void test1(){
        //连接Redis
        Jedis jedis =  new Jedis("127.0.0.1",6379);
        //添加数据
        jedis.set("age","15");
        jedis.set("name","lisi");
        jedis.set("genter","man");
        jedis.set("id","1");
        //获取数据
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        for (String key:
        keys) {
            String s = jedis.get(key);
            System.out.println(key+":"+s);
        }
        //查看过期时间
        System.out.println(jedis.ttl("age"));
        //插入多个字符串
        jedis.mset("name","zhangsan","age","20");
        List<String> mget = jedis.mget("name", "age");
        System.out.println(mget);
    }
    @Test
    /**
     * Redis存入List
     */
    public void ListTest(){
        Jedis jedis =  new Jedis("127.0.0.1",6379);
        jedis.lpush("user01","age","name","genter");
        List<String> user01 = jedis.lrange("user01", 0, -1);
        System.out.println(user01);

    }
}
