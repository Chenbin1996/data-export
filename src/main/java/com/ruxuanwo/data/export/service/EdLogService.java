package com.ruxuanwo.data.export.service;


import com.ruxuanwo.data.export.domain.EdLog;
import com.ruxuanwo.data.export.dto.EdLogDTO;
import com.ruxuanwo.data.export.core.Service;
import java.util.List;

/**
 * 数据导入日志表-Service接口类
 * @author ruxuanwo on 2018/05/08
 * @version 3.0.0
 */
public interface EdLogService extends Service<EdLog, EdLogDTO, String> {

    /**
     * 新增日志
     * @param templateId 模块id
     * @param count 导入数量
     * @param userId 登录用户ID
     * @return 日志对象
     */
    EdLog add(String templateId, Long count, String userId);

    /**
     * 根据状态查询
     * @param name 模板名称
     * @param state 状态
     * @return dto集合
     */
    List<EdLogDTO> findByState(String name,Integer ... state);

    /**
     * 根据id查询
     * @param id 日志id
     * @return dto
     */
    EdLogDTO findDtoById( String id);

    /**
     * 根据模板id和状态查询
     * @param templateId 模板id
     * @param status 状态
     * @return
     */
    List<EdLog> findByTemplateIdAndStatus(String templateId,
                                          Integer status);
}
