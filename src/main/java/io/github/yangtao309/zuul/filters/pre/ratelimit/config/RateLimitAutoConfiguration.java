package io.github.yangtao309.zuul.filters.pre.ratelimit.config;

import io.github.yangtao309.zuul.filters.pre.ratelimit.RateLimitFilter;
import io.github.yangtao309.zuul.filters.pre.ratelimit.config.redis.RedisRateLimiter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * @author Marcos Barbero
 */
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnProperty(name = RateLimitProperties.PREFIX + ".enabled", havingValue = "true")
public class RateLimitAutoConfiguration {

    @Bean
    public RateLimitFilter rateLimiterFilter(RateLimiter rateLimiter, RateLimitProperties rateLimitProperties,
                                             RouteLocator routeLocator, RateLimiterStore rateLimiterStore) {
        return new RateLimitFilter(rateLimiter, rateLimitProperties, routeLocator, rateLimiterStore);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterStore rateLimiterStore(JdbcOperations jdbcOperations) {
        return new JdbcRateLimiterStore(jdbcOperations);
    }

    @ConditionalOnClass(RedisTemplate.class)
    public static class RedisConfiguration {
        @Bean
        public StringRedisTemplate redisTemplate(RedisConnectionFactory cf) {
            return new StringRedisTemplate(cf);
        }

        @Bean
        public RateLimiter redisRateLimiter(RedisTemplate redisTemplate) {
            return new RedisRateLimiter(redisTemplate);
        }
    }

}