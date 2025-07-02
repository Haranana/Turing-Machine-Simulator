package com.hubosm.turingsimulator.services;

import com.hubosm.turingsimulator.dtos.SimulationStepDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CacheServiceImpl {
    private final RedisTemplate<String, Object> redis;
    private static final int defaultHoursTTL = 2;

    public CacheServiceImpl() {
        redis = new RedisTemplate<>();
    }

    public void saveDefObject(UUID jobId, Object object, Duration TTL){
        redis.opsForValue().set(redisKey(jobId, "def") , object , TTL);
    }

    public void saveDefObject(UUID jobId, Object object){
        redis.opsForValue().set(redisKey(jobId, "def") , object , Duration.ofHours(defaultHoursTTL));
    }

    public void saveObject(UUID jobId, String keySuffix, Object object, Duration TTL){
        redis.opsForValue().set(redisKey(jobId,keySuffix),object, TTL);
    }

    public void saveObject(UUID jobId, String keySuffix, Object object){
        redis.opsForValue().set(redisKey(jobId,keySuffix),object, Duration.ofHours(defaultHoursTTL));
    }

    public void saveAllHash(UUID jobId, String keySuffix, Map<?,?> map){
        redis.opsForHash().putAll(redisKey(jobId,keySuffix),map);
    }

    public void saveAllHash(UUID jobId, Map<?,?> map){
        redis.opsForHash().putAll(redisKey(jobId,"meta"),map);
    }

    public void saveHash(UUID jobId, String keySuffix, String key, Object value){
        redis.opsForHash().put(redisKey(jobId,keySuffix),key,value);
    }

    public void saveHash(UUID jobId, String key, Object value){
        redis.opsForHash().put(redisKey(jobId,"meta"),key,value);
    }

    public void saveAllList(UUID jobId, String keySuffix, List<?> values){
        redis.opsForList().rightPushAll(redisKey(jobId, keySuffix), values);
    }

    public List<?> getList(UUID jobId, String keySuffix){
        return redis.opsForList().range(redisKey(jobId,keySuffix) , 0 , -1);
    }

    public Object getDefObject(UUID jobId){
        return redis.opsForValue().get(redisKey(jobId, "def"));
    }

    public Object getObject(UUID jobId, String keySuffix){
        return redis.opsForValue().get(redisKey(jobId, keySuffix));
    }

    public Object getHash(UUID jobId, String keySuffix, String key){
        return redis.opsForHash().get(redisKey(jobId,keySuffix), key);
    }

    public void setTTL(UUID jobId, String part, Duration TTL){
        redis.expire(redisKey(jobId, part), TTL);
    }

    private String redisKey(UUID jobId, String part) {
        return "sim:" + jobId + ":" + part;
    }
}
