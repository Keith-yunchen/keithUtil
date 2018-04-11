package com.zhouy.module.poiexcel;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Map;

/**
 * sheet样式
 *
 * @author:zhouy,date:20170608
 * @Version 1.0
 */
public class SheetStyle {
    private XSSFSheet sheet = null;
    private Map<Integer,Integer> colWidthMap = null;

    public SheetStyle(XSSFSheet sheet ){
        this.sheet = sheet;
    }

    public Map<Integer, Integer> getColWidthMap() {
        return colWidthMap;
    }

    public SheetStyle setColWidthMap(Map<Integer, Integer> colWidthMap) {
        this.colWidthMap = colWidthMap;
        return this;
    }

    public XSSFSheet excuteMerged(CellRangeAddress region){
        sheet.addMergedRegion(region);
        RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        RegionUtil.setBorderLeft(XSSFCellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        RegionUtil.setBorderRight(XSSFCellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        RegionUtil.setBorderTop(XSSFCellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        return sheet;
    }

    public XSSFSheet builde(){
        if(colWidthMap!=null && !colWidthMap.isEmpty()){
            for(Integer key : colWidthMap.keySet()){
                Integer value = colWidthMap.get(key);
                this.sheet.setColumnWidth(key,value);
            }
        }
        return this.sheet;
    }


}
