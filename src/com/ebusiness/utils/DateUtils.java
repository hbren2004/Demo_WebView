package com.ebusiness.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	static String[] formats = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH:mmZ", "yyyy-MM-dd HH:mm:ss.SSSZ",
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ", };
	/**
	 * 缺省的日期显示格式： yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 类采用全静态函数调用,私有构造方法，禁止对该类进行实例化
	 */
	private DateUtils() {
	}

	/**
	 * 将给定的字符串用对应的方式解析成日期类型
	 * @param dateStr   需要解析的日期字符串
	 * @param pattern   解析格式,缺省默认格式为"yyyy-MM-dd"
	 * @return 解析得到的日期,返回null时,解析失败
	 */
	public static Date parse(String dateStr, String pattern) {
		Date date = null;
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * 得到系统当前日期时间
	 * @return 当前日期时间
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 得到用缺省方式格式化的当前日期
	 * @return 当前日期
	 */
	public static String getDate() {
		return format(DEFAULT_DATE_FORMAT);
	}

	/**
	 * 得到用缺省方式格式化的当前日期及时间
	 * @return 当前日期及时间
	 */
	public static String getDateTime() {
		return format(DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 得到系统当前日期及时间，并用指定的方式格式化
	 * @param pattern  显示格式
	 * @return 当前日期及时间
	 */
	public static String format(String pattern) {
		Date datetime = Calendar.getInstance().getTime();
		return format(datetime, pattern);
	}

	/**
	 * 得到指定方式的格式化日期
	 * @param date  需要被格式化的日期
	 * @param pattern   格式化方式
	 * @return 格式化后的日期字符串
	 */
	public static String format(Date date, String pattern) {
		String dateStr = null;
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 得到当前年份
	 * @return 当前年份
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份
	 * @return 当前月份
	 */
	public static int getCurrentMonth() {
		//通过get( int field)方法得到的month比实际的month少1,所以最后加上1;
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前日:当前是某月的第几号;
	 * @return 当前日
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 得到当前日期后面days天后的日期
	 * @param days 天数
	 * @return days天后的日期
	 */
	public static Date addDays(int days) {
		return add(getNow(), days, Calendar.DATE);
	}

	/**
	 * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
	 * @param date  指定的日期;
	 * @param days  指定的天数:可为负数,即指定日期之前多少天的日期;
	 * @return 指定日期,指定天数后或天数前的日期;
	 */
	public static Date addDays(Date date, int days) {
		return add(date, days, Calendar.DATE);
	}

	/**
	 * 获取当前月份后months月的日期.如果要得到以前的日期,参数用负数.
	 * @param months  月份数
	 * @return 指定月份数后的日期
	 */
	public static Date addMonths(int months) {
		return add(getNow(), months, Calendar.MONTH);
	}

	/**
	 * 取得指定日期以后若干月的日期。如果要得到以前的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
	 * @param date   指定的日期
	 * @param months   指定的月份数
	 * @return 指定日期,指定月份数后的日期.
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, months, Calendar.MONTH);
	}

	/**
	 * 定义内部方法,为指定的日期增加一些天数,月数或年数;
	 * @param date  指定的日期
	 * @param amount  增加的数量
	 * @param field  指定单位:年,月,日;Calendar.YEAR,Calendar.MONTH,Calendar.DATE;
	 * @return 增加天数,月数或年数后的新日期
	 */
	private static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数:以startDay为基准,endDay为比较,用startDay减去endDay;
	 * 前一个日期小于后一个日期,则会返回负数,则表示比较天数在起始的天数之后;
	 * @param startDay   基准日期
	 * @param endDay   比较日期
	 * @return 基准日期与比较日期之间相差的天数
	 */
	public static long diffDays(Date startDay, Date endDay) {
		final long ONE_DAY = 24 * 60 * 60 * 1000;
		return (startDay.getTime() - endDay.getTime()) / ONE_DAY;
	}

	/**
	 * 计算两个日期之间的月份差:用前一个startDay作基准,后一个endDay为比较 如果startDay比endDay小,则返回负值
	 * @param startDay   基准月份
	 * @param endDay   比较月份
	 * @return 基准月与比较月份之间的月数差
	 */
	public static long diffMonths(Date startDay, Date endDay) {
		Calendar calendar = Calendar.getInstance();
		//获取起始日期对应的calendar年和月
		calendar.setTime(startDay);
		int startYear = calendar.get(Calendar.YEAR);
		int startMonth = calendar.get(Calendar.MONTH);
		//获取结束日期对应的calendar年和月
		calendar.setTime(endDay);
		int endYear = calendar.get(Calendar.YEAR);
		int endMonth = calendar.get(Calendar.MONTH);
		return (startYear - endYear) * 12 + (startMonth - endMonth);
	}

	/**
	 * 获取指定日期对应月份最后一天的日期:根据给定日期,得到对应的下一个月第一天的日期,然后减一天
	 * @param date   给定的日期
	 * @return 对应的月份最后一天的日期
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		//获取给定的日期对应的date
		calendar.setTime(date);
		//获取给定日期对应的下一个月第一天的日期
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1, 1);
		//用获取到的下一个月第一天的日期减一天,得到前一个月最后一天的日期
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间24小时制式
	 * @return
	 */
	public static String getCurrentTime(String formatStr) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(formatStr);
		return sDateFormat.format(new java.util.Date());
	}
}
