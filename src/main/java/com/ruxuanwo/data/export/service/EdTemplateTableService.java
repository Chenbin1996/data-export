package com.ruxuanwo.data.export.service;

import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.dto.EdTemplateTableDTO;

import java.util.List;

/**
 * 模版关联表-Service接口类
 * @author ChenBin on 2018/06/22
 */
public interface EdTemplateTableService extends Service<EdTemplateTable, EdTemplateTableDTO, String> {
    /**
     * 新增模版关联表，多表新增
     * @param jsonData JSON数组
     * @param templateId 模版ID
     * @param toolId 工具ID
     * @return
     */
    String add(String jsonData, String templateId, String toolId);

    /**
     * 根据模版ID查找表名
     * @param templateId
     * @return
     */
    List<EdTemplateTable> findByTableName(String templateId);

}
