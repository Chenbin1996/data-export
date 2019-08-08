package com.ruxuanwo.data.export.importer.impl;

import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.mapper.EdTemplateDbconfigMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateMapper;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import com.ruxuanwo.data.export.utils.DataBaseUtil;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.core.Data;
import com.ruxuanwo.data.export.dto.EdTemplateTableDTO;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import com.ruxuanwo.data.export.importer.Importer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 默认导入器
 * 只能导入一对一关系型数据
 * @author ruxuanwo
 */
@Component
public class DefaultImporter implements Importer {

    @Autowired
    private EdTemplateMapper edTemplateMapper;
    @Autowired
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;
    @Autowired
    private EdTemplateService edTemplateService;

    @Override
    public void importData(Data data) throws SQLException {
        //获取数据库配置
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(data.getTemplateId());
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));
        //获取表和字段信息
        List<EdTemplateTableDTO> list = edTemplateMapper.findTableAndField(data.getTemplateId());
        //获取临时表数据
        List<Map<String, Object>> dataList = edTemplateMapper.findNewTempData(Constant.TABLE_PREFIX + data.getTemplateId() + Constant.TABLE_SUFFIX,
                data.getLogId(),
                edTemplateService.tempKey(data.getTemplateId()));
        //创建连接
        Connection connection = DataBaseUtil.getConnection(dbconfig);
        connection.setAutoCommit(false);
        try {
            for (Map<String, Object> dataMap : dataList) {
                for (EdTemplateTableDTO table : list) {
                    table.setConnection(connection);
                    table.cleanAndNewMap();
                    table.putAllValue(table, dataMap, list);
                    table.createInsertPrepareSql();
                    Object keyBack = DataBaseUtil.insertPrepare(connection, table.getInsertPrepareSql().toString(), table.getKeyAndValue());
                    if (table.getKenGenerate().equals(GeneratorEnum.AUTOINCREMENT.getNum())) {
                        table.putKeyAndValue(table.getKeyName(), keyBack.toString());
                    }
                }
            }
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException(e.getMessage());
        }
        connection.commit();
        DataBaseUtil.closeConnection(connection, null, null);
    }
}
