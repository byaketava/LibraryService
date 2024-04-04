package by.byak.library.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public InMemoryCache<Integer, Object> cache() {
        return new InMemoryCache<>();
    }
}
