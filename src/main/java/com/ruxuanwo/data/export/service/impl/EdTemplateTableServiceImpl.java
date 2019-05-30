package com.ruxuanwo.data.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.domain.EdTemplateTool;
import com.ruxuanwo.data.export.dto.EdTemplateTableDTO;
import com.ruxuanwo.data.export.mapper.EdTemplateTableMapper;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.service.EdTemplateTableService;
import com.ruxuanwo.data.export.service.EdTemplateToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 模版关联表-ServiceImpl接口实现类
 * @author ChenBin on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateTableServiceImpl extends AbstractService<EdTemplateTable, EdTemplateTableDTO, String> implements EdTemplateTableService {
    @Autowired
    private EdTemplateTableMapper edTemplateTableMapper;
    @Autowired
    private EdTemplateTableService edTemplateTableService;
    @Autowired
    private EdTemplateToolService edTemplateToolService;
    @Autowired
    private EdTemplateService edTemplateService;

    @Override
    public String add(String jsonData, String templateId, String toolId) {
        JSONArray array = JSON.parseArray(jsonData);
        EdTemplateTable edTemplateTable = null;
        for (int i = 0; i < array.size(); i++){
            JSONObject object = array.getJSONObject(i);
            String id = object.getString("id");
            edTemplateTable = new EdTemplateTable();
            edTemplateTable.setTemplateId(templateId);
            edTemplateTable.setTableName(object.getString("tableName"));
            edTemplateTable.setImportSort(object.getInteger("importSort"));
            edTemplateTable.setKeyName(object.getString("keyName"));
            edTemplateTable.setKenGenerate(object.getInteger("kenGenerate"));
            edTemplateTable.setKeyType(object.getString("keyType"));
            //判断是否有ID传入，有的话进行更新操作，没有的话进行插入操作
            if (id != null || "".equals(id)){
                edTemplateTable.setId(object.getString("id"));
                edTemplateTableService.update(edTemplateTable);
            }else {
                edTemplateTableMapper.insert(edTemplateTable);
            }

        }
        //根据模版ID查询出是否有模版工具关联表数据
        Condition condition = new Condition(EdTemplateTool.class);
        condition.createCriteria().andEqualTo("templateId", templateId);
        List<EdTemplateTool> tools = edTemplateToolService.findByCondition(condition);
        EdTemplateTool edTemplateTool = new EdTemplateTool();
        //没有新增，有就更新
        if (tools == null || tools.size() == 0){
        edTemplateTool.setToolId(toolId);
        edTemplateTool.setTemplateId(templateId);
        edTemplateToolService.insert(edTemplateTool);
        } else {
            for (EdTemplateTool tool : tools) {
                edTemplateTool.setId(tool.getId());
                edTemplateTool.setTemplateId(templateId);
                edTemplateTool.setToolId(toolId);
                edTemplateToolService.update(edTemplateTool);
            }
        }

        return edTemplateTable.getId();
    }

    @Override
    public List<EdTemplateTable> findByTableName(String templateId) {
        return edTemplateTableMapper.findByTableName(templateId);
    }
}
