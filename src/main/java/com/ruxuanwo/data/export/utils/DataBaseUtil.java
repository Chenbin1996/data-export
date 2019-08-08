package com.ruxuanwo.data.export.utils;

import com.alibaba.fastjson.JSON;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import com.ruxuanwo.data.export.exception.SqlErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自制操作连接数据库工具类
 *
 * @Author: ruxuanwo
 * @Date: 2018/4/20/0020 15:46
 */
public class DataBaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseUtil.class);


    /**
     * 获取数据库下的所有表名
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getTableNames(EdTemplateDbconfigDTO dbConfig) {
        List<String> data = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(dbConfig);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW TABLES");
            data = resultSetToList(resultSet);
            logger.info("查询成功" + data);
        } catch (SQLException e) {
            throw new SqlErrorException("获取数据库表名失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, resultSet, statement);
        }
        return data;
    }

    /**
     * 获取数据库下的所有表字段名称
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getColumnNames(EdTemplateDbconfigDTO dbConfig, String tableName) {
        List<String> columnNames = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + tableName;
        try {
            statement = connection.prepareStatement(sql);
            //结果集元数据
            ResultSetMetaData metaData = statement.getMetaData();
            //表列数
            int size = metaData.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(metaData.getColumnName(i + 1));
            }
            logger.info("查询成功" + columnNames);
        } catch (SQLException e) {
            throw new SqlErrorException("获取数据库表字段失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, null, statement);
        }
        return columnNames;
    }

    /**
     * 获取数据库下的所有表字段类型名称
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getColumnTypes(EdTemplateDbconfigDTO dbConfig, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        PreparedStatement statement = null;
        //查询数据库字段类型
        String sql = "SELECT column_type FROM information_schema.COLUMNS WHERE table_name = '" + tableName + "' AND table_schema  = '" + dbConfig.getDbname() + "'";
        try {
            statement = connection.prepareStatement(sql);
//            //结果集元数据
//            ResultSetMetaData metaData = statement.getMetaData();
//            //表列数
//            int size = metaData.getColumnCount();
//            for (int i = 0; i < size; i++) {
//                columnTypes.add(metaData.getColumnTypeName(i + 1));
//            }
            ResultSet resultSet = statement.executeQuery();
            columnTypes = resultSetToList(resultSet);
            logger.info("查询成功", columnTypes);
        } catch (SQLException e) {
            throw new SqlErrorException("获取数据库表字段类型失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, null, statement);
        }
        return columnTypes;
    }

    /**
     * 获取数据库下的所有表字段注释
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    private static List<String> getColumnComments(EdTemplateDbconfigDTO dbConfig, String tableName) {
        List<String> columnComments = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW FULL COLUMNS FROM " + tableName);
            while (resultSet.next()) {
                columnComments.add(resultSet.getString("Comment"));
            }
            logger.info("查询成功" + columnComments);
        } catch (SQLException e) {
            throw new SqlErrorException("获取数据库表字段注释失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, resultSet, statement);
        }
        return columnComments;
    }

    /**
     * 往数据库中插入数据
     *
     * @return
     */
    public static Object insert(Connection connection, String sql) throws SQLException {
        Object object = null;
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            object = resultSet.getObject(1);
        }
        return object;
    }

    /**
     * 预处理方式往数据库中插入数据
     *
     * @return
     */
    public static Object insertPrepare(Connection connection, String sql, Map<String, Object> keyAndValue) throws SQLException {
        logger.info("insertPrepareSql : {}", sql);
        logger.info("keyAndValue : {}", JSON.toJSONString(keyAndValue));
        Object object = null;
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        for (String key : keyAndValue.keySet()) {
            i++;
            statement.setObject(i, keyAndValue.get(key));
        }
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            object = resultSet.getObject(1);
        }
        return object;
    }

    /**
     * 往数据库中更新数据
     *
     * @return
     */
    public static void update(Connection connection, String sql) throws SQLException {
        Object object = null;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    /**
     * 往数据库中更新数据
     *
     * @return
     */
    public static void updatePrepare(Connection connection, String sql, Map<String, Object> keyAndValue, Object keyValue) throws SQLException {
        if (StringUtils.isEmpty(sql)){
            return ;
        }
        logger.info("updatePrepareSql : {}", sql);
        logger.info("keyAndValue : {}", JSON.toJSONString(keyAndValue));
        if (keyAndValue == null) {
            keyAndValue = new HashMap<>(1);
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        int i = 0;
        for (String key : keyAndValue.keySet()) {
            i++;
            statement.setObject(i, keyAndValue.get(key));
        }
        statement.setObject(++i, keyValue);
        statement.executeUpdate();
    }

    /**
     * 预处理方式查询数据库
     *
     * @return
     */
    public static Object selectPrepare(Connection connection, String sql, Map<String, Object> keyAndValue, String keyName) throws SQLException {
        if (StringUtils.isEmpty(sql)){
            return null;
        }
        logger.info("selectPrepareSql : {}", sql);
        logger.info("keyAndValue : {}", JSON.toJSONString(keyAndValue));
        if (keyAndValue == null) {
            keyAndValue = new HashMap<>(1);
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        int i = 0;
        for (String key : keyAndValue.keySet()) {
            i++;
            statement.setObject(i, keyAndValue.get(key));
        }
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString(keyName) : null;
    }

    /**
     * 删除指定表
     *
     * @param dbConfig  数据库配置对象
     * @param tableName 指定的表名
     */
    public static void deleteTable(EdTemplateDbconfigDTO dbConfig, String tableName) {
        Connection connection = getConnection(dbConfig);
        Statement statement = null;
        String sql = "DROP TABLE " + tableName;
        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new SqlErrorException("删除表失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, null, statement);
        }
    }

    /**
     * 获取所有不能为null的字段
     *
     * @param edTemplateDbconfig
     * @return
     */
    public static List<String> getNotNUllField(EdTemplateDbconfigDTO edTemplateDbconfig, String tableName) {
        Connection connection = getConnection(edTemplateDbconfig);
        List<String> fields = new ArrayList<>();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            rs = dbmd.getColumns(null, "%", tableName, "%");
            String flag;
            while (rs.next()) {
                flag = rs.getString("IS_NULLABLE");
                if ("NO".equals(flag)) {
                    fields.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            throw new SqlErrorException("获取所有不能为null的字段失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, rs, null);
        }
        return fields;
    }

    /**
     * 获取表主键信息
     *
     * @param edTemplateDbconfig
     * @return
     */
    public static List<Map<String, Object>> getTablePk(EdTemplateDbconfigDTO edTemplateDbconfig) {
        Connection connection = getConnection(edTemplateDbconfig);
        Statement statement = null;
        ResultSet rs = null;
        List<Map<String, Object>> tableConfig = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.TABLE_NAME AS tableName,p.COLUMN_NAME AS columnName,")
                .append("a.COLUMN_TYPE AS dataType,")
                .append("CASE WHEN p.column_Name IS NULL THEN 'false' ELSE 'true' END  AS pkColumn,")
                .append("CASE WHEN a.extra = 'auto_increment' THEN 'true' ELSE 'false' END  AS autoAdd ")
                .append("FROM ").append("( ")
                .append("SELECT TABLE_NAME,COLUMN_NAME ").append("FROM ")
                .append("INFORMATION_SCHEMA.KEY_COLUMN_USAGE ")
                .append("WHERE ").append("constraint_name = 'PRIMARY' ")
                .append("AND TABLE_SCHEMA = ").append("'" + edTemplateDbconfig.getDbname() + "'")
                .append(" GROUP BY TABLE_NAME")
                .append(" ) p").append(" INNER JOIN information_schema.COLUMNS a ")
                .append("ON (").append(" a.TABLE_SCHEMA = ").append("'" + edTemplateDbconfig.getDbname() + "'")
                .append(" AND a.TABLE_NAME = p.TABLE_NAME ")
                .append("AND p.COLUMN_NAME = a.COLUMN_NAME ").append(")");

        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(60000);
            rs = statement.executeQuery(sql.toString());
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("tableName", rs.getString("tableName"));
                map.put("columnName", rs.getString("columnName"));
                map.put("jdbcType", rs.getString("dataType"));
                tableConfig.add(map);
            }
        } catch (SQLException e) {
            throw new SqlErrorException("获取表主键信息失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, rs, statement);
        }
        return tableConfig;
    }

    /**
     * 数据库连接
     *
     * @param dbConfig 连接配置对象
     * @return Connection对象
     */
    public static Connection getConnection(EdTemplateDbconfigDTO dbConfig) {
        String usingPassword = "using password";
        String unknownDatabase = "Unknown database";
        String linkFailure = "link failure";
        try {
            Class.forName(dbConfig.getDriverClassName());
            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://").append(dbConfig.getDburl()).append(":")
                    .append(dbConfig.getPort()).append("/").append(dbConfig.getDbname())
                    .append("?connectTimeout=5000&socketTimeout=60000&useUnicode=true&characterEncoding=UTF-8")
                    .append("&useSSL=false&autoReconnect=true&maxReconnects=2&initialTimeout=5&failOverReadOnly=false");
            logger.info("正在连接[" + url.toString() + "]...");
            Connection connection = DriverManager.getConnection(url.toString(), dbConfig.getUsername(), dbConfig.getPassword());
            return connection;

        } catch (ClassNotFoundException e) {
            throw new SqlErrorException("连接失败,请检查数据库驱动类型是否正确！");
        } catch (SQLException e) {
            if (e.getMessage().contains(usingPassword)) {
                throw new SqlErrorException();
            } else if (e.getMessage().contains(unknownDatabase)) {
                throw new SqlErrorException("找不到数据库名" + e.getMessage().substring((e.getMessage().indexOf("'")) + 1, e.getMessage().lastIndexOf("'")));
            } else if (e.getMessage().contains(linkFailure)) {
                throw new SqlErrorException("数据库连接失败，请检查配置是否正确！");
            } else {
                throw new SqlErrorException("数据库连接失败，请检查配置是否正确！");
            }
        }
    }

    /**
     * 转换元数据
     *
     * @param rs ResultSet对象
     * @return List数据
     * @throws SQLException
     */
    private static List<String> resultSetToList(ResultSet rs) throws SQLException {
        if (rs == null) {
            logger.info("调试" + "转换失败元数据为空");
            return null;
        }
        //获取字段数
        int columnCount = rs.getMetaData().getColumnCount();
        List<String> result = new ArrayList<>();
        while (rs.next()) {
            for (int j = 1; j <= columnCount; j++) {
                result.add(rs.getString(j));
            }
        }
        return result;
    }

    /**
     * 关闭所有连接
     */
    public static void closeConnection(Connection connection, ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new SqlErrorException("resultSet关闭出错：" + e.getMessage());
            }
            resultSet = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new SqlErrorException("statement关闭出错：" + e.getMessage());
            }
            statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
                logger.info("数据库关闭连接");
            } catch (SQLException e) {
                throw new SqlErrorException("connection关闭出错：" + e.getMessage());
            }
            connection = null;
        }
    }
}
