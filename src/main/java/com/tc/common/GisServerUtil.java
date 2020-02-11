package com.tc.common;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GisServerUtil {
	private static Log logger = LogFactory.getLog(GisServerUtil.class);
	
	@Value("${portal.gisurl}")
	private static String gisurl;
	
	@Value("${portal.giskey}")
	private static String giskey;
	 
	
    	
	/**
	 * 逆地理编码
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
//	@Cacheable(value="myCache")
	public static String reverseGeo(String x, String y) {
		String address = "";
		try {
			if (StringUtils.isEmpty(x) || StringUtils.isEmpty(y)){
				return null;
			}
			
			if (!gisurl.endsWith("\\/")) {
				gisurl += "/";
			} 
			gisurl += "rgeo?location=" + x + "," + y + "&pois=1&ak=" + giskey;
			byte[] data = HttpUtil.forward(gisurl, "GET", null, "UTF-8", 10);

			if (data != null && data.length > 0) {
				String jsons = new String(data,"utf-8");
				JSONObject json = JSONObject.parseObject(jsons);
				JSONArray jarray = json.getJSONArray("result");

				if (jarray != null && jarray.size() > 0) {
					JSONObject jsonobj = jarray.getJSONObject(0);
					address = jsonobj.getString("formatted_address"); 
				}
			}
			logger.info("逆地理编码：("+x+","+y+")=>"+address);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return address;

	}
	
	public static String coordCvt(String x, String y) {
		String ret = "";
		try {
			if (StringUtils.isEmpty(x) || StringUtils.isEmpty(y)){
				return null;
			}
			
			String xys = x +","+y; 
			 
			if (!gisurl.endsWith("\\/")) {
				gisurl += "/";
			} 
			gisurl += "coord/convert?ak="+giskey+"&coords="+xys;

			byte[] data = HttpUtil.forward(gisurl, "GET", null, "UTF-8", 10);

			if (data != null && data.length > 0) {
				//解析高德地理编码
				JSONObject dj = JSONObject.parseObject(new String(
						data, "UTF-8"));
				JSONArray jar = dj.getJSONArray("result");
				if (jar != null && jar.size() > 0) {
					JSONObject locJson = jar.getJSONObject(0);
					String gx = locJson.getString("x");
					String gy = locJson.getString("y"); 
					ret = gx +","+gy;
				}
			}
			logger.info("原始坐标转换为高德坐标：("+x+","+y+")=>"+ret);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}
	
	public static String addressGeocoding(String address,String city) {
		String ret = "";
		long s = System.currentTimeMillis();
		try {
			if (StringUtils.isEmpty(address)){
				return null;
			}
			address = address.replaceAll(" ", "");
			address = address.replaceAll("\t", "");
			address = address.replaceAll("\r", "");
			address = address.replaceAll("\n", "");
			 
			gisurl += "?sid=1003&city="+city+"&address="+URLEncoder.encode(address, "UTF-8");

			byte[] data = HttpUtil.forward(gisurl, "GET", null, "UTF-8", 10);

			if (data != null && data.length > 0) {
				//解析高德地理编码
				JSONObject dj = JSONObject.parseObject(new String(
						data, "UTF-8"));
				JSONArray jar = dj.getJSONArray("geocodes");
				if (jar != null && jar.size() > 0) {
					JSONObject locJson = jar.getJSONObject(0);
					ret = locJson.getString("location"); 
				}
			}
			long e = System.currentTimeMillis();
			logger.info("地理编码,address:"+address+",location="+ret+",耗时:"+(e-s)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return ret;

	}
	
	/**
	 * 根据坐标查看附近POI信息
	 * @param xys 格式：x,y;x,y
	 * @return
	 */
	public static String nearByInfo(String xys) {
		String address = "";
		try { 
			if (!gisurl.endsWith("\\/")) {
				gisurl += "/";
			} 
			gisurl += "rgeo?location=" +xys + "&pois=1&ak=" + giskey;

			byte[] data = HttpUtil.forward(gisurl, "GET", null, "UTF-8", 10);

			if (data != null && data.length > 0) {
				String jsons = new String(data,"utf-8");
				
				JSONObject json = JSONObject.parseObject(jsons);
				JSONArray jarray = json.getJSONArray("result");
				JSONObject resObj = jarray.getJSONObject(0);
				JSONObject addressComponentJson = resObj.getJSONObject("addressComponent");
				String street = addressComponentJson.getString("street");
				return street;
 			}
			logger.info("逆地理编码：("+xys+")=>"+address);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return address;

	}
	 

}
