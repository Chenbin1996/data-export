package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author ruxuanwo
 */
@Table(name = "ed_template_table")
public class EdTemplateTable implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT REPLACE(UUID(),\"-\",\"\")")
    private String id;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "import_sort")
    private Integer importSort;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "ken_generate")
    private Integer kenGenerate;

    @Column(name = "key_type")
    private String keyType;

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
     * @return template_id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * @return table_name
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
     * @return import_sort
     */
    public Integer getImportSort() {
        return importSort;
    }

    /**
     * @param importSort
     */
    public void setImportSort(Integer importSort) {
        this.importSort = importSort;
    }

    /**
     * @return key_name
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * @param keyName
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * @return ken_generate
     */
    public Integer getKenGenerate() {
        return kenGenerate;
    }

    /**
     * @param kenGenerate
     */
    public void setKenGenerate(Integer kenGenerate) {
        this.kenGenerate = kenGenerate;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
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
        EdTemplateTable other = (EdTemplateTable) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getImportSort() == null ? other.getImportSort() == null : this.getImportSort().equals(other.getImportSort()))
            && (this.getKeyName() == null ? other.getKeyName() == null : this.getKeyName().equals(other.getKeyName()))
            && (this.getKenGenerate() == null ? other.getKenGenerate() == null : this.getKenGenerate().equals(other.getKenGenerate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getImportSort() == null) ? 0 : getImportSort().hashCode());
        result = prime * result + ((getKeyName() == null) ? 0 : getKeyName().hashCode());
        result = prime * result + ((getKenGenerate() == null) ? 0 : getKenGenerate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateId=").append(templateId);
        sb.append(", tableName=").append(tableName);
        sb.append(", importSort=").append(importSort);
        sb.append(", keyName=").append(keyName);
        sb.append(", kenGenerate=").append(kenGenerate);
        sb.append(", keyType=").append(keyType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}