package com.ruxuanwo.data.export.read;

/**
 * @author ruxuanwo
 * 2019-6-24 16:39
 */
public abstract class AbstractRead {

    /**
     * 读取字节流文件
     * @param params 参数
     * @return字节数组
     */
    public abstract byte[] read(String params);

}
