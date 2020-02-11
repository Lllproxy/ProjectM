package com.tc.common;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.tc.entity.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/***
 *
 */
@Component
public class DBUtil implements CommandLineRunner {
	
	public static String LOGGER_DRIVER = "";
	public static String LOGGER_URL = "";
	public static String LOGGER_USERNAME = "";
	public static String LOGGER_PASSWORD = "";
			
	@Value("${spring.datasource.driver-class-name}") public String loggerDriver;
	@Value("${spring.datasource.url}") public String loggerUrl;
	@Value("${spring.datasource.username}") public String loggerUsername;
	@Value("${spring.datasource.password}") public String loggerPassword;

	
	@Override
	public void run(String... args) throws Exception {
		init();
	}

	/**
	 * 配置参数初始化
	 */
	public void init() {
		
		LOGGER_DRIVER = this.loggerDriver;
		LOGGER_URL = this.loggerUrl;
		LOGGER_USERNAME = this.loggerUsername;
		LOGGER_PASSWORD = this.loggerPassword;
		
//		ITSDB_DRIVER = this.itsdbDriver;
//		ITSDB_URL = this.itsdbUrl;
//		ITSDB_USERNAME = this.itsdbUsername;
//		ITSDB_PASSWORD = this.itsdbPassword;
		
	}
	
	/**
	 * 获取链接logger
	 * @return
	 */
	public static Connection getLoggerConnection() {
		Connection conn = null;
		try {
			Class.forName(LOGGER_DRIVER);
			conn = DriverManager.getConnection(LOGGER_URL, LOGGER_USERNAME, LOGGER_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
//	/**
//	 * 获取链接ITSDB
//	 * @return
//	 */
//	public static Connection getITSDBConnection() {
//		Connection conn = null;
//		try {
//			Class.forName(ITSDB_DRIVER);
//			conn = DriverManager.getConnection(ITSDB_URL, ITSDB_USERNAME, ITSDB_PASSWORD);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return conn;
//	}
	
	/**
	 * 获取链接
	 * @return
	 */
	public static Connection getConnection(String jdbcUrl) {
		Connection conn = null;
		try {
			Class.forName(LOGGER_DRIVER);
			conn = DriverManager.getConnection(jdbcUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static  void loggingToDb(Logger logger){
		String sql="insert into log_test(uuid,method,date,user,status,localAddr,uri,wastetimemillis)values(?,?,?,?,?,?,?,?)";
		Integer total=0;
		PreparedStatement ps = null;
		Connection conn=null;
		try {
			conn=getLoggerConnection();
			ps =conn.prepareStatement(sql);
			ps.setString(1,uuid());
			ps.setString(2,logger.getMethod());
			ps.setString(3,logger.getDate());
			ps.setString(4,logger.getUser());
			ps.setString(5,logger.getStatus());
			ps.setString(6,logger.getLocalAddr());
			ps.setString(7,logger.getUri());
			ps.setLong(8,logger.getWasteTimeMillis());
			total=ps.executeUpdate();
			if(total>0){
				System.out.println(logger.getDate()+"==> logging "+total+"rows successful");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将时间戳转换成时间（yyyy-MM-dd HH:mm:ss）
	 * @param
	 * @return
	 */
	public static String toDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(Long.valueOf(System.currentTimeMillis()));
		return df.format(date);
	}
}
