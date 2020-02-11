package com.tc.common;

/**
 * 事件分类
 * 
 * @author Administrator
 *
 */
public class CategoryUtils {

	private static String[] c100 = { "101", "102", "103" };
	// private String[] c200 = {"101","102","103"};
	// private String[] c300 = {"101","102","103"};
	private static String[] c400 = { "302", "303", "304", "102302", "201302", "404302", "406302", "409302" };
	private static String[] c500 = { "501", "901", "902", "903", "904", "907" };

	/**
	 * 根据小类进行大类归类
	 * 
	 * @param eventType
	 *            小类编号
	 * @return
	 */
	public static String getCategory(String eventType) {
		if (eventType.equals("201"))
			return "200";
		if (eventType.equals("301"))
			return "300";

		for (int i = 0; i < c100.length; i++) {
			if (eventType.equals(c100[i])) {
				return "100";
			}
		}

		for (int i = 0; i < c400.length; i++) {
			if (eventType.equals(c400[i])) {
				return "400";
			}
		}
		for (int i = 0; i < c500.length; i++) {
			if (eventType.equals(c500[i])) {
				return "500";
			}
		}
		return "";
	}
}
