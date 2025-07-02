package com.hubosm.turingsimulator.services;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CacheService {

    public void saveDefObject(UUID jobId, Object object, Duration TTL);
    public void saveDefObject(UUID jobId, Object object);
    public void saveObject(UUID jobId, String keySuffix, Object object, Duration TTL);
    public void saveObject(UUID jobId, String keySuffix, Object object);
    public void saveAllHash(UUID jobId, String keySuffix, Map<?,?> map);
    public void saveAllHash(UUID jobId, Map<?,?> map);
    public void saveHash(UUID jobId, String keySuffix, String key, Object value);
    public void saveHash(UUID jobId, String key, Object value);
    public void saveAllList(UUID jobId, String keySuffix, List<?> values);
    public List<?> getList(UUID jobId, String keySuffix);
    public Object getDefObject(UUID jobId);
    public Object getObject(UUID jobId, String keySuffix);
    public Object getHash(UUID jobId, String keySuffix, String key);
    public void setTTL(UUID jobId, String part, Duration TTL);
}
