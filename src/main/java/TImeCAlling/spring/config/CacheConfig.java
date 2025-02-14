package TImeCAlling.spring.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean(name = "customCacheManager")
    public CacheManager customCacheManager() {
        org.ehcache.config.CacheConfiguration<Object, Object> ehcacheConfig =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Object.class,
                                Object.class,
                                ResourcePoolsBuilder.heap(100)
                        )
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(3600)))
                        .build();

        javax.cache.configuration.Configuration<Object, Object> jcacheConfig =
                Eh107Configuration.fromEhcacheCacheConfiguration(ehcacheConfig);

        javax.cache.spi.CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        cacheManager.createCache("hibernateCache", jcacheConfig);

        return cacheManager;
    }
}
