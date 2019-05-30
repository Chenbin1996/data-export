package com.ruxuanwo.data.export.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.check.Factory;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.domain.EdFieldTools;
import com.ruxuanwo.data.export.domain.EdTemplateField;
import com.ruxuanwo.data.export.domain.EdTemplateTablefield;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdTemplateFieldDTO;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import com.ruxuanwo.data.export.exception.CheckErrorException;
import com.ruxuanwo.data.export.mapper.EdFieldToolsMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateFieldMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateTablefieldMapper;
import com.ruxuanwo.data.export.service.EdFieldToolsService;
import com.ruxuanwo.data.export.service.EdTemplateFieldService;
import com.ruxuanwo.data.export.service.EdTemplateTablefieldService;
import com.ruxuanwo.data.export.service.EdToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 模板字段表-ServiceImpl接口实现类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateFieldServiceImpl extends AbstractService<EdTemplateField, EdTemplateFieldDTO, String> implements EdTemplateFieldService {
    @Resource
    private EdTemplateFieldMapper edTemplateFieldMapper;
    @Autowired
    private EdFieldToolsService edFieldToolsService;
    @Autowired
    private EdFieldToolsMapper edFieldToolsMapper;
    @Autowired
    private EdToolsService edToolsService;
    @Autowired
    private EdTemplateMapper edTemplateMapper;
    @Autowired
    private EdTemplateTablefieldMapper edTemplateTablefieldMapper;
    @Autowired
    private EdTemplateTablefieldService edTemplateTablefieldService;
    private Pattern compile = Pattern.compile("[0-9]*");


    @Override
    public void delete(String fieldId) {
        this.remove(fieldId);

        List<EdFieldTools> tools = edFieldToolsMapper.findByField(fieldId);
        if (tools != null){
            for (EdFieldTools tool : tools) {
                edFieldToolsService.remove(tool.getId());
            }
        }
        Condition condition = new Condition(EdTemplateTablefield.class);
        condition.createCriteria().andEqualTo("templateFieldId", fieldId);
        List<EdTemplateTablefield> byCondition = edTemplateTablefieldService.findByCondition(condition);
        if (byCondition != null){
            byCondition.forEach(x -> {
                edTemplateTablefieldService.remove(x.getId());
            });
        }

    }

    /**
     * 新增修改都在同一个界面，所以每次请求都是新增，把旧的删除
     * @param vo JSON数组
     * @param id
     */
    @Override
    public void add(String vo, String id) {
        //根据模版ID查询出模版字段、字段工具、模版字段导入字段关联等表，并删除
        //Condition condition = new Condition(EdTemplateField.class);
        //condition.createCriteria().andEqualTo("templateId", id);
        //List<EdTemplateField> byCondition = this.findByCondition(condition);
        //if (byCondition != null) {
        //    byCondition.forEach(x -> {
        //        //删除字段工具表
        //        List<EdFieldTools> fieldTools = edFieldToolsMapper.findByField(x.getId());
        //        if (fieldTools != null){
        //            fieldTools.forEach(s -> {
        //                edFieldToolsService.remove(s.getId());
        //            });
        //        }
        //
        //        Condition condition1 = new Condition(EdTemplateTablefield.class);
        //        condition1.createCriteria().andEqualTo("templateFieldId", x.getId());
        //        List<EdTemplateTablefield> byCondition1 = edTemplateTablefieldService.findByCondition(condition1);
        //        //删除模版字段导入字段关联表
        //        if (byCondition1 != null) {
        //            byCondition1.forEach(y ->{
        //                edTemplateTablefieldService.remove(y.getId());
        //            });
        //        }
        //        //删除模版字段表
        //        this.remove(x.getId());
        //    });
        //}

        JSONArray array = JSON.parseArray(vo);
        EdTemplateField edTemplateField;
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            edTemplateField = new EdTemplateField();
            edTemplateField.setName(object.getString("name"));
            edTemplateField.setTemplateId(id);
            edTemplateField.setDefaultValue(object.getString("defaultValue"));
            edTemplateField.setSort(i + 1);
            //新增模版字段表
            this.insert(edTemplateField);

            JSONArray toolArr = JSON.parseArray(object.getString("checks"));
            JSONArray excelArr = JSON.parseArray(object.getString("excels"));
            JSONObject changeObject = JSON.parseObject(object.getString("change"));
            //新增字段工具表
            if (toolArr != null) {
                this.fieldToolAdd(toolArr, edTemplateField.getId(), object.getString("defaultValue"));
            }
            //新增模版字段导入字段关联表
            if (excelArr != null) {
                this.tableFieldAdd(excelArr, edTemplateField.getId());
            }
            //新增字段工具表，转换器只有一个，所以不再arr中，是一个单独的对象，单独的数据
            if (changeObject != null) {
                this.toolsAdd(changeObject, edTemplateField.getId());
            }
        }
    }

    @Override
    public List<EdTemplateField> findByTemplate(String id) {
        return edTemplateFieldMapper.findByTemplate(id);
    }

    /**
     * 根据模板id删除临时表，并修改日志表导入数据状态
     * @param templateId
     */
    private void dropTable(String templateId){
        String tableName = Constant.TABLE_PREFIX + templateId;
        List<String> logIds = edTemplateMapper.findLogId(tableName);

    }

    /**
     * 校验缺省值是否正确
     * @param toolId 工具ID
     * @param defaultValue 缺省值
     * @param length 长度校验所需长度
     */
    private void checkDefaultValue(String toolId, String defaultValue, Integer length){
        if(defaultValue == null || "".equals(defaultValue)){
            return;
        }
        //根据工具ID找出当前这条工具信息
        EdTools edTools = edToolsService.get(toolId);
        //创建校验器
        Client client = new Client();
        //添加校验参数
        Parameter parameter = new Parameter();
        parameter.setData(defaultValue);
        parameter.setType(RecordStateEnum.ERROR.getCode());
        //判断有无长度校验器，有就赋值长度
        if (Constant.LENGCHECK_NAME.equals(edTools.getName())){
            parameter.setLength(length);
        }
        //添加实例化校验器
        client.addCheck(Factory.createCheck(edTools.getClassName()));
        //重置校验器索引
        client.reset();
        //添加参数对象
        client.addToParameters(parameter);
        //校验结果
        List<Information> informations = client.startValidate();
        for (Information information : informations) {
            //判断校验结果是否错误，错误抛出异常
            if (information.getState().equals(RecordStateEnum.ERROR.getCode())){
                String errorMsg = information.getMsg().substring(4);
                throw new CheckErrorException(Constant.DEFAULTVALUE + errorMsg);
            }
        }
    }

    /**
     * 新增字段工具表
     * @param toolArr 前端前端传入参数vo中的toolArrJSON数据
     * @param fieldId 模版字段ID
     */
    private void fieldToolAdd(JSONArray toolArr, String fieldId, String defaultValue){
        //新增
        for (int j = 0; j < toolArr.size(); j++) {
            EdFieldTools edFieldTools = new EdFieldTools();

            JSONObject toolObject = toolArr.getJSONObject(j);
            edFieldTools.setType(Integer.valueOf(toolObject.getString("failType")));
            edFieldTools.setToolId(toolObject.getString("id"));
            edFieldTools.setFieldId(fieldId);

            String other = toolObject.getString("other");
            Integer length = 0;
            edFieldTools.setOther(other);
            if (isInteger(other)){
                length = Integer.parseInt(other);
            }
            this.checkDefaultValue(toolObject.getString("id"), defaultValue, length);
            edFieldToolsService.insert(edFieldTools);
        }
    }

    /**
     * 新增模版字段导入字段关联表
     * @param excelArr 前端传入参数vo中的excelArrJSON数据
     * @param fieldId 模版字段ID
     */
    private void tableFieldAdd(JSONArray excelArr, String fieldId){
        for (int k = 0; k < excelArr.size(); k++) {
            JSONObject excelObject = excelArr.getJSONObject(k);
            EdTemplateTablefield edTemplateTablefield = new EdTemplateTablefield();
            edTemplateTablefield.setTableFieldId(excelObject.getString("id"));
            edTemplateTablefield.setTemplatefieldId(fieldId);
            edTemplateTablefieldMapper.insert(edTemplateTablefield);
        }
    }

    /**
     * 新增字段工具表
     * @param changeObject 前端前端传入参数vo中的changeObjectJSON数据，为一个单独对象
     * @param fieldId 模版字段ID
     */
    private void toolsAdd(JSONObject changeObject, String fieldId){
        EdFieldTools field = new EdFieldTools();
        field.setType(changeObject.getInteger("failType"));
        field.setToolId(changeObject.getString("id"));
        field.setFieldId(fieldId);
        edFieldToolsService.insert(field);
    }

    public boolean isInteger(String str) {
        Pattern pattern = this.compile;
        if (str != null || "".equals(str)){
            return pattern.matcher(str).matches();
        }
        return false;
    }
}
