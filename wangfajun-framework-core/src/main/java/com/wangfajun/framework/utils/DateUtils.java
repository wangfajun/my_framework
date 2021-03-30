package com.wangfajun.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class DateUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	private static String datePattern = "yyyy-MM-dd";

	private static String datePattern_YYYYMMdd = "yyyyMMdd";

	private static String datePattern_YYYYMMddHHmmss = "YYYYMMddHHmmss";

	private static String datePattern_YYYYMM = "yyyyMM";

	public static final String CHINA_DATETIME = "yyyy年MM月dd日";

	public static final String CHINAYYYYMM_DATETIME = "yyyy年MM月";

	private static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

	private static String timePattern = "HH:mm";

	private final static DateFormat FORMAT_YYYYMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	public final static DateFormat FORMAT_HHMMSS = new SimpleDateFormat("HHmmss");

	public final static DateFormat FORMAT_HH = new SimpleDateFormat("HH");

	private static final String FORMAT_YYYY_MM = "yyyy-MM";

	/**
	 * Return 缺省的日期格式 (yyyy/MM/dd)
	 *
	 * @return 在页面中显示的日期格式
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 *
	 * @param aDate 日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 *
	 * @param aDate 日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDateTime(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(dateTimePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param aMask   输入字符串的格式
	 * @param strDate 一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 * @see SimpleDateFormat
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			LOGGER.error("ParseException: " + pe);
			//throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: yyyy/MM/dd HH:MM a
	 *
	 * @param theTime the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: yyyy-MM-dd
	 *
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in the format you specify
	 * on input
	 *
	 * @param aMask the date pattern the string is in
	 * @param aDate a date object
	 * @return a formatted string representation of the date
	 * @see SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			LOGGER.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 *
	 * @param aDate
	 * @return
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * 根据日期格式，返回日期按dateTimePattern格式转换后的字符串
	 *
	 * @param aDate
	 * @return
	 */
	public static final String convertDateTimeToString(Date aDate) {
		return getDateTime(dateTimePattern, aDate);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate (格式 yyyyMMdd)
	 * @return
	 * @throws ParseException
	 */
	public static Date convertYYYYMMDDToDate(String strDate) {
		Date aDate = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting date with pattern: " + datePattern_YYYYMMdd);
		}
		aDate = convertStringToDate(datePattern_YYYYMMdd, strDate);
		return aDate;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate (格式 YYYYMMddHHmmss)
	 * @return
	 * @throws ParseException
	 */
	public static Date convertLocalTimeToDate(String strDate) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");//定义时间格式
		LocalDateTime ldt = LocalDateTime.parse(strDate, df);//把刚刚转化的String类型再转为LocalDateTime类型
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate (格式 yyyy-MM-dd)
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting date with pattern: " + datePattern);
		}

		aDate = convertStringToDate(datePattern, strDate);

		return aDate;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate (格式 yyyyMMdd)
	 * @return
	 * @throws ParseException
	 */
	public static Date convertYyyymmddToDate(String strDate) {
		Date aDate = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting date with pattern: " + datePattern_YYYYMMdd);
		}

		aDate = convertStringToDate(datePattern_YYYYMMdd, strDate);

		return aDate;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param strDate (格式 yyyy-MM-dd HH:mm:ss)
	 * @return
	 * @throws ParseException
	 */
	public static Date convertTimeStringToDate(String strDate) {
		Date aDate = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting date with pattern: " + dateTimePattern);
		}

		aDate = convertStringToDate(dateTimePattern, strDate);

		return aDate;
	}

	/**
	 * 时间相加
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dateAdd(Date date, int day) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiffer(Date date1, Date date2) {
		return (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
	}

	/**
	 * 得到两个日期之间的天数差，包括开始和结束日期(如：beginCalender=2007-10-01，endCalendar=2007-10-20 结果为：20)
	 *
	 * @param beginDay 开始日期(小的)
	 * @param endDay   结束日期(大的)
	 * @return
	 */
	public static Long getDifferenceDays(Date beginDay, Date endDay) {
		Calendar beginCalender = Calendar.getInstance();
		beginCalender.setTime(beginDay);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDay);

		Long days = (endCalendar.getTimeInMillis() - beginCalender
				.getTimeInMillis())
				/ (24 * 60 * 60 * 1000);
		days = days + 1;
		return days;
	}

	public static Date getFirstDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 返回本月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String getLastDayStr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
		return format.format(cal.getTime());
	}

	/**
	 * 返回日期String格式  yyyyMMdd
	 *
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
		return format.format(cal.getTime());
	}

	/**
	 * 返回本月第一天
	 *
	 * @param date  日期
	 * @param month 0 当月  1下月  -1  上月  以此类推
	 * @return
	 */
	public static String getFirstDateStr(int month, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, month);
		DateFormat format = new SimpleDateFormat(datePattern_YYYYMMdd);
		return format.format(cal.getTime());
	}

	/**
	 * 得到当前时间,格式为yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getyyyyMMddHHmmssCurDate() {
		return FORMAT_YYYYMMddHHmmss.format(new Date());
	}

	/**
	 * 得到格式为yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getyyyyMMddHHmmssToDate(String date) {
		return FORMAT_YYYYMMddHHmmss.format(convertTimeStringToDate(date));
	}

	/**
	 * date转换成calendar，只精确到yyyyMMdd
	 *
	 * @throws ParseException
	 */
	public static Calendar getyyyyMMDDCalendar(Date date) throws ParseException {
		SimpleDateFormat dataFormat = new SimpleDateFormat(datePattern_YYYYMMdd);
		dataFormat.parse(dataFormat.format(date));
		return dataFormat.getCalendar();
	}

	/**
	 * 获取日期毫秒数(精确到天)
	 *
	 * @throws ParseException
	 */
	public static long getyyyyMMDDTimeInMillis(Date date) throws ParseException {
		return getyyyyMMDDCalendar(date).getTimeInMillis();
	}

	/**
	 * 获取日期的HHmmss
	 *
	 * @param date
	 * @return
	 */
	public static long getFormatHHmmss(Date date) {
		return Long.valueOf(FORMAT_HHMMSS.format(date));
	}

	/**
	 * 获取日期的HH
	 *
	 * @param date
	 * @return
	 */
	public static long getFormatHH(Date date) {
		return Long.valueOf(FORMAT_HH.format(date));
	}

	/**
	 * 时间是否在<code>days</code>内
	 *
	 * @param old
	 * @param days
	 * @return
	 */
	public static boolean isDaysInterval(Date old, int days) {
		Calendar now = Calendar.getInstance();
		return (now.getTimeInMillis() - old.getTime()) <= (1000L * 3600 * 24 * days);
	}

	/**
	 * 得到当前日期后的N天的日期
	 *
	 * @param days 天数
	 * @return
	 */
	public static Date getBackDaysOfNow(int days) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(now.getTimeInMillis() + 1000L * 3600 * 24 * days);
		return now.getTime();
	}

	public static Date getBackDaysOfDay(Date date, int days) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.setTimeInMillis(day.getTimeInMillis() + 1000L * 3600 * 24 * days);
		return day.getTime();
	}

	/**
	 * @param @param  date
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: formatDate
	 * @Description: 格式化年月日  yyyy-mm-dd
	 */
	public static String formatDate(String date) {
		String result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param @param  date
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: formatChinaDate
	 * @Description: yyyymmdd 转成中文
	 */
	public static String formatChinaDate(String date) {
		String result = null;
		try {
			DateFormat sdf = new SimpleDateFormat(datePattern_YYYYMMdd);
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat(CHINA_DATETIME);
			result = sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public static String formatChinaYMDDate(String date) {
		String result = null;
		try {
			DateFormat sdf = new SimpleDateFormat(CHINA_DATETIME);
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat(datePattern);
			result = sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param @param  date
	 * @param @return
	 * @return String
	 * @throws
	 * @Title: formatChinaDate
	 * @Description: yyyymm 转成中文
	 */
	public static String formatChinaDateyyyyMM(String date) {
		String result = null;
		try {
			DateFormat sdf = new SimpleDateFormat(datePattern_YYYYMM);
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat(CHINAYYYYMM_DATETIME);
			result = sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取日期的起始时间
	 *
	 * @param date 日期
	 * @return 起始时间
	 */
	public static Date getStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取日期的结束时间
	 *
	 * @param date 日期
	 * @return 结束时间
	 */
	public static Date getEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取日期所属月份的开始时间
	 *
	 * @param date 日期
	 * @return 日期所属月份的开始时间
	 */
	public static Date getMonthStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取日期所属月份的结束时间
	 *
	 * @param date 日期
	 * @return 日期所属月份的结束时间
	 */
	public static Date getMonthEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间年月日字符串（yyyyMMdd）
	 *
	 * @return 当前时间年月日字符串
	 */
	public static String getyyyyMMddStr() {
		return LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
	}

	/**
	 * 获取当前时间时分秒字符串（HHmmss）
	 *
	 * @return 当前时间时分秒字符串
	 */
	public static String getHHmmssStr() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
	}

	/**
	 * 获取当前时间时分秒毫秒字符串（HHmmssSSS）
	 *
	 * @return 当前时间时分秒毫秒字符串
	 */
	public static String getHHmmssSSSStr() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS"));
	}

	/**
	 * 获取当前时间年月日字符串（yyyyMMddHHmmssSSS）
	 *
	 * @return 当前时间年月日字符串
	 */
	public static String getyyyyMMddHHmmssSSSStr() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	}

	/**
	 * 获取当天剩余时间（秒）
	 *
	 * @return 当天剩余时间（秒）
	 */
	public static long getRemainTime() {
		LocalDateTime midNight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return ChronoUnit.SECONDS.between(LocalDateTime.now(), midNight);
	}

	/**
	 * 时间字符串转换成时间（yyyy-MM）
	 *
	 * @param dateStr 时间字符串
	 * @return 时间
	 */
	public static Date parse_yyyy_mm(String dateStr) {
		return cn.hutool.core.date.DateUtil.parse(dateStr, FORMAT_YYYY_MM);
	}

	/**
	 * 昨天的日期
	 *
	 * @return
	 */
	public static Date yesterdayDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 时间转换成时间字符串
	 *
	 * @param date       时间
	 * @param dateFormat 时间格式
	 * @return 时间字符串
	 */
	public static String getDateStr(Date date, String dateFormat) {
		return cn.hutool.core.date.DateUtil.format(date, dateFormat);

	}

	/**
	 * 时间字符串转换成时间
	 *
	 * @param date       时间字符串
	 * @param dateFormat 时间格式
	 * @return 时间
	 */
	public static Date parse(String date, String dateFormat) {
		return cn.hutool.core.date.DateUtil.parse(date, dateFormat);
	}

	/**
	 * 一天的开始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date beginOfDate(Date date) {
		return cn.hutool.core.date.DateUtil.beginOfDay(date);
	}

	/**
	 * 一天的结束时间
	 *
	 * @param date
	 * @return
	 */
	public static Date endOfDate(Date date) {
		return cn.hutool.core.date.DateUtil.endOfDay(date);
	}

}
