package com.tc.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	private static Log logger = LogFactory.getLog(FtpUtil.class);
	private static String encoding = System.getProperty("file.encoding");
	private static int defaultTimeoutSecond = 60;
	private static int connectTimeoutSecond = 60;
	private static int dataTimeoutSecond = 60;
	
	/**
	 * 上传文件（可供Action/Controller层使用）
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param fileName
	 *            上传到FTP服务器后的文件名称
	 * @param inputStream
	 *            输入文件流
	 * @return
	 */
	public static boolean uploadFile(String hostname, int port,
			String username, String password, String pathname, String fileName,
			InputStream inputStream,String passiveMode) {

		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding(encoding);
		ftpClient.setControlEncoding(encoding);
		ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
		ftpClient.setConnectTimeout(connectTimeoutSecond * 1000);
		ftpClient.setDataTimeout(dataTimeoutSecond * 1000);
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 是否成功登录FTP服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				return flag;
			}
			 
			if (StringUtils.equals(passiveMode, "true"))
				ftpClient.enterLocalPassiveMode();
			
			boolean result = false;
			// 转移工作目录至指定目录下
			boolean change = ftpClient.changeWorkingDirectory(new String(pathname.getBytes(encoding), "iso-8859-1"));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (change) {
				result = ftpClient.storeFile(
						new String(fileName.getBytes(encoding), "iso-8859-1"),
						inputStream); 
				flag = result;
			}
			inputStream.close();
			ftpClient.logout();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传到ftp服务器异常", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					IOUtils.closeQuietly(inputStream);
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 上传文件（可对文件进行重命名）
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器后的文件名称
	 * @param originfilename
	 *            待上传文件的名称（绝对地址）
	 * @return
	 */
	public static boolean uploadFileFromProduction(String hostname, int port,
			String username, String password, String pathname, String filename,
			String originfilename,String passiveMode) {
		boolean flag = false;
		try {
			InputStream inputStream = new FileInputStream(new File(
					originfilename));
			flag = uploadFile(hostname, port, username, password, pathname,
					filename, inputStream,passiveMode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 上传文件（不可以进行文件的重命名操作）
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param originfilename
	 *            待上传文件的名称（绝对地址）
	 * @return
	 */
	public static boolean uploadFileFromProduction(String hostname, int port,
			String username, String password, String pathname,
			String originfilename,String passiveMode) {
		boolean flag = false;
		try {
			String fileName = new File(originfilename).getName();
			InputStream inputStream = new FileInputStream(new File(
					originfilename));
			flag = uploadFile(hostname, port, username, password, pathname,
					fileName, inputStream,passiveMode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param filename
	 *            要删除的文件名称
	 * @return
	 */
	public static boolean deleteFile(String hostname, int port,
			String username, String password, String pathname, String filename) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding(encoding);
		ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
		ftpClient.setConnectTimeout(connectTimeoutSecond * 1000);
		ftpClient.setDataTimeout(dataTimeoutSecond * 1000);
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器文件目录
	 * @param filename
	 *            文件名称
	 * @param localpath
	 *            下载后的文件路径
	 * @return
	 */
	public static boolean downloadFile(String hostname, int port,
			String username, String password, String pathname, String filename,
			String localpath) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding(encoding);
		ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
		ftpClient.setConnectTimeout(connectTimeoutSecond * 1000);
		ftpClient.setDataTimeout(dataTimeoutSecond * 1000);
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {
					File localFile = new File(localpath + "/" + file.getName());
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			flag = true;
			logger.info("从ftp远程读取数据：ip="+hostname+",port="+port+",path="+pathname+",file="+filename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return flag;
	}
	
	public static byte[] downloadFileStream(String hostname, int port,
			String username, String password, String pathname, String filename) {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding(encoding);
		ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
		ftpClient.setConnectTimeout(connectTimeoutSecond * 1000);
		ftpClient.setDataTimeout(dataTimeoutSecond * 1000);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return null;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding(encoding);
			ftpClient.enterLocalPassiveMode();
			
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {  
					ftpClient.retrieveFile(file.getName(), bos);
					break;
				}
			}
			ftpClient.logout();
			logger.info("从ftp远程读取数据：ip= "+hostname+",port="+port+",path="+pathname+",file="+filename+",encoding="+encoding);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return bos.toByteArray();
	}
 

}
