package com.ebusiness.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDcardUtil {
	/**
	 * 返回sdcard的总容量或可用容量:标记true:总容量;否则:可用容量
	 * @param flag  标记位
	 * @return
	 */
	public static String showSDCardSize(boolean flag) {
		File sdcard = Environment.getExternalStorageDirectory();
		//可以通过StatFs访问文件系统的空间容量等信息
		StatFs statFs = new StatFs(sdcard.getPath());
		//statFs.getBlockSize可以获取当前的文件系统中，一个block所占有的字节数
		int blockSize = statFs.getBlockSize();
		//statFs.getAvaliableBlocks方法可以返回尚未使用的block的数量
		int avaliableBlocks = statFs.getAvailableBlocks();
		//statFs.getBlockCount可以获取总的block数量
		int totalBlocks = statFs.getBlockCount();
		//avaliableSize :可用容量 :byte totalSize :总容量:byte
		double avaliableSize = (double) avaliableBlocks * blockSize;

		double totalSize = (double) totalBlocks * blockSize;
		// float a = (float)avaliableBlocks/totalBlocks;
		// int b =
		// Integer.valueOf(Float.valueOf(a*100).toString().substring(0,2));
		return flag ? formatFileSize(totalSize) : formatFileSize(avaliableSize);
	}

	/**
	 * 取SDcard容量,返回M单位容量值
	 * @param flag  true:总容量M
	 * @return
	 */
	public static double getSDCardSize(boolean flag) {
		File sdcard = Environment.getExternalStorageDirectory();
		//我们可以通过StatFs访问文件系统的空间容量等信息
		StatFs statFs = new StatFs(sdcard.getPath());
		//statFs.getBlockSize可以获取当前的文件系统中，一个block所占有的字节数
		int blockSize = statFs.getBlockSize();
		//statFs.getAvaliableBlocks方法可以返回尚未使用的block的数量
		int avaliableBlocks = statFs.getAvailableBlocks();
		//statFs.getBlockCount可以获取总的block数量
		int totalBlocks = statFs.getBlockCount();
		Log.i("totalBlocks", "" + totalBlocks);
		Log.i("blockSize", "" + blockSize);
		//avaliableSize :可用容量 :byte totalSize :总容量:byte
		double avaliableSize = avaliableBlocks * blockSize;
		double totalSize = (double) totalBlocks * blockSize;
		Log.i("avaliableSize", "" + avaliableSize);
		Log.i("totalSize", "" + totalSize);
		return flag ? (totalSize / 1048576) : (avaliableSize / 1048576);
	}

	/**
	 * 将byte转化为对应的数量级格式
	 * @param size  byte
	 * @param flag  需转化类型结尾
	 * @return
	 */
	public static String formatSize(double size, String flag) {// 转换文件大小
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
	 * 获取指定路径下文件总大小
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
	 * 计算不同类型文件夹总大小
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
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	public static String formatFileSize(double fileS) {// 转换文件大小
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
