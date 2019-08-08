package com.ruxuanwo.data.export.mapper;

import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.core.Mapper;

import java.util.List;

/**
 * @author ruxuanwo
 */
public interface EdTemplateTableMapper extends Mapper<EdTemplateTable> {
    /**
     * 根据模版ID查找表名
     * @param templateId
     * @return
     */
    List<EdTemplateTable> findByTableName(String templateId);
}