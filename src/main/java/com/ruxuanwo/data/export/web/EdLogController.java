package com.ruxuanwo.data.export.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.domain.EdTemplate;
import com.ruxuanwo.data.export.dto.EdLogDTO;
import com.ruxuanwo.data.export.enums.LogStateEnum;
import com.ruxuanwo.data.export.service.EdLogService;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据导入日志表-Controller类
 *
 * @author chenbin on 2018/05/08
 * @version 3.0.0
 */
@Api(description = "数据导入日志表")
@RestController
@RequestMapping("/edLog")
public class EdLogController {
    @Resource
    private EdLogService edLogService;
    @Resource
    private EdTemplateService edTemplateService;

    @ApiOperation(value = "详情", notes = "单表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @GetMapping("/detail")
    public Result<EdLogDTO> detail(@RequestParam(required = true) String id) {
        EdLogDTO edLogDTO = edLogService.findDtoById(id);
        edLogDTO.setStateName(LogStateEnum.findByCode(edLogDTO.getStatus()));
        EdTemplate edTemplate = edTemplateService.get(edLogDTO.getTemplateId());
        edLogDTO.setTemplateName(edTemplate.getTemplateName());
        return ResponseMsgUtil.success(edLogDTO);
    }

    @ApiOperation(value = "分页,可加模板名称进行搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "模板名称", required = false, dataType = "String")
    })
    @GetMapping("/list")
    public Result<PageInfo> list(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String name) {
        PageHelper.startPage(page, size);
        List<EdLogDTO> list = edLogService.findByState(name,LogStateEnum.WAIT.getCode(), LogStateEnum.FINISH.getCode());
        for (EdLogDTO edLogDTO : list) {
            edLogDTO.setStateName(
                    LogStateEnum.findByCode(edLogDTO.getStatus()));
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResponseMsgUtil.success(pageInfo);
    }


}
