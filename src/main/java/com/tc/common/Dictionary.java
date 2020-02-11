package com.tc.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据字典映射
 * 
 * @author hfx
 *
 * @datetime 2017-12-7 上午10:14:20
 *
 * @version 1.0
 */
public class Dictionary {
	/**
	 * 转向归一
	 * @param turntype
	 * @return
	 */
	public static String convertTurntype(String turntype){
		//左、直左、左掉头、直行加左转、左转加掉头归为左
		if (StringUtils.equals(turntype, "1") 
				|| StringUtils.equals(turntype, "4")  
				|| StringUtils.equals(turntype, "5")
				||StringUtils.equals(turntype, "10") ){
			turntype = "l";
		}
		//直行、直行加右转、左转加直行加右转归为直行
		if (StringUtils.equals(turntype, "2") 
				|| StringUtils.equals(turntype, "3")  
				|| StringUtils.equals(turntype, "6")
				||StringUtils.equals(turntype, "15") ){
			turntype = "s";
		}
		return turntype;
	}

	/**
     * 图盟转向类型：1.左转、2.右转、3.直行、4.左掉头
     * 阿里转向类型：l.左转、r.右转、s.直行、u.左掉头
     * @param val
     * @return
     */
    public static String turnType(String val) {
    	
    	switch (val) {
		case "1":
			return "l";
		case "2":
			return "r";
		case "3":
			return "s";
		case "4":
			return "u";
		default:
			return val;
		}
    }
    
    public static String getDirName(String dirtype) {
		String dirname = ""; 
		switch (dirtype) {
		case "1":
			dirname = "北";
			break;
		case "2":
			dirname = "东北";
			break;
		case "3":
			dirname = "东";
			break;
		case "4":
			dirname = "东南";
			break;
		case "5":
			dirname = "南";
			break;
		case "6":
			dirname = "西南";
			break;
		case "7":
			dirname = "西";
			break;
		case "8":
			dirname = "西北";
			break;

		default:
			break;
		}
		return dirname;
	}
    /**
     * l：左 r:右 s:直 u：掉头
     * @param turntype
     * @return
     */
	public static String getTurnName(String turntype) {
		String turnname = "";
		switch (turntype) {
		case "l":
			turnname = "左";
			break;
		case "r":
			turnname = "右";
			break;
		case "s":
			turnname = "直";
			break;
		case "u":
			turnname = "掉头";
			break;
		default:
			break;
		}
		return turnname;
	}
	/*
	 * 早高峰：06:30-10:00，晚高峰：16:00-20:30
	 */
	public static String getSectimeName(String startTime,String endTime){
		String secname = "平峰";
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
			secname = "早高峰";
		}else if (s>=1600 && e <=2030){
			secname = "晚高峰";
		}
		
//		if (s>=0 && e<=6){
//			secname = "低峰";
//		}else if (s>=6 && e<=7){
//			secname = "早平峰";
//		}else if (s>=7 && e<=9){
//			secname = "早高峰";
//		}else if (s>=9 && e<=12){
//			secname = "平峰";
//		}else if (s>=12 && e<=14){
//			secname = "次平峰";
//		}else if (s>=14 && e<=17){
//			secname = "平峰";
//		}else if (s>=17 && e<=19){
//			secname = "晚高峰";
//		}else if (s>=19 && e<=22){
//			secname = "次平峰";
//		}else if (s>=22 && e<=24){
//			secname = "低峰";
//		}else{
//			//早高峰：06:30-10:00，晚高峰：16:00-20:30
//			st = startTime.replaceAll(":", "");
//			et  = endTime.replaceAll(":", "");
//			s = Integer.parseInt(st);
//			e = Integer.parseInt(et);
//			if (s>=630 && e <=1000){
//				secname = "早高峰";
//			}else if (s>=1600 && e <=2030){
//				secname = "晚高峰";
//			}
//		}
		
		return secname;
	}
	/**
	 * 步阶名称
	 * @param stepNo
	 * @return
	 */
	public static String getStepName(String stepNo) {
		String stepName= "";
		switch (stepNo) {
		case "1":
			stepName = "绿灯时间";
			break;
		case "2":
			stepName = "行人绿闪";
			break;
		case "3":
			stepName = "机动车绿闪";
			break;
		case "4":
			stepName = "黄灯时间";
			break;
		case "5":
			stepName = "全红";
			break;
		default:
			break;
		}
		return stepName;
	}

	 
	/**
	 * 信号机运行模式 2手动相位锁定 3手动全红 4手动黄闪 8正常计划
	 * @param num
	 * @return
	 */
	public static String getSignalRunModelDesc(String num) {
		String name= "";
		switch (num) {
		case "2":
			name = "手动锁定";
			break;
		case "3":
			name = "手动全红";
			break;
		case "4":
			name = "手动黄闪";
			break;
		case "8":
			name = "正常计划";
			break; 
		default:
			break;
		}
		return name;
	}
}
