package com.tc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 工具帮助类
 * 
 * @author shiguang.zhou
 * 
 */
public class Tools {

	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static Calendar calendar = Calendar.getInstance();
	public static SerializerFeature[] fastJsonFeatures = {SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect}; 
	
	/**
	 * 判断是否为高峰
	 * 
	 * 早高峰：06:30-10:00，晚高峰：16:00-20:30
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String isPeekTime(String startTime,String endTime){
		String ispeektime = "0";
		String st = startTime.split(":")[0];
		String et = endTime.split(":")[0];
		int s = Integer.parseInt(st);
		int e = Integer.parseInt(et);
		
		//早高峰：06:30-10:00，晚高峰：16:00-20:30
		st = startTime.replaceAll(":", "");
		et  = endTime.replaceAll(":", "");
		s = Integer.parseInt(st);
		e = Integer.parseInt(et);
		if (s>=630 && e <=1000){
			ispeektime = "1";
		}else if (s>=1600 && e <=2030){
			ispeektime = "1";
		}
		
		return ispeektime;
		
//		if (s>=0 && e<=6){
//			ispeektime = "0";
//		}else if (s>=6 && e<=7){
//			ispeektime = "0";
//		}else if (s>=7 && e<=9){
//			ispeektime = "1";
//		}else if (s>=9 && e<=12){
//			ispeektime = "0";
//		}else if (s>=12 && e<=14){
//			ispeektime = "0";
//		}else if (s>=14 && e<=17){
//			ispeektime = "0";
//		}else if (s>=17 && e<=19){
//			ispeektime = "1";
//		}else if (s>=19 && e<=22){
//			ispeektime = "0";
//		}else if (s>=22 && e<=24){
//			ispeektime = "0";
//		} else{
//			//早高峰：06:30-10:00，晚高峰：16:00-20:30
//			st = startTime.replaceAll(":", "");
//			et  = endTime.replaceAll(":", "");
//			s = Integer.parseInt(st);
//			e = Integer.parseInt(et);
//			if (s>=630 && e <=1000){
//				ispeektime = "1";
//			}else if (s>=1600 && e <=2030){
//				ispeektime = "1";
//			}
//		}
//		return ispeektime;
	}
	
	 public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
	        if (map == null)  
	            return null;   
	        Object obj = beanClass.newInstance();   
	        BeanUtils.populate(obj, map);   
	        return obj;  
	    }    
	      
	    
	/**
	 * GMT转时间戳
	 * @param gmtDate
	 * @return
	 */
	public static String formatGMT2Timestamp(String gmtDate){
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE,d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		Date ld=null;
		try {
			ld = sdf.parse(gmtDate);
			String ts = ld.getTime()/1000+"";
			return ts;// Tools.dateToStr(ld);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String formatGMT(String gmtDate){
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE,d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		Date ld=null;
		try {
			ld = sdf.parse(gmtDate);
//			String ts = ld.getTime()/1000+"";
			return Tools.dateToStr(ld);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static char int2char(int n){
		return (char)(n+48);
	}
	
	/**
	 * fastjson 空数据处理
	 * @param jsonObj
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String fastJsonNullFormat(JSONObject jsonObj){
		return jsonObj.toJSONString(jsonObj, fastJsonFeatures);
	}
	
	/**
	 * 验证时间格式是否正确  yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkDateFormat(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date cdate = sdf.parse(date);
		String str = sdf.format(cdate);
		if(str.equals(date))
			return true;
		else
			return false;
	}
	
	/**
	 * 正则验证时间格式是否正确  yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static boolean checkDateFormatEL(String date){
		String eL = "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]) (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		return m.matches();
	}

	/**
	 * utc时间转换为北京时间 1442160000 1490976000
	 * 
	 * @param time
	 * @param partten
	 */
	public static String formartUtcToLocal(long time, String partten) {
		long ds = time + 60*60*8 ;
		Date date = new Date();
		date.setTime(ds*1000);
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String sdate = sdf.format(date);

		return sdate;
	}

	/**
	 * 反射生成实例
	 * 
	 * @param className
	 * @return
	 */
	public static Object getClassInstance(String className) {
		if (className == null || className.trim().length() == 0) {
			return null;
		}

		Class<?> objClass = null;
		Object obj = null;
		try {
			objClass = Class.forName(className);

			obj = objClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;

	}

	/**
	 * hashmap 按值排序
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				if (o1.getValue() == null || o2.getValue() == null)
					return 0;
				if ((o1.getValue()).compareTo(o2.getValue()) < 0) {
					return 1;
				} else if ((o1.getValue()).compareTo(o2.getValue()) > 0) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 把坐标度转换成毫秒
	 * 
	 * @param xy
	 *            坐标点，经度或纬度
	 * @return 毫秒字符串
	 * @author shiguang.zhou
	 */
	public static String Du2Second(String xy) {
		double DDD = Double.parseDouble(xy);
		double ms = DDD * 60 * 60 * 1000;
		return (int) ms + "";
	}

	/**
	 * 16进制的字符转换成 byte数组
	 * 
	 * @param s
	 *            为16进制串，每个字节16进制为2位，s合法长度为偶数
	 * @return 字节数组byte[]
	 * @author shiguang.zhou
	 */
	public static byte[] fromHexString(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException(
					"fromHexString   requires   an   even   number   of   hex   characters");
		}
		byte[] b = new byte[stringLength / 2];

		for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
			int high = charToNibble(s.charAt(i));
			int low = charToNibble(s.charAt(i + 1));
			b[j] = (byte) ((high << 4) | low);
		}
		return b;
	}

	private static int charToNibble(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'f') {
			return c - 'a' + 0xa;
		} else if ('A' <= c && c <= 'F') {
			return c - 'A' + 0xa;
		} else {
			throw new IllegalArgumentException("Invalid   hex   character:   "
					+ c);
		}
	}

	/**
	 * 把byte数组转换成16进制字符
	 * 
	 * @param bs
	 *            待转换的数组
	 * @return 16进制字符串
	 * @author shiguang.zhou
	 */
	public static String bytesToHexString(byte[] bs) {
		String s = "";
		for (int i = 0; i < bs.length; i++) {
			String tmp = Integer.toHexString(bs[i] & 0xff);
			if (tmp.length() < 2) {
				tmp = "0" + tmp;
			}
			s = s + tmp;
		}
		return s;
	}

	/**
	 * 把byte转换成16进制字符
	 * 
	 * @param bs
	 *            待转换的数组
	 * @return 16进制字符串
	 * @author shiguang.zhou
	 */
	public static String byteToHexString(byte bs) {

		String s = Integer.toHexString(bs & 0xff);
		if (s.length() < 2) {
			s = "0" + s;
		}

		return s;
	}

	/**
	 * 将低自己在前转换为低字节在后数组 或将高字节在前转换为高字节在后数组
	 * 
	 * @param littleByte
	 *            待转换字节数组
	 * @return 转换后的字节数组
	 */

	public static byte[] convertBytePos(byte[] littleByte) {

		byte[] ret = new byte[littleByte.length];
		for (int i = 0; i < littleByte.length; i++) {
			ret[i] = littleByte[littleByte.length - i - 1];
		}
		return ret;
	}

	/**
	 * 整合格林威治时间到北京时间
	 * 
	 * @param time
	 *            格式：hhmmss
	 * @param date
	 *            格式：ddmmyy
	 * @return 转换后的北京时间 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String conformtime(String time, String date) {
		try {
			String hour = time.substring(0, 2);
			String min = time.substring(2, 4);
			String sec = time.substring(4, 6);
			String day = date.substring(0, 2);
			String month = date.substring(2, 4);
			String year = date.substring(4, 6);
			String result = "";
			result = "20" + year + "-" + month + "-" + day + " ";
			result += hour + ":" + min + ":" + sec;
			SimpleDateFormat simpleDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date d = simpleDate.parse(result);
			Calendar car = Calendar.getInstance();
			car.setTime(d);
			car.add(Calendar.HOUR, 8);
			Date newDate = new Date(car.getTimeInMillis());
			return simpleDate.format(newDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * bytes to int 通过移位实现
	 * 
	 * @param decBytes
	 * @return int
	 * @author shiguang.zhou
	 * @modify 将遍历次数调整为数组长度 2014.03.25 wu.wei
	 */
	public static int byte2Int(byte[] decBytes) {
		int value = 0;
		for (int i = 0; i < decBytes.length; i++) {
			int shift = (decBytes.length - 1 - i) * 8;
			value += (decBytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	/**
	 * bytes to long
	 * 
	 * @param decBytes
	 * @return long
	 * @author shiguang.zhou
	 */
	public static long byte2Long(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * 把整型转换成指定长度的十六进制的字符
	 * 
	 * @param num
	 *            整数值
	 * @param ws
	 *            转换后的长度
	 * @return 转换后的16进制字符串
	 * @author shiguang.zhou
	 */
	public static String int2Hexstring(int num, int ws) {
		String intHex = Integer.toHexString(num);
		while (intHex.length() < ws) {
			intHex = "0" + intHex;
		}
		return intHex;
	}

	/**
	 * 把整型字符串值转换为指定长度的16进制
	 * 
	 * @param num
	 *            待转换的整型串
	 * @param n
	 *            转换后的长度
	 * @return 转换后的16进制字符串
	 * @author shiguang.zhou
	 */
	public static String convertToHex(String num, int n) {
		String temp = "";
		long i = Long.parseLong(num);
		String hex = Long.toHexString(i);// .toUpperCase();
		if (hex.length() > n) {
			int off = 0;
			while (off < n) {
				temp = temp + "F";
				off++;
			}
			return temp;
		} else if (hex.length() < n) {
			while (hex.length() < n) {
				hex = "0" + hex;
			}
			temp = hex;
		} else {
			temp = hex;
		}
		return temp;
	}

	/**
	 * 数据ZLIB压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * @return 压缩后的数据
	 */
	public static byte[] compressZLib(byte[] data) {

		byte[] output = new byte[0];
		Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();

		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		compresser.end();
		return output;

	}

	/**
	 * ZLIB数据解压
	 * 
	 * @param data
	 *            待解压数据
	 * @return 解压后的数据
	 */
	public static byte[] decompressByteData(byte[] data) {

		byte[] output = new byte[0];
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		decompresser.end();
		return output;

	}

	/**
	 * BCD码转为10进制串(阿拉伯数据)
	 * 
	 * @param bytes
	 *            BCD码字节数组
	 * @return 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuilder temp = new StringBuilder(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 * 10进制串转为BCD码
	 * 
	 * @param asc
	 *            10进制串
	 * @return BCD码字节数组
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * 把字符串转换为Date
	 * 
	 * @param date
	 *            String:日期
	 * @param format
	 *            String：日期格式 Date
	 * @return java.util.Date
	 * @throws ParseException
	 */
	public static Date formatStrToDate(String date, String format)
			  {
		Date d = null;
		// 改为单例
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
				.getDateTimeInstance();
		sdf.applyPattern(format);
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf = null;
		return d;
	}

	/**
	 * 把Date转换为字符串日期
	 * 
	 * @param date
	 *            日期对象
	 * @param format
	 *            转换格式
	 * @return 转换后的日期字符串
	 */
	public static String formatDate2Str(Date date, String format) {
		String d = null;
		// 改为单例
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
				.getDateTimeInstance();
		sdf.applyPattern(format);
		d = sdf.format(date);

		return d;
	}
	

	// jiangkeping
	public static JSONArray sortJSONArray(JSONArray jsonArr, String sortKey){  
        JSONObject jObject = null;  
     for(int i = 0;i<jsonArr.size();i++){  
         long l = Long.parseLong(jsonArr.getJSONObject(i).get(sortKey).toString());  
         for(int j = i+1; j<jsonArr.size();j++){  
                 long nl = Long.parseLong(jsonArr.getJSONObject(j).get(sortKey).toString());  
                 if(l>nl){  
                     jObject = jsonArr.getJSONObject(j);  
                     jsonArr.set(j, jsonArr.getJSONObject(i));  
                     jsonArr.set(i, jObject);  
                 }  
         }  
     } 
      return jsonArr;
    } 
	
	
	
	/**
	 * 
	 * @param ja json数组
	 * @param field 要排序的key
	 * @param isAsc 是否升序
	 */

//	private static void sort(JSONArray ja,final String field, boolean isAsc){
//	Collections.sort(ja, new Comparator<JSONObject>() {
//	@Override
//	public int compare(JSONObject o1, JSONObject o2) {
//	Object f1 = o1.get(field);
//	Object f2 = o2.get(field);
//	if(f1 instanceof Number && f2 instanceof Number){
//	return ((Number)f1).intValue() - ((Number)f2).intValue();
//	}else{
//	return f1.toString().compareTo(f2.toString());
//	}
//	}
//	});
//	if(!isAsc){
//	Collections.reverse(ja);
//	}
//	}	
//
	/**
	 * 把Long转换为字符串日期
	 * 
	 * @param Long
	 *            日期对象
	 * @param format
	 *            转换格式
	 * @return 转换后的日期字符串
	 */
	public static String formatLong2Str(Long date, String format) {
		String d = null;
		// 改为单例
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
				.getDateTimeInstance();
		sdf.applyPattern(format);
		d = sdf.format(date);

		return d;
	}

	/**
	 * date convert to yyyy-MM-dd HH:mm:ss 效率比formatDate2Str高
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		calendar.setTime(date);
		StringBuilder bufDate = new StringBuilder();
		bufDate.append(calendar.get(Calendar.YEAR));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.MONTH) + 1
				+ "", 2));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.DAY_OF_MONTH)
				+ "", 2));
		bufDate.append(" ");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.HOUR_OF_DAY)
				+ "", 2));
		bufDate.append(":");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.MINUTE) + "",
				2));
		bufDate.append(":");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.SECOND) + "",
				2));
		return bufDate.toString();
	}

	/**
	 * yyyy-MM-dd HH:mm:ss covert to date 效率比formatStrToDate方法高
	 * 
	 * @param formatDate
	 * @return
	 */
	public static Date strToDate(StringBuilder formatDate) {
		int year = Integer.parseInt(formatDate.subSequence(0, 4).toString());
		int month = Integer.parseInt(formatDate.subSequence(5, 7).toString()) - 1;
		int day = Integer.parseInt(formatDate.subSequence(8, 10).toString());
		int hour = Integer.parseInt(formatDate.subSequence(11, 13).toString());
		int minute = Integer
				.parseInt(formatDate.subSequence(14, 16).toString());
		int second = Integer
				.parseInt(formatDate.subSequence(17, 19).toString());
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * date convert to yyyy-MM-dd 效率比formatDate2Str高
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrYMD(Date date) {
		calendar.setTime(date);
		StringBuilder bufDate = new StringBuilder();
		bufDate.append(calendar.get(Calendar.YEAR));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.MONTH) + 1
				+ "", 2));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.DAY_OF_MONTH)
				+ "", 2));
		return bufDate.toString();
	}

	/**
	 * date convert to yyyyMMdd-HH 效率比formatDate2Str高
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrYMDHM(Date date) {
		calendar.setTime(date);
		StringBuilder bufDate = new StringBuilder();
		bufDate.append(calendar.get(Calendar.YEAR));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.MONTH) + 1
				+ "", 2));
		bufDate.append("-");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.DAY_OF_MONTH)
				+ "", 2));
		bufDate.append(" ");
		bufDate.append(Tools.fillZeroFront(
				calendar.get(Calendar.HOUR_OF_DAY - 1) + "", 2));
		bufDate.append(":");
		bufDate.append(Tools.fillZeroFront(calendar.get(Calendar.MINUTE) + "",
				2));
		return bufDate.toString();
	}

	/**
	 * 把度转换成16进制毫秒
	 * 
	 * @param xy
	 *            坐标点经度或纬度
	 * @return 转换后的16进制毫秒值
	 * @author shiguang.zhou
	 */
	public static String Du2Mills(String xy) {
		double DDD = Double.parseDouble(xy);
		int ms = (int) (DDD * 60 * 60 * 1000);
		String hex = Integer.toHexString(ms);
		return hex.toUpperCase();
	}

	/**
	 * 获取一个n位随机数值
	 * 
	 * @param n
	 *            随机数位数
	 * 
	 * @return n位随机数
	 * 
	 * @author shiguang.zhou
	 */
	public static String getRandomNum(int n) {
		String seed = "0123456789";
		byte chs[] = seed.getBytes();
		byte bs[] = new byte[n];
		Random random = new Random();

		int length = chs.length;
		for (int i = 0; i < n; i++) {
			bs[i] = chs[random.nextInt(length)];
		}
		return new String(bs);
	}

	/**
	 * 数据包异或校验值
	 * 
	 * @param b
	 *            待校验内容
	 * @return 校验值
	 * 
	 * @author shiguang.zhou
	 */
	public static byte checkData(byte[] b) {
		byte result = b[0];
		int i = 1;
		while (i < b.length) {
			result ^= b[i];
			i++;
		}
		return result;
	}

	/**
	 * 格式化double数值
	 * 
	 * @param df
	 *            待格式化数据
	 * @param maxfracDigit
	 *            允许保留最大小数点
	 * @param minfracDigit
	 *            允许保留最小小数点
	 * @return 格式化后的数据
	 * 
	 * @author shiguang.zhou
	 */
	public static String getNumberFormatString(double df, int maxfracDigit,
			int minfracDigit) {
		String ret = "";
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMaximumFractionDigits(maxfracDigit);
		nf.setMinimumFractionDigits(minfracDigit);
		ret = nf.format(df).replaceAll("\\,", "");

		return ret;
	}

	/**
	 * 
	 * 判断一个字节每个bit位的数值
	 * 
	 * @param data
	 *            高位在前，地位在后的字节内容
	 * @param pos
	 *            字节的第几位
	 * @return 字节bit位值(为0或1)
	 * @author shiguang.zhou
	 * @modify 调整compare类型，使之可以判断4字节整形数字
	 */
	public static int getByteBit(int data, int pos) {
		int bitData = 0;

		int compare = (int) Math.pow(2, pos);

		if ((data & compare) == compare) {
			bitData = 1;
		}
		return bitData;
	}

	/**
	 * 负数补码获取到原码
	 * 
	 * @param hexString
	 *            16进制数
	 * @return 原码
	 * 
	 * @author shiguang.zhou
	 */
	public static int getValueFromCompCode(String hexString) {
		int ret = 0;
		int i = Integer.parseInt(hexString.substring(1), 16);
		int j = Integer.parseInt("fffffff", 16);
		int m = i ^ j;

		if (hexString.charAt(0) == 'F') {// 负数
			ret = -(m + 1);
		}
		return ret;

	}

	/**
	 * 16进制毫秒转换为度
	 * 
	 * @param mills
	 *            16进制毫秒
	 * @return 浮点度值
	 * 
	 * @author shiguang.zhou
	 */
	public static String fromMs2XY(String mills) {
		String ret = "";
		try {
			double ms = Integer.parseInt(mills, 16);
			double ds = ms / 1000 / 60 / 60;

			DecimalFormat format = new DecimalFormat("0.000000");
			format.setMaximumFractionDigits(6);
			ret = format.format(ds);
		} catch (Exception e) {
			ret = "0";
		}
		return ret;
	}

	/**
	 * 去除字符串前的0
	 * 
	 * @param str
	 *            待处理字符串
	 * @return 转换的字符串
	 * 
	 * @author shiguang.zhou
	 */
	public static String removeZeroStr(String str) {
		String ret = null;
		if (str != null && str.trim() != "") {
			int i = 0;
			while (i < str.length()) {
				if (str.charAt(i) != '0') {
					break;
				}
				i++;
			}
			if (i != str.length())
				ret = str.substring(i);
			else
				ret = "0";
		}

		return ret;
	}

	/**
	 * 把节转换成公里
	 * 
	 * @param knot
	 *            节
	 * @return 公里
	 * 
	 * @author shiguang.zhou
	 */
	public static String formatKnotToKm(String knot) {
		if (knot == null || knot.trim().length() <= 0) {
			return "0";
		}
		String ret = "";
		double speed = 0;
		if (knot != null) {
			try {
				speed = Double.parseDouble(knot);
			} catch (java.lang.NumberFormatException ex) {
				ex.printStackTrace();
			}
			speed = speed * 1.852;
		}
		ret = "" + speed;
		if (ret.length() > 4) {
			ret = ret.substring(0, 4);
		}
		return ret;
	}

	/**
	 * 把公里转换成节
	 * 
	 * @param km
	 *            公里
	 * @return 节
	 * 
	 * @author shiguang.zhou
	 */
	public static String formatKmToKnot(String km) {
		String knot = "";
		double dSpeed = Double.parseDouble(km);
		double hSpeed = dSpeed / 1.852;
		int iSpeed = (int) hSpeed;
		knot = "" + iSpeed;
		return knot;
	}

	/**
	 * 对字节数组进行字节累加和校验的16进制串
	 * 
	 * @param bcont
	 *            待校验的内容
	 * @return 校验和16进制字符
	 * 
	 */
	public static String getVerfyCode(byte[] bcont) {
		String ret = "";
		byte[] br = bcont;
		int sum = 0;
		for (int i = 0; i < br.length; i++) {
			sum += br[i] & 0xFF;
		}
		ret = Integer.toHexString(sum);

		return ret;
	}

	public static byte[] double2Hexstring(double num, int ws) {

		double n = num * 3600000;
		String douHex = Integer.toHexString((int) n);
		while (douHex.length() < ws) {
			douHex = "0" + douHex;
		}
		return fromHexString(douHex);
	}

	public static String HexToBinary(String hexString) {
		long l = Long.parseLong(hexString, 16);
		String binaryString = Long.toBinaryString(l);
		int shouldBinaryLen = hexString.length() * 4;
		StringBuilder addZero = new StringBuilder();
		int addZeroNum = shouldBinaryLen - binaryString.length();
		for (int i = 1; i <= addZeroNum; i++) {
			addZero.append("0");
		}
		return addZero.toString() + binaryString;

	}

	/**
	 * 十进制转二进制 IntToBinary 方法
	 * 
	 * @param
	 * @return String
	 */
	public static String IntToBinary(int intNum) {
		String binaryString = Integer.toBinaryString(intNum);
		int shouldBinaryLen = 8;// byte 八位
		StringBuilder addZero = new StringBuilder();
		int addZeroNum = shouldBinaryLen - binaryString.length();

		for (int i = 1; i <= addZeroNum; i++) {
			addZero.append("0");
		}
		return addZero.toString() + binaryString;

	}

	/**
	 * 修改指定二进制位置数字 IntToBinary 方法
	 * 
	 * @param
	 * @return String
	 */
	public static String changeBinary(String binary, int pos, int value) {
		if (binary == null || binary.length() < 8 || binary.length() < pos)
			return binary;
		pos = binary.length() - pos - 1;
		String start = binary.substring(0, pos);
		String end = binary.substring(pos + 1);
		return start + value + end;
	}

	/**
	 * 把度分格式为DDMMmmmmm的经度转换成度
	 * 
	 * @param DDMMmmmmm
	 *            纬度
	 * @return 浮点经度字符串
	 * @author shiguang.zhou
	 */
	public static String formatYtoDu(String DDMMmmmmm) {
		double xy = Double.parseDouble(DDMMmmmmm);
		if (xy == 0) {
			return "0";
		}
		String result = null;
		double DDD = Double.parseDouble(DDMMmmmmm.substring(0, 2));
		double MMmmmm = Double.parseDouble(DDMMmmmmm.substring(2,
				DDMMmmmmm.length()));
		MMmmmm = MMmmmm / 60;
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(6);
		format.setMinimumFractionDigits(6);
		result = format.format(DDD + MMmmmm).replaceAll(",", "");
		return result;
	}

	/**
	 * 把格式为DDDMMmmmmm的度分经度转换成度
	 * 
	 * @param DDDMMmmmmm
	 *            经度
	 * @return 浮点经度字符串
	 * @author shiguang.zhou
	 */
	public static String formatXtoDu(String DDDMMmmmmm) {
		double xy = Double.parseDouble(DDDMMmmmmm);
		if (xy == 0) {
			return "0";
		}
		String result = null;
		double DDD = Double.parseDouble(DDDMMmmmmm.substring(0, 3));
		double MMmmmm = Double.parseDouble(DDDMMmmmmm.substring(3,
				DDDMMmmmmm.length()));
		MMmmmm = MMmmmm / 60;
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(6);
		format.setMinimumFractionDigits(6);
		result = format.format(DDD + MMmmmm).replaceAll(",", "");
		return result;
	}

	/**
	 * 字符串前补0
	 * 
	 * @param str
	 *            待补0字符串
	 * @param i
	 *            补0的个数
	 * @return 补0后的字符串
	 * 
	 */
	public static String fillZeroFront(String str, int i) {
		while (str.length() < i) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 字符串后补0
	 * 
	 * @param str
	 *            待补0字符串
	 * @param i
	 *            补0的个数
	 * @return 补0后的字符串
	 * 
	 */
	public static String fillZeroBack(String str, int i) {
		while (str.length() < i) {
			str = str + "0";
		}
		return str;
	}

	/**
	 * 生成随即密码,由数字、大小写字母随机组成
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String getRandomString(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 48;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };

		StringBuilder pwd = new StringBuilder("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	final static int BUFFER_SIZE = 4096;

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), encoding); // "ISO-8859-1"
	}

	/**
	 * telnet目标主机ip和端口，可连接返回true，无连接返回false
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public static boolean telnet(String ip, int port) {
		boolean isAlive = false;

		try {

			Socket socket = new Socket(ip, port);

			if (socket.isConnected()) {
				isAlive = true;
				socket.close();
			} else {
				isAlive = false;
			}

		} catch (SocketException e) {
			isAlive = false;
		} catch (IOException e) {
			isAlive = false;
		}

		return isAlive;
	}

	/**
	 * 判断是否是本地IP
	 * 
	 * @return
	 */
	public static boolean isLocalIpAddres(String configIpPort) {
		String gaterIpPort = new String();
		int flag = 0;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						gaterIpPort = gaterIpPort
								+ inetAddress.getHostAddress().toString() + ",";
						flag++;
					}

				}
			}
			if (flag > 1) {
				if (gaterIpPort.indexOf(configIpPort) < 0) {
					return false;
				}

			}

		} catch (Exception e) {
		}
		return true;
	}

	/**
	 * 转义
	 * 
	 * @param str
	 * @return
	 */
	public static String convert(String str) {
		if (null == str || str.equals("") || str.equals("null")) {
			return "";
		}
		str = str.replace("&", "&amp;");
		str = str.replace("$", "&dol;");
		str = str.replace(",", "&cma;");
		str = str.replace("(", "&lps;");
		str = str.replace(")", "&rps;");
		str = str.replace("#", "&num;");
		return str;
	}

	/**
	 * 反转义
	 * 
	 * @param str
	 * @return
	 */
	public static String disConvert(String str) {
		if (null == str || str.equals("") || str.equals("null")) {
			return "";
		}
		str = str.replace("&amp;", "&");
		str = str.replace("&dol;", "$");
		str = str.replace("&cma;", ",");
		str = str.replace("&lps;", "(");
		str = str.replace("&rps;", ")");
		str = str.replace("&num;", "#");
		return str;
	}

	public static int getHour(int flag) {
		Calendar cal = Calendar.getInstance();
		int i = cal.get(Calendar.HOUR_OF_DAY);
		return i / flag;
	}

	/**
	 * 坐标点是否在中国境内
	 * 
	 * @return false
	 */
	public static boolean isInChinaPoint(double tmpx, double tmpy) {
		float maxX = 135.041666F;
		float minX = 73.666666f;
		float maxY = 53.55f;
		float minY = 3.866666F;
		if (tmpx > maxX || tmpx < minX || tmpy > maxY || tmpy < minY) {
			return false;
		}
		return true;
	}

	/**
	 * 特殊字符转换
	 * 
	 * @param str
	 * @return
	 */
	public static StringBuilder convert(StringBuilder str) {
		if (null == str || str.equals("")) {
			return new StringBuilder();
		}
		while (str.indexOf("&num;") != -1)
			str = str.replace(str.indexOf("&num;"), str.indexOf("&num;") + 5,
					"#");
		while (str.indexOf("&dol;") != -1)
			str = str.replace(str.indexOf("&dol;"), str.indexOf("&dol;") + 5,
					"$");
		while (str.indexOf("&cma;") != -1)
			str = str.replace(str.indexOf("&cma;"), str.indexOf("&cma;") + 5,
					",");
		while (str.indexOf("&lps;") != -1)
			str = str.replace(str.indexOf("&lps;"), str.indexOf("&lps;") + 5,
					"(");
		while (str.indexOf("&rps;") != -1)
			str = str.replace(str.indexOf("&rps;"), str.indexOf("&rps;") + 5,
					")");
		while (str.indexOf("&amp;") != -1)
			str = str.replace(str.indexOf("&amp;"), str.indexOf("&amp;") + 5,
					"#");
		while (str.indexOf("/u007c") != -1)
			str = str.replace(str.indexOf("/u007c"), str.indexOf("/u007c") + 6,
					"|");

		return str;
	}

	/**
	 * 判断当前操作是否Windows.
	 * 
	 * @return true---是Windows操作系统
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
 

	/**
	 * MD5计算
	 * 
	 * @param content
	 * @param charset
	 * @return
	 */
	public static String getMd5(String content, String charset) {
		byte[] bcont = null;
		if (charset == null || "".equals(charset)) {
			return null;
		}
		try {
			bcont = content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}

		String mysign = DigestUtils.md5Hex(bcont);

		return mysign;
	}

	/**
	 * gzip压缩数据
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] gunzipData(byte[] data) throws IOException {
		GZIPInputStream gin = null;
		byte[] undata = null;
		try{
			gin = new GZIPInputStream(
					new ByteArrayInputStream(data));
			ByteArrayOutputStream tout = new ByteArrayOutputStream();
			byte[] buffer = new byte[256];
			int n = -1;
			while ((n = gin.read(buffer)) >= 0) {
				tout.write(buffer, 0, n);
			}
			undata = tout.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
 		}finally{
 			if (gin != null){
 				gin.close();
 			}
 		}
		return undata;
	}

	/**
	 * gzip压缩数据
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] gzipData(byte[] data) throws IOException {
		
		GZIPOutputStream gos = null;
		byte[] gzdata = null;
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
	
			ByteArrayOutputStream gzipout = new ByteArrayOutputStream();
			  gos = new GZIPOutputStream(gzipout);
			int len = 0;
			byte[] buf = new byte[1024];
			// 从in中读数据后经压缩流压缩后写入out流
			while ((len = bais.read(buf)) != -1) {
				gos.write(buf, 0, len);
			}
	
			gos.finish();
			gos.flush();
			gzdata = gzipout.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (gos != null)
				gos.close();
		}
		
		return gzdata;
		  
	}
 
	
	// long类型转成byte数组
	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}
	
	/**
	 * 路段唯一ID
	 * 
	 * @param meshId
	 * @param roadId
	 * @return
	 */
	public static String getId(String meshId, String roadId) {
		String ret = "";
		if (meshId != null) {
			ret = meshId + "_" + roadId;
		}
		return ret;
	}
	 
	
	/** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Date date) {  
  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }  
    
  //全角转半角
  	public static String ToDBC(String input) {
  		if (StringUtils.isEmpty(input))
  			return null;
  		
          char c[] = input.toCharArray();
          for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
              c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
              c[i] = (char) (c[i] - 65248);

            }
          }
         String returnString = new String(c);
     
          return returnString;
  }
    
    

	public static void main(String[] args) throws ParseException { }

}
