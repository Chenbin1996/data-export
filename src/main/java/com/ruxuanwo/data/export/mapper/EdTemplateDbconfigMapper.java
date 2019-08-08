package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.dto.EdTemplateDbconfigDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruxuanwo
 */
public interface EdTemplateDbconfigMapper extends Mapper<EdTemplateDbconfig> {
    /**
     * 多表查看详情
     * @param id
     * @return
     */
    EdTemplateDbconfigDTO detail(String id);

    /**
     * 根据ID查询模版
     * @param id
     * @return
     */
    EdTemplateDbconfig findByTemplate(String id);

    /**
     * 查询模块数据库配置
     * @param templateId 模板id
     * @return
     */
    EdTemplateDbconfigDTO selectDbConfig(@Param("templateId") String templateId);
}