package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdFieldTools;
import com.ruxuanwo.data.export.dto.EdFieldToolsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Chenbin
 */
public interface EdFieldToolsMapper extends Mapper<EdFieldTools> {
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

    /**
     * 根据字段id查询字段校验工具所有出错提示
     * @param fieldId 字段id
     * @return 出错提示集合
     */
    List<String> findFieldType(@Param("fieldId") String fieldId);
}