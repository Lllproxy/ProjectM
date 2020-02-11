package com.tc.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int status;
    private String msg;
    private Object data;

    public Result setCode(ResultCode resultCode) {
        this.status = resultCode.status;
        return this;
    }
 
    

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() { 
        return JSON.toJSONString(this);
    }


	public int getStatus() {
		return status;
	}


	public Result setStatus(int status) {
		this.status = status;
		return this;
	}



	public String getMsg() {
		return msg;
	}



	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
