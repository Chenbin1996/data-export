package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.service.EdFieldGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字段生成器关联表-Controller类
 * @author ChenBin on 2018/06/22
 */
@RestController
@RequestMapping("/edFieldGenerator")
public class EdFieldGeneratorController {
    @Autowired
    private EdFieldGeneratorService edFieldGeneratorService;

}
