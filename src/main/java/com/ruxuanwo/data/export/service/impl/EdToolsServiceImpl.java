package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.domain.EdTools;
import com.ruxuanwo.data.export.dto.EdToolsDTO;
import com.ruxuanwo.data.export.mapper.EdToolsMapper;
import com.ruxuanwo.data.export.service.EdToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 工具表-ServiceImpl接口实现类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdToolsServiceImpl extends AbstractService<EdTools, EdToolsDTO, String> implements EdToolsService {
    @Resource
    private EdToolsMapper edToolsMapper;
    @Autowired
    private EdToolsService edToolsService;

    @Override
    public List<EdTools> findToolType(Integer type) {
        Condition condition = new Condition(EdTools.class);
        condition.createCriteria().andEqualTo("type", type);
        List<EdTools> byCondition = edToolsService.findByCondition(condition);
        return byCondition;
    }

    @Override
    public List<EdToolsDTO> findAllDto() {
        return edToolsMapper.findAllDto();
    }

    @Override
    public EdToolsDTO findDtoById(String id) {
        return edToolsMapper.findDtoById(id);
    }

    @Override
    public Integer findMaxSort() {
        return edToolsMapper.findMaxSort();
    }

    @Override
    public HashMap<String,Object> findByName(String name) {
        return edToolsMapper.findByName(name);
    }

    @Override
    public List<EdTools> selectByName(String name) {
        return edToolsMapper.selectByName(name);
    }
}
