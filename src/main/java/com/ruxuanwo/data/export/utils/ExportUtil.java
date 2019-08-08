package com.ruxuanwo.data.export.utils;

import com.ruxuanwo.data.export.constants.ExcelConstant;
import com.ruxuanwo.data.export.core.ExcelData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/3/0003 11:37
 */
@Component
public class ExportUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 生成excel模板
     *
     * @param list 模板字段集合
     * @return excel
     */
    public static Workbook export(List<HashMap<String, Object>> list) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        for (int i = 0; i < list.size(); i++) {
            sheet.setColumnWidth(i, list.get(i).get("name").toString().getBytes().length * 2 * 256);
            sheet.autoSizeColumn(i);
            cellStyle.setWrapText(true);
            sheet.setDefaultColumnStyle(i, cellStyle);
            Cell cell = row.createCell(i);
            cell.setCellValue(list.get(i).get("name").toString());
            isRed(workbook, cell, (Boolean) list.get(i).get("bool"));
        }
        return workbook;
    }

    /**
     * 生成excel
     *
     * @param excelData 模板数据
     * @return excel
     */
    public static Workbook export(ExcelData excelData) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        CellStyle cellStyle = getStyle(workbook, ExcelConstant.HUAWENSONG_TYPE, null, true, ExcelConstant.HEAD_FONTHEIGHT, false);
        //当前行
        int nowRow = 0;
        //数据的行数和列数
        int rowNum = excelData.getBody().length;
        int column = excelData.getHead().length;
        Row row;
        Cell cell;
        int notData = 1;
        if (StringUtils.isNotEmpty(excelData.getTitle())){
            notData++;
            //标题
            row = sheet.createRow(nowRow++);
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,column - 1));
            cell = row.createCell(0);
            cell.setCellValue(excelData.getTitle());
            cell.setCellStyle(cellStyle);
        }

        cellStyle = getStyle(workbook, ExcelConstant.SONG_TYPE, null, true, ExcelConstant.DEFAULT_FONTHEIGHT, false);
        //行首
        row = sheet.createRow(nowRow++);
        for (int j = 0; j < column; j++) {
            sheet.setColumnWidth(j, excelData.getHead()[j].getBytes().length * 3 * 256);
            sheet.autoSizeColumn(j);
            cellStyle.setWrapText(true);
            sheet.setDefaultColumnStyle(j, cellStyle);
            cell = row.createCell(j);
            cell.setCellValue(excelData.getHead()[j]);
            cell.setCellStyle(cellStyle);
        }

        cellStyle = getStyle(workbook, ExcelConstant.SONG_TYPE, null, false, ExcelConstant.BODY_FONTHEIGHT, true);
        //内容
        for ( ; nowRow < rowNum + notData; nowRow++) {
            row = sheet.createRow(nowRow);
            for (int j = 0; j < column; j++) {
                cell = row.createCell(j);
                cell.setCellValue(excelData.getBody()[nowRow - notData][j].toString());
                cell.setCellStyle(cellStyle);
            }
        }
        return workbook;
    }

    /**
     * 设置样式
     *
     * @param workbook   excel
     * @param fontName   字体名称
     * @param color      颜色
     * @param isBold     加粗
     * @param fontHeight 字号
     * @return
     */
    private static CellStyle getStyle(Workbook workbook, String fontName, Short color, Boolean isBold, Short fontHeight, Boolean isWrapText) {
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        //水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //自动换行
        cellStyle.setWrapText(isWrapText != null && isWrapText);
        Font font = workbook.createFont();
        font.setFontName(StringUtils.isEmpty(fontName) ? ExcelConstant.FONTNAME : fontName);
        font.setFontHeightInPoints(fontHeight == null ? ExcelConstant.DEFAULT_FONTHEIGHT : fontHeight);
        font.setColor(color == null ? ExcelConstant.COLOR_BLACK : color);
        font.setBold(isBold != null && isBold);
        cellStyle.setFont(font);
        return cellStyle;
    }



    /**
     * 字段飞空则字体颜色为红色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     * @param flag     是否非空校验
     */
    private static void isRed(Workbook workbook, Cell cell, boolean flag) {
        if (flag) {
            setFont(workbook, cell, null, ExcelConstant.COLOR_RED);
        } else {
            setFont(workbook, cell);
        }
    }

    /**
     * 设置字体格式和颜色 默认微软雅黑 默认颜色黑色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     */
    private static void setFont(Workbook workbook, Cell cell) {
        setFont(workbook, cell, null, null);
    }

    /**
     * 设置字体格式和颜色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     * @param fontName 字体格式 默认微软雅黑
     * @param color    字体颜色    默认黑色
     */
    private static void setFont(Workbook workbook, Cell cell, String fontName, Short color) {
        CellStyle style = getStyle(workbook, fontName, color, false, (short) 12, false);
        cell.setCellStyle(style);
    }

    /**
     * 数据导入
     *
     * @param stream 文件流
     * @param path   文件全称
     * @return 数据集合
     */
    public static List<List<String>> importExcel(InputStream stream, String path, int size) {
        Workbook workbook = getWorkBook(stream, path);
        List<Sheet> sheets = getSheets(workbook);
        return readExcel(sheets, size);
    }

    /**
     * 判断获取不同版本的Workbook实现类
     *
     * @param stream 文件流
     * @param path   文件全称
     * @return Workbook对象
     */
    private static Workbook getWorkBook(InputStream stream, String path) {
        Workbook workbook = null;
        try {
            workbook = path.endsWith(".xls") ? (new HSSFWorkbook(stream))
                    : (path.endsWith(".xlsx") ? (new XSSFWorkbook(stream)) : (null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 获取每一个sheet，以防有多sheet情况
     *
     * @param book Workbook对象
     * @return sheet集合
     */
    private static List<Sheet> getSheets(Workbook book) {
        int numberOfSheets = book.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(book.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * 解析导入的文件数据
     *
     * @param sheets sheet集合
     * @return 解析后的数据
     */
    private static List<List<String>> readExcel(List<Sheet> sheets, int size) {
        List<List<String>> dataList = new ArrayList<>();
        List<String> data;
        Row row;
        Cell cell;
        for (int i = 0; i < sheets.size(); i++) {
            Sheet sheet = sheets.get(i);
            if (sheet.getLastRowNum() >= 1) {
                System.out.println("=======" + sheet.getSheetName() + "=======");
            }

            Iterator<Row> sheetIterator = sheet.rowIterator();
            while (sheetIterator.hasNext()) {
                row = sheetIterator.next();
                if (row == null) {
                    continue;
                }
                data = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        //去除首尾空格
                        String cellValue = cell.getStringCellValue();
                        data.add(StringUtils.isBlank(cellValue) ? null : cellValue.trim());
                    } else {
                        data.add("");
                    }
                }
                if (! isEmptyList(data)){
                    dataList.add(data);
                }
            }
        }
        return dataList;
    }

    /**
     * 判断集合是否为空
     * @param list
     * @return
     */
    private static boolean isEmptyList(List<String> list){
        if (CollectionUtils.isEmpty(list)){
            return true;
        }
        boolean flag = true;
        for (String data : list) {
            if (StringUtils.isNotEmpty(data)){
                flag = false;
                break;
            }
        }
        return flag;
    }

}
