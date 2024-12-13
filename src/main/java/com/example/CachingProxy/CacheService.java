package com.example.CachingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    // get value from Redis cache
    public String getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Store data in Redis cache
    public void cacheResponse(String key, String response) {
        redisTemplate.opsForValue().set(key, response);
    }

    // Clear cache
    public void clearCache() {
        redisTemplate.delete(redisTemplate.keys("*"));  // Clears all keys from Redis
    }
}
