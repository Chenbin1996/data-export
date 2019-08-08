package com.ruxuanwo.data.export.web;

import com.ruxuanwo.data.export.core.ExcelData;
import com.ruxuanwo.data.export.core.ServiceException;
import com.ruxuanwo.data.export.utils.ExportUtil;
import com.ruxuanwo.data.export.utils.ResponseMsgUtil;
import com.ruxuanwo.data.export.utils.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * excel
 * @author ruxuanwo
 * 2019-3-6 9:13
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @PostMapping("/export")
    public Result excelExport(HttpServletResponse response,
                              @RequestBody ExcelData excelData)  {
        Workbook workbook = ExportUtil.export(excelData);
        OutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((excelData.getFileName()).getBytes("utf-8"), "ISO-8859-1") + ".xls");
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
        } catch (IOException e) {
            throw new ServiceException("导入异常：", e);
        }
        LOGGER.info("导出文件成功,{}", excelData.getFileName());
        return ResponseMsgUtil.success();
    }

}
