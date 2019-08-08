package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.core.FieldConfig;
import com.ruxuanwo.data.export.domain.EdTemplate;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdTemplateDTO;
import com.ruxuanwo.data.export.dto.EdTemplateTableDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ruxuanwo
 */
public interface EdTemplateMapper extends Mapper<EdTemplate> {
    /**
     * 根据appId和moduleId返回对应模块的模版数据
     * @param appId
     * @param moduleId
     * @param templateName
     * @return
     */
    List<EdTemplateDTO> list(@Param("appId") String appId, @Param("moduleId")String moduleId, @Param("templateName")String templateName);

    /**
     * 根据名称查询数量
     * @param templateName 模板名称
     * @return 数量
     */
    Integer countByName(@Param("templateName") String templateName);

    /**
     * 多表C联查工具表信息
     * @param templateId 模版ID
     * @return
     */
    List<EdTools> findByTools(String templateId);

    /**
     * 创建原始数据临时表
     * @param map
     */
    void createOldDataTable(HashMap<String , Object> map);

    /**
     * 创建新数据临时表
     * @param map
     */
    void createNewDataTable(HashMap<String , Object> map);


    /**
     * 查询临时表的所有日志id
     * @param tableName
     * @return
     */
    List<String> findLogId(@Param("tableName")String tableName);

    /**
     * 原始数据临时表插入数据
     * @param map
     */
    void oldTableInsert(HashMap<String,Object> map);

    /**
     * 新数据临时表插入数据
     * @param map
     */
    void newTableInsert(HashMap<String,Object> map);


    /**
     * 动态删除表
     * @param tempTable 临时表名称
     */
    void deleteTable(@Param(value = "tempTable") String tempTable);

    /**
     * 根据日志id查询临时表
     * @param tableName 模板id
     * @param logId 日志id
     * @param dataType 数据状态
     * @param dataMap 临时表字段 英文-中文
     * @return
     */
    List<HashMap> findTemByLogId(@Param("tableName") String tableName,
                                       @Param("logId") String logId,
                                       @Param("dataType") String dataType,
                                       @Param("dataMap") HashMap dataMap);


    /**
     * 从临时表删除数据
     * @param tableName 模板id
     * @param id  数据id
     * @param logId 日志ID
     * @param temporaryId 临时表ID
     */
    void deleteTemporary(@Param("tableName") String tableName,
                         @Param("id") String id,
                         @Param("logId") String logId,
                         @Param("temporaryId") String temporaryId);


    /**
     * 更新临时表数据
     * @param tableName 模板id
     * @param id 数据id
     * @param dataMap 更新数据
     */
    void updateTemporary(@Param("tableName") String tableName,
                         @Param("id") String id,
                         @Param("dataMap") HashMap dataMap);

    /**
     * 根据旧的临时表ID查询出新的临时ID
     * @param tableName 临时表名称
     * @param temporaryId 旧的临时表ID
     * @return
     */
    String selectNewTemporary(@Param("tableName") String tableName, @Param("temporaryId") String temporaryId);

    /**
     * 判断表是否存在
     * @param tableName
     * @return
     */
    String isExists(@Param("tableName") String tableName);


    /**
     * 根据模块id查询模板字段相关信息
     * @param templateId 模板id
     * @return
     */
    List<FieldConfig> selectByTemplateId(@Param("templateId") String templateId);

    /**
     * 获取所有表和字段xx
     * @param templateId
     * @return
     */
    List<EdTemplateTableDTO> findTableAndField(@Param("templateId") String templateId);

    /**
     * 查询新数据临时表
     * @param templateId
     * @param logId
     * @param hashMap
     * @return
     */
    List<Map<String,Object>> findNewTempData(@Param("templateId")String templateId,
                                                 @Param("logId")String logId,
                                                 @Param("map")Map hashMap);

    /**
     * 根据模板id获取导入器全限定名
     * @param templateId 模板id
     * @return
     */
    String getTemplateImport(@Param("templateId") String templateId);

    /**
     * 获取所有勾选非空效验的字段
     * @param params
     * @return
     */
    List<String> getNullCheckFileds(Map<String, Object> params);
}