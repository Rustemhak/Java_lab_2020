package ru.itis.javalab.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.redis.repository.RedisRepository;
@Service
public class RedisService {

    @Autowired
    private RedisRepository redisRepository;

    public String getElementByKey(String key) {
        return redisRepository.getElementByKey(key);
    }

    public Boolean hasKey(String key) {
        return redisRepository.hasKey(key);
    }

    public void set(String key, String value) {
        redisRepository.set(key, value);
    }
}
