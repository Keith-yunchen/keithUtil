package com.zhouy.module.poiexcel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.concurrent.CountDownLatch;

/**
 * 处理行数据线程
 *
 * @author:zhouy,date:20170829
 * @Version 1.0
 */
public class GenerateRowThread implements Runnable {
    private final JSONArray rows;
    private final ExcelStyle excelStyle;
    private final String[] style;
    private final XSSFRow excelRow;
    private CountDownLatch cdl;

    public GenerateRowThread(JSONArray row,
                             ExcelStyle excelStyle, XSSFRow excelRow, String[] style, CountDownLatch _cdl) {
        this.rows = row;
        this.excelStyle = excelStyle;
        this.style = style;
        this.excelRow = excelRow;
        this.cdl = _cdl;
    }

    @Override
    public void run() {
        try {
            int rowSize = rows.size();
            for(int j = 0;j<rowSize;j++){
                JSONObject celObj = (JSONObject) rows.get(j);
                XSSFCell value_cell = excelRow.createCell(j);
                value_cell.setCellStyle(excelStyle.getStyleByStr(style==null ?"":style[j]));
                String cellValue = celObj.getString("val") != null ? celObj.getString("val").toString() : "";
                if(cellValue!=null){
                    cellValue = cellValue.replaceAll("<br/>","\\\n");
                    XSSFRichTextString richString = new XSSFRichTextString(cellValue);
                    value_cell.setCellValue(richString);
                }else{
                    value_cell.setCellValue("");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }finally {
            cdl.countDown();
        }
    }
}
