package com.ruxuanwo.data.export.mapper;

import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.dto.EdTableFieldDTO;

import java.util.List;

/**
 * @author ruxuanwo
 */
public interface EdTableFieldMapper extends Mapper<EdTableField> {
    /**
     * 多表详情返回
     * @param templateId
     * @return
     */
    List<EdTableFieldDTO> detail(String templateId);
}