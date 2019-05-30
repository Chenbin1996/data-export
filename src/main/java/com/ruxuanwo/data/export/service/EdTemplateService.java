package com.ruxuanwo.data.export.service;


import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.domain.EdTemplate;
import com.ruxuanwo.data.export.dto.EdTemplateDTO;
import com.ruxuanwo.data.export.config.FieldConfig;
import org.apache.poi.ss.usermodel.Workbook;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据导入模板表-Service接口类
 *
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public interface EdTemplateService extends Service<EdTemplate, EdTemplateDTO, String> {
    /**
     * 根据appId和moduleId返回对应模块的模版数据
     * @param appId
     * @param moduleId
     * @param templateName
     * @return
     */
    List<EdTemplateDTO> list(String appId, String moduleId, String templateName);

    /**
     * 新增
     *
     * @param templateName   模版名称
     * @param description    模版描述
     * @param connectionName 连接名称
     * @param dbtypeId       数据库类型
     * @param dburl          数据库IP地址
     * @param username       数据库用户名
     * @param password       数据库密码
     * @param port           数据库端口
     * @param dbname         数据库名
     * @param moduleId 菜单Id
     * @param appId 平台ID
     * @param moduleName 模块名称
     * @param userId 登录用户的ID
     * @return
     */
    String add(String templateName, String description,
               String connectionName, String dbtypeId,
               String dburl, String username,
               String password, Integer port,
               String dbname, String appId, String moduleId,
               String moduleName, String userId);

    /**
     * 根据模版ID返回数据库配置信息以及ID名称
     *
     * @param templateId 模版ID
     * @return
     */
    List<Map<String, Object>> tableConfig(String templateId);

    /**
     * 获取表的所有字段以及字段类型
     *
     * @param id
     * @return
     */
    HashMap<String, Object> columnAndType(String id);

    /**
     * 多表删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 文件导入操作
     *
     * @param templateId
     * @param list
     * @param fieldConfigs
     * @param logId
     * @return
     */
    void excelImport(String templateId, List<List<String>> list, List<FieldConfig> fieldConfigs, String logId) throws Exception;

    /**
     * 文件导出操作
     *
     * @param templateId 模版ID
     * @return
     */
    Workbook excelExport(String templateId);

    /**
     * 导入后数据回显
     *
     * @param templateId 模板id
     * @param logId      日志id
     * @param dataType   数据状态
     * @param page       页数
     * @param size       大小
     * @return
     */
    PageInfo dataBack(String templateId, String logId, String dataType, Integer page, Integer size);

    /**
     * 从临时表删除数据
     *
     * @param tableName   模板id
     * @param id          数据id
     * @param logId       日志ID
     * @param temporaryId 临时表ID
     */
    void deleteTemporary(String tableName, String id, String logId, String temporaryId);


    /**
     * 校验临时表单条数据
     *
     * @param templateId 模板id
     * @param data       单条数据
     * @return
     */
    HashMap checkTemporary(String templateId, String data);

    /**
     * 保存数据到真实数据库
     *
     * @param templateId
     * @param logId
     * @throws SQLException
     */
    void saveTemporary(String templateId, String logId) throws SQLException;

    /**
     * 根据日志id查询临时表
     *
     * @param tableName 模板id
     * @param logId     日志id
     * @return
     */
    List<HashMap> findTemByLogId(String tableName,
                                 String logId);

    /**
     * 查询临时表字段对应的excel值
     * @param templateId
     * @return
     */
    Map<String, String> tempKey(String templateId);

    /**
     * 根据名称查询数量
     *
     * @param templateName 模板名称
     * @return 数量
     */
    Integer countByName(String templateName);

    /**
     * 删除临时表,删除所有未导入的日志
     *
     * @param tempTable 临时表名称
     */
    void deleteTable(String tempTable);

    /**
     * 获取表单非空字段
     *
     * @param templateId 模板id
     * @return
     */
    List<List<String>> getNotNUllField(String templateId);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    EdTemplate findById(String id);

    /**
     * 根据模块id查询模板字段相关信息
     *
     * @param templateId 模板id
     * @return
     */
    List<FieldConfig> selectByTemplateId(String templateId);
}
