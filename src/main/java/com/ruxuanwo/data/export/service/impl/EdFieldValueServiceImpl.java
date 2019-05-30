package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdFieldValue;
import com.ruxuanwo.data.export.dto.EdFieldValueDTO;
import com.ruxuanwo.data.export.mapper.EdFieldValueMapper;
import com.ruxuanwo.data.export.service.EdFieldValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 字段默认值表-ServiceImpl接口实现类
 * @author ChenBin on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdFieldValueServiceImpl extends AbstractService<EdFieldValue, EdFieldValueDTO, String> implements EdFieldValueService {
    @Resource
    private EdFieldValueMapper edFieldValueMapper;

}
