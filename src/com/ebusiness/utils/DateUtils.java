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
	 * ȱʡ��������ʾ��ʽ�� yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * ȱʡ������ʱ����ʾ��ʽ��yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * �����ȫ��̬��������,˽�й��췽������ֹ�Ը������ʵ����
	 */
	private DateUtils() {
	}

	/**
	 * ���������ַ����ö�Ӧ�ķ�ʽ��������������
	 * @param dateStr   ��Ҫ�����������ַ���
	 * @param pattern   ������ʽ,ȱʡĬ�ϸ�ʽΪ"yyyy-MM-dd"
	 * @return �����õ�������,����nullʱ,����ʧ��
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
	 * �õ�ϵͳ��ǰ����ʱ��
	 * @return ��ǰ����ʱ��
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ����
	 * @return ��ǰ����
	 */
	public static String getDate() {
		return format(DEFAULT_DATE_FORMAT);
	}

	/**
	 * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ���ڼ�ʱ��
	 * @return ��ǰ���ڼ�ʱ��
	 */
	public static String getDateTime() {
		return format(DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * �õ�ϵͳ��ǰ���ڼ�ʱ�䣬����ָ���ķ�ʽ��ʽ��
	 * @param pattern  ��ʾ��ʽ
	 * @return ��ǰ���ڼ�ʱ��
	 */
	public static String format(String pattern) {
		Date datetime = Calendar.getInstance().getTime();
		return format(datetime, pattern);
	}

	/**
	 * �õ�ָ����ʽ�ĸ�ʽ������
	 * @param date  ��Ҫ����ʽ��������
	 * @param pattern   ��ʽ����ʽ
	 * @return ��ʽ����������ַ���
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
	 * �õ���ǰ���
	 * @return ��ǰ���
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * �õ���ǰ�·�
	 * @return ��ǰ�·�
	 */
	public static int getCurrentMonth() {
		//ͨ��get( int field)�����õ���month��ʵ�ʵ�month��1,����������1;
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * �õ���ǰ��:��ǰ��ĳ�µĵڼ���;
	 * @return ��ǰ��
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * �õ���ǰ���ں���days��������
	 * @param days ����
	 * @return days��������
	 */
	public static Date addDays(int days) {
		return add(getNow(), days, Calendar.DATE);
	}

	/**
	 * ȡ��ָ�������Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø����� ����Ҫ�õ�������ͬһ������ڣ�������Ϊ-7
	 * @param date  ָ��������;
	 * @param days  ָ��������:��Ϊ����,��ָ������֮ǰ�����������;
	 * @return ָ������,ָ�������������ǰ������;
	 */
	public static Date addDays(Date date, int days) {
		return add(date, days, Calendar.DATE);
	}

	/**
	 * ��ȡ��ǰ�·ݺ�months�µ�����.���Ҫ�õ���ǰ������,�����ø���.
	 * @param months  �·���
	 * @return ָ���·����������
	 */
	public static Date addMonths(int months) {
		return add(getNow(), months, Calendar.MONTH);
	}

	/**
	 * ȡ��ָ�������Ժ������µ����ڡ����Ҫ�õ���ǰ�����ڣ������ø����� ע�⣬���ܲ���ͬһ���ӣ�����2003-1-31����һ������2003-2-28
	 * @param date   ָ��������
	 * @param months   ָ�����·���
	 * @return ָ������,ָ���·����������.
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, months, Calendar.MONTH);
	}

	/**
	 * �����ڲ�����,Ϊָ������������һЩ����,����������;
	 * @param date  ָ��������
	 * @param amount  ���ӵ�����
	 * @param field  ָ����λ:��,��,��;Calendar.YEAR,Calendar.MONTH,Calendar.DATE;
	 * @return ��������,�������������������
	 */
	private static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * ������������֮����������:��startDayΪ��׼,endDayΪ�Ƚ�,��startDay��ȥendDay;
	 * ǰһ������С�ں�һ������,��᷵�ظ���,���ʾ�Ƚ���������ʼ������֮��;
	 * @param startDay   ��׼����
	 * @param endDay   �Ƚ�����
	 * @return ��׼������Ƚ�����֮����������
	 */
	public static long diffDays(Date startDay, Date endDay) {
		final long ONE_DAY = 24 * 60 * 60 * 1000;
		return (startDay.getTime() - endDay.getTime()) / ONE_DAY;
	}

	/**
	 * ������������֮����·ݲ�:��ǰһ��startDay����׼,��һ��endDayΪ�Ƚ� ���startDay��endDayС,�򷵻ظ�ֵ
	 * @param startDay   ��׼�·�
	 * @param endDay   �Ƚ��·�
	 * @return ��׼����Ƚ��·�֮���������
	 */
	public static long diffMonths(Date startDay, Date endDay) {
		Calendar calendar = Calendar.getInstance();
		//��ȡ��ʼ���ڶ�Ӧ��calendar�����
		calendar.setTime(startDay);
		int startYear = calendar.get(Calendar.YEAR);
		int startMonth = calendar.get(Calendar.MONTH);
		//��ȡ�������ڶ�Ӧ��calendar�����
		calendar.setTime(endDay);
		int endYear = calendar.get(Calendar.YEAR);
		int endMonth = calendar.get(Calendar.MONTH);
		return (startYear - endYear) * 12 + (startMonth - endMonth);
	}

	/**
	 * ��ȡָ�����ڶ�Ӧ�·����һ�������:���ݸ�������,�õ���Ӧ����һ���µ�һ�������,Ȼ���һ��
	 * @param date   ����������
	 * @return ��Ӧ���·����һ�������
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		//��ȡ���������ڶ�Ӧ��date
		calendar.setTime(date);
		//��ȡ�������ڶ�Ӧ����һ���µ�һ�������
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1, 1);
		//�û�ȡ������һ���µ�һ������ڼ�һ��,�õ�ǰһ�������һ�������
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * ��ȡ��ǰʱ��24Сʱ��ʽ
	 * @return
	 */
	public static String getCurrentTime(String formatStr) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(formatStr);
		return sDateFormat.format(new java.util.Date());
	}
}
