package com.ruxuanwo.data.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruxuanwo.data.export.domain.EdFieldGenerator;
import com.ruxuanwo.data.export.domain.EdForeignKey;
import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.mapper.EdTableFieldMapper;
import com.ruxuanwo.data.export.service.EdFieldGeneratorService;
import com.ruxuanwo.data.export.service.EdForeignKeyService;
import com.ruxuanwo.data.export.service.EdTableFieldService;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdFieldValue;
import com.ruxuanwo.data.export.dto.EdTableFieldDTO;
import com.ruxuanwo.data.export.service.EdFieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 导入字段表-ServiceImpl接口实现类
 * @author ruxuanwo on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTableFieldServiceImpl extends AbstractService<EdTableField, EdTableFieldDTO, String> implements EdTableFieldService {
    @Autowired
    private EdTableFieldMapper edTableFieldMapper;
    @Autowired
    private EdTableFieldService edTableFieldService;
    @Autowired
    private EdForeignKeyService edForeignKeyService;
    @Autowired
    private EdFieldValueService edFieldValueService;
    @Autowired
    private EdFieldGeneratorService edFieldGeneratorService;

    @Override
    public EdTableField add(String jsonData, String tempId) {
        JSONArray array = JSON.parseArray(jsonData);
        EdTableField edTableField = null;
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String id = object.getString("id");
            Integer type = object.getInteger("type");
            String tableName = object.getString("tableName");
            String fieldName = object.getString("fieldName");
            String fieldType = object.getString("fieldType");
            Integer repeatCheck = object.getInteger("repeatCheck");
            //赋值导入字段表参数
            edTableField = new EdTableField();
            edTableField.setTableName(tableName);
            edTableField.setFieldName(fieldName);
            edTableField.setType(type);
            edTableField.setFieldType(fieldType);
            edTableField.setTemplateId(tempId);
            edTableField.setRepeatCheck(repeatCheck);
            //判断有无ID，有更新，没有新增
            if (id == null || "".equals(id)){
                //新增操作
                this.insert(edTableField);
                //根据状态判断新增字段关联外键表、字段默认值表还是字段生成器关联表
                this.getAdd(edTableField.getId(), object);
            } else{
                edTableField.setId(id);
                //判断有无改变值来源type
                if (isChange(type, id)){
                    //没有就更新
                    String otherId = this.selectId(type, id);
                    this.getUpdate(type, otherId, id, tableName, fieldName, object.getString("value"), object.getString("toolId"));
                }else {
                    //有改变值来源type先删除相关表的记录，再新增
                    EdTableField tableField = edTableFieldService.get(id);
                    //删除
                    this.deleteData(tableField.getType(), id);
                    this.getAdd(edTableField.getId(),object);
                }

                this.update(edTableField);

            }
        }
        return edTableField;
    }

    /**
     * 更新操作时
     * 根据type判断原来插入的值来源type是否相等
     * @param type type
     * @param tableFieldId 导入字段表ID
     * @return
     */
    private boolean isChange(Integer type, String tableFieldId){
        EdTableField edTableField = edTableFieldService.get(tableFieldId);
        if (edTableField != null){
            return edTableField.getType().equals(type) ? true : false;
        }
        return false;
    }

    @Override
    public List<EdTableFieldDTO> detail(String templateId) {
        return edTableFieldMapper.detail(templateId);
    }

    /**
     * 更新操作时
     * 根据type返回字段关联外键表、字段默认值表还是字段生成器关联表的ID
     * @param type 值来源
     * @param tableFieldId 导入字段表ID
     * @return
     */
    private String selectId(Integer type, String tableFieldId){
        //三张表都有一个同字段，tableFieldId，去查询出对应的记录
        String id = "";
        switch (type){
            case 1:
                break;
            case 2:
                Condition value = this.getCondition(EdFieldValue.class, tableFieldId);
                List<EdFieldValue> values = edFieldValueService.findByCondition(value);
                if (values != null && values.size() != 0){
                    id = values.get(0).getId();
                }
                break;
            case 3:
                Condition generator = this.getCondition(EdFieldGenerator.class, tableFieldId);
                List<EdFieldGenerator> generators = edFieldGeneratorService.findByCondition(generator);
                if (generators != null || generators.size() != 0){
                    id = generators.get(0).getId();
                }
                break;
            case 4:
                Condition key = this.getCondition(EdForeignKey.class, tableFieldId);
                List<EdForeignKey> keys = edForeignKeyService.findByCondition(key);
                if (keys != null || keys.size() != 0){
                    id = keys.get(0).getId();
                }
                break;
                default:
        }
        return id;
    }

    /**
     * 通用根据tableFieldId创建Condition
     * @param object
     * @param tableFieldId
     * @return
     */
    private Condition getCondition(Class object, String tableFieldId){
        Condition condition = new Condition(object);
        condition.createCriteria().andEqualTo("tableFieldId", tableFieldId);
        return condition;
    }

    /**
     * 封装更新
     * @param type
     * @param id
     * @param tableFieldId
     * @param tableName
     * @param field
     * @param value
     * @param toolId
     */
    private void getUpdate(Integer type, String id, String tableFieldId, String tableName, String field, String value, String toolId){
        switch (type){
            case 1:
                break;
            case 2:
                this.updateFieldValue(id, tableFieldId, value);
                break;
            case 3:
                this.updateGenerator(id, tableFieldId, toolId);
                break;
            case 4:
                this.updateForeignKey(id, tableFieldId, tableName, field);
                break;
                default:
        }
    }

    /**
     * 封装新增
     * @param tableFieldId 导入字段表id
     * @param object json对象
     */
    private void getAdd(String tableFieldId,JSONObject object){
        Integer type = object.getInteger("type");
        switch (type){
            case 1:
                break;
            case 2:
                String value  = object.getString("value");
                this.addFieldValue(tableFieldId, value);
                break;
            case 3:
                String toolId = object.getString("toolId");
                this.addGenerator(tableFieldId, toolId);
                break;
            case 4:
                String tableName = object.getString("foreignTableName");
                String fieldName = object.getString("foreignField");
                this.addForeignKey(tableFieldId, tableName, fieldName);
                break;
            default:
        }
    }

    private void addForeignKey(String tableFieldId, String tableName, String field){
        EdForeignKey edForeignKey = new EdForeignKey();
        edForeignKey.setTableFieldId(tableFieldId);
        edForeignKey.setTableName(tableName);
        edForeignKey.setField(field);
        edForeignKeyService.insert(edForeignKey);
    }
    private void addFieldValue(String tableFieldId, String value){
        EdFieldValue edFieldValue = new EdFieldValue();
        edFieldValue.setTableFieldId(tableFieldId);
        edFieldValue.setValue(value);
        edFieldValueService.insert(edFieldValue);
    }

    private void addGenerator(String tableFieldId, String toolId){
        EdFieldGenerator edFieldGenerator = new EdFieldGenerator();
        edFieldGenerator.setTableFieldId(tableFieldId);
        edFieldGenerator.setToolId(toolId);
        edFieldGeneratorService.insert(edFieldGenerator);
    }

    private void updateForeignKey(String id, String tableFieldId, String tableName, String field){
        EdForeignKey edForeignKey = new EdForeignKey();
        edForeignKey.setId(id);
        edForeignKey.setTableFieldId(tableFieldId);
        edForeignKey.setTableName(tableName);
        edForeignKey.setField(field);
        edForeignKeyService.update(edForeignKey);
    }

    private void updateFieldValue(String id, String tableFieldId, String value){
        EdFieldValue edFieldValue = new EdFieldValue();
        edFieldValue.setId(id);
        edFieldValue.setTableFieldId(tableFieldId);
        edFieldValue.setValue(value);
        edFieldValueService.update(edFieldValue);
    }

    private void updateGenerator(String id, String tableFieldId, String toolId){
        EdFieldGenerator edFieldGenerator = new EdFieldGenerator();
        edFieldGenerator.setId(id);
        edFieldGenerator.setTableFieldId(tableFieldId);
        edFieldGenerator.setToolId(toolId);
    }

    /**
     * 根据type选择删除哪一张表记录
     * @param type
     * @param tableFieldId
     */
    private void deleteData(Integer type, String tableFieldId){
        String valueId = this.selectId(type, tableFieldId);
        switch (type){
            case 1:
                break;
            case 2:
                edFieldValueService.remove(valueId);
                break;
            case 3:
                edFieldGeneratorService.remove(valueId);
                break;
            case 4:
                edForeignKeyService.remove(valueId);
                break;
                default:
        }
    }
}
