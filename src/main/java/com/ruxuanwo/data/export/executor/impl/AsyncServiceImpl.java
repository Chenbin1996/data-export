package com.ruxuanwo.data.export.executor.impl;

import com.ruxuanwo.data.export.mapper.EdTemplateMapper;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.core.FieldConfig;
import com.ruxuanwo.data.export.core.Line;
import com.ruxuanwo.data.export.executor.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: ruxuanwo
 * @Date: 2018/7/13/0013 14:33
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    @Resource
    private EdTemplateMapper edTemplateMapper;


    @Override
    @Async("asyncServiceExecutor")
    public Future<String> executeAsync(String templateId, List<FieldConfig> headConfig, List<Line> lineList, String logId) {
        logger.info("start executeAsync");
        tableInsert(templateId, headConfig, lineList, logId);
        logger.info("end executeAsync");
        return new AsyncResult<>("success");
    }

    private void tableInsert(String templateId, List<FieldConfig> headConfig, List<Line> lineList, String logId) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("tableName", Constant.TABLE_PREFIX + templateId + Constant.TABLE_SUFFIX);
        map.put("columns", headConfig);
        map.put("data", lineList);
        map.put("logId", logId);
        edTemplateMapper.newTableInsert(map);
    }
}
