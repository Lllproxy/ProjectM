package com.tc.common;

import java.util.HashMap;
import java.util.Map;

public class CrossDirDesc {
	
	private static Map<String, String> dirMap = new HashMap<String,String>();
	private static Map<String, String> relMap = new HashMap<String,String>();
	
	static{
		dirMap.put("1", "北向");
		dirMap.put("2", "东北向");
		dirMap.put("3", "东向");
		dirMap.put("4", "东南向");
		dirMap.put("5", "南向");
		dirMap.put("6", "西南向");
		dirMap.put("7", "西向"); 
		dirMap.put("8", "西北向"); 
		
		relMap.put("1", "双向通行");
		relMap.put("2", "进口");
		relMap.put("3", "出口");
		relMap.put("4", "禁止通行");
		relMap.put("0", "路口内边");
	}
	
 	 
	public static String getCrossDriDesc(String dirAngle,String relation){
		
		return dirMap.get(dirAngle)+relMap.get(relation);
		
	}

}
