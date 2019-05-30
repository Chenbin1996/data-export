package com.ruxuanwo.data.export.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**   
 * @ClassName:  FileUtil
 * @Description:文件操作帮助类
 * @author: yangjian
 * @date:2017年10月19日 下午2:36:17
 * @Copyright: 2017 www.uhope.com Inc. All rights reserved.
 */
public class ConfigFileUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileUtil.class);
	

	/**
	 * @Title: readFile
	 * @Description: 根据文件路径和编码格式, 读取文件内容字符串并返回
	 * @param: @param Path
	 * @param: @param encoding
	 * @param: @return
	 * @return: String   
	 * @throws
	 */
	public static String readFile(String path, String encoding) {

		BufferedReader reader = null;
		
		StringBuffer sb = new StringBuffer("");
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		try {
			fileInputStream = new FileInputStream(path);
			inputStreamReader = new InputStreamReader(fileInputStream, encoding);
			reader = new BufferedReader(inputStreamReader);
			
			String tempString = null;
			
			while ((tempString = reader.readLine()) != null) {
				
				sb.append(tempString);
			}
			
		} catch (IOException e) {
			LOGGER.error("readFile IOException:", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error("close reader IOException:", e);
				}
			}
			if(inputStreamReader != null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LOGGER.error("close inputStreamReader IOException:", e);
				}
			}
			if(fileInputStream != null){
				try{
					fileInputStream.close();
				}catch (IOException e){
					LOGGER.error("close fileInputStream IOException:", e);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 返回值参考 {@link String} 
	 * @Title: readConfigFile
	 * @Description: 根据相对路径获取文件内容String, 支持两种模式下的文件读取:jar|war
	 * @param relativePath
	 * @return
	 * String {@link String}
	 * @throw
	 */
	public static String readConfigFile(String relativePath){

		StringBuffer sb = new StringBuffer("");
		
		InputStream is = ConfigFileUtil.class.getClassLoader().getResourceAsStream(relativePath);
		BufferedReader reader = null;
		if(is != null){
			reader = new BufferedReader(new InputStreamReader(is));
		}
		
		String tempString = null;
		
		try {
			while ((tempString = reader.readLine()) != null) {
				
				sb.append(tempString);
			}
		} catch (IOException e) {
			LOGGER.error("readConfigFile IOException:", e);
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch (IOException e){
					LOGGER.error("close BufferedReader reader IOException:", e);
				}
			}
			if(is != null){
				try{
					is.close();
				}catch (IOException e){
					LOGGER.error("close InputStream is IOException:", e);
				}
			}
		}
		
        return sb.toString();
	}
}
