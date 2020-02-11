package com.tc.constant;

import java.util.HashMap;
import java.util.Map;

public class RoadClassMap{
	//5位道路等级到2位道路等级码映射
	public static Map<String, String> sixRC2twoRC = new HashMap<String, String>();
	//2位道路等级到5位道路等级码映射
	public static Map<String, String> twoRC2sixRC = new HashMap<String, String>();
	
	static{
		sixRC2twoRC.put("41000","0");//高速公路
		sixRC2twoRC.put("42000","1");//国道
		sixRC2twoRC.put("51000","2");//省道
		sixRC2twoRC.put("52000","3");//县道
		sixRC2twoRC.put("53000","4");//乡公路
		sixRC2twoRC.put("54000","5");//县乡内存道路
		sixRC2twoRC.put("43000","6");//主要大街、城市快速道
		sixRC2twoRC.put("44000","7");//主要道路
		sixRC2twoRC.put("45000","8");//次要道路
		sixRC2twoRC.put("47000","9"); //普通道路
		sixRC2twoRC.put("49","10"); //小路 非导航道路
	}
	
	static{
		twoRC2sixRC.put("0","41000");//高速公路 
		twoRC2sixRC.put("1","42000");//国道
		twoRC2sixRC.put("2","51000");//省道
		twoRC2sixRC.put("3","52000");//县道
		twoRC2sixRC.put("4","53000");//乡公路
		twoRC2sixRC.put("5","54000");//县乡内存道路
		twoRC2sixRC.put("6","43000");//主要大街、城市快速道
		twoRC2sixRC.put("7","44000");//主要道路
		twoRC2sixRC.put("8","45000");//次要道路
		twoRC2sixRC.put("9","47000"); //普通道路
		twoRC2sixRC.put("10","49"); //小路非导航道路 
	}
	 
}
