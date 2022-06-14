package com.example.fruits.service;

import com.example.fruits.utils.RedisUtil;
import com.example.fruits.utils.SpringbootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisTest extends SpringbootTestBase {

    @Autowired
    private StringRedisTemplate StringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
//        ValueOperations<String, String> valueOperations = StringRedisTemplate.opsForValue();
//        valueOperations.set("zpf","test",20, TimeUnit.SECONDS);
        redisUtil.setSetWithTime("zpf",10,"test");
    }

}
