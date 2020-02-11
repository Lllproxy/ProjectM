package com.tc.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * User: ChengLi Date: 12-9-13 Time: 上午10:20 HTTP请求
 */
public class HttpUtil {

	static Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * post data to destUrl
	 * 
	 * @param destUrl
	 *            目标地址
	 * @param postData
	 *            发送的数据
	 * @return 目标地址回馈的内容
	 */

	public static byte[] getPostURLData(String destUrl, byte[] postData) {
		byte[] btemp = null;
		URL url = null;
		URLConnection urlConn = null;
		DataOutputStream printout = null;
		DataInputStream input = null;
		java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
		try {
			url = new URL(destUrl);
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content=length", String.valueOf(postData.length));
			printout = new DataOutputStream(urlConn.getOutputStream());
			printout.write(postData);
			printout.flush();
			printout.close();
			logger.info("post 数据长度：" + postData.length);
			input = new DataInputStream(urlConn.getInputStream());
			byte[] bufferByte = new byte[256];
			int l = -1;
			while ((l = input.read(bufferByte)) > -1) {
				bout.write(bufferByte, 0, l);
				bout.flush();
			}
			btemp = bout.toByteArray();
			logger.info("post 回传数据长度：" + btemp.length);
			bout.close();
			input.close();
		} catch (Exception ex) {
			logger.error("post data error:", ex);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (bout != null) {
					bout.close();
				}
				if (printout != null) {
					printout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return btemp;
	}

	/**
	 * @param urlString 请求地址
	 * @param method POST OR GET
	 * @param parameters 参数
	 * @param propertys
	 * @return
	 * @throws java.io.IOException
	 */
	public static String send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
		
		HttpURLConnection urlConnection = null;
		String encode = parameters.get("encode");
		encode = StringUtils.isNotEmpty(encode) ? encode : "utf-8";
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=")
						.append(URLEncoder.encode(parameters.get(key), encode));
				i++;
			}
			urlString += param;
		}
		String timeout = parameters.get("timeout");
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setReadTimeout(null == timeout ? 10000 : Integer.valueOf(timeout));
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=")
						.append(URLEncoder.encode(parameters.get(key), encode));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
		if (logger.isDebugEnabled())
			logger.info("----------send http request,urlString="+urlString);
		InputStream in = urlConnection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int l = 0;
		while ((l = in.read(buffer)) > 0) {
			baos.write(buffer, 0, l);
		}
		baos.flush();
		String response = new String(baos.toByteArray(), encode);
		if (logger.isDebugEnabled())
			logger.info("----------http  end response:" + response);
		return response;
	}

	public static byte[] forward(String urlStr, String method, String content, String encoding, int timeOut) {
		HttpURLConnection conn = null;
		byte[] btemp = null;
		OutputStreamWriter out = null;
		InputStream input = null;
		java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method.toUpperCase());
			conn.setConnectTimeout(timeOut * 1000);
			conn.setReadTimeout(timeOut * 1000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			if (conn.getRequestMethod() != null
					&& conn.getRequestMethod().equalsIgnoreCase("get")) {
			} else {
				out = new OutputStreamWriter(conn.getOutputStream(), encoding);
				out.write(content);
				out.close();
			}

			input = conn.getInputStream();
			byte[] bufferByte = new byte[256];
			int l = -1;
			while ((l = input.read(bufferByte)) > -1) {
				bout.write(bufferByte, 0, l);
				bout.flush();
			}
			btemp = bout.toByteArray();
			conn.getResponseCode();
			conn.getResponseMessage();
			conn.getHeaderFields();
			bout.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (out != null)
					out.close();
				if (conn != null)
					conn.disconnect();
			} catch (IOException e) {
			}
		}
		return btemp;
	}
	
	/**
	 * post请求
	 */
	public static String post(String requestMethod, String url, JSONObject params) {
		 
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
        try {
            String strMessage = "";
            URL uploadServlet = new URL(url);
            HttpURLConnection servletConnection = (HttpURLConnection) uploadServlet.openConnection();
            // 设置连接参数
            servletConnection.setRequestMethod(requestMethod);
            servletConnection.setDoOutput(true);
            servletConnection.setDoInput(true);
            servletConnection.setAllowUserInteraction(true);
            servletConnection.setRequestProperty("content-type", "application/json;charset=UTF-8");
            OutputStream output = servletConnection.getOutputStream();
            if(params != null) {
            	output.write(params.toString().getBytes());
            }
            output.flush();
            output.close();
            // 获取返回的数据
            InputStream inputStream = servletConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strMessage = reader.readLine()) != null) {
                buffer.append(strMessage);
            }
            return buffer.toString();
        } catch (java.net.ConnectException e) {
            try {
                throw new Exception();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
