package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdTableFieldDTO;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.dto.SelectDbDTO;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import com.ruxuanwo.data.export.enums.ToolTypeEnum;
import com.ruxuanwo.data.export.enums.ValueTypeEnum;
import com.ruxuanwo.data.export.mapper.EdTemplateDbconfigMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateTableMapper;
import com.ruxuanwo.data.export.service.EdTableFieldService;
import com.ruxuanwo.data.export.service.EdToolsService;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import com.ruxuanwo.data.export.utils.DataBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 导入字段表-Controller类
 * @author ChenBin on 2018/06/22
 */
@RestController
@RequestMapping("/edTableField")
public class EdTableFieldController {
    @Autowired
    private EdTableFieldService edTableFieldService;
    @Autowired
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;
    @Autowired
    private EdTemplateTableMapper edTemplateTableMapper;
    @Autowired
    private EdToolsService edToolsService;


    @PostMapping("/add")
    public Result<EdTableField> add(String jsonData, String tempId) {
        EdTableField edTableField = edTableFieldService.add(jsonData, tempId);
        return ResponseMsgUtil.success(edTableField);
    }


    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        edTableFieldService.remove(id);
        return ResponseMsgUtil.success(null);
    }

    /**
     * 用于回显
     * @param templateId
     * @return
     */
    @GetMapping("/detail")
    public Result detail(@RequestParam String templateId) {
        List<EdTableFieldDTO> edTableFieldDTO = edTableFieldService.detail(templateId);
        return ResponseMsgUtil.success(edTableFieldDTO);
    }


    /**
     * 返回所有值来源列表
     * @return
     */
    @GetMapping("/valueType")
    public Result valueType(){
        HashMap<String, Object> map = new HashMap<>(16);
        for (ValueTypeEnum valueTypeEnum : ValueTypeEnum.values()) {
            map.put(valueTypeEnum.getName(), valueTypeEnum.getCode());
        }
        return ResponseMsgUtil.success(map);
    }

    /**
     * 将字段类型、字段名称以及所属表名称封装成对象集合返回
     * @param templateId 模版字段
     * @return
     */
    @GetMapping("/selectDb")
    public Result selectDb(@RequestParam String templateId){
        List<SelectDbDTO> list = new ArrayList<>();
        //找出所有表
        List<EdTemplateTable> tables = edTemplateTableMapper.findByTableName(templateId);
        //找出数据库配置
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(templateId);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));

        for (EdTemplateTable table : tables) {
            //获取字段名称
            List<String> columnNames = DataBaseUtil.getColumnNames(dbconfig, table.getTableName());
            //获取字段类型
            List<String> columnTypes = DataBaseUtil.getColumnTypes(dbconfig, table.getTableName());
            //create方法返回封装的对象结合
            List<SelectDbDTO> dtos = this.create(columnNames, columnTypes, table.getTableName(), table.getId(), table.getImportSort());
            list.addAll(dtos);
        }

        return ResponseMsgUtil.success(list);
    }

    /**
     * 返回当前模版ID存储表中不为空的字段
     * @param templateId 模版ID
     * @return
     */
    @GetMapping("/notNull")
    public Result notNull(@RequestParam String templateId){
        HashMap<String, Object> map = new HashMap<>(16);
        List<EdTemplateTable> tables = edTemplateTableMapper.findByTableName(templateId);
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(templateId);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));

        for (EdTemplateTable table : tables) {
            //获取不为空的字段
            List<String> notNull = DataBaseUtil.getNotNUllField(dbconfig, table.getTableName());
            //判断主键生成方式，不为自定义的删除ID返回前端，UUID和自增统一后台生成
            if (!table.getKenGenerate().equals(GeneratorEnum.CUSTOMIZE.getNum())){
                notNull.remove(table.getKeyName());
            }
            map.put(table.getTableName(), notNull);
        }
        return ResponseMsgUtil.success(map);
    }

    /**
     * 获取所有生成器列表
     * @return
     */
    @GetMapping("/generatorAll")
    public Result generatorAll(){
        Condition condition = new Condition(EdTools.class);
        condition.createCriteria().andEqualTo("type", ToolTypeEnum.type_generate.getCode());
        List<EdTools> tools = edToolsService.findByCondition(condition);
        return ResponseMsgUtil.success(tools);
    }


    /**
     * 字段名称长度和字段类型长度是对应的，根据字段循环
     * 每次循环创建一个对象，将值通过构造传入，再添加到List中
     * @param columnNames 字段名称集合
     * @param columnTypes 字段类型集合
     * @param tableName 表名
     * @return
     */
    public List<SelectDbDTO> create(List<String> columnNames, List<String> columnTypes, String tableName, String tableId, Integer importSort){
        List<SelectDbDTO> list = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            SelectDbDTO selectDbDTO = new SelectDbDTO(columnNames.get(i), columnTypes.get(i), tableName, tableId, importSort);
            list.add(selectDbDTO);
        }
        return list;
    }

}
