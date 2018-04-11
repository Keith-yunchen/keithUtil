package com.zhouy.module.poiexcel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * poi操作excel样式
 *
 * @author:zhouy,date:20170608
 * @Version 1.0
 */
public class ExcelStyle {
    XSSFWorkbook workbook = null;
    private XSSFCellStyle style = null;
    private short alignment = XSSFCellStyle.ALIGN_CENTER;
    private boolean wrapText = true;//是否自动换行
    private boolean locked = false;//是否锁定
    private short verticalAligment = XSSFCellStyle.VERTICAL_CENTER;
    private XSSFFont font = null;

    public ExcelStyle(XSSFWorkbook workbook){
        this.workbook = workbook;
        this.style = workbook.createCellStyle();
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
    }

    public ExcelStyle setAlignment(short alignment) {
        this.alignment = alignment;
        return this;
    }

    public ExcelStyle setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
        return this;
    }

    public ExcelStyle setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public ExcelStyle setVerticalAligment(short verticalAligment) {
        this.verticalAligment = verticalAligment;
        return this;
    }

    public ExcelStyle setFont(XSSFFont font) {
        this.font = font;
        return this;
    }

    public XSSFCellStyle builde(){
        this.style.setAlignment(alignment);
        this.style.setVerticalAlignment(verticalAligment);
        this.style.setLocked(locked);
        this.style.setWrapText(wrapText);
        if (font != null) {
            this.style.setFont(font);
        }
        return this.style;
    }

    public XSSFCellStyle getStyleByStr(String name) {
        this.style.setAlignment(alignment);
        this.style.setVerticalAlignment(verticalAligment);
        switch (name){
            case "C":
                this.style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderRight(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderTop(XSSFCellStyle.BORDER_THIN);
                return this.style;
            case "L":
                this.style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderRight(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderTop(XSSFCellStyle.BORDER_THIN);
                return this.style;
            case "R":
                this.style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderRight(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderTop(XSSFCellStyle.BORDER_THIN);
                return this.style;
            default:
                this.style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderRight(XSSFCellStyle.BORDER_THIN);
                this.style.setBorderTop(XSSFCellStyle.BORDER_THIN);
                return this.style;
        }
    }

}
