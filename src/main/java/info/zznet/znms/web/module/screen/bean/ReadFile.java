package info.zznet.znms.web.module.screen.bean;

import info.zznet.znms.base.common.ZNMSLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class ReadFile {

	
	public static String readDataConfig(String path){
		File file=new File(path);
		File[] files=file.listFiles();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < files.length; i++) {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				String str = "";
				fis = new FileInputStream(files[i]+File.separator+files[i].getName()+".txt");
				// 从文件系统中的某个文件中获取字节
				isr = new InputStreamReader(fis,"UTF-8");
				br = new BufferedReader(isr);
				while ((str = br.readLine()) != null) {
					sb.append(str);sb.append(",");
				}
				sb.append(files[i].getName());sb.append(";");
			} catch (FileNotFoundException e) {
				ZNMSLogger.error("找不到指定文件");
			} catch (IOException e) {
				ZNMSLogger.error("读取文件失败");
			} finally {
				try {
					if(br!=null){
						br.close();
						isr.close();
						fis.close();
					}
				} catch (IOException e) {
					ZNMSLogger.error(e.getMessage());
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		/*List<ScreenModuleBean> beanList=getConfigData();
		for (ScreenModuleBean screenModuleBean : beanList) {
			System.out.println(screenModuleBean.getModuleName() + "  : "+screenModuleBean.getWidth() +" X "+screenModuleBean.getHeight());
		}*/
		/*File file=new File("E:\\znmsworkspace\\z-nms-java\\src\\main\\webapp\\views\\screen\\module");
		File[] files=file.listFiles();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < files.length; i++) {
			//System.out.println(files[i].isDirectory() + "***"+files[i].getName());
			//System.out.println(files[i]+File.separator+files[i].getName()+".txt");
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				String str = "";
				fis = new FileInputStream(files[i]+File.separator+files[i].getName()+".txt");
				// 从文件系统中的某个文件中获取字节
				isr = new InputStreamReader(fis,"UTF-8");
				br = new BufferedReader(isr);
				while ((str = br.readLine()) != null) {
					sb.append(str);sb.append(",");
				}
			} catch (FileNotFoundException e) {
				ZNMSLogger.error("找不到指定文件");
			} catch (IOException e) {
				ZNMSLogger.error("读取文件失败");
			} finally {
				try {
					br.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					ZNMSLogger.error(e.getMessage());
				}
				sb.append(";");
			}
		}
		System.out.println(sb.toString());*/
	}

	public static List<ScreenModuleBean> getConfigData(String path,int row,int col) {
		String data=readDataConfig(path);
		List<ScreenModuleBean> configDataEntitys=new ArrayList<ScreenModuleBean>();
		String str[]=data.split(";");
		for (int i = 0; i < str.length; i++) {
			String filedata=str[i].trim();
			if(filedata!=null&&filedata.length()<=0){
				continue;
			}
			String strdata[]=str[i].split(",");
			if(strdata.length<3){
				ZNMSLogger.info("模块信息配置有误请检查");
				ZNMSLogger.error("模块信息配置有误请检查");
				continue;				
			}
			ScreenModuleBean configDataEntity=new ScreenModuleBean();
			try {
				configDataEntity.setModuleChineseName(strdata[0]);
				String dataRowCol[]=strdata[1].toUpperCase().trim().split("X");
				int _row=(12/col);
				
				configDataEntity.setWidth(Integer.parseInt(dataRowCol[1])*_row);
				configDataEntity.setHeight(Integer.parseInt(dataRowCol[0]));
				
				configDataEntity.setSpec(Integer.parseInt(dataRowCol[0])+"x"+Integer.parseInt(dataRowCol[1]));
				configDataEntity.setModuleName(strdata[2]);
				configDataEntitys.add(configDataEntity);
			} catch (NumberFormatException e) {
				ZNMSLogger.info("模块信息配置有误请检查");
				ZNMSLogger.error("模块信息配置有误请检查");
			}
		}
		return configDataEntitys;
	}
}