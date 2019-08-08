package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import com.ruxuanwo.data.export.core.Service;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;

/**
 * 模板数据库配置表-Service接口类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
public interface EdTemplateDbconfigService extends Service<EdTemplateDbconfig, EdTemplateDbconfigDTO, String> {
    /**
     * 多表查看详情
     * @param id
     * @return
     */
    EdTemplateDbconfigDTO detail(String id);
}
