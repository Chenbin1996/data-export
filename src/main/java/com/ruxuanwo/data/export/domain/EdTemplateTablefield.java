package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author ruxuanwo
 */
@Table(name = "ed_template_tablefield")
public class EdTemplateTablefield implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT REPLACE(UUID(),\"-\",\"\")")
    private String id;

    @Column(name = "tableField_id")
    private String tableFieldId;

    @Column(name = "templateField_id")
    private String templateFieldId;

    private static final long serialVersionUID = 1L;

    /**
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return tableField_id
     */
    public String getTableFieldId() {
        return tableFieldId;
    }

    /**
     * @param tableFieldId
     */
    public void setTableFieldId(String tableFieldId) {
        this.tableFieldId = tableFieldId;
    }

    /**
     * @return templateField_id
     */
    public String getTemplateFieldId() {
        return templateFieldId;
    }

    /**
     * @param templateFieldId
     */
    public void setTemplatefieldId(String templateFieldId) {
        this.templateFieldId = templateFieldId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EdTemplateTablefield other = (EdTemplateTablefield) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTableFieldId() == null ? other.getTableFieldId() == null : this.getTableFieldId().equals(other.getTableFieldId()))
            && (this.getTemplateFieldId() == null ? other.getTemplateFieldId() == null : this.getTemplateFieldId().equals(other.getTemplateFieldId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTableFieldId() == null) ? 0 : getTableFieldId().hashCode());
        result = prime * result + ((getTemplateFieldId() == null) ? 0 : getTemplateFieldId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tableFieldId=").append(tableFieldId);
        sb.append(", templateFieldId=").append(templateFieldId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}