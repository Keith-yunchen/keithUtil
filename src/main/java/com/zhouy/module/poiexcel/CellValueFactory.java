package com.zhouy.module.poiexcel;

/**
 * @author:zhouy,date:20170805
 * @Version 1.0
 */
public class CellValueFactory {
    private static CellValue celval = CellValue.getCelValueInstance();
    public static CellValue getCelValue(){
        return CellValueFactory.celval.clone();
    }
}
