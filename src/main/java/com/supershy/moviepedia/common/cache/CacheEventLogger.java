package com.supershy.moviepedia.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        Object key = cacheEvent.getKey();
        Object oldValue = cacheEvent.getOldValue();
        Object newValue = cacheEvent.getNewValue();

        log.info("Cache event occurred - Key: {}, Old Value: {}, New Value: {}",
                key != null ? key : "null",
                oldValue != null ? oldValue : "null",
                newValue != null ? newValue : "null");
    }
}
