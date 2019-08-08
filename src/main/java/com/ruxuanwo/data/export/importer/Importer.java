package com.ruxuanwo.data.export.importer;

import com.ruxuanwo.data.export.core.Data;

import java.sql.SQLException;

/**
 * 导入器
 */
public interface Importer {

    /**
     * 导入数据
     */
    void importData(Data data) throws SQLException;

}
