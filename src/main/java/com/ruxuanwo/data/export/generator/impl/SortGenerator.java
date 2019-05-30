package com.ruxuanwo.data.export.generator.impl;

import com.ruxuanwo.data.export.dto.GeneratorData;
import com.ruxuanwo.data.export.generator.Generator;
import com.ruxuanwo.data.export.redis.RedisClient;
import com.ruxuanwo.data.export.utils.DataBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;

/**
 * 排序号生成器
 * @author chenbin
 */
@Component
public class SortGenerator implements Generator {

    @Autowired
    private RedisClient redisClient;

    private static final String SQL_1 = "select * from ";
    private static final String SQL_2 = " order by ";
    private static final String SQL_3 = " desc limit 1";
    private static final int INCREASE = 8;
    private static final String SEPARATED = "_";
    private static final int REDIS_TIME = 120;

    @Override
    public Object generator(GeneratorData data) throws Exception {
        Object sort;
        //放入Redis缓存
        String key = data.getTableName() + SEPARATED + data.getFieldName();
        if(redisClient.existsKey(key)){
            sort = redisClient.get(key);
        }else {
            String sql = SQL_1 + data.getTableName() + SQL_2 + data.getFieldName() + SQL_3;
            Connection connection = data.getConnection();
            sort = DataBaseUtil.selectPrepare(connection, sql, new HashMap<>(1), data.getFieldName());
            if(sort == null ){
                sort = "1";
            }
        }
        sort = (Integer.parseInt(sort.toString()) + INCREASE) + "";
        redisClient.set(key, sort,REDIS_TIME);
        return sort;
    }
}
