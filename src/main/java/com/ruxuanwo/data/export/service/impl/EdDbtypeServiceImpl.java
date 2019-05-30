package com.ruxuanwo.data.export.service.impl;


import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdDbtype;
import com.ruxuanwo.data.export.dto.EdDbtypeDTO;
import com.ruxuanwo.data.export.mapper.EdDbtypeMapper;
import com.ruxuanwo.data.export.service.EdDbtypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库类型表-ServiceImpl接口实现类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdDbtypeServiceImpl extends AbstractService<EdDbtype, EdDbtypeDTO, String> implements EdDbtypeService {
    @Resource
    private EdDbtypeMapper edDbtypeMapper;

    @Override
    public List<EdDbtype> findByName(String name) {
        return edDbtypeMapper.findByName(name);
    }
}
