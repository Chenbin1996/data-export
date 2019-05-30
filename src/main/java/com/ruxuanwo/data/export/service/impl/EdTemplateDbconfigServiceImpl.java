package com.ruxuanwo.data.export.service.impl;


import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.mapper.EdTemplateDbconfigMapper;
import com.ruxuanwo.data.export.service.EdTemplateDbconfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 模板数据库配置表-ServiceImpl接口实现类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateDbconfigServiceImpl extends AbstractService<EdTemplateDbconfig, EdTemplateDbconfigDTO, String> implements EdTemplateDbconfigService {
    @Resource
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;

    @Override
    public EdTemplateDbconfigDTO detail(String id) {
        return edTemplateDbconfigMapper.detail(id);
    }
}
