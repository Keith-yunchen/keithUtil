package com.zhouy.module.poiexcel;

/**
 * @author:zhouy,date:20170805
 * @Version 1.0
 */
public class CellValue implements Cloneable {
    private static CellValue cellValue = new CellValue();
    private String val;
    private String region="1";
    private String rowSpan="1";
    private String colSpan="1";

    public static CellValue getCelValueInstance(){
        return cellValue;
    }

    @Override
    protected CellValue clone()  {
        CellValue cellValue = null;
        try {
            cellValue = (CellValue) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cellValue;
    }

    private CellValue(){}

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(String rowSpan) {
        this.rowSpan = rowSpan;
    }

    public String getColSpan() {
        return colSpan;
    }

    public void setColSpan(String colSpan) {
        this.colSpan = colSpan;
    }
}
