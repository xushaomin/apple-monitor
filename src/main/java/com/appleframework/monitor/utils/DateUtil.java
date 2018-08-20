package com.appleframework.monitor.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期转换类
 */
public class DateUtil {

	public static final String DATA_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_FORMAT_22 = "yyyy-MM-dd HH:mm:ssSSS";
	public static final String DATA_FORMAT_19_S = "yyyy-MM-dd 00:00:00";
	public static final String DATA_FORMAT_19_E = "yyyy-MM-dd 23:59:59";
	public static final String DATA_FORMAT_17 = "yyyyMMddHHmmssSSS";
	public static final String DATA_FORMAT_10 = "yyyy-MM-dd";
	public static final String DATA_FORMAT_4 = "yyyy";
	public static final String DATA_FORMAT_7 = "yyyy-MM";
	public static final String DATA_FORMAT_6 = "yyyyMM";
	public static final String DATA_FORMAT_DAY = "yyyyMMdd";
	public static final String DATA_FORMAT_8 = "HH:mm:ss";

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat(DATA_FORMAT_4);

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(DATA_FORMAT_10);

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(DATA_FORMAT_19);

	public static Date getDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 时间格式化
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDateFrom19(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_19);
		return dateFormat.format(date);
	}

	/**
	 * 时间格式化
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDateFrom10(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_10);
		return dateFormat.format(date);
	}

	/**
	 * 时间格式化
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatFromDate(String format, Date date) {
		if (StringUtils.isBlank(format)) {
			format = DATA_FORMAT_19;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 字符串转时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date formatToDate(String format, String date) throws ParseException {
		if (StringUtils.isBlank(format)) {
			format = DATA_FORMAT_19;
		}
		if (StringUtils.isBlank(date)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(date);
	}

	/**
	 * 字符串转时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String format, String date) {
		if (StringUtils.isBlank(format)) {
			format = DATA_FORMAT_19;
		}
		if (StringUtils.isBlank(date)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat(DATA_FORMAT_10);
			try {
				return dateFormat.parse(date);
			} catch (ParseException e1) {
			}
		}
		return null;
	}

	/**
	 * 字符串转时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatToDate(String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_19);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat(DATA_FORMAT_10);
			try {
				return dateFormat.parse(date);
			} catch (ParseException e1) {
			}
		}
		return null;
	}

	/**
	 * 计算两个日期之间 相差多少分钟
	 * 
	 * @param updateTime
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static long minuteBetween(String updateTime, Date bdate) throws ParseException {
		long lt = new Long(updateTime);
		Date date = new Date(lt);
		// Date smdate =
		// formatToDate(formatFromDate(DATA_FORMAT_19,date),DATA_FORMAT_19);
		// Date endDate =
		// formatToDate(formatFromDate(DATA_FORMAT_19,bdate),DATA_FORMAT_19);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_Mins = (time2 - time1) / (1000 * 60);
		return between_Mins;
	}

	/**
	 * 计算两个日期之间 相差多少分钟
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long minuteBetween(Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endTime);
		long time2 = cal.getTimeInMillis();
		long between_Mins = (time2 - time1) / (1000 * 60);
		return between_Mins;
	}
	/**
	 * 计算两个日期之间 相差多少秒
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static double miaoBetween(Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endTime);
		long time2 = cal.getTimeInMillis();
		double between_Mins = (time2 - time1) / (1000);
		return between_Mins;
	}
	/**
	 * yyyy-MM-dd 转时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatToDate10(String date) {
		try {
			return formatToDate(DATA_FORMAT_10, date);
		} catch (ParseException e1) {
		}
		return null;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatToDate19(String date) {
		try {
			return formatToDate(DATA_FORMAT_19, date);
		} catch (ParseException e1) {
		}
		return null;
	}

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */
	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_19);
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String calculateEndDate(String beginDate, int timeSlot, int calculateType) throws ParseException {
		String endDate = "";
		Date dt = sdfDay.parse(beginDate);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(calculateType, timeSlot);
		Date dt1 = rightNow.getTime();
		endDate = sdfDay.format(dt1);
		return endDate;
	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD hh:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);
		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 获取多少月份之前的时间
	 */
	public static String getBanYearAgo(Integer month) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		date = calendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);
		return dateStr;
	}

	/**
	 * 时间增加或减少年
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 时间增加或减少月
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 时间增加或减少天
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * 时间增加或减少小时
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 时间增加或减少分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 时间增加或减少秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 时间加
	 * 
	 * @param date
	 * @param type
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, int type, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, amount);
		return calendar.getTime();
	}

	/**
	 * @Description 某个时间和当前时间相隔的天数，小时，以及 分钟
	 * @param date
	 * @return xx天xx小时xx分
	 */
	public static String Timebetween(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		// long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		return "" + day + "天" + hour + "小时" + min + "分";
	}

	public static void main(String[] args) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df.parse("2017-04-28 17:31:40");
		System.err.println(Timebetween(d1));
	}

	/**
	 * @return
	 * @throws ParseException
	 * @Description 上个月第一天
	 */
	public static String lastMonthOne() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return firstDay;

	}

	/**
	 * @throws ParseException
	 * @Description 上个月最后一天
	 */
	public static String lastMonthLast() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	/**
	 * 最得今天的最后一秒
	 * 
	 * @return
	 */
	public static String tadayLast() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar cale = Calendar.getInstance();
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	/**
	 * @throws ParseException
	 * @Description 这个月第一天
	 */
	public static String thisMonthOne() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}

	public static String timeBetweenDay(Date dateBegin, Date dateEnd) {
		long l = dateEnd.getTime() - dateBegin.getTime();
		long day = l / 86400000L;
		long hour = l / 3600000L - day * 24L;
		long min = l / 60000L - day * 24L * 60L - hour * 60L;
		return "" + day + "天" + hour + "小时" + min + "分";
	}

}

