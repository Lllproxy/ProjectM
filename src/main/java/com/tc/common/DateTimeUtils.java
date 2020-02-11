package com.tc.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * 
 * @author lxd
 *
 */
public class DateTimeUtils {

	/**
	 * 判断是否是周末
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算某年的2月是多少天
	 * 
	 * @param year
	 * @return
	 */
	public static int judgeDay(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, 2, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某年全部的天
	 * 
	 * @param year
	 *            年
	 * @param format
	 *            格式
	 * @return
	 */
	public static List<String> days(int year, String format) {

		List<String> list = new ArrayList<String>();
		SimpleDateFormat f = new SimpleDateFormat(format);

		int month = 0;
		Calendar c = Calendar.getInstance();
		for (int i = 1; i <= 12; i++) {
			if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10
					|| i == 12)
				month = 31;
			else if (i == 4 || i == 6 || i == 9 || i == 11)
				month = 30;
			else
				month = judgeDay(year);

			for (int j = 1; j <= month; j++) {
				c.set(year, i - 1, j);
				list.add(f.format(c.getTime()));
			}
			month = 0;
		}
		return list;
	}

	/**
	 * 获取某年全部的天
	 * 
	 * @param year
	 *            年
	 * @param format
	 *            格式
	 * @return
	 */
	public static List<Date> days(int year) {

		List<Date> list = new ArrayList<Date>();
		int month = 0;
		Calendar c = Calendar.getInstance();
		for (int i = 1; i <= 12; i++) {
			if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10
					|| i == 12)
				month = 31;
			else if (i == 4 || i == 6 || i == 9 || i == 11)
				month = 30;
			else
				month = judgeDay(year);

			for (int j = 1; j <= month; j++) {
				c.set(year, i - 1, j);
				list.add(c.getTime());
			}
			month = 0;
		}
		return list;
	}

	/**
	 * 获取某年某月的全部天
	 * 
	 * @param year
	 * @param month
	 * @param format
	 * @return
	 */
	public static List<String> days(int year, int month, String format) {

		List<String> list = new ArrayList<String>();
		SimpleDateFormat f = new SimpleDateFormat(format);

		Calendar c = Calendar.getInstance();
		int days = 0;

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12)
			days = 31;
		else if (month == 4 || month == 6 || month == 9 || month == 11)
			days = 30;
		else
			days = judgeDay(year);

		for (int i = 1; i <= days; i++) {
			c.set(year, month, i);
			list.add(f.format(c.getTime()));
		}
		return list;
	}
 	 

	/**
	 * 计算两个日期之前相差几个月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String date1, String date2)
			throws ParseException {
		int n = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(df.parse(date1));
		c2.setTime(df.parse(date2));
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			n++;
			c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
		}
		return n;
	}

	/**
	 * 计算两个日期之前的月份
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween(String date1, String date2)
			throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(date1));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(date2));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 得到两个日期之间的天
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDayBetween(String minDate, String maxDate)
			throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));

		max.setTime(sdf.parse(maxDate));

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.DATE, 1);
		}

		return result;
	}

	/***
	 * 获取上一周的时间
	 * 
	 * @param format
	 *            格式化字符串
	 * @return
	 */
	public static String getBeforeWeek(String format) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/***
	 * 获取上一个月的时间
	 * 
	 * @param format
	 *            格式化字符串
	 * @return
	 */
	public static String getBeforeMonth(String format) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/***
	 * 获取上一周的时间
	 * 
	 * @return Date date
	 */
	public static Date getBeforeWeek() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		date = calendar.getTime();
		return date;
	}
	
	/***
	 * 获取上一个月的时间
	 * 
	 * @param format
	 *            格式化字符串
	 * @return
	 */
	public static Date getBeforeMonth() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);

        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                      calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                     calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的前一周最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfWeek(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.WEEK_OF_YEAR) - 1);
    }

    /**
     * 返回指定日期的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的上个月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH) - 1, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的季的第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFirstDayOfQuarter(calendar.get(Calendar.YEAR),
                                    getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的季的第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 1 - 1;
        } else if (quarter == 2) {
            month = 4 - 1;
        } else if (quarter == 3) {
            month = 7 - 1;
        } else if (quarter == 4) {
            month = 10 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfQuarter(calendar.get(Calendar.YEAR),
                                   getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 3 - 1;
        } else if (quarter == 2) {
            month = 6 - 1;
        } else if (quarter == 3) {
            month = 9 - 1;
        } else if (quarter == 4) {
            month = 12 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的上一季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfLastQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR),
                                       getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的上一季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 12 - 1;
        } else if (quarter == 2) {
            month = 3 - 1;
        } else if (quarter == 3) {
            month = 6 - 1;
        } else if (quarter == 4) {
            month = 9 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季度
     *
     * @param date
     * @return
     */
    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }

    /**
	 * 获取当前星期
	 */
	public static int getWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(new Date());
		return calendar.get(Calendar.DAY_OF_WEEK);
	   
	}
	
	/**
	 * 获取前几分钟时间
	 * @param minute
	 */
	public static String getBeforeTime(int minute){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, - minute);
		return df.format(calendar.getTime());
	}

	/**
	 * 获取系统时间
	 * 格式：自定义，默认yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime(String format){
		if(format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
	
	/**
	 * 日期时间转换成豪秒
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static long dateToStamp(String dateTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateTime);
        return date.getTime();
    }
	
	/**
	 * 日期时间转换成豪秒
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String versionToStrDate(long version) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(version));
    }
	
	/**
	 * 字符串类型转换成日期类型
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String strDate) throws ParseException {
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return sf.parse(strDate);
	}
	
	/**
	 * 日期类型转换成字符串类型
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dateToStr(Date date) throws ParseException {
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return sf.format(date);
	}
	
}
