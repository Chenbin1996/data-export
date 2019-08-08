package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.service.EdTemplateToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模版工具关联表-Controller类
 * @author ruxuanwo on 2018/06/22
 */
@RestController
@RequestMapping("/edTemplateTool")
public class EdTemplateToolController {
    @Autowired
    private EdTemplateToolService edTemplateToolService;

}
