package com.ruxuanwo.data.export.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConfigurationProperties(prefix = "spring.jedis")
public class JedisConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisConfiguration.class);

    private int database;
    private String host;
    private int port;
    private String password;
    /**
     * 连接池中的最大空闲连接(默认为8)
     */
    private Integer maxIdle;
    /**
     * 连接池中的最小空闲连接(默认为0)
     */
    private Integer minIdle;
    /**
     * 连接超时时间（毫秒）
     */
    private Integer timeout;
    /**
     * 连接池最大连接数（使用负值表示没有限制）
     */
    private Integer maxTotal;
    /**
     * 连接池最大阻塞等待时间（毫秒）（使用负值表示没有限制）
     */
    private Long maxWaitMillis;

    @Bean
    public JedisPool redisPoolFactory() {
        LOGGER.info("开始初始化JedisPool...");
        if (host == null || "".equals(host)) {
            LOGGER.error("JedisPool初始化失败:host为空!");
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if (maxIdle != null) {
            jedisPoolConfig.setMaxIdle(maxIdle);
        }
        if (minIdle != null) {
            jedisPoolConfig.setMinIdle(minIdle);
        }
        if (maxTotal != null) {
            jedisPoolConfig.setMaxTotal(maxTotal);
        }
        if (maxWaitMillis != null) {
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        }
        password = "".equals(password) ? null : password;
        JedisPool jedisPool = null;
        try {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        } catch (Exception e) {
            LOGGER.error("redisPoolFactory new JedisPool Exception:", e);
            return null;
        }
        LOGGER.info("JedisPool初始化完毕...");
        return jedisPool;
    }


    public void setDatabase(int database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
}
