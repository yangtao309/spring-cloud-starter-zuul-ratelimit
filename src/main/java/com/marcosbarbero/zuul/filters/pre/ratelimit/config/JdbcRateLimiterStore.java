package com.marcosbarbero.zuul.filters.pre.ratelimit.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by yangtao on 17/3/22.
 */
public class JdbcRateLimiterStore implements RateLimiterStore {

  /**
   * The shared instance of row mapper.
   */
  private static final ZuulRateLimiterRowMapper ZUUL_RATE_LIMITER_ROW_MAPPER = new ZuulRateLimiterRowMapper();

  /**
   * The default table name.
   */
  private static final String DEFAULT_TABLE_NAME = "zuul_rate_limiter";

  /**
   * Casandra template.
   */
  private final JdbcOperations jdbcOperations;

  /**
   * The table name.
   */
  private final String table;

  /**
   * Creates new instance of {@link JdbcRateLimiterStore}.
   *
   * @param jdbcOperations the jdbc template
   */
  public JdbcRateLimiterStore(JdbcOperations jdbcOperations) {
    this(jdbcOperations, DEFAULT_TABLE_NAME);
  }

  /**
   * Creates new instance of {@link JdbcRateLimiterStore}.
   *
   * @param jdbcOperations      the jdbc template
   * @param table               the table name
   * @throws IllegalArgumentException if {@code keyspace} is {@code null}
   *                                  or {@code table} is {@code null} or empty
   */
  public JdbcRateLimiterStore(JdbcOperations jdbcOperations, String table) {
    Assert.notNull(jdbcOperations, "Parameter 'jdbcOperations' can not be null.");
    Assert.hasLength(table, "Parameter 'table' can not be empty.");
    this.jdbcOperations = jdbcOperations;
    this.table = table;
  }


  @Override
  public Map<String, Policy> findAll() {
    Map<String, Policy> policyMap = Maps.newHashMap();
    final String sql = "select * from " + table;
    List<Policy> policies = jdbcOperations.query(sql, ZUUL_RATE_LIMITER_ROW_MAPPER);
    for (Policy policy : policies) {
      policyMap.put(policy.getId(), policy);
    }
    return policyMap;
  }

  /**
   *
   * @author tao.yang
   */
  private static class ZuulRateLimiterRowMapper implements RowMapper<Policy> {
    @Override
    public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {

      final String type = rs.getString("type");
      List<Policy.Type> types = Lists.newArrayList();
      if (StringUtils.isNotEmpty(type)) {
        String[] arr = type.split(",");
        for (String key : arr) {
          types.add(Policy.Type.convert(key));
        }
      }

      Policy policy = new Policy(
          rs.getString("id"),
          rs.getLong("refresh_interval"),
          rs.getLong("limit"),
          types
      );

      return policy;
    }
  }
}