package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.domain.EdTableField;
import com.ruxuanwo.data.export.domain.EdTemplateField;

import java.util.List;

/**
 * 模板字段表-DTO数据传输对象类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public class EdTemplateFieldDTO extends EdTemplateField {

    private List<EdToolsDTO> edTools;
    private List<EdTableField> edTableFields;

    public List<EdToolsDTO> getEdTools() {
        return edTools;
    }

    public void setEdTools(List<EdToolsDTO> edTools) {
        this.edTools = edTools;
    }

    public List<EdTableField> getEdTableFields() {
        return edTableFields;
    }

    public void setEdTableFields(List<EdTableField> edTableFields) {
        this.edTableFields = edTableFields;
    }

    @Override
    public String toString() {
        return "EdTemplateFieldDTO{" +
                "edTools=" + edTools +
                ", edTableFields=" + edTableFields +
                '}';
    }
}
