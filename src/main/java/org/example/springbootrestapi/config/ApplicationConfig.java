package org.example.springbootrestapi.config;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

@EnableRetry
@Configuration
@EnableCaching
public class ApplicationConfig {
    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        return new GeolatteGeomModule();
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("locations", "locationsById", "address");
    }
}