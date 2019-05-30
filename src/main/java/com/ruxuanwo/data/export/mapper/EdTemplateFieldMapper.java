package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdTemplateField;
import com.ruxuanwo.data.export.dto.EdTemplateFieldDTO;

import java.util.List;

/**
 * @author Chenbin
 */
public interface EdTemplateFieldMapper extends Mapper<EdTemplateField> {


    /**
     * 根据模版ID查询
     * @param id
     * @return
     */
    List<EdTemplateField> findByTemplate(String id);

    /**
     * 根据模版ID连表查询字段所有信息
     * @param id
     * @return
     */
    List<EdTemplateFieldDTO> queryByTempId(String id);
}