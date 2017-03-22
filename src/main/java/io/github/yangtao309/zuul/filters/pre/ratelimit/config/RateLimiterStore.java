package io.github.yangtao309.zuul.filters.pre.ratelimit.config;

import java.util.Map;

/**
 * Created by yangtao on 17/3/22.
 */
public interface RateLimiterStore {
  /**
   * Retrieves the list of all stored Zuul rate limiter policy from the persistence storage.
   *
   * @return the list of zuul policies
   */
  Map<String, Policy> findAll();
}