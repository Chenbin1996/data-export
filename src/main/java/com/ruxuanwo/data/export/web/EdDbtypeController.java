package com.ruxuanwo.data.export.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.data.export.domain.EdDbtype;
import com.ruxuanwo.data.export.service.EdDbtypeService;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库类型表-Controller类
 *
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
@Api(description = "数据库类型表")
@RestController
@RequestMapping("/edDbtype")
public class EdDbtypeController {
    @Resource
    private EdDbtypeService edDbtypeService;

    @ApiOperation(value = "新增", notes = "单表新增")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "数据库类型名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "driverClass", value = "数据库驱动", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "port", value = "数据库端口", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "数据库描述", required = false, dataType = "String")
    })
    @PostMapping("/add")
    public Result<EdDbtype> add(@RequestParam(required = true) String name, @RequestParam(required = true) String driverClass,
                                @RequestParam(required = true) Integer port, @RequestParam(required = false) String description) {
        EdDbtype edDbtype = new EdDbtype();
        edDbtype.setDescription(description);
        edDbtype.setDriverClass(driverClass);
        edDbtype.setName(name);
        edDbtype.setPort(port);
        edDbtypeService.insert(edDbtype);
        return ResponseMsgUtil.success(edDbtype);
    }

    @ApiOperation(value = "删除", notes = "单表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = true) String id) {
        edDbtypeService.remove(id);
        return ResponseMsgUtil.success(null);
    }

    @ApiOperation(value = "更新", notes = "单表更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "数据库类型名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "driverClass", value = "数据库驱动", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "port", value = "数据库端口", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "数据库描述", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @PutMapping("/update")
    public Result<EdDbtype> update(@RequestParam(required = true) String id, @RequestParam(required = true) String name, @RequestParam(required = true) String driverClass,
                                   @RequestParam(required = true) Integer port, @RequestParam(required = false) String description) {
        EdDbtype edDbtype = new EdDbtype();
        edDbtype.setPort(port);
        edDbtype.setName(name);
        edDbtype.setDriverClass(driverClass);
        edDbtype.setDescription(description);
        edDbtype.setId(id);
        edDbtypeService.update(edDbtype);
        return ResponseMsgUtil.success(edDbtype);
    }

    @ApiOperation(value = "详情", notes = "单表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "String")
    })
    @GetMapping("/detail")
    public Result<EdDbtype> detail(@RequestParam(required = true) String id) {
        EdDbtype edDbtype = edDbtypeService.get(id);
        return ResponseMsgUtil.success(edDbtype);
    }

    @ApiOperation(value = "下拉框", notes = "下拉框")
    @ApiImplicitParams({
    })
    @GetMapping("/types")
    public Result types() {
        List<EdDbtype> list = edDbtypeService.find();
        return ResponseMsgUtil.success(list);
    }

    @ApiOperation(value = "分页", notes = "单表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页数", required = true, dataType = "Integer")
    })
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "50") Integer size) {
        PageHelper.startPage(page, size);
        List<EdDbtype> list = edDbtypeService.find();
        PageInfo pageInfo = new PageInfo(list);
        return ResponseMsgUtil.success(pageInfo);
    }

    @ApiOperation(value = "判断数据库源名称是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "数据库源名称", required = true, dataType = "String")
    })
    @GetMapping("/isRepeat")
    public Result<EdDbtype> isRepeat(@RequestParam(required = true) String name) {
        List<EdDbtype> list = edDbtypeService.findByName(name);
        if(list.size() > 0){
            return ResponseMsgUtil.failure();
        }
        return ResponseMsgUtil.success(null);
    }

}
