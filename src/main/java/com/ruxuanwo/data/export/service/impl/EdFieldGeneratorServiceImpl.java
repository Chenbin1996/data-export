package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdFieldGenerator;
import com.ruxuanwo.data.export.dto.EdFieldGeneratorDTO;
import com.ruxuanwo.data.export.mapper.EdFieldGeneratorMapper;
import com.ruxuanwo.data.export.service.EdFieldGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 字段生成器关联表-ServiceImpl接口实现类
 * @author ChenBin on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdFieldGeneratorServiceImpl extends AbstractService<EdFieldGenerator, EdFieldGeneratorDTO, String> implements EdFieldGeneratorService {
    @Resource
    private EdFieldGeneratorMapper edFieldGeneratorMapper;

}
