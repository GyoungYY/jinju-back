package com.aguang.jinjuback;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestRedis {

    private ApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-redis.xml");
    }

    @Test
    public void testJedisPool() {
        Jedis jedis = null;
        JedisPool pool = (JedisPool) applicationContext.getBean("jedisPool");
        try  {
            jedis = pool.getResource();
            jedis.set("name", "lisi");
            String name = jedis.get("name");
            System.out.println(name);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(jedis != null){
                //关闭连接
                jedis.close();
            }
        }
    }
}