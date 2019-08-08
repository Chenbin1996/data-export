package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.domain.EdForeignKey;
import com.ruxuanwo.data.export.mapper.EdForeignKeyMapper;
import com.ruxuanwo.data.export.service.EdForeignKeyService;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.dto.EdForeignKeyDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 字段关联外键表-ServiceImpl接口实现类
 * @author ruxuanwo on 2018/06/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdForeignKeyServiceImpl extends AbstractService<EdForeignKey, EdForeignKeyDTO, String> implements EdForeignKeyService {
    @Resource
    private EdForeignKeyMapper edForeignKeyMapper;

}
