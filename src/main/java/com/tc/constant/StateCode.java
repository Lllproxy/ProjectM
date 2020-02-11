package com.tc.constant;

public enum StateCode {
	
	SUCCESS(1, "OK"),
	FAIL(10002, "FAIL");

	private int code;
	private String desc_zh;

	private StateCode(int code, String desc_zh) {
		this.code = code;

		this.desc_zh = desc_zh;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc_zh() {
		return this.desc_zh;
	}

	public void setDesc_zh(String desc_zh) {
		this.desc_zh = desc_zh;
	}

}