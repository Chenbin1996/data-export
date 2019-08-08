package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.service.EdFieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字段默认值表-Controller类
 * @author ruxuanwo on 2018/06/22
 */
@RestController
@RequestMapping("/edFieldValue")
public class EdFieldValueController {
    @Autowired
    private EdFieldValueService edFieldValueService;

}
