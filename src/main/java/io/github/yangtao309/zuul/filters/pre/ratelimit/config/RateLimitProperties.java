package io.github.yangtao309.zuul.filters.pre.ratelimit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import static io.github.yangtao309.zuul.filters.pre.ratelimit.config.RateLimitProperties.PREFIX;

/**
 * @author Marcos Barbero
 */
@Data
@ConfigurationProperties(PREFIX)
public class RateLimitProperties {

    public static final String PREFIX = "zuul.ratelimit";

//    private Map<String, Policy> policies = new LinkedHashMap<>();
    private boolean enabled;
    private boolean behindProxy;
}