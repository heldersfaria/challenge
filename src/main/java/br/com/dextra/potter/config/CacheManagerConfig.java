package br.com.dextra.potter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@EnableCaching
@Configuration
public class CacheManagerConfig {

    @Value("${spring.cache.cache-names}")
    private List<String> cacheNames;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(cacheNames.toArray(new String[0]));
    }
}
