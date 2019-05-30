package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author Chenbin
 */
@Table(name = "ed_field_generator")
public class EdFieldGenerator implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT REPLACE(UUID(),\"-\",\"\")")
    private String id;

    @Column(name = "tableField_id")
    private String tableFieldId;

    @Column(name = "tool_id")
    private String toolId;

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
     * @return tool_id
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * @param toolId
     */
    public void setToolId(String toolId) {
        this.toolId = toolId;
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
        EdFieldGenerator other = (EdFieldGenerator) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTableFieldId() == null ? other.getTableFieldId() == null : this.getTableFieldId().equals(other.getTableFieldId()))
            && (this.getToolId() == null ? other.getToolId() == null : this.getToolId().equals(other.getToolId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTableFieldId() == null) ? 0 : getTableFieldId().hashCode());
        result = prime * result + ((getToolId() == null) ? 0 : getToolId().hashCode());
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
        sb.append(", toolId=").append(toolId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}