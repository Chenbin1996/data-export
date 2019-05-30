package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdToolsDTO;
import com.ruxuanwo.data.export.core.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 工具表-Service接口类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public interface EdToolsService extends Service<EdTools, EdToolsDTO, String> {
    /**
     * 查询所有
     * @param type 类型
     * @return DTO数据传输对象类
     */
    List<EdTools> findToolType(Integer type);

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
    EdToolsDTO findDtoById(String id);

    /**
     * 查询最大排序
     * @return
     */
    Integer findMaxSort();
    /**
     * 根据名称查询，返回map
     * @param name
     * @return
     */
    HashMap<String,Object> findByName(String name);

    /**
     * 根据名称查询，返回对象集合
     * @param name 名称
     * @return
     */
    List<EdTools> selectByName(String name);
}
