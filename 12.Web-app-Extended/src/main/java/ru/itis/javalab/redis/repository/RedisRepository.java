package ru.itis.javalab.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String getElementByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Boolean hasKey(String key) {
        Boolean find = redisTemplate.hasKey(key);
        System.out.println(find);
        return find;
    }
}
