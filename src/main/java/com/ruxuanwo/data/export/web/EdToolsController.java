package com.ruxuanwo.data.export.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.domain.EdDbtype;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdToolsDTO;
import com.ruxuanwo.data.export.enums.CheckFailEnum;
import com.ruxuanwo.data.export.enums.ToolTypeEnum;
import com.ruxuanwo.data.export.service.EdToolsService;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 工具表-Controller类
 *
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "工具表")
@RestController
@RequestMapping("/edTools")
public class EdToolsController {
    @Resource
    private EdToolsService edToolsService;
    @ApiOperation(value = "新增", notes = "单表新增")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "校验工具名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "校验工具类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "className", value = "校验器或转换器类名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "描述", required = false, dataType = "String")
    })
    @PostMapping("/add")
    public Result<EdTools> add(@RequestParam(required = true) String name,
                               @RequestParam(required = true) Integer type,
                               @RequestParam(required = true) String className,
                               @RequestParam(required = false) String description,
                               HttpServletRequest request) {
        String userId = "1";
        EdTools edTools = new EdTools();
        edTools.setName(name);
        edTools.setType(type);
        edTools.setClassName(className);
        edTools.setDescription(description);
        edTools.setCreateTime(new Date());
        edTools.setCreator(userId);
        edTools.setSortOrder(edToolsService.findMaxSort() + 1);
        edToolsService.insert(edTools);
        return ResponseMsgUtil.success(edTools);
    }

    @ApiOperation(value = "删除", notes = "单表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = true) String id) {
        edToolsService.remove(id);
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "更新", notes = "单表更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "校验工具名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "校验工具类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "className", value = "校验器或转换器类名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "描述", required = false, dataType = "String")
    })
    @PostMapping("/update")
    public Result<EdTools> update(@RequestParam(required = true) String id, @RequestParam(required = true) String name, @RequestParam(required = true) Integer type,
                                  @RequestParam(required = true) String className, @RequestParam(required = false) String description) {
        EdTools edTools = new EdTools();
        edTools.setId(id);
        edTools.setDescription(description);
        edTools.setClassName(className);
        edTools.setType(type);
        edTools.setName(name);
        edToolsService.update(edTools);
        return ResponseMsgUtil.success(edTools);
    }

    @ApiOperation(value = "详情", notes = "单表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @GetMapping("/detail")
    public Result<EdToolsDTO> detail(@RequestParam(required = true) String id) {
        EdToolsDTO edToolsDTO = edToolsService.findDtoById(id);

        edToolsDTO.setTypeName(ToolTypeEnum.findName(edToolsDTO.getType()));
        return ResponseMsgUtil.success(edToolsDTO);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/findAll")
    public Result findAll() {
        List<EdToolsDTO> list = edToolsService.findAllDto();
        for (EdToolsDTO edToolsDTO : list) {
            edToolsDTO.setTypeName(ToolTypeEnum.findName(edToolsDTO.getType()));
        }
        return ResponseMsgUtil.success(list);
    }


    @ApiOperation(value = "分页", notes = "分页")
    @GetMapping("/list")
    public Result<PageInfo> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<EdToolsDTO> list = edToolsService.findAllDto();
        for (EdToolsDTO edToolsDTO : list) {
            edToolsDTO.setTypeName(ToolTypeEnum.findName(edToolsDTO.getType()));
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResponseMsgUtil.success(pageInfo);
    }

    @ApiOperation(value = "工具类型", notes = "工具类型")
    @GetMapping("/findType")
    public Result findType() {
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> hashMap;
        for (ToolTypeEnum typeEnum : ToolTypeEnum.values()) {
            hashMap = new HashMap<>(16);
            hashMap.put("code", typeEnum.getCode());
            hashMap.put("name", typeEnum.getName());
            list.add(hashMap);
        }
        return ResponseMsgUtil.success(list);
    }


    @ApiOperation(value = "根据状态查询工具类型", notes = "查询所有")
    @GetMapping("/findToolType")
    public Result findToolType(@RequestParam Integer type) {
        List<EdTools> list = edToolsService.findToolType(type);
        return ResponseMsgUtil.success(list);
    }

    @ApiOperation(value = "获取非空校验器id,name以及出错状态2")
    @GetMapping("/getNullCheck")
    public Result getNullCheck() {
        HashMap<String,Object> hashMap = edToolsService.findByName("非空校验");
        hashMap.put("failType",CheckFailEnum.fail_error.getCode());
        return ResponseMsgUtil.success(hashMap);
    }

    @ApiOperation(value = "判断工具名称是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "工具名称", required = true, dataType = "String")
    })
    @GetMapping("/isRepeat")
    public Result<EdDbtype> isRepeat(@RequestParam(required = true) String name) {
        List<EdTools> list = edToolsService.selectByName(name);
        if(list.size() > 0){
            return ResponseMsgUtil.failure();
        }
        return ResponseMsgUtil.success(null);
    }

}
