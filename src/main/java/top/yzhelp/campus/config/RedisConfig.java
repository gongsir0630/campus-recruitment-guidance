package top.yzhelp.campus.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Configuration
@EnableCaching
public class RedisConfig {
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
    return RedisCacheManager.create(factory);
  }
}
