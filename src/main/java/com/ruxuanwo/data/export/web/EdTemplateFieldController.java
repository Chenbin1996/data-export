package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.domain.EdTemplateField;
import com.ruxuanwo.data.export.dto.EdTemplateFieldDTO;
import com.ruxuanwo.data.export.dto.TemplateDTO;
import com.ruxuanwo.data.export.service.EdLogService;
import com.ruxuanwo.data.export.service.EdTableFieldService;
import com.ruxuanwo.data.export.service.EdTemplateFieldService;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import com.ruxuanwo.data.export.enums.CheckFailEnum;
import com.ruxuanwo.data.export.enums.ValueTypeEnum;
import com.ruxuanwo.data.export.mapper.EdTemplateFieldMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模板字段表-Controller类
 *
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "模板字段表")
@RestController
@RequestMapping("/edTemplateField")
public class EdTemplateFieldController {
    @Resource
    private EdTemplateFieldService edTemplateFieldService;
    @Autowired
    private EdLogService edLogService;
    @Autowired
    private EdTemplateService edTemplateService;
    @Autowired
    private EdTableFieldService edTableFieldService;

    @Autowired
    private EdTemplateFieldMapper edTemplateFieldMapper;

    @GetMapping("/detail")
    public Result detail(String id){
        List<EdTemplateFieldDTO> dtos = edTemplateFieldMapper.queryByTempId(id);
        dtos.forEach(x ->{
            x.getEdTools().forEach(y ->{
                y.setTypeName(CheckFailEnum.findName(y.getFailType()));
            });
        });
       return  ResponseMsgUtil.success(dtos);
    }

    @ApiOperation(value = "新增", notes = "单表新增")
    @PostMapping("/add")
    public Result<TemplateDTO> add(@RequestParam(required = true) String vo, @RequestParam(required = true) String id) {
        edTemplateFieldService.add(vo, id);
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "新增", notes = "单表新增")
    @PostMapping("/addBody")
    public Result<TemplateDTO> add(@RequestBody(required = true) FieldReceiver receiver) {
        edTemplateFieldService.add(receiver.getStringFields(), receiver.getId());
        return ResponseMsgUtil.success(null);
    }

    /**
     * 字段接收类
     */
    private static class FieldReceiver{
        /**
         * id
         */
        private String id;
        /**
         * json字段
         */
        private String stringFields;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStringFields() {
            return stringFields;
        }

        public void setStringFields(String stringFields) {
            this.stringFields = stringFields;
        }
    }

    @ApiOperation(value = "删除", notes = "单表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = true) String id) {
        if (id != null && !"".equals(id)) {
            EdTemplateField edTemplateField = edTemplateFieldService.get(id);
            //删除临时表
            edTemplateService.deleteTable(Constant.TABLE_PREFIX + edTemplateField.getTemplateId());
            edTemplateFieldService.delete(id);
            return ResponseMsgUtil.success(null);
        } else {
            return ResponseMsgUtil.failure();
        }

    }

    @GetMapping("/excelAll")
    public Result excelAll(String tempId){
        Condition condition = new Condition(EdTableField.class);
        condition.createCriteria().andEqualTo("type", ValueTypeEnum.EXCEL.getCode()).andEqualTo("templateId", tempId);
        List<EdTableField> byCondition = edTableFieldService.findByCondition(condition);
        return ResponseMsgUtil.success(byCondition);
    }

}
