package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.service.EdTemplateDbconfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 模板数据库配置表-Controller类
 *
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "模板数据库配置表")
@RestController
@RequestMapping("/edTemplateDbconfig")
public class EdTemplateDbconfigController {
    @Resource
    private EdTemplateDbconfigService edTemplateDbconfigService;


    @ApiOperation(value = "详情", notes = "多表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @GetMapping("/detail")
    public Result<EdTemplateDbconfigDTO> detail(@RequestParam(required = true) String id) {
        EdTemplateDbconfigDTO edTemplateDbconfigDTO = edTemplateDbconfigService.detail(id);
        return ResponseMsgUtil.success(edTemplateDbconfigDTO);
    }

}
