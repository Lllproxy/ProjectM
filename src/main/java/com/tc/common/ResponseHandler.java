package com.tc.common;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ResponseHandler {
	
	/**
	 * 返回数据
	 * @param data
	 * @param resp
	 * @param headers
	 */
	public static void writeResponseData(byte[] data,
			HttpServletResponse resp,Map<String, String> headers,String callback) {

		try {
			
			if (headers != null && headers.size()>0){
				Set<String> keys = headers.keySet();
				for (String key: keys){
					resp.addHeader(key,headers.get(key));
				}
			}
			
			if (StringUtils.isNotBlank(callback)){
				String cbdata = new String(data);
				resp.getOutputStream().write(getCallback(cbdata,callback).getBytes());
			}else{
				resp.getOutputStream().write(data);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static String getCallback(String outer,String callback){ 
		outer = callback + "(" + outer + ");"; 
		return outer;
	}

}
