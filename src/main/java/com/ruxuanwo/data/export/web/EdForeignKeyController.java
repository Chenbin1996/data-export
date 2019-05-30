package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.service.EdForeignKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字段关联外键表-Controller类
 * @author ChenBin on 2018/06/22
 */
@RestController
@RequestMapping("/edForeignKey")
public class EdForeignKeyController {
    @Autowired
    private EdForeignKeyService edForeignKeyService;

}
