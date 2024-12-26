package com.spring.springboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) { return redisTemplate.opsForValue().get(key); }

    public void delete(String key) { redisTemplate.delete(key); }

    public void authEmail(String key, Object object) {
        redisTemplate.opsForValue().set(key, object, 180, TimeUnit.SECONDS);
    }
}
