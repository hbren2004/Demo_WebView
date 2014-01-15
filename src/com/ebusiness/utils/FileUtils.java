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
	 * ��SDCard��ȡ�ļ�,�ж�SDCard�Ƿ���� ����SDcard�򹹽�FileInputStream������,���ļ������������
	 * �ٴ����ж�ȡ������buffer,���ҽ������е����ݱ��浽����ֽ�������,��ŵ�ϵͳ�ڴ�,ֱ���ļ�ĩβ
	 * ���ڴ��е��ֽ�������ת��Ϊ�ֽ�����,������String�Ĺ��췽��ת��Ϊ���ص�Stringֵ
	 * @param filename   �ļ�����
	 * @return ����ֵ
	 * @throws Exception  �쳣
	 */
	public static String readFileFromSDcard(String filename) throws Exception {
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileInputStream fileInStream = new FileInputStream(file);

		// ��ȡ����������,��ȡ�ļ��ĳ��ô������
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

		// ��ȡ����������,��ȡ�ļ��ĳ��ô������
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
	 * ��SD����ȡbitmapdrawableͼƬ
	 * @param filename
	 * @return
	 */
	public static BitmapDrawable getDrawableFromSDcard(String filename)throws Exception {
		Log.i("x", "fileName:  " + filename);
		File file = new File(filename);
		if (!file.exists()) {
			Log.i("�ļ�������!", "���SDcardĿ¼");
			return null;
		}

		FileInputStream fileInStream = null;
		fileInStream = new FileInputStream(file);

		Bitmap bitmap = BitmapFactory.decodeStream(fileInStream);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;

	}

	/**
	 * ��SD����ȡBitmapͼƬ
	 * @param filename
	 * @return
	 */
	public static Bitmap getBitmapFromSDcard(String filename)throws Exception{
		Log.i("x", "fileName:  " + filename);
		File file = new File(filename);
		if (!file.exists()) {
			Log.i("�ļ�������!", "���SDcardĿ¼");
			return null;
		}

		FileInputStream fileInStream = null;
		fileInStream = new FileInputStream(file);
		Bitmap bitmap = BitmapFactory.decodeStream(fileInStream);
		return bitmap;

	}

	/**
	 * ��bitmapת��Ϊbyte[]������png��ʽ
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * ��InputStream ��ȡ��byte[]
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
	 * ɾ��Ŀ¼��������Ŀ¼���ļ�
	 * @param dir  ����Ŀ¼
	 * @return true : ɾ���ɹ�;false:ɾ��ʧ��
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}

	/**
	 * ɾ��������ļ�
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists() && file.isFile()) {
			file.delete();
		} else {
			System.out.println("��ɾ�����ļ������ڣ�" + '\n');
		}
	}

	/**
	 * ��ȡ��ͬ������ļ�
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
	 * ��ָ����������д�뵽ָ����Ŀ¼�ļ�������
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
	 * �����ɵ�docת�����ַ���
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
