package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdTemplateTool;
import com.ruxuanwo.data.export.dto.EdTemplateToolDTO;
import com.ruxuanwo.data.export.mapper.EdTemplateToolMapper;
import com.ruxuanwo.data.export.service.EdTemplateToolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 模版工具关联表-ServiceImpl接口实现类
 * @author ChenBin on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateToolServiceImpl extends AbstractService<EdTemplateTool, EdTemplateToolDTO, String> implements EdTemplateToolService {
    @Resource
    private EdTemplateToolMapper edTemplateToolMapper;

}
