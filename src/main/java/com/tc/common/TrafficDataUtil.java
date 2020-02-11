package com.tc.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

public class TrafficDataUtil {
	private static Log logger = LogFactory.getLog(TrafficDataUtil.class);
    
	public static byte[] fetchTpDataByFtpServer(String host, String port,
			String username, String password, String filePath, String fileName) {
		byte[] bs = null;
		try {
			logger.info("从FTP服务下载文件：host=" + host + ",port=" + port
					+ ",filePath=" + filePath + ",fileName=" + fileName);
			bs = FtpUtil.downloadFileStream(host, Integer.parseInt(port),
					username, password, filePath, fileName);

			if (StringUtils.endsWithIgnoreCase(fileName, "gz")) {
				if (bs != null && bs.length > 0) {
					bs = Tools.gunzipData(bs);

					logger.info("Finished Download ,FTP 读取数据解压后长度："
							+ bs.length);
				}
			}

		} catch (Exception e) {
			logger.error("读取ftp远程restdata数据异常" + ",fileName=" + fileName, e);
		}
		return bs;
	}

	/**
	 * 本地路径中获取tpservice data
	 * 
	 * @param filePath
	 * @param fileName
	 */
	public static byte[] fetchTpDataByLocal(String filePath, String fileName) {

		long s = System.currentTimeMillis();

		logger.info("filePath:" + filePath + ",fileName:" + fileName);
		String path = filePath;
		if (!filePath.endsWith("/"))
			path += "/";
		path += fileName;

		File file = new File(path);
		if (!file.exists()) {
			logger.info("读取的本地文件不存在,path:" + path);
			return null;
		}
		FileInputStream fs = null;
		ByteArrayOutputStream out = null;
		try {
			fs = new FileInputStream(file);
			out = inStreamToOutStream(fs);

			byte[] bs = out.toByteArray();

			if (StringUtils.endsWithIgnoreCase(fileName, "gz")) {
				if (bs != null && bs.length > 0)
					bs = Tools.gunzipData(bs);
			}

			return bs;
		} catch (Exception e) {
			logger.error("读取本地tpdata数据异常,fileName=" + fileName, e);
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long e = System.currentTimeMillis();
		logger.info(" 读取本地路况数据并更新耗时：" + (e - s) + "ms");
		return null;
	}
	
	public static ByteArrayOutputStream inStreamToOutStream(InputStream input)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 拿出获取到的数据
		byte[] bufferByte = new byte[256];
		int l = -1;

		while ((l = input.read(bufferByte)) > -1) {
			out.write(bufferByte, 0, l);
			out.flush();
		}
		return out;
	}
    	 
}
