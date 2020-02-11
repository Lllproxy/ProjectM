package com.tc.constant;


public class DConstant {
	
	public static long REDIS_EXPIRE = 24*60*60;//redis 缓存过期时间24小时
	 
	// 路段间偏距100米
	public static double offsetDistance = 100d;
	// 1经度等于多少米
	public static double rate = 85275.23012380573d;

	// 1米等于多少经度
	public static final double PER_METER_LONGITUDE = 1.172673469831935E-5;
	// 1米等于多少纬度
	public static final double PER_METER_LATITUDE = 8.983152841195202E-6;
	// 1经度等于多少米
	public static final double PER_LONGITUDE_METER = 85275.23012380573;
	// 1纬度等于多少米
	public static final double PER_LATITUDE_METER = 111319.49079327373;

	// 路况数据
	public static final int TRAFFIC_ROAD_STATUS_DATA = 0;
	// 事件数据
	public static final int TRAFFIC_EVENT_DATA = 1;
	// 时间格式
	public static final String DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";
	// 超级用户
	public static final String SUPER_ACCOUNT_ACCESS = "MapABC_AdMiN";//"MapABC_AdMiN";
	// 超级用户密码
	public static final String SUPER_ACCOUNT_ACCESS_PWD = "C3tp57";//"C3tp57";
	
	public static final String DATA_SAVE_POOL_ID="dataSavePool";
	
	public static final String MULTILINESTRING = "MULTILINESTRING";
	
	public static final String LINESTRING = "LINESTRING";
	
	public static final String POLICE_REAL_TIME_TABLE="";
	
	public static final String REDIS_JGDATA_TJ = "REDIS_JGDATA_TJ";
	
	public static final String TP_DATA_PREFIX = "TPSERVICE_PROXY";
	
	public static final String OPLR_JSON = "json";
	public static final String OPLR_PROTOBUF = "protobuf";
}
