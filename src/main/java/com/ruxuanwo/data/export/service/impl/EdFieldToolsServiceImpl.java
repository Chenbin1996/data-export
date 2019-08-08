package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.mapper.EdFieldToolsMapper;
import com.ruxuanwo.data.export.service.EdFieldToolsService;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdFieldTools;
import com.ruxuanwo.data.export.dto.EdFieldToolsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字段工具表-ServiceImpl接口实现类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdFieldToolsServiceImpl extends AbstractService<EdFieldTools, EdFieldToolsDTO, String> implements EdFieldToolsService {
    @Resource
    private EdFieldToolsMapper edFieldToolsMapper;

    @Override
    public List<EdFieldTools> findByField(String fieldId) {
        return edFieldToolsMapper.findByField(fieldId);
    }

    @Override
    public List<EdFieldToolsDTO> findByFieldAndTool(String fieldId) {
        return edFieldToolsMapper.findByFieldAndTool(fieldId);
    }
}
