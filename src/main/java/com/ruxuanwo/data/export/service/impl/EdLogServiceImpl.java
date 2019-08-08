package com.ruxuanwo.data.export.service.impl;

import com.ruxuanwo.data.export.domain.EdLog;
import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.enums.LogStateEnum;
import com.ruxuanwo.data.export.mapper.EdLogMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateDbconfigMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateTableMapper;
import com.ruxuanwo.data.export.service.EdLogService;
import com.ruxuanwo.data.export.core.AbstractService;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.dto.EdLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 数据导入日志表-ServiceImpl接口实现类
 * @author ruxuanwo on 2018/05/08
 * @version 3.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EdLogServiceImpl extends AbstractService<EdLog, EdLogDTO, String> implements EdLogService {
    @Resource
    private EdLogMapper edLogMapper;
    @Autowired
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;
    @Autowired
    private EdTemplateTableMapper edTemplateTableMapper;

    @Override
    public EdLog add(String templateId, Long count, String userId) {
        StringBuilder tableName = new StringBuilder();
        EdLog edLog = new EdLog();
        EdTemplateDbconfig edTemplateDbconfig = edTemplateDbconfigMapper.findByTemplate(templateId);
        List<EdTemplateTable> tables = edTemplateTableMapper.findByTableName(templateId);
        for (EdTemplateTable edTemplateTable : tables) {
            tableName.append(edTemplateTable.getTableName()).append(",");
        }
        tableName.deleteCharAt(tableName.length() - 1);
        edLog.setDburl(edTemplateDbconfig.getDburl());
        edLog.setPort(edTemplateDbconfig.getPort());
        edLog.setDbname(edTemplateDbconfig.getDbname());
        edLog.setTableName(tableName.toString());
        edLog.setTemplateId(templateId);
        edLog.setStatus(LogStateEnum.WAIT.getCode());
        edLog.setCreatorId(userId);
        edLog.setForeignTable(Constant.TABLE_PREFIX + templateId);
        edLog.setTotalCount(count);
        Integer sort = edLogMapper.getMaxSort();
        edLog.setSort(sort == null ? 1 : sort + 1);
        edLogMapper.insert(edLog);
        return edLog;
    }

    @Override
    public List<EdLogDTO> findByState(String name,Integer... state) {
        List<Integer> list = Arrays.asList(state);
        return edLogMapper.findByState(name,list);
    }

    @Override
    public EdLogDTO findDtoById(String id) {
        return edLogMapper.findDtoById(id);
    }

    @Override
    public List<EdLog> findByTemplateIdAndStatus(String templateId, Integer status) {
        return edLogMapper.findByTemplateIdAndStatus(templateId,status);
    }

}
