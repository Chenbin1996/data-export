package com.ruxuanwo.data.export.importer.impl;

import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.conversion.impl.EncryptionConversion;
import com.ruxuanwo.data.export.conversion.impl.NameConversion;
import com.ruxuanwo.data.export.dto.Data;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.dto.EdTemplateTableDTO;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import com.ruxuanwo.data.export.exception.SqlErrorException;
import com.ruxuanwo.data.export.importer.Importer;
import com.ruxuanwo.data.export.mapper.EdTemplateDbconfigMapper;
import com.ruxuanwo.data.export.mapper.EdTemplateMapper;
import com.ruxuanwo.data.export.service.EdTemplateService;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import com.ruxuanwo.data.export.utils.DataBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 河长河道导入器
 * 因以下几点无法满足，只能特定设计导入器来导入河长河道
 * 1.跨数据库插入数据
 * 2.用户姓名转拼音问题
 * 3.密码加密需要传入username字段
 * ...
 */
@Component
public class ReachChairmanImporer implements Importer {

    @Autowired
    private EdTemplateMapper edTemplateMapper;
    @Autowired
    private EdTemplateDbconfigMapper edTemplateDbconfigMapper;
    @Autowired
    private EdTemplateService edTemplateService;
    @Autowired
    private NameConversion nameConversion;
    @Autowired
    private EncryptionConversion encryptionConversion;

    private static final String DEFAULT_PASSWORD = "123456";

    @Value("${data-export.user_db}")
    private String user_db;

    @Override
    public void importData(Data data) throws SQLException {
        //获取表和字段信息
        List<EdTemplateTableDTO> list = edTemplateMapper.findTableAndField(data.getTemplateId());
        //获取临时表数据
        List<HashMap<String, String>> dataList = edTemplateMapper.findNewTempData(Constant.TABLE_PREFIX + data.getTemplateId() + Constant.TABLE_SUFFIX,
                data.getLogId(),
                edTemplateService.tempKey(data.getTemplateId()));
        //需要获取两个连接...
        List<Connection> connections = getConnection(data.getTemplateId());
        try {
            for (HashMap<String, String> dataMap : dataList) {
                for (EdTemplateTableDTO table : list) {
                    table.cleanAndNewMap();
                    table.putAllValue(table, dataMap, list);
                    table.createSelectPrepareSql();
                    Object keyBack = isRepeat(table.getTableName(), connections, table.getSelectPrepareSql().toString(), table.getSelectKeyAndValue(), table.getKeyName());
                    if (keyBack == null) {
                        putUserNameAndPassword(table,connections);
                        table.createInsertPrepareSql();
                        keyBack = insert(table.getTableName(), connections, table.getInsertPrepareSql().toString(), table.getKeyAndValue());
                        if (table.getKenGenerate().equals(GeneratorEnum.AUTOINCREMENT.getNum())) {
                            table.putKeyAndValue(table.getKeyName(), keyBack.toString());
                        }
                    } else {
                        table.putKeyAndValue(table.getKeyName(), keyBack.toString());

                    }
                    updateThisData(connections, table);
                }
            }
        } catch (Exception e) {
            rollback(connections);
            throw new SqlErrorException(e.getMessage());
        }
        commit(connections);
        close(connections);
    }

    /**
     * 对数据进行更新
     * @param table
     */
    private void updateThisData(List<Connection> connections, EdTemplateTableDTO table) throws SQLException {
        if ("md_reach".equals(table.getTableName())){
            String sql = "UPDATE md_reach mr SET mr.reach_code = id, mr.parents = CASE " +
                    " WHEN mr.parents_code is NULL OR mr.parents_code = '' THEN " +
                    " mr.id ELSE concat( ( SELECT a.parents FROM ( SELECT * FROM md_reach ) a WHERE a.id = mr.parents_code ), " +
                    " '|', mr.id )  " +
                    "END  " +
                    "WHERE mr.id = " + "'" + table.getKeyAndValue().get(table.getKeyName()) + "'";
            Connection connection = getUseConnection(table.getTableName(), connections);
            DataBaseUtil.update(connection, sql);
        }
    }

    /**
     * 执行插入操作
     * @param tableName
     * @param connections
     * @param sql
     * @param keyAndValue
     */
    private Object insert(String tableName, List<Connection> connections, String sql, Map<String, String> keyAndValue) throws SQLException {
        Connection connection = getUseConnection(tableName, connections);
        return DataBaseUtil.insertPrepare(connection, sql, keyAndValue);
    }

    /**
     * 根据要插入的表名称获取不同的连接
     * @param tableName
     * @param connections
     * @return
     */
    private Connection getUseConnection(String tableName, List<Connection> connections){
        //根据表使用不同的数据库连接
        Connection connection;
        switch (tableName){
            case "user_info":
                connection = connections.get(1);
                break;
            case "user_application":
                connection = connections.get(1);
                break;
            default:
                connection = connections.get(0);
        }
        return connection;
    }

    /**
     * 给用户表生成用户名和密码
     * @param table
     */
    private void putUserNameAndPassword(EdTemplateTableDTO table, List<Connection> connections) throws SQLException {
        if(!"user_info".equals(table.getTableName())){
            return;
        }
        table.putKeyAndValue("user_name",getUserName(table, getUseConnection(table.getTableName(), connections)));
        table.putKeyAndValue("password",getPassword(table));
    }

    /**
     * 生成用户名
     * @param table
     * @return
     */
    private String getUserName(EdTemplateTableDTO table, Connection connection) throws SQLException {
        String userName = null;
        Object hasName;
        int index = 0;
        do{
            userName = getUserSpell(table,index);
            hasName = selectUserName(connection, userName);
            index++;
        }while (hasName != null);
        return userName;
    }

    /**
     * 获取用户名称拼音,或者在拼音后加1
     * @param table
     * @param index 代表重复个数
     * @return
     */
    private String getUserSpell(EdTemplateTableDTO table,int index){
        Parameter parameter = new Parameter();
        parameter.setData(table.getKeyAndValue().get("name"));
        String userName = nameConversion.conversion(parameter).getData().toString();
        if(index != 0){
            userName += index + "";
        }
        return userName;
    }

    /**
     * 查询是否名称重复
     * @param connection
     * @param userName
     * @return
     * @throws SQLException
     */
    private Object selectUserName(Connection connection, String userName) throws SQLException {
        String sql = "select * from user_info where user_name = ?";
        HashMap<String, String> map = new HashMap<>(16);
        map.put("user_name", userName);
        return DataBaseUtil.selectPrepare(connection, sql, map, "user_name");
    }

    /**
     * 生成密码
     * @param table
     * @return
     */
    private String getPassword(EdTemplateTableDTO table){
        String userName = table.getKeyAndValue().get("user_name");
        Parameter parameter = new Parameter();
        parameter.setData(userName.trim().toUpperCase().concat(DEFAULT_PASSWORD));
        Information conversion = encryptionConversion.conversion(parameter);
        return conversion.getData().toString();
    }

    /**
     * 查询是否重复数据，重复则返回之前的id
     * @param tableName
     * @param connections
     * @param sql
     * @param keyAndValue
     * @return
     */
    private Object isRepeat(String tableName, List<Connection> connections, String sql, Map<String, String> keyAndValue, String keyName) throws SQLException {
        //根据表使用不同的数据库连接
        Connection connection = getUseConnection(tableName, connections);
        return  DataBaseUtil.selectPrepare(connection, sql, keyAndValue, keyName);
    }

    /**
     * 关闭所有连接
     * @param connections
     */
    private void close(List<Connection> connections){
        for (Connection connection : connections) {
            DataBaseUtil.closeConnection(connection, null, null);
        }
    }

    /**
     * 回滚所有连接
     * @param connections
     * @throws SQLException
     */
    private void rollback(List<Connection> connections) throws SQLException {
        for (Connection connection : connections) {
            connection.rollback();
        }
    }

    /**
     * 提交所有连接
     * @param connections
     * @throws SQLException
     */
    private void commit(List<Connection> connections) throws SQLException {
        for (Connection connection : connections) {
            connection.commit();
        }
    }

    /**
     * 返回数据库连接
     * @param templateId
     * @return
     * @throws SQLException
     */
    private List<Connection> getConnection(String templateId) throws SQLException {
        //获取数据库配置
        EdTemplateDbconfigDTO dbconfig = edTemplateDbconfigMapper.selectDbConfig(templateId);
        dbconfig.setPassword(CryptoUtil.decode(Constant.KEY, dbconfig.getPassword()));

        List<Connection> list = new ArrayList<>();
        //第一个连接
        Connection reachConnection = DataBaseUtil.getConnection(dbconfig);
        reachConnection.setAutoCommit(false);
        list.add(reachConnection);

        //第2个连接，可以自己配置连接地址等等
        dbconfig.setDbname(user_db);
        Connection userConnection = DataBaseUtil.getConnection(dbconfig);
        userConnection.setAutoCommit(false);
        list.add(userConnection);

        return list;
    }

}
