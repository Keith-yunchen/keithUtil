package com.zhouy.module.poiexcel.util;

/**
 * 处理字符串的辅助类
 * @author Administrator
 * 
 */
public class StringUtil {

	/**
	 * 
	 * @Title: checkStringIsNotEmpty
	 * @Description:验证字符串是否不为空
	 * @param stringValue
	 *            传入要验证的字符串
	 * @return boolean true：不为 空 或 不为null; false:值为 空 或 为null
	 */
	public static boolean isNotEmpty(String stringValue) {
		if (null == stringValue || "".equals(stringValue.trim())) {
			return false;
		}
		return true;
	}


}