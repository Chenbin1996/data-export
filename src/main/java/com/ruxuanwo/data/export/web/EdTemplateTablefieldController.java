package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.service.EdTemplateTablefieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模版字段导入字段关联表-Controller类
 * @author ChenBin on 2018/06/22
 */
@RestController
@RequestMapping("/edTemplateTablefield")
public class EdTemplateTablefieldController {
    @Autowired
    private EdTemplateTablefieldService edTemplateTablefieldService;

}
