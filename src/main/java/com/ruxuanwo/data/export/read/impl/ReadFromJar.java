package com.ruxuanwo.data.export.read.impl;

import com.ruxuanwo.data.export.read.AbstractRead;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 去取jar包文件
 * @author ruxuanwo
 * 2019-6-24 16:41
 */
public class ReadFromJar extends AbstractRead {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFromJar.class);

    @Override
    public byte[] read(String filePath) {
        if (StringUtils.isEmpty(filePath)){
            LOGGER.error("文件地址不能为空 filePath = {}", filePath);
            return null;
        }
        LOGGER.info("获取文件路径为filepath = {}", filePath);
        InputStream io = null;
        try {
            io = getClass().getClassLoader().getResourceAsStream(filePath);
            int maxSize = io.available();
            byte[] bytes = new byte[maxSize];
            io.read(bytes);
            return bytes;
        }catch (Exception e){
            LOGGER.error("exception = {}, message = {}", e, e.getMessage());
            return null;
        }finally {
            if (io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

}
