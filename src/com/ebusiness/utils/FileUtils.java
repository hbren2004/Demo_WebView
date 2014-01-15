package com.ebusiness.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class FileUtils {

	private String TAG = "FileUtils";
	
	/**
	 * 从SDCard读取文件,判断SDCard是否存在 存在SDcard则构建FileInputStream输入流,将文件对象放入流中
	 * 再从流中读取到缓存buffer,并且将缓缓中的数据保存到输出字节流数组,存放到系统内存,直到文件末尾
	 * 将内存中的字节数组流转化为字节数组,并调用String的构造方法转化为返回的String值
	 * @param filename   文件名称
	 * @return 返回值
	 * @throws Exception  异常
	 */
	public static String readFileFromSDcard(String filename) throws Exception {
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileInputStream fileInStream = new FileInputStream(file);

		// 获取到输入流后,读取文件的常用代码操作
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
		int len = 0;
		while ((len = fileInStream.read(buffer)) != -1) {
			bufferArray.write(buffer, 0, len);
		}
		byte[] data = bufferArray.toByteArray();
		bufferArray.close();
		fileInStream.close();
		return new String(data);
	}

	public static String readFile(String filename) throws Exception {
		File file = new File(filename);
		FileInputStream fileInStream = new FileInputStream(file);

		// 获取到输入流后,读取文件的常用代码操作
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
		int len = 0;
		while ((len = fileInStream.read(buffer)) != -1) {
			bufferArray.write(buffer, 0, len);
		}
		byte[] data = bufferArray.toByteArray();
		bufferArray.close();
		fileInStream.close();
		return new String(data);
	}

	/**
	 * 从SD卡读取bitmapdrawable图片
	 * @param filename
	 * @return
	 */
	public static BitmapDrawable getDrawableFromSDcard(String filename)throws Exception {
		Log.i("x", "fileName:  " + filename);
		File file = new File(filename);
		if (!file.exists()) {
			Log.i("文件不存在!", "检查SDcard目录");
			return null;
		}

		FileInputStream fileInStream = null;
		fileInStream = new FileInputStream(file);

		Bitmap bitmap = BitmapFactory.decodeStream(fileInStream);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;

	}

	/**
	 * 从SD卡读取Bitmap图片
	 * @param filename
	 * @return
	 */
	public static Bitmap getBitmapFromSDcard(String filename)throws Exception{
		Log.i("x", "fileName:  " + filename);
		File file = new File(filename);
		if (!file.exists()) {
			Log.i("文件不存在!", "检查SDcard目录");
			return null;
		}

		FileInputStream fileInStream = null;
		fileInStream = new FileInputStream(file);
		Bitmap bitmap = BitmapFactory.decodeStream(fileInStream);
		return bitmap;

	}

	/**
	 * 将bitmap转化为byte[]保存这png格式
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 从InputStream 中取得byte[]
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFileFromInputStream(InputStream inStream)throws Exception {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream byteOStream = new ByteArrayOutputStream();
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			byteOStream.write(buffer, 0, len);
		}
		inStream.close();
		byteOStream.close();
		return byteOStream.toByteArray();
	}

	/**
	 * 删除目录下所有子目录和文件
	 * @param dir  给定目录
	 * @return true : 删除成功;false:删除失败
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 删除具体的文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists() && file.isFile()) {
			file.delete();
		} else {
			System.out.println("所删除的文件不存在！" + '\n');
		}
	}

	/**
	 * 读取不同编码的文件
	 * @param path
	 * @return
	 */
	public static List<String> readFileToCharArray(String path)throws Exception {
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		char[] buffer = new char[891];
		BufferedReader fis = null;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		bis.mark(4);
		byte[] first3bytes = new byte[3];
		bis.read(first3bytes);
		bis.reset();
		if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
				&& first3bytes[2] == (byte) 0xBF) {// utf-8
			fis = new BufferedReader(new InputStreamReader(bis, "utf-8"));
		} else if (first3bytes[0] == (byte) 0xFF
				&& first3bytes[1] == (byte) 0xFE) {
			fis = new BufferedReader(new InputStreamReader(bis, "unicode"));
		} else if (first3bytes[0] == (byte) 0xFE
				&& first3bytes[1] == (byte) 0xFF) {
			fis = new BufferedReader(new InputStreamReader(bis, "utf-16be"));
		} else if (first3bytes[0] == (byte) 0xFF
				&& first3bytes[1] == (byte) 0xFF) {
			fis = new BufferedReader(new InputStreamReader(bis, "utf-16le"));
		} else {
			fis = new BufferedReader(new InputStreamReader(bis, "GBK"));
		}
		int len = 0;
		while ((len = fis.read(buffer)) != -1) {
			list.add(new String(buffer, 0, len));
		}
		fis.close();
		return list;
	}

	/**
	 * 把指定的输入流写入到指定的目录文件夹下面
	 * @param writePath
	 * @param is
	 */
	public static void writeFile(String writePath, InputStream is)throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(writePath));
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		fos.flush();
		fos.close();
		is.close();
	}
	
	/**
	 * 将生成的doc转换成字符串
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static String domToString(Document doc)throws Exception{
		OutputFormat format = new OutputFormat("", true);
        format.setEncoding("UTF-8");
        StringWriter out = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(out, format);
        xmlWriter.write(doc);
        xmlWriter.close();
        return out.toString();
	}

}
