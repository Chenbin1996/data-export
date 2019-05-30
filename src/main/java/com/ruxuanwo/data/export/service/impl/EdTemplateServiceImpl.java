package com.ruxuanwo.data.export.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.check.Factory;
import com.ruxuanwo.data.export.config.FieldConfig;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.constants.ExcelConstant;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.*;
import com.ruxuanwo.data.export.dto.*;
import com.ruxuanwo.data.export.enums.LogStateEnum;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import com.ruxuanwo.data.export.executor.AsyncService;
import com.ruxuanwo.data.export.importer.Importer;
import com.ruxuanwo.data.export.mapper.*;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import com.ruxuanwo.data.export.utils.DataBaseUtil;
import com.ruxuanwo.data.export.utils.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据导入模板表-ServiceImpl接口实现类
 *
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateServiceImpl extends AbstractService<EdTemplate, EdTemplateDTO, String> implements EdTemplateService {
    @Resource
    private EdTemplateMapper edTemplateMapper;
    @Autowired
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;
    @Autowired
    private EdTemplateFieldMapper edTemplateFieldMapper;
    @Autowired
    private EdFieldToolsMapper edFieldToolsMapper;
    @Autowired
    private EdLogMapper edLogMapper;
    @Autowired
    private EdTemplateTableMapper edTemplateTableMapper;
    @Autowired
    private AsyncService asyncService;

    @Override
    public List<EdTemplateDTO> list(String appId, String moduleId, String templateName) {
        List<EdTemplateDTO> list = edTemplateMapper.list(appId, moduleId, templateName);
        return list;
    }

    @Override
    public String add(String templateName, String description,
                      String connectionName, String dbtypeId,
                      String dburl, String username,
                      String password, Integer port,
                      String dbname, String appId, String moduleId,
                      String moduleName, String userId) {
        EdTemplate edTemplate = new EdTemplate();
        edTemplate.setTemplateName(templateName);
        edTemplate.setDescription(description);
        edTemplate.setCreateTime(new Date());
        edTemplate.setCreator(userId);
        edTemplate.setStatus(Constant.STATE_START);
        edTemplate.setAppId(appId);
        edTemplate.setModuleId(moduleId);
        edTemplate.setModuleName(moduleName);
        this.insert(edTemplate);
        if (edTemplate.getId() != null && !"".equals(edTemplate.getId())) {
            EdTemplateDbconfig edTemplateDbconfig = new EdTemplateDbconfig();
            edTemplateDbconfig.setConnectionName(connectionName);
            edTemplateDbconfig.setDbtypeId(dbtypeId);
            edTemplateDbconfig.setDburl(dburl);
            edTemplateDbconfig.setPassword(CryptoUtil.encode(Constant.KEY, password));
            edTemplateDbconfig.setPort(port);
            edTemplateDbconfig.setDbname(dbname);
            edTemplateDbconfig.setTemplateId(edTemplate.getId());
            edTemplateDbconfig.setUsername(username);
            edTemplateDbconfigMapper.insert(edTemplateDbconfig);
        }
        return edTemplate.getId();
    }

    @Override
    public List<Map<String, Object>> tableConfig(String templateId) {
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(templateId);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));
        List<Map<String, Object>> tableConfig = DataBaseUtil.getTablePk(dbconfig);
        return tableConfig;
    }

    @Override
    public HashMap<String, Object> columnAndType(String id) {
        HashMap<String, Object> map = new HashMap<>(16);
        HashMap<String, Object> data;
        List<EdTemplateTable> tables = edTemplateTableMapper.findByTableName(id);
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(id);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));
        for (EdTemplateTable edTemplateTable : tables) {
            List<String> columnNames = DataBaseUtil.getColumnNames(dbconfig, edTemplateTable.getTableName());
            List<String> columnTypes = DataBaseUtil.getColumnTypes(dbconfig, edTemplateTable.getTableName());
            data = new HashMap<>(16);
            data.put("columnNames", columnNames);
            data.put("columnTypes", columnTypes);
            data.put("tableId", edTemplateTable.getId());
            map.put(edTemplateTable.getTableName(), data);
        }
        return map;
    }

    @Override
    public void delete(String id) {
        this.remove(id);

        EdTemplateDbconfig edTemplateDbconfig = edTemplateDbconfigMapper.findByTemplate(id);
        if (edTemplateDbconfig != null) {
            edTemplateDbconfigMapper.delete(edTemplateDbconfig);
        }

        List<EdTemplateField> fields = edTemplateFieldMapper.findByTemplate(id);
        for (EdTemplateField edTemplateField : fields) {
            if (edTemplateField != null) {
                edTemplateFieldMapper.delete(edTemplateField);
                List<EdFieldTools> toolsList = edFieldToolsMapper.findByField(edTemplateField.getId());
                for (EdFieldTools edFieldTools : toolsList) {
                    edFieldToolsMapper.delete(edFieldTools);
                }
            }
        }

    }

    @Override
    public void excelImport(String templateId, List<List<String>> list, List<FieldConfig> headConfig, String logId){
        //创建临时表
        this.createTable(templateId, headConfig);
        //导入数据列数
        int columnSize = headConfig.size();
        //导入数据的行数
        int lineSize = list.size();
        List<Line> lineList = new ArrayList<>();
        //行数循环
        for (int i = 1; i < lineSize; i++) {
            Line line = new Line();
            //获取除了头部标题以外其他行数据
            List<String> dataLine = list.get(i);
            for (int j = 0; j < columnSize; j++) {
                //获取当前一列的所有数据库信息
                FieldConfig fieldConfig = headConfig.get(j);
                fieldConfig.setParamData(dataLine.get(j));
                //创建一个单元格对象，头部标题与列数据对应
                Cell cell = new Cell(fieldConfig.getExcelName(), dataLine.get(j));
                this.check(cell, fieldConfig);
                this.dealWithDefaultValue(cell, fieldConfig);
                this.executeConver(cell, fieldConfig);
                line.addToCells(cell);
            }
            line.createSql();
            line.cleanCells();
            lineList.add(line);
        }
        this.tableInsert(templateId, headConfig, lineList, logId);
    }

    /**
     * 赋值缺省值
     *
     * @param cell         当前单元格
     * @param fieldConfig 字段信息
     */
    private void dealWithDefaultValue(Cell cell,FieldConfig fieldConfig) {
        if (cell.getValue() == null) {
            cell.setValue(fieldConfig.getDefaultValue());
            fieldConfig.setConverParameter(cell.getValue());
        }
    }

    /**
     * 判断字段是否需要校验，并校验
     *
     * @param cell        当前单元格
     * @param fieldConfig 该字段配置
     */
    private void check(Cell cell, FieldConfig fieldConfig) {
        if (fieldConfig.getClient() == null) {
            return;
        }
        //若无值但存在缺省值，则不需要校验
        if (cell.getValue() == null  && fieldConfig.getDefaultValue() != null) {
            return;
        }
        executeCheck(cell, fieldConfig.getClient());
    }

    /**
     * 执行校验链
     *
     * @param cell   当前单元格
     * @param client 校验链
     * @return
     */
    private void executeCheck(Cell cell, Client client) {
        client.reset();
        List<Information> informations = client.startValidate();
        cell.setCheckInforms(informations);
    }

    /**
     * 执行转换方法
     *
     * @param cell        当前单元格
     * @param fieldConfig 该字段配置
     */
    private void executeConver(Cell cell, FieldConfig fieldConfig){
        if (fieldConfig.getConversion() == null) {
            return;
        }
        //没值不进行转换
        if(cell.getValue() == null){
            return;
        }
        Information information = fieldConfig.getConversion().conversion(fieldConfig.getConverParameter());
        cell.setConverInform(information);
    }


    /**
     * 保存数据到临时表
     *
     * @param templateId 模板id
     * @param headConfig 所有字段信息
     * @param lineList   所有行数据
     * @param logId      日志id
     */
    private void tableInsert(String templateId, List<FieldConfig> headConfig, List<Line> lineList, String logId) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("tableName", Constant.TABLE_PREFIX + templateId);
        map.put("logId", logId);
        map.put("columns", headConfig);
        map.put("data", lineList);
        edTemplateMapper.oldTableInsert(map);
        asyncService.executeAsync(templateId, headConfig, lineList, logId);

    }


    /**
     * 创建表
     *
     * @param templateId 模板id
     */
    private void createTable(String templateId, List<FieldConfig> fieldConfigs) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("tableName", Constant.TABLE_PREFIX + templateId);
        map.put("list", fieldConfigs);
        edTemplateMapper.createOldDataTable(map);
        map.clear();
        map.put("tableName", Constant.TABLE_PREFIX + templateId + Constant.TABLE_SUFFIX);
        map.put("list", fieldConfigs);
        edTemplateMapper.createNewDataTable(map);
    }


    @Override
    public Workbook excelExport(String templateId) {
        List<EdTemplateField> list = edTemplateFieldMapper.findByTemplate(templateId);
        List<HashMap<String, Object>> tempList = new ArrayList<>();
        HashMap<String, Object> hashMap;
        List<EdFieldTools> edFieldTools;
        for (EdTemplateField edTemplateField : list) {
            hashMap = new HashMap<>(16);
            hashMap.put("name", edTemplateField.getName());
            edFieldTools = edFieldToolsMapper.findByField(edTemplateField.getId());
            hashMap.put("bool", isCheckNull(edFieldTools));
            tempList.add(hashMap);
        }
        return ExportUtil.export(tempList);
    }

    @Override
    public PageInfo dataBack(String templateId, String logId, String dataType, Integer page, Integer size) {
        HashMap<String, String> hashMap = this.findField(templateId);
        hashMap.put("id", "id");
        hashMap.put("log_id", "log_id");
        hashMap.put("state", "数据校验状态");
        hashMap.put("message", "错误信息");
        PageHelper.startPage(page, size);
        List dataList = edTemplateMapper.findTemByLogId(Constant.TABLE_PREFIX + templateId, logId, dataType, hashMap);
        PageInfo pageInfo = new PageInfo(dataList);
        return pageInfo;
    }

    /**
     * 返回英文字段对应的名称
     *
     * @param templateId 模板id
     * @return
     */
    private HashMap<String, String> findField(String templateId) {
        List<FieldConfig> fieldConfigList = this.selectByTemplateId(templateId);
        HashMap<String, String> hashMap = new HashMap<>(16);
        for (FieldConfig fieldConfig : fieldConfigList) {
            hashMap.put(fieldConfig.getColumnName(), fieldConfig.getExcelName());
        }
        return hashMap;
    }

    @Override
    public void deleteTemporary(String tableName, String id, String logId,String temporaryId) {
        edTemplateMapper.deleteTemporary(tableName, id, logId, temporaryId);
    }

    @Override
    public HashMap checkTemporary(String templateId, String data){
        List<FieldConfig> fieldConfigList = this.selectByTemplateId(templateId);
        HashMap<String, Object> map = new HashMap<>(16);
        HashMap<String, Object> sqlMap = new HashMap<>(16);
        JSONObject object = JSON.parseObject(data);
        Line line = new Line();
        String temporaryId = object.getString("id");
        StringBuilder builder = new StringBuilder();
        for (FieldConfig fieldConfig : fieldConfigList) {
            //根据头部名称获取值
            String dataValue = object.getString(fieldConfig.getExcelName());
            fieldConfig.setParamData(dataValue);
            //创建单元格对象赋值数据
            Cell cell = new Cell(fieldConfig.getExcelName(), dataValue);
            //校验
            this.check(cell, fieldConfig);
            //转换
            this.executeConver(cell, fieldConfig);
            //将单元格放入一行对象中
            line.addToCells(cell);
            //存放错误信息
            builder.append(cell.putErrorMsg());
            map.put(cell.getExcelName(), cell.getValue());
            //sql拼接
            sqlMap.put(fieldConfig.getColumnName(), dataValue);
        }
        if (builder.length() <= 0) {
            builder.append(Constant.DATA_RIGHT);
        } else {
            builder.deleteCharAt(builder.length() - 1);
        }
        line.createState();
        map.put("log_id",object.getString("log_id"));
        map.put("数据校验状态", RecordStateEnum.findByCode(line.getState()));
        map.put("错误信息", builder.toString());
        map.put("id", temporaryId);
        sqlMap.put("message", builder.toString());
        sqlMap.put("state", line.getState());
        edTemplateMapper.updateTemporary(Constant.TABLE_PREFIX + templateId, temporaryId, sqlMap);
        for (int i = 0; i < fieldConfigList.size(); i++) {
            Cell cell = line.getCells().get(i);
            String value = cell.getValue();
            if(cell.getConverInform() != null){
                if(cell.getConverInform().getData()!=null){
                    value = cell.getConverInform().getData().toString();
                }
            }
            sqlMap.put(fieldConfigList.get(i).getColumnName(), value);
        }
        this.updateNewTemporary(templateId, temporaryId, sqlMap);
        return map;
    }

    @Override
    public void saveTemporary(String templateId, String logId) throws SQLException {
        this.getTemplateImport(templateId).importData(new Data(templateId, logId));
        //更新日志
        EdLog edLog = new EdLog();
        edLog.setId(logId);
        edLog.setStatus(LogStateEnum.FINISH.getCode());
        edLog.setExpoortTime(new Date());
        edLogMapper.updateByPrimaryKeySelective(edLog);
    }

    /**
     * 根据模板id获取导入器
     * @param templateId
     * @return
     */
    private Importer getTemplateImport(String templateId){
        String importName  = edTemplateMapper.getTemplateImport(templateId);
        return (Importer) Factory.newInstance(importName);
    }


    /**
     * 根据旧临时表ID查询出新的临时表ID，并更新新的临时表
     *
     * @param templateId  模版ID
     * @param temporaryId 旧的临时表ID
     * @param sqlMap      sql语句
     */
    private void updateNewTemporary(String templateId, String temporaryId, HashMap<String, Object> sqlMap) {
        String newTemporaryId = edTemplateMapper.selectNewTemporary(Constant.TABLE_PREFIX + templateId + Constant.TABLE_SUFFIX, temporaryId);
        sqlMap.remove("state");
        sqlMap.remove("message");
        sqlMap.put("temporary_id", temporaryId);
        edTemplateMapper.updateTemporary(Constant.TABLE_PREFIX + templateId + Constant.TABLE_SUFFIX, newTemporaryId, sqlMap);
    }

    /**
     * 查询临时表字段对应的excel值
     *
     * @param templateId
     * @return
     */
    @Override
    public Map<String, String> tempKey(String templateId) {
        HashMap<String, String> hashMap = new HashMap<>(16);
        List<EdTemplateField> excelField = edTemplateFieldMapper.findByTemplate(templateId);
        for (EdTemplateField field : excelField) {
            hashMap.put(field.getName(), Constant.TABEL_FIELD + field.getSort());
        }
        return hashMap;
    }

    @Override
    public List<HashMap> findTemByLogId(String templateId, String logId) {
        HashMap<String, String> hashMap = this.findField(templateId);
        hashMap.put("state", "state");
        return edTemplateMapper.findTemByLogId(Constant.TABLE_PREFIX + templateId, logId, null, hashMap);
    }

    @Override
    public Integer countByName(String templateName) {
        return edTemplateMapper.countByName(templateName);
    }

    @Override
    public void deleteTable(String tempTable) {
        String flag = edTemplateMapper.isExists(tempTable);
        if (flag != null) {
            edLogMapper.deleteByTemp(tempTable, LogStateEnum.WAIT.getCode());
        }
        edTemplateMapper.deleteTable(tempTable);
    }

    @Override
    public List<List<String>> getNotNUllField(String templateId) {
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(templateId);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));
        List<EdTemplateTable> tables = edTemplateTableMapper.findByTableName(templateId);
        List<List<String>> list = new ArrayList<>();
        for (EdTemplateTable edTemplateTable : tables) {
            List<String> strings = DataBaseUtil.getNotNUllField(dbconfig, edTemplateTable.getTableName());
            list.add(strings);
        }
        return list;
    }

    @Override
    public EdTemplate findById(String id) {
        return edTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FieldConfig> selectByTemplateId(String templateId) {
        List<FieldConfig> list = edTemplateMapper.selectByTemplateId(templateId);
        return list;
    }

    /**
     * 字段是否可以非空
     *
     * @param list 字段校验工具
     * @return 是否
     */
    private boolean isCheckNull(List<EdFieldTools> list) {
        for (EdFieldTools edFieldTools : list) {
            if (ExcelConstant.NOT_NULL_ID.equals(edFieldTools.getToolId())) {
                return true;
            }
        }
        return false;
    }

}
