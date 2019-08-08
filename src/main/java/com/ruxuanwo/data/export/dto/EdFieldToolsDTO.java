package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.domain.EdFieldTools;

/**
 * 字段工具表-DTO数据传输对象类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
public class EdFieldToolsDTO extends EdFieldTools {
    private String toolName;

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    @Override
    public String toString() {
        return super.toString() + "EdFieldToolsDTO{}";
    }
}
