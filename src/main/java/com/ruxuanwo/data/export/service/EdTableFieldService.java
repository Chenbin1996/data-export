package com.ruxuanwo.data.export.service;

import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.dto.EdTableFieldDTO;

import java.util.List;

/**
 * 导入字段表-Service接口类
 * @author ruxuanwo on 2018/06/22
 */
public interface EdTableFieldService extends Service<EdTableField, EdTableFieldDTO, String> {
    /**
     * 新增导入字段表
     * @param jsonData json数组
     * @param tempId 模版ID
     * @return
     */
    EdTableField add(String jsonData, String tempId);

    /**
     * 多表关联查看详情
     * @param templateId 模版ID
     * @return
     */
    List<EdTableFieldDTO> detail(String templateId);

}
