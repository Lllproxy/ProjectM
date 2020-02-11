package com.tc.common;

public class NumChange {
	public static void main(String[] args) {
		String id = getId(10);
		System.out.println(id);
	}

	//将少于六位数的拼接'0'数字转为六位数字
	public static String getId(int a){
		String b = a+"";
		int length = b.length();
		if (length < 6) {
			for (int i = 0; i < 6-length; i++) {
				b = "0"+b;
			}
		}
		return b;
	}
}
