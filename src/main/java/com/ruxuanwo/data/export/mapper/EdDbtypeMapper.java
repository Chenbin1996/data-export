package com.ruxuanwo.data.export.mapper;


import com.ruxuanwo.data.export.core.Mapper;
import com.ruxuanwo.data.export.domain.EdDbtype;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ruxuanwo
 */
public interface EdDbtypeMapper extends Mapper<EdDbtype> {
    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<EdDbtype> findByName(@Param("name") String name);
}