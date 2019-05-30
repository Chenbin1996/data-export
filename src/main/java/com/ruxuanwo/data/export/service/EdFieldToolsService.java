package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.domain.EdFieldTools;
import com.ruxuanwo.data.export.dto.EdFieldToolsDTO;

import java.util.List;

/**
 * 字段工具表-Service接口类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public interface EdFieldToolsService extends Service<EdFieldTools, EdFieldToolsDTO, String> {
    /**
     * 根据ID查询模版字段
     * @param fieldId
     * @return
     */
    List<EdFieldTools> findByField(String fieldId);
    /**
     * 多表联查，返回模版字段以及工具名称
     * @param fieldId
     * @return
     */
    List<EdFieldToolsDTO> findByFieldAndTool(String fieldId);
}
