package com.supershy.moviepedia.common.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.IOException;
import java.net.URISyntaxException;

@EnableCaching
@Configuration
public class EhCacheConfig {

    @Bean
    public CacheManager cacheManager() throws URISyntaxException, IOException {
        // ehcache를 의존성 주입하면 자동으로 잡아서 ehcache provider를 반환한다.
        // 만약 프로젝트에 여러 개의 캐시 프로바이더를 쓴다면 명시적으로 지정해 주어야 한다.
        CachingProvider cachingProvider = Caching.getCachingProvider();

        // 캐시 설정 파일 로드
        ClassPathResource resource = new ClassPathResource("ehcache.xml");

        // ehcache에 ehcache.xml의 설정을 주입한 후 JCacheCacheManager 객체 반환
        javax.cache.CacheManager ehCacheManager = cachingProvider.getCacheManager(
                resource.getURL().toURI(),
                getClass().getClassLoader()
        );
        return new JCacheCacheManager(ehCacheManager);
    }
}
