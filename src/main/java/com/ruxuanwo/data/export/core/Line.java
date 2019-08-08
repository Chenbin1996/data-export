package com.ruxuanwo.data.export.core;

import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 一行数据集合
 * @author ruxuanwo
 */
public class Line {
    /**
     * 一行数据的单元格集合
     */
    private List<Cell> cells;
    /**
     * 整行数据状态
     */
    private Integer state;

    /**
     * 排序字段
     */
    private Integer sortOrder;

    /**
     * 原始数据临时表sql
     */
    private String tempSql;
    /**
     * 转换后数据临时表sql
     */
    private String tempNewSql;

    {
        this.cells = new ArrayList<>();
    }

    public Line(){
    }

    public Line(Integer sortOrder){
        this.sortOrder = sortOrder;
    }

    /**
     * 创建值是uuid的单元格,放在单元格最后，
     * 该uuid为原始数据表sql的主键，为新数据表sql的temporary_id外键
     */
    private void createIdCell(){
        Cell cell = new Cell();
        cell.setValue(UUID.randomUUID().toString().replace("-", ""));
        this.cells.add(cell);
    }

    /**
     * 添加单元格
     * @param cell 数据
     */
    public void addToCells(Cell cell){
        this.cells.add(cell);
    }

    /**
     * 释放cells资源
     */
    public void cleanCells(){
        this.cells = null;
    }

    /**
     * 创建两个sql
     */
    public void createSql(){
        this.createIdCell();
        //第一张临时表sql
        StringBuilder tempSqlBuilder = new StringBuilder();
        //第二张临时表sql
        StringBuilder tempNewSqlBuilder = new StringBuilder();
        //错误信息
        StringBuilder msg = new StringBuilder().append("'");
        //有错误的字段
        StringBuilder wrongFiled = new StringBuilder().append("'");
        Integer state = RecordStateEnum.RIGHT.getCode();
        for (Cell cell : this.cells) {
            tempSqlBuilder.append(returnTempValue(cell));
            tempNewSqlBuilder.append(returnTempNewValue(cell));
            StringBuilder wrongMsg = cell.putErrorMsg();
            msg.append(wrongMsg);
            wrongFiled.append(wrongMsg.length() == 0 ? "" : cell.getExcelName() + "|");
            state = cellState(cell,state);
            cell = null;
        }
        if(msg.length() <= 1){
            msg.append(Constant.DATA_RIGHT);
        }else {
            msg.deleteCharAt(msg.length() -1);
        }
        msg.append("'");
        wrongFiled.append("'");
        tempSqlBuilder.append(msg).append(",").append(wrongFiled);
        if(tempNewSqlBuilder.length() > 0){
            tempNewSqlBuilder.deleteCharAt(tempNewSqlBuilder.length() - 1);
        }
        this.tempSql = tempSqlBuilder.toString();
        this.tempNewSql = tempNewSqlBuilder.toString();
        this.state = state;
    }

    /**
     * 返回原始数据临时表sql的数据
     * @param cell
     * @return
     */
    private String returnTempValue(Cell cell){
        String value = cell.getValue();
        if(value == null){
            value = "";
        }
        return "'" + value + "',";
    }

    /**
     * 返回转换后数据临时表sql的数据
     * @param cell
     * @return
     */
    private String returnTempNewValue(Cell cell){
        String value = cell.getValue();
        //判断效验器是否异常
        if (cell.checkError()){
            return null + ",";
        }

        //是否转换成功
        if(cell.getConverInform() != null){
            if(RecordStateEnum.RIGHT.getCode().equals(cell.getConverInform().getState())){
                value = cell.getConverInform().getData() == null ? null : cell.getConverInform().getData().toString();
            }else {
                value = null;
            }
        }
        if (StringUtils.isEmpty(value)){
            return null + ",";
        }
        return "'" + value + "',";
    }

    /**
     * 返回状态
     * @param cell
     * @param state
     * @return
     */
    private Integer cellState(Cell cell,Integer state){
        for (Information information : cell.getCheckInforms()) {
            state = this.change(state,information.getState());
        }
        if(cell.getConverInform() != null){
            state = this.change(state,cell.getConverInform().getState());
        }
        return state;
    }

    /**
     * 设置数据状态
     */
    public void createState() {
        Integer state = RecordStateEnum.RIGHT.getCode();
        for (Cell cell : this.cells) {
            for (Information information : cell.getCheckInforms()) {
                state = this.change(state,information.getState());
            }
            if(cell.getConverInform() != null){
                state = this.change(state,cell.getConverInform().getState());
            }
        }
        this.state = state;
    }

    /**
     * 返回最大值
     * @param state 当前值
     * @param other 改变值
     * @return
     */
    private Integer change(int state, int other){
        if(state >= other){
            return state;
        }else {
            return other;
        }
    }

    public String getTempSql() {
        return tempSql;
    }

    public String getTempNewSql() {
        return tempNewSql;
    }

    public Integer getState() {
        return state;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
