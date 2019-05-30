package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import com.ruxuanwo.data.export.domain.EdFieldTools;
import com.ruxuanwo.data.export.enums.CheckFailEnum;
import com.ruxuanwo.data.export.service.EdFieldToolsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段工具表-Controller类
 *
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "字段工具表")
@RestController
@RequestMapping("/edFieldTools")
public class EdFieldToolsController {
    @Resource
    private EdFieldToolsService edFieldToolsService;

    @ApiOperation(value = "查找Field", notes = "查找Field")
    @ApiImplicitParams({
    })
    @GetMapping("/findByField")
    public Result findByField(@RequestParam(required = true) String fieldId) {
        List<EdFieldTools> edFieldTools = edFieldToolsService.findByField(fieldId);
        return ResponseMsgUtil.success(edFieldTools);
    }

    @ApiOperation(value = "校验字段失败时提示状态", notes = "校验字段失败时提示状态")
    @GetMapping("/failType")
    public Result failType() {
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> hashMap;
        for (CheckFailEnum failEnum : CheckFailEnum.values()) {
            hashMap = new HashMap<>(16);
            hashMap.put("code", failEnum.getCode());
            hashMap.put("name", failEnum.getName());
            list.add(hashMap);
        }
        return ResponseMsgUtil.success(list);
    }

}
