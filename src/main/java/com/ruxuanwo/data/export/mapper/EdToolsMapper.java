package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.dto.EdToolsDTO;
import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdTools;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author ruxuanwo
 */

public interface EdToolsMapper extends Mapper<EdTools> {
    /**
     * 查询所有
     * @return DTO数据传输对象类
     */
    List<EdToolsDTO> findAllDto();

    /**
     * 根据id查询
     * @param id 主键
     * @return dto
     */
    EdToolsDTO findDtoById(@Param("id") String id);

    /**
     * 根据模板字段id查询校验工具
     * @param fieldId 模板字段id
     * @return 校验工具集合
     */
    List<EdTools> findByFieldId(@Param("fieldId") String fieldId);

    /**
     * 查询最大排序
     * @return
     */
    Integer findMaxSort();

    /**
     * 根据名称查询，返回map
     * @param name 名称
     * @return
     */
    HashMap<String,Object> findByName(@Param("name") String name);

    /**
     * 根据名称查询，返回对象集合
     * @param name 名称
     * @return
     */
    List<EdTools> selectByName(@Param("name") String name);
}