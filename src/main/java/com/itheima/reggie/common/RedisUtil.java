package com.itheima.reggie.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for handling Redis operations, such as caching, JWT token management,
 * and blacklisting tokens after logout.
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Stores a key-value pair in Redis with an expiration time.
     *
     * @param key The key to store
     * @param value The value associated with the key
     * @param timeout Expiration time duration
     * @param unit The time unit (e.g., SECONDS, MINUTES)
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Retrieves the value associated with a given key from Redis.
     *
     * @param key The key to retrieve
     * @return The value stored in Redis, or null if not found
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Deletes a key from Redis.
     *
     * @param key The key to remove
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Checks if a key exists in Redis.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Adds a JWT token to the Redis blacklist, preventing further use.
     *
     * @param token The JWT token to blacklist
     * @param expiration The expiration timestamp of the token in milliseconds
     */
    public void addToBlacklist(String token, long expiration) {
        long remainingTime = expiration - System.currentTimeMillis();
        if (remainingTime > 0) {
            redisTemplate.opsForValue().set(token, "blacklisted", remainingTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Checks if a JWT token is blacklisted in Redis.
     *
     * @param token The JWT token to check
     * @return true if the token is in the blacklist, false otherwise
     */
    public boolean isBlacklisted(String token) {
        return hasKey(token);
    }
}
