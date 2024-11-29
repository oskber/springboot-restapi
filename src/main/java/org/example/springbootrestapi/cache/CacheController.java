package org.example.springbootrestapi.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/cache")
    public void getCache() {
        cacheService.printCacheContents();
    }
}
