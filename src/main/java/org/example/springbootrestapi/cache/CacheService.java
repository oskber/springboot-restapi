package org.example.springbootrestapi.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void printCacheContents() {
        Cache locationsCache = cacheManager.getCache("locations");
        Cache addressCache = cacheManager.getCache("address");

        System.out.println("Locations Cache:");
        printCache(locationsCache);

        System.out.println("Address Cache:");
        printCache(addressCache);
    }

    private void printCache(Cache cache) {
        if (cache != null) {
            ConcurrentMap<Object, Object> nativeCache = (ConcurrentMap<Object, Object>) cache.getNativeCache();
            nativeCache.forEach((key, value) -> System.out.println(key + " = " + value));
        }
    }

}