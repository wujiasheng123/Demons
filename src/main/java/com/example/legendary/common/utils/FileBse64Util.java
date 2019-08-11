package com.example.legendary.common.utils;

import org.apache.commons.net.util.Base64;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * base64工具类
 * @Author: 吴嘉晟
 * @Date: 2019/3/1 18:39
 * @Version 1.0
 */
public class FileBse64Util {

	/**
	 * 将文件转成base64 字符串
	 * @param path 文件路径
	 * @return *
	 */
	@SuppressWarnings("restriction")
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		 return Base64.encodeBase64String(buffer);
	}

	/**
	 * 将base64字符解码保存文件
	 * @param base64Code base64编码
	 * @param savePath 路径
	 */
	public static String decoderBase64File(String base64Code,String savePath) {
		String imgPix = base64Code.trim().split(",")[0];
		String imgName = getRandomFileName();
		byte[] buffer = Base64.decodeBase64(base64Code.trim().split(",")[1]);
		try {
			FileOutputStream out = new FileOutputStream(savePath+imgName+"."+imgPix.split(";")[0].substring(imgPix.split(";")[0].lastIndexOf("/")+1));
			out.write(buffer);
			out.close();
		} catch (IOException io){
			io.printStackTrace();
		}
		return imgName+"."+imgPix.split(";")[0].substring(imgPix.split(";")[0].lastIndexOf("/")+1);
	}

	/**
	 * 将base64字符保存文本文件
	 * 
	 * @param base64Code base64编码
	 * @param targetPath 文件存储路径
	 */
	public static void toFile(String base64Code, String targetPath) throws Exception {

		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	/**
	 * 生成随机文件名：当前年月日时分秒+五位随机数
	 * 
	 * @return 文件名
	 */
	private static String getRandomFileName() {
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();
		// 获取5位随机数
		int ranNum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		// 当前时间
		return ranNum + str;
	}

}