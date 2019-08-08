package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.domain.EdTemplateField;
import com.ruxuanwo.data.export.dto.EdTemplateFieldDTO;
import com.ruxuanwo.data.export.core.Service;
import java.util.List;

/**
 * 模板字段表-Service接口类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
public interface EdTemplateFieldService extends Service<EdTemplateField, EdTemplateFieldDTO, String> {


    /**
     * 多表添加
     * @param vo JSON数组
     * @param id
     */
    void add(String vo, String id);

    /**
     * 连表删除
     * @param fieldId
     */
    void delete(String fieldId);

    /**
     * 根据模版ID查询模版字段
     * @param id
     * @return
     */
    List<EdTemplateField> findByTemplate(String id);
}
