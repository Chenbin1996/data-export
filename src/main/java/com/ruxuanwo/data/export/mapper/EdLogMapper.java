package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdLog;
import com.ruxuanwo.data.export.dto.EdLogDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ruxuanwo on 2018/05/08
 * @version 3.0.0
 */
public interface EdLogMapper extends Mapper<EdLog> {

    /**
     * 根据状态查询
     * @param name 模板名称
     * @param stateList 状态集合
     * @return dto集合
     */
    List<EdLogDTO> findByState(@Param("name") String name,@Param("stateList") List stateList);

    /**
     * 根据id查询
     * @param id 日志id
     * @return dto
     */
    EdLogDTO findDtoById(@Param("id") String id);

    /**
     * 根据模版ID查找日志
     * @param templateId 模版ID
     * @return
     */
    List<EdLog> findByTempId(String templateId);

    /**
     * 根据模板id和状态查询
     * @param templateId 模板id
     * @param status 状态
     * @return
     */
    List<EdLog> findByTemplateIdAndStatus(@Param("templateId") String templateId,
                                          @Param("status") Integer status);

    /**
     * 根据临时表里的日志id和状态删除日志
     * @param tableName
     * @param status
     */
    void deleteByTemp(@Param("tableName") String tableName,
                      @Param("status") Integer status);

    /**
     * 获取最大排序
     * @return
     */
    Integer getMaxSort();
}