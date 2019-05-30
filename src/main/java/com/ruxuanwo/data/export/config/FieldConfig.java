package com.ruxuanwo.data.export.config;

import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.check.Factory;
import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.dto.EdToolsDTO;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.ToolTypeEnum;
import com.ruxuanwo.data.export.utils.SpringContextUtil;

import java.util.List;

/**
 * 存放字段所有信息
 * @author chenbin
 */
public class FieldConfig {

    /**
     * 字段名称
     */
    private String excelName;
    /**
     * 字段缺省值
     */
    private String defaultValue;
    /**
     * 临时表字段名称
     */
    private String columnName;
    /**
     * 校验方法和转化器集合
     */
    private List<EdToolsDTO> toosList;
    /**
     * 校验链
     */
    private Client client;
    /**
     * 转换器
     */
    private Conversion conversion;
    /**
     * 转换器参数
     */
    private Parameter converParameter;

    /**
     * 设置校验器和转换器中的值
     * @param data 数值
     */
    public void setParamData(String data){
        if(this.client != null){
            this.getClient().setParametersData(data);
        }
        if(this.converParameter != null){
            this.converParameter.setData(data);
        }
    }

    /**
     * 为转换器赋值
     * @param data
     */
    public void setConverParameter(String data){
        if(this.converParameter != null){
            this.converParameter.setData(data);
        }
    }

    /**
     * 创建转换器
     * @param className 类名
     */
    private void createConversion(String className) throws ClassNotFoundException {
        this.conversion = (Conversion) SpringContextUtil.getBean(Class.forName(className));
    }

    private void setConverParameter(EdToolsDTO edToolsDTO){
        Parameter parameter = new Parameter()
                .setExcelName(this.getExcelName())
                .setType(edToolsDTO.getFailType());
        switch (edToolsDTO.getName()){
            case Constant.RIVER_SHORE:
                parameter.setPid(Constant.RIVER_REACH_PID);
                break;
            case Constant.LAKES_SHORE:
                parameter.setPid(Constant.LAKES_REACH_PID);
                break;
            case Constant.RESERVOIR_SHORE:
                parameter.setPid(Constant.RESERVOIR_REACH_PID);
                break;
                default:
        }
        this.converParameter = parameter;
    }

    /**
     * 往校验器链中添加校验
     * @param client  校验器链
     * @param className 校验方法名称
     */
    private void addToCheckChain(Client client,String className){
        client.addCheck(Factory.createCheck(className));

    }

    /**
     * 往校验器链中添加校验参数
     */
    private void addToCheckParameters(Client client,EdToolsDTO edToolsDTO){
        Parameter parameter = new Parameter()
                .setExcelName(this.getExcelName())
                .setType(edToolsDTO.getFailType());
        if(Constant.LENGCHECK_NAME.equals(edToolsDTO.getName())){
            parameter.setLength(Integer.parseInt(edToolsDTO.getOther()));
        }
        client.addToParameters(parameter);
    }

    public void setToosList(List<EdToolsDTO> toosList) throws ClassNotFoundException {
        if(toosList == null || toosList.size() <= 0){
            return;
        }
        Client client = new Client();
        for (EdToolsDTO edToolsDTO : toosList) {
            if(edToolsDTO.getType().equals(ToolTypeEnum.type_check.getCode())){
                this.addToCheckChain(client,edToolsDTO.getClassName());
                this.addToCheckParameters(client,edToolsDTO);
            }else if(edToolsDTO.getType().equals(ToolTypeEnum.tyep_conver.getCode())){
                this.createConversion(edToolsDTO.getClassName());
                this.setConverParameter(edToolsDTO);
            }
        }
        this.client = client;
    }


    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = Constant.TABEL_FIELD + columnName;
    }

    public List<EdToolsDTO> getToosList() {
        return toosList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Conversion getConversion() {
        return conversion;
    }

    public void setConversion(Conversion conversion) {
        this.conversion = conversion;
    }

    public Parameter getConverParameter() {
        return converParameter;
    }

    public void setConverParameter(Parameter converParameter) {
        this.converParameter = converParameter;
    }


}
