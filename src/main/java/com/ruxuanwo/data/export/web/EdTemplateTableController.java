package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.domain.EdTemplateTool;
import com.ruxuanwo.data.export.service.EdTemplateTableService;
import com.ruxuanwo.data.export.service.EdTemplateToolService;
import com.ruxuanwo.data.export.service.EdToolsService;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模版关联表-Controller类
 * @author ruxuanwo on 2018/06/22
 */
@RestController
@RequestMapping("/edTemplateTable")
public class EdTemplateTableController {
    @Autowired
    private EdTemplateTableService edTemplateTableService;
    @Autowired
    private EdTemplateToolService edTemplateToolService;
    @Autowired
    private EdToolsService edToolsService;


    @PostMapping("/add")
    public Result add(@RequestParam String jsonData, @RequestParam String templateId, @RequestParam String toolId) {
        String tableId = edTemplateTableService.add(jsonData, templateId, toolId);
        return ResponseMsgUtil.success(tableId);
    }


    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        edTemplateTableService.remove(id);
        return ResponseMsgUtil.success(null);
    }

    /**
     * 返回主键生成方式信息
     * @return
     */
    @GetMapping("/keyEnum")
    public Result keyEnum(){
        Map<String, Object> map = new HashMap<>(16);
        for (GeneratorEnum generatorEnum : GeneratorEnum.values()){
            map.put(generatorEnum.getName(), generatorEnum.getNum());
        }
        return ResponseMsgUtil.success(map);
    }

    /**
     * 根据模版ID查询模版关联表
     * @param templateId 模版ID
     * @return EdTemplateTable对象集合
     */
    @GetMapping("/findByTable")
    public Result findByTable(String templateId){
        List<EdTemplateTable> list = edTemplateTableService.findByTableName(templateId);
        return ResponseMsgUtil.success(list);
    }

    @GetMapping("/importer")
    public Result importer(String tempId){
        Condition condition = new Condition(EdTemplateTool.class);
        condition.createCriteria().andEqualTo("templateId", tempId);
        List<EdTemplateTool> byCondition = edTemplateToolService.findByCondition(condition);
        String toolName = "";
        for (EdTemplateTool edTemplateTool : byCondition) {
            EdTools edTools = edToolsService.get(edTemplateTool.getToolId());
            toolName = edTools.getName();
        }
        return ResponseMsgUtil.success(toolName);
    }


}
