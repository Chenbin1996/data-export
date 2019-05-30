package com.ruxuanwo.data.export.config;


import com.ruxuanwo.data.export.redis.RedisClient;
import com.ruxuanwo.data.export.redis.impl.JedisClientCluster;
import com.ruxuanwo.data.export.redis.impl.JedisClientSingle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisClientConfiguration {
    @Bean
    public RedisClient getJedisClient(JedisPool jedisPool, JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            return new JedisClientCluster(jedisCluster);
        }
        if (jedisPool != null) {
            return new JedisClientSingle(jedisPool);
        }
        return null;
    }
}
