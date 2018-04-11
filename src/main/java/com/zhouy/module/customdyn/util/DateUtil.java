package com.zhouy.module.customdyn.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT0 = "yyyy-MM-dd";

	/**
	 * @Title: date2String
	 * @Description: 日期转字符串
	 * @param date
	 * @param pattern
	 * @return String
	 * @throws
	 */
	public static String date2String(Date date, String pattern) {
		String dateStr = null;
		if (null == date) {
			date = new Date();
		}
		if (null == pattern) {
			pattern = FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		dateStr = format.format(date);
		return dateStr;
	}

	/**
	 * 
	 * @Title: string2Date
	 * @Description: 字符串转日期
	 * @param dateStr
	 * @param pattern
	 * @return Date
	 * @throws
	 */
	public static Date string2Date(String dateStr, String pattern) {
		Date date = null;
		if (null == dateStr) {
			date = new Date();
			return date;
		}
		if (null == pattern) {
			pattern = FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Title: getDate
	 * @Description: 获取日期之前或之后的日期
	 * @param date
	 * @param days
	 * @return Date
	 * @throws
	 */
	public static Date getDate(Date date, int days) {
		Date temp = null;
		if (null == date) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		temp = calendar.getTime();
		return temp;
	}

	/**
	 * @Title: isBefore
	 * @Description: 判断start是否在end之前,-1表示在之前,0表示时间相同,1表示start在end之后
	 * @param start
	 * @param end
	 * @return int
	 * @throws
	 */
	public static int isBefore(Date start, Date end) {
		int before = -1;
		if (date2String(start, FORMAT).equals(date2String(end, FORMAT))) {
			before = 0;
			return before;
		}
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(start);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(end);
		if (cStart.before(cEnd)) {
			before = -1;
		} else {
			before = 1;
		}
		return before;
	}

	/**
	 * 
	 * @Title: getDays
	 * @Description: 计算两个时间间的天数间隔
	 * @param start
	 * @param end
	 * @return int
	 * @throws
	 */
	public static int getDays(Date start, Date end) {
		int days = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		long startMillis = calendar.getTimeInMillis();
		calendar.setTime(end);
		long endMillis = calendar.getTimeInMillis();
		days = Integer.parseInt(String.valueOf((endMillis - startMillis)
				/ (1000 * 3600 * 24)));
		return Math.abs(days);
	}

}