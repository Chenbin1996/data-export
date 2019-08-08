package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author ruxuanwo
 */
@Table(name = "ed_foreign_key")
public class EdForeignKey implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT REPLACE(UUID(),\"-\",\"\")")
    private String id;

    @Column(name = "tableField_id")
    private String tableFieldId;

    @Column(name = "table_name")
    private String tableName;

    private String field;

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
     * @return table_id
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     */
    public void setField(String field) {
        this.field = field;
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
        EdForeignKey other = (EdForeignKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTableFieldId() == null ? other.getTableFieldId() == null : this.getTableFieldId().equals(other.getTableFieldId()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getField() == null ? other.getField() == null : this.getField().equals(other.getField()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTableFieldId() == null) ? 0 : getTableFieldId().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getField() == null) ? 0 : getField().hashCode());
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
        sb.append(", tableName=").append(tableName);
        sb.append(", field=").append(field);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}