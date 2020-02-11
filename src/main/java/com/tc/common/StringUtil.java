package com.tc.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author hfx
 *
 * @Time 2016-12-19上午11:53:49
 * 
 * @version
 */
public class StringUtil {
	
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static int stringToInt(String intstr)
	{
		Integer integer;
	    integer = Integer.valueOf(intstr);
	   return integer.intValue();
    }
	
	public static float  stringToFloat(String floatstr)
	{
		Float floatee;
		floatee = Float.valueOf(floatstr);
	    return floatee.floatValue();
    }
	
	public static float  stringToDouble(String doublestr)
	{
		Double doublee;
		doublee = Double.valueOf(doublestr);
	    return doublee.floatValue();
    }
//	获取当前路径方法
	public static String CatchCurrentPath() {
		File directory = new File("");//参数为空 
		String courseFile = null;
			try {
				courseFile = directory.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//得到当前路径
//			System.out.println(courseFile); 
		if (courseFile==null || "".equals(courseFile)) {
			throw new RuntimeException("未获取到当前文件路径");
		}
		return courseFile;
	}
	
	public static String ImportLog(String str,String Name,String TableName) {
		System.out.println("日志路径         "+StringUtil.CatchCurrentPath());
		Calendar now = Calendar.getInstance();
		String year=String.valueOf(now.get(Calendar.YEAR));
		String month=String.valueOf(now.get(Calendar.MONTH));
		String day=String.valueOf(now.get(Calendar.DAY_OF_MONTH)+1);
		String Hour=String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		String Mine=String.valueOf(now.get(Calendar.MINUTE));
		String Second=String.valueOf(now.get(Calendar.SECOND));
		File file=new File(StringUtil.CatchCurrentPath()+"/"+TableName+"/"+year+"/"+month+"/"+day+"/"+Hour+"/"+Mine+"/"+Second+"/"+TableName+".txt");
		String Loadpath=null;
		Loadpath=StringUtil.CatchCurrentPath()+"/"+TableName+"/"+year+"/"+month+"/"+day+"/"+Hour+"/"+Mine+"/"+Second+"/"+TableName+".txt";
//	    File file = new File("./mywork/work.txt");
		
	    FileOutputStream out = null;
	    try {
	        if (!file.exists()) {
	            // 先得到文件的上级目录，并创建上级目录，在创建文件
	            file.getParentFile().mkdirs();
	            file.createNewFile();
	        }

	        //创建文件输出流
	        out = new FileOutputStream(file);
	        //将字符串转化为字节
	        byte[] byteArr = str.getBytes();
	        out.write(byteArr);
	        out.flush();
	        byteArr = null;
	        out.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return Loadpath;
			
		}

}
