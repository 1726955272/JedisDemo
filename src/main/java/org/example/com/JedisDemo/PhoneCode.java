package org.example.com.JedisDemo;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.SortedMap;

public class PhoneCode {
    public static void main(String[] args) {
        String code=GetCode();
        String phone="13217925580";
        CRRedis(phone,code);
        CYCode(code,phone);
    }
    //验证码检测
    public static void CYCode(String code,String phone){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String codeKey="VerifyCode"+phone+":code";
        if (jedis.get(codeKey).equals(code)){
            System.out.println("验证码正确");
        }
        else System.out.println("验证码错误");
        jedis.close();
    }
    //获取六位手机验证码
    public static String GetCode(){
        String code="";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            code+=i1;
        }
        return code;
    }
    //将验证码，手机号存入Redis
    public static void CRRedis(String phone,String code){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String countKey="VerifyCode"+phone+":count";
        String codeKey="VerifyCode"+phone+":code";
        jedis.setex(codeKey,Long.valueOf(2*60),code);
        String count=jedis.get("count");
        if (count==null)
        {
            Long second= Long.valueOf(24*60*60);
            jedis.setex("count",second,"1");
        }else if (Integer.parseInt(count)<=2){
            jedis.incr("count");
        }else{
            System.out.println("你今天已经发送了三次验证码了");
            jedis.close();
        }
        jedis.close();
    }
}
