package com.tc.common;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
 
 

/**
 * key=value属性配置文件读取类
 * 
 * 
 */
public class PropertyReader { 

	protected Properties properties = null;

	private static Hashtable<String, Properties> proHash = new Hashtable<String, Properties>();

	/**
	 * 配置文件读取构造函数
	 * 
	 * @param propertyFile
	 *            配置文件名
	 * @throws Exception
	 */
	public PropertyReader(String propertyFile) throws Exception {
		Properties prop = proHash.get(propertyFile);
		if (prop != null) {
			properties = prop;
		} else {
			loadPropertyFile(propertyFile);
		}
	}

	/**
	 * 加载指定配置文件的属性信息
	 * 
	 * @param propertyFile
	 * @throws Exception
	 */
	private void loadPropertyFile(String propertyFile) throws Exception {
		// Get our class loader
		ClassLoader cl = getClass().getClassLoader();
		java.io.InputStream in = null;

		if (cl != null) {
			in = cl.getResourceAsStream(propertyFile);
		} else {
			in = ClassLoader.getSystemResourceAsStream(propertyFile);
		}
		if (in == null) {
			throw new Exception("Configuration file '" + propertyFile
					+ "' not found");
		} else {
			try {
				properties = new Properties();

				// Load the configuration file into the properties table
				properties.load(in);

			} finally {
				// Close the input stream
				if (in != null) {
					try {
						in.close();
					} catch (Exception ex) { 
						throw ex;
					}
				}
			}
		}
	}

	/**
	 * 根据属性获取属性值
	 * 
	 * @param propertyName
	 *            属性值
	 * @return 属性值
	 */
	public String getProperty(String propertyName) {
		String val = "";
		try {
			val = properties.getProperty(propertyName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return val;
	}

	/**
	 * 得到指定配置文件的所有属性集合
	 * 
	 * @return HashMap<key,value>,key为属性，value属性值
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, String> getAllValue() {
		HashMap<String, String> retMap = new HashMap<String, String>();
		Set sets = properties.keySet();

		for (Object key : sets) {
			String tmpKey = key.toString();
			String value = properties.getProperty(tmpKey);
			retMap.put(tmpKey, value);
		}

		return retMap;
	}

	public static void main(String[] args) {

		String name = "load.properties";
		try {
			PropertyReader pr = new PropertyReader(name);
			HashMap<String, String> map = pr.getAllValue();
			System.out.println(map.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
