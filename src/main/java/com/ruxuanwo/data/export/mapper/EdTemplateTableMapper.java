package com.ruxuanwo.data.export.mapper;

import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdTemplateTable;

import java.util.List;

/**
 * @author Chenbin
 */
public interface EdTemplateTableMapper extends Mapper<EdTemplateTable> {
    /**
     * 根据模版ID查找表名
     * @param templateId
     * @return
     */
    List<EdTemplateTable> findByTableName(String templateId);
}