package com.ruxuanwo.data.export.executor;

import com.ruxuanwo.data.export.core.FieldConfig;
import com.ruxuanwo.data.export.core.Line;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: ruxuanwo
 * @Date: 2018/7/13/0013 14:32
 */
public interface AsyncService {
    /**
     * 执行异步任务
     * @param templateId
     * @param headConfig
     * @param lineList
     * @param logId
     */
    Future<String> executeAsync(String templateId, List<FieldConfig> headConfig, List<Line> lineList, String logId);
}
