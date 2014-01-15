package com.ebusiness.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDcardUtil {
	/**
	 * ����sdcard�����������������:���true:������;����:��������
	 * @param flag  ���λ
	 * @return
	 */
	public static String showSDCardSize(boolean flag) {
		File sdcard = Environment.getExternalStorageDirectory();
		//����ͨ��StatFs�����ļ�ϵͳ�Ŀռ���������Ϣ
		StatFs statFs = new StatFs(sdcard.getPath());
		//statFs.getBlockSize���Ի�ȡ��ǰ���ļ�ϵͳ�У�һ��block��ռ�е��ֽ���
		int blockSize = statFs.getBlockSize();
		//statFs.getAvaliableBlocks�������Է�����δʹ�õ�block������
		int avaliableBlocks = statFs.getAvailableBlocks();
		//statFs.getBlockCount���Ի�ȡ�ܵ�block����
		int totalBlocks = statFs.getBlockCount();
		//avaliableSize :�������� :byte totalSize :������:byte
		double avaliableSize = (double) avaliableBlocks * blockSize;

		double totalSize = (double) totalBlocks * blockSize;
		// float a = (float)avaliableBlocks/totalBlocks;
		// int b =
		// Integer.valueOf(Float.valueOf(a*100).toString().substring(0,2));
		return flag ? formatFileSize(totalSize) : formatFileSize(avaliableSize);
	}

	/**
	 * ȡSDcard����,����M��λ����ֵ
	 * @param flag  true:������M
	 * @return
	 */
	public static double getSDCardSize(boolean flag) {
		File sdcard = Environment.getExternalStorageDirectory();
		//���ǿ���ͨ��StatFs�����ļ�ϵͳ�Ŀռ���������Ϣ
		StatFs statFs = new StatFs(sdcard.getPath());
		//statFs.getBlockSize���Ի�ȡ��ǰ���ļ�ϵͳ�У�һ��block��ռ�е��ֽ���
		int blockSize = statFs.getBlockSize();
		//statFs.getAvaliableBlocks�������Է�����δʹ�õ�block������
		int avaliableBlocks = statFs.getAvailableBlocks();
		//statFs.getBlockCount���Ի�ȡ�ܵ�block����
		int totalBlocks = statFs.getBlockCount();
		Log.i("totalBlocks", "" + totalBlocks);
		Log.i("blockSize", "" + blockSize);
		//avaliableSize :�������� :byte totalSize :������:byte
		double avaliableSize = avaliableBlocks * blockSize;
		double totalSize = (double) totalBlocks * blockSize;
		Log.i("avaliableSize", "" + avaliableSize);
		Log.i("totalSize", "" + totalSize);
		return flag ? (totalSize / 1048576) : (avaliableSize / 1048576);
	}

	/**
	 * ��byteת��Ϊ��Ӧ����������ʽ
	 * @param size  byte
	 * @param flag  ��ת�����ͽ�β
	 * @return
	 */
	public static String formatSize(double size, String flag) {// ת���ļ���С
		DecimalFormat df = new DecimalFormat("0.0");
		String str = "";
		if ("b".equalsIgnoreCase(flag)) {
			str = df.format(size);
		} else if ("k".equalsIgnoreCase(flag)) {
			str = df.format(size / 1024);
		} else if ("m".equalsIgnoreCase(flag)) {
			str = df.format(size / 1048576);
		} else if ("g".equalsIgnoreCase(flag)) {
			str = df.format(size / 1073741824);
		}
		return str;
	}

	/**
	 * ��ȡָ��·�����ļ��ܴ�С
	 * @param filePath
	 * @return
	 */
	public static String getFilesSize(String filePath) {
		File file = new File(filePath);
		long fileSize;
		if (file.exists()) {
			fileSize = getFileSize(file);
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileSize = 0;
		}
		return formatFileSize(fileSize);
	}

	/**
	 * ���㲻ͬ�����ļ����ܴ�С
	 * @param type
	 * @return
	 */
	public static long getFileSize(File file) {
		long diskSize = 0;
		if (!file.exists())
			return 0;
		File[] flist = file.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				diskSize = diskSize + getFileSize(flist[i]);
			} else {
				diskSize = diskSize + flist[i].length();
			}
		}
		return diskSize;
	}

	/**
	 * ת���ļ���С
	 * @param fileS
	 * @return
	 */
	public static String formatFileSize(double fileS) {// ת���ļ���С
		DecimalFormat df = new DecimalFormat("0.0");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
