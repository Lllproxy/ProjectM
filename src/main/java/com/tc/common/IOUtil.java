/**
 * 
 */
package com.tc.common;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

 
public class IOUtil {

	/**
	 * 获得控制台用户输入的信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getInputMessage(String tipMsg) throws IOException {
		System.out.println(tipMsg);
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++)
			ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
	}

	/**
	 * 以文件流的方式复制文件； 支持中文处理； 并且可以复制多种类型，比如txt，xml，jpg，doc等多种格式；
	 * 
	 * @param src
	 *            源文件
	 * @param dest
	 *            目的文件
	 * @throws IOException
	 */
	public static void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++) {
				out.write(buffer[i]);
			}
		}
		in.close();
		out.close();
	}

	/**
	 * NIO 方式复制文件
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileByNio(String src, String dest) throws IOException {
		FileInputStream fin = new FileInputStream(src);
		FileOutputStream fout = new FileOutputStream(dest);

		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}
	}

	/**
	 * 利用PrintStream写文件 可以一行一行的写入文件
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public static void writeFileByPrint(String content, String fileStr)
			throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream(fileStr);
		PrintStream p = new PrintStream(out);
		p.println(content);
	}

	/**
	 * 
	 * @param str
	 * @param file
	 * @throws IOException
	 */
	public static void writeFile(String str, String file) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader(file));
		FileWriter fwriter = new FileWriter(file);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		bwriter.newLine();
		bwriter.write(str);

		if (null != bwriter) {
			bwriter.close();
		}
	}

	/**
	 * add str content to file
	 * 
	 * @throws IOException
	 */
	public static void addContentToFile(String content, String fileStr, String encoding)
			throws IOException {
		File file = new File(fileStr);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file, true);
		StringBuffer sb = new StringBuffer();
		sb.append(content);
		out.write(sb.toString().getBytes(encoding));
		out.close();
	}

	/**
	 * read from InputStream
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in, String charset) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1024];
		int size = buffer.length;
		while (size != -1) {
			size = in.read(buffer);
			if (size == -1) {
				break;
			}
			if (null == charset || "".equals(charset)) {
				sb.append(new String(buffer).trim());
			}
			else{
				sb.append(new String(buffer, charset).trim());
			}
			buffer = new byte[size];
		}
		in.close();
		return sb.toString().trim();
	}
	
	public static byte[] read(InputStream is) throws IOException {
		ByteArrayOutputStream btos = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			btos.write(ch);
		}
		byte data[] = btos.toByteArray();
		btos.close();
		return data;
	}

	/**
	 * get the real path of classpath
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealClassPath(Class cls) {
		URL url = cls.getResource("/");
		File dir = new File(url.getPath());
		return dir.toString();
	}
	
	/**
	 * get file url in classpath
	 * @param cls
	 * @param file
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static URL getClassPathFile(Class cls, String file){
		return cls.getClassLoader().getResource(file);
	}

	public static void main(String[] args) {
		try {
			addContentToFile("OK", "d:\\test.txt", "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
