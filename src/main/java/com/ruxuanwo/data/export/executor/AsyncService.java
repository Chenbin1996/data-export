package com.ruxuanwo.data.export.executor;

import com.ruxuanwo.data.export.config.FieldConfig;
import com.ruxuanwo.data.export.dto.Line;

import java.util.List;

/**
 * @Author: ChenBin
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
    void executeAsync(String templateId, List<FieldConfig> headConfig, List<Line> lineList, String logId);
}
