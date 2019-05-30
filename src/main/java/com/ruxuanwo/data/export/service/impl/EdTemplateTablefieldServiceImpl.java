package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdTemplateTablefield;
import com.ruxuanwo.data.export.dto.EdTemplateTablefieldDTO;
import com.ruxuanwo.data.export.mapper.EdTemplateTablefieldMapper;
import com.ruxuanwo.data.export.service.EdTemplateTablefieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 模版字段导入字段关联表-ServiceImpl接口实现类
 * @author ChenBin on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdTemplateTablefieldServiceImpl extends AbstractService<EdTemplateTablefield, EdTemplateTablefieldDTO, String> implements EdTemplateTablefieldService {
    @Resource
    private EdTemplateTablefieldMapper edTemplateTablefieldMapper;

}
