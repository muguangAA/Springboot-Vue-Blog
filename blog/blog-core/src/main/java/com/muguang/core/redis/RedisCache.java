package com.muguang.core.redis;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

    @Resource
    private RedisTemplate redisTemplate;

    public <T> boolean exist(final String key){
        return !Objects.isNull(getCacheObject(key));
    }

    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
    }

    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public <T> T getCacheObject(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Long deleteObjects(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    public <T> long setCacheList(final String key, final List<T> list) {
        Long count = redisTemplate.opsForList().rightPushAll(key, list);
        return count == null ? 0 : count;
    }

    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperations.add(t);
        }
        return setOperations;
    }

    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public <T> void setCacheMap(final String key, Map<String, T> map){
        if (map != null) {
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public <T> void setCacheMapValue(final String key, final String hashKey, final T value){
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <T> T getCacheMapValue(final String key, final String hashKey){
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteCacheMapValue(final String key, final String hashKey){
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hashKeys){
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    public boolean expire(final String key, final Integer timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.MINUTES));
    }

    public boolean expire(final String key, final Integer timeout, final TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

}
