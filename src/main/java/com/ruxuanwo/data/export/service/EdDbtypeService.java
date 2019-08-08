package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.domain.EdDbtype;
import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.dto.EdDbtypeDTO;

import java.util.List;

/**
 * 数据库类型表-Service接口类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
public interface EdDbtypeService extends Service<EdDbtype, EdDbtypeDTO, String> {
    /**
     * 根据名称查询
     * @param name 数据库源名称
     * @return 数据库源对象
     */
    List<EdDbtype> findByName(String name);
}
