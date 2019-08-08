package com.ruxuanwo.data.export.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.core.FieldConfig;
import com.ruxuanwo.data.export.core.ServiceException;
import com.ruxuanwo.data.export.domain.EdLog;
import com.ruxuanwo.data.export.domain.EdTemplate;
import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.dto.EdTemplateDTO;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import com.ruxuanwo.data.export.enums.LogStateEnum;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import com.ruxuanwo.data.export.read.impl.ReadFromJar;
import com.ruxuanwo.data.export.service.EdLogService;
import com.ruxuanwo.data.export.service.EdTemplateDbconfigService;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.service.EdTemplateTableService;
import com.ruxuanwo.data.export.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据导入模板表-Controller类
 *
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "数据导入模板表")
@RestController
@RequestMapping("/edTemplate")
public class EdTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EdTemplateController.class);

    @Autowired
    private EdTemplateService edTemplateService;
    @Autowired
    private EdTemplateDbconfigService edTemplateDbconfigService;
    @Autowired
    private EdLogService edLogService;
    @Autowired
    private EdTemplateTableService edTemplateTableService;


    @ApiOperation(value = "新增", notes = "单表新增")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "templateName", value = "模版名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "模版描述", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "connectionName", value = "数据库连接名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "dbtypeId", value = "数据库类型", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "dburl", value = "数据库地址", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "数据库用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "数据库密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "port", value = "数据库端口", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "dbname", value = "数据库名称", required = true, dataType = "String")
    })
    @PostMapping("/add")
    public Result add(@RequestParam(required = true) String templateName, @RequestParam(required = false) String description,
                      @RequestParam(required = true) String connectionName, @RequestParam(required = true) String dbtypeId,
                      @RequestParam(required = true) String dburl, @RequestParam(required = true) String username,
                      @RequestParam(required = true) String password, @RequestParam(required = true) Integer port,
                      @RequestParam(required = true) String dbname, @RequestParam(required = false) String appId,
                      @RequestParam(required = false) String moduleId, @RequestParam(required = false) String moduleName,
                      HttpServletRequest request) {
        String userId = "1";
        Integer size = edTemplateService.countByName(templateName);
        if (size > 0) {
            return ResponseMsgUtil.failure("模版名称已经存在");
        }
        String id;
        try {
            id = edTemplateService.add(templateName, description, connectionName, dbtypeId, dburl,
                    username, password, port, dbname, appId, moduleId, moduleName, userId);
        } catch (Exception e) {
            return ResponseMsgUtil.failure();
        }
        if (id != null || "".equals(id)) {
            return ResponseMsgUtil.success(id);
        } else {
            return ResponseMsgUtil.failure(null);
        }

    }

    @ApiOperation(value = "删除", notes = "单表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = true) String id) {
        edTemplateService.delete(id);
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "更新", notes = "单表更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "templateName", value = "模版名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "模版描述", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "connectionName", value = "数据库连接名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "dbtypeId", value = "数据库类型", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "dburl", value = "数据库地址", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "数据库用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "数据库密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "port", value = "数据库端口", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "dbname", value = "数据库名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "templateId", value = "模版ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "状态吗", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "配置表ID", required = true, dataType = "String"),
    })
    @PostMapping("/update")
    public Result<EdTemplate> update(@RequestParam(required = true) String templateName, @RequestParam(required = false) String description,
                                     @RequestParam(required = true) String connectionName, @RequestParam(required = true) String dbtypeId,
                                     @RequestParam(required = true) String dburl, @RequestParam(required = true) String username,
                                     @RequestParam(required = true) String password, @RequestParam(required = true) Integer port,
                                     @RequestParam(required = true) String dbname, @RequestParam(required = true) String state, @RequestParam(required = true) String templateId,
                                     @RequestParam(required = true) String id) {
        //0加密
        String num = "0";
        if (num.equals(state)) {
            password = CryptoUtil.encode(Constant.KEY, password);
        }
        EdTemplate edTemplate = new EdTemplate();
        edTemplate.setId(templateId);
        edTemplate.setTemplateName(templateName);
        edTemplate.setDescription(description);

        EdTemplateDbconfig edTemplateDbconfig = new EdTemplateDbconfig();
        edTemplateDbconfig.setId(id);
        edTemplateDbconfig.setTemplateId(templateId);
        edTemplateDbconfig.setDbname(dbname);
        edTemplateDbconfig.setPort(port);
        edTemplateDbconfig.setDburl(dburl);
        edTemplateDbconfig.setPassword(password);
        edTemplateDbconfig.setConnectionName(connectionName);
        edTemplateDbconfig.setDbtypeId(dbtypeId);
        edTemplateDbconfig.setUsername(username);

        this.deleteTable(edTemplateDbconfig);

        edTemplateService.update(edTemplate);
        edTemplateDbconfigService.update(edTemplateDbconfig);
        return ResponseMsgUtil.success(null);
    }

    /**
     * 若数据库类型，地址，数据库，表则改变删除临时表
     *
     * @param edTemplateDbconfig
     */
    private void deleteTable(EdTemplateDbconfig edTemplateDbconfig) {
        EdTemplateDbconfig oldConif = edTemplateDbconfigService.get(edTemplateDbconfig.getId());
        boolean typeChange = !oldConif.getDbtypeId().equals(edTemplateDbconfig.getDbtypeId());
        boolean urlChange = !oldConif.getDburl().equals(edTemplateDbconfig.getDburl());
        boolean dbChange = !oldConif.getDbname().equals(edTemplateDbconfig.getDbname());
        if (typeChange || urlChange || dbChange) {
            edTemplateService.deleteTable(Constant.TABLE_PREFIX + edTemplateDbconfig.getTemplateId());
        }
    }

    @ApiOperation(value = "分页", notes = "单表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页数", required = true, dataType = "Integer")
    })
    @GetMapping("/list")
    public Result<PageInfo> list(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String appId,
                                 @RequestParam(required = false) String moduleId,
                                 @RequestParam(required = false) String templateName,
                                 @RequestParam(required = false) Integer type) {

        if (page > 0 && size > 0) {
            PageHelper.startPage(page, size);
        }
        List<EdTemplateDTO> list = edTemplateService.list(appId, moduleId, templateName);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseMsgUtil.success(pageInfo);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @ApiImplicitParams({
    })
    @GetMapping("/findAll")
    public Result findAll() {
        List<EdTemplate> list = edTemplateService.find();
        return ResponseMsgUtil.success(list);
    }

    @ApiOperation(value = "测试数据库连接", notes = "数据库连接")
    @ApiImplicitParams({
    })
    @GetMapping("/connection")
    public Result connection(EdTemplateDbconfigDTO dbConfig, @RequestParam(required = true) String state) {
        String num = "1";
        if (num.equals(state)) {
            dbConfig.setPassword(CryptoUtil.decode(Constant.KEY, dbConfig.getPassword()));
        }
        List<String> list = DataBaseUtil.getTableNames(dbConfig);
        if (list == null || list.isEmpty()) {
            return ResponseMsgUtil.failure();
        } else {
            return ResponseMsgUtil.success(null);
        }
    }

    @ApiOperation(value = "数据库配置信息", notes = "数据库连接")
    @ApiImplicitParams({
    })
    @GetMapping("/tableConfig")
    public Result tableConfig(String templateId) {
        List<Map<String, Object>> tableConfig = edTemplateService.tableConfig(templateId);
        if (tableConfig == null || tableConfig.isEmpty()) {
            return ResponseMsgUtil.failure();
        }
        return ResponseMsgUtil.success(tableConfig);
    }

    @ApiOperation(value = "获取表所有字段以及字段类型", notes = "获取表所有字段以及字段类型")
    @ApiImplicitParams({
    })
    @GetMapping("/columnAndType")
    public Result columnAndType(@RequestParam(required = true) String id) {
        HashMap<String, Object> map = edTemplateService.columnAndType(id);
        return ResponseMsgUtil.success(map);
    }


    @ApiOperation(value = "数据导入", notes = "数据导入")
    @ApiImplicitParams({

    })
    @PostMapping("/excelImport")
    @Transactional(rollbackFor = Exception.class)
    public Result excelImport(@RequestParam(required = true) String templateId,
                              @RequestParam(required = true) MultipartFile multipartFile,
                              HttpServletRequest request) throws Exception {
        try {
            String userId = "1";
            List<FieldConfig> fieldConfigs = edTemplateService.selectByTemplateId(templateId);
            if (fieldConfigs.isEmpty()) {
                return ResponseMsgUtil.failure("当前模板没有excel字段，请为模板添加excel字段！");
            }

            List<List<String>> list = null;
            try {
                list = ExportUtil.importExcel(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), fieldConfigs.size());
            }catch (Exception e){
                LOGGER.error("excelImport 接口异常，{}", e);
                return ResponseMsgUtil.failure("解析excel异常，请下载正确的模板进行导入!");
            }
            //excel解析正确
            if (list.size() <= 1) {
                return ResponseMsgUtil.failure("请勿导入没有数据的excel");
            }
            //判断excel是否为导入模板
            List<FieldConfig> headConfig = new ArrayList<>();
            boolean flag;
            for (FieldConfig fieldConfig : fieldConfigs) {
                flag = false;
                for (String excelField : list.get(0)) {
                    if (fieldConfig.getExcelName().equals(excelField)) {
                        headConfig.add(fieldConfig);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return ResponseMsgUtil.failure("所导入的excel不属于该模板，请重新导入！");
                }
            }
            EdLog edLog = edLogService.add(templateId, Long.parseLong((list.size() - 1) + ""), userId);
            edTemplateService.excelImport(templateId, list, headConfig, edLog.getId());
            return ResponseMsgUtil.success(edLog.getId());
        } catch (Exception e) {
            throw new ServiceException("数据导入异常：" + e.getMessage());
        }
    }

    @ApiOperation(value = "导入后数据回显", notes = "导入后数据回显")
    @ApiImplicitParams({
    })
    @GetMapping("/dataBack")
    public Result dataBack(@RequestParam(required = true) String templateId,
                           @RequestParam(required = true) String logId,
                           @RequestParam(required = false) String dataType,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer size) {
        PageInfo pageInfo = edTemplateService.dataBack(templateId, logId, dataType, page, size);
        return ResponseMsgUtil.success(pageInfo);
    }

    @ApiOperation(value = "从临时表删除单条数据", notes = "从临时表删除数据")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteTemporary")
    public Result deleteTemporary(@RequestParam(required = true) String templateId,
                                  @RequestParam(required = false) String id,
                                  @RequestParam(required = false) String logId) {
        EdLog edLog = edLogService.get(logId);
        edTemplateService.deleteTemporary(Constant.TABLE_PREFIX + templateId, id, logId, null);
        edTemplateService.deleteTemporary(Constant.TABLE_PREFIX + templateId + Constant.TABLE_SUFFIX, null, logId, id);
        if ("".equals(id) || id == null) {
            edLogService.remove(edLog);
            return ResponseMsgUtil.success(null);
        } else {
            Long count = edLog.getTotalCount() - 1L;
            if (count.equals(0L)) {
                edLogService.remove(edLog);
            } else {
                edLog.setTotalCount(count);
                edLogService.update(edLog);
            }
            return ResponseMsgUtil.success(id);
        }
    }

    @ApiOperation(value = "校验临时表单条数据", notes = "校验临时表单条数据")
    @ApiImplicitParams({
    })
    @PostMapping("/checkTemporary")
    public Result checkTemporary(@RequestParam(required = true) String templateId,
                                 @RequestParam(required = true) String data) {

        HashMap hashMap = edTemplateService.checkTemporary(templateId, data);
        return ResponseMsgUtil.success(hashMap);
    }

    @ApiOperation(value = "模版导出", notes = "模版导出")
    @ApiImplicitParams({
    })
    @GetMapping("/excelExport")
    public Result excelExport(HttpServletResponse response, @RequestParam(required = true) String templateId) throws IOException {
        //获取模板
        EdTemplate edTemplate = edTemplateService.get(templateId);
        OutputStream os = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String((edTemplate.getTemplateName() + "导入模板").getBytes("gb2312"), "ISO-8859-1") + ".xls");

        LOGGER.info("下载模板名称 name = {}", edTemplate.getTemplateName());

        Workbook workbook = edTemplateService.excelExport(templateId);
        try {
            LOGGER.info("重新生成模板-------------------");
            workbook.write(os);
        } catch (IOException e) {
            throw new ServiceException("导出异常：" + e.getMessage());
        } finally {
            os.flush();
            os.close();
        }
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "保存数据到真实表", notes = "保存数据到真实表")
    @ApiImplicitParams({
    })
    @PostMapping("/saveTemporary")
    public Result saveTemporary(@RequestParam(required = true) String templateId,
                                @RequestParam(required = true) String logId) throws SQLException {

        edTemplateService.saveTemporary(templateId, logId);
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "效验要保存的数据是否正确", notes = "效验要保存的数据是否正确")
    @ApiImplicitParams({
    })
    @GetMapping("/checkAllData")
    public Result checkAllData(@RequestParam(required = true) String templateId,
                               @RequestParam(required = true) String logId) {

        List<HashMap> dataList = edTemplateService.findTemByLogId(templateId, logId);
        for (HashMap hashMap : dataList) {
            if (!hashMap.get("state").equals(RecordStateEnum.RIGHT.getCode())) {
                return ResponseMsgUtil.failure();
            }
        }
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "数据校验状态", notes = "数据校验状态")
    @ApiImplicitParams({
    })
    @GetMapping("/dataType")
    public Result dataType() {

        List<HashMap> list = new ArrayList<>();
        HashMap<String, Object> hashMap;
        for (RecordStateEnum stateEnum : RecordStateEnum.values()) {
            hashMap = new HashMap<>(16);
            hashMap.put("name", stateEnum.getName());
            hashMap.put("code", stateEnum.getCode());
            list.add(hashMap);
        }
        return ResponseMsgUtil.success(list);
    }


    @ApiOperation(value = "判断模板名称是否重复", notes = "判断模板名称是否重复")
    @ApiImplicitParams({
    })
    @GetMapping("/isRepeat")
    public Result isRepeat(String name) {
        Integer size = edTemplateService.countByName(name);
        if (size > 0) {
            return ResponseMsgUtil.failure();
        }
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "判断模板是否存在未导入完成的数据", notes = "数据存在于临时表")
    @ApiImplicitParams({
    })
    @GetMapping("/isFinishImport")
    public Result isFinishImport(String templateId) {
        List<EdLog> edLogs = edLogService.findByTemplateIdAndStatus(templateId, LogStateEnum.WAIT.getCode());
        if (!edLogs.isEmpty()) {
            return ResponseMsgUtil.failure(edLogs.size() + "");
        }
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "获取表内的非空字段")
    @ApiImplicitParams({
    })
    @GetMapping("/getNullField")
    public Result getNullField(String templateId) {
        List<List<String>> list = edTemplateService.getNotNUllField(templateId);
        Condition condition = new Condition(EdTemplateTable.class);
        condition.createCriteria().andEqualTo("templateId", templateId);
        List<EdTemplateTable> byCondition = edTemplateTableService.findByCondition(condition);
        for (int i = 0; i < byCondition.size(); i++) {
            if (!byCondition.get(i).getKenGenerate().equals(GeneratorEnum.CUSTOMIZE.getNum())) {
                list.get(i).remove(0);
            }
        }
        return ResponseMsgUtil.success(list);
    }

    @ApiOperation(value = "校验已选字段是否包括了表内的非空字段")
    @ApiImplicitParams({
    })
    @GetMapping("/checkNUllField")
    public Result checkNUllField(String templateId, String data) {
        List<List<String>> list = edTemplateService.getNotNUllField(templateId);
        //删除id字段
        list.remove("id");
        JSONArray array = JSON.parseArray(data);
        JSONObject object;
        boolean flag;
        for (List<String> s : list) {
            flag = false;
            for (int i = 0; i < array.size(); i++) {
                object = array.getJSONObject(i);
                for (String s1 : s) {
                    flag = s1.equals(object.getString("type")) ? true : false;
                }
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                return ResponseMsgUtil.failure(nullFieldTips(list));
            }
        }
        return ResponseMsgUtil.success(null);
    }


    /**
     * 拼接非空字段
     *
     * @param list
     * @return
     */
    private String nullFieldTips(List<List<String>> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object s : list) {
            stringBuilder.append(s + ",");
        }
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 查询所有需要非空效验字段
     * @param templateId
     * @return
     */
    @GetMapping("/getNullCheckFileds")
    public Result getNullCheckFileds(String templateId) {
        List<String> list = edTemplateService.getNullCheckFileds(templateId);
        return ResponseMsgUtil.success(list);
    }


    /**
     * 查询所有需要非空效验字段
     * @param filePath
     * @return
     */
    @GetMapping("/test")
    public Result test(String filePath, HttpServletResponse response) {
        ReadFromJar readFromJar = new ReadFromJar();
        byte[] read = readFromJar.read(filePath);
        OutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(("test").getBytes("gb2312"), "ISO-8859-1") + ".xls");
            os = response.getOutputStream();
            os.write(read);
        } catch (IOException e) {
            throw new ServiceException("导出异常：" + e.getMessage());
        } finally {

        }
        return ResponseMsgUtil.success();
    }
}
