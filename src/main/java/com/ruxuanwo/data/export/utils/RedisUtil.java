package com.ruxuanwo.data.export.utils;

import com.ruxuanwo.data.export.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ruxuanwo
 * @Date: 2018/8/30/0030 15:00
 */
@Component("redisUtil")
public class RedisUtil {
    @Autowired
    private RedisClient redisClient;

    public boolean existsKey(String key) {
        return redisClient.existsKey(key);
    }

    public Object get(String key) {
        Object object = null;
        boolean isKey = redisClient.existsKey(key);
        if (isKey) {
            try {
                object = redisClient.get(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public void set(String key, Object value, int seconds) {
        try {
            redisClient.set(key, value, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
