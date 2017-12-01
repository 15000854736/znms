package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.BackupConfigurationDeviceMapper;
import info.zznet.znms.base.dao.ConfigBackupRecordMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.base.entity.ConfigBackupRecord;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.bean.BackupFile;
import info.zznet.znms.web.module.system.bean.DetailConfigBackupRecord;
import info.zznet.znms.web.util.BeanHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backupRecord")
public class BackupRecordController extends BaseController{
	
	private static final String VIEW = "system/backupConfiguration/backupRecord";
	private static final String VIEW_DETAIL = "system/backupConfiguration/backupRecordDetail";
	private static final String VIEW_COMPARE = "system/backupConfiguration/backupRecordCompare";
	private static final String ATTR_HOST_LIST = "hostList";
	private static final String ATTR_DATA = "data";
	
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private HostMapper hostMapper;
	
	@Autowired
	private ConfigBackupRecordMapper recordMapper;
	@Autowired
	private BackupConfigurationDeviceMapper confMapper;
	
	@RequestMapping({"","/"})
	public ModelAndView init(){
		List<Host> hostList = hostMapper.findAll();
		
		List<Map> hostMapList = new ArrayList<Map>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "");
		map.put("text", "请选择主机");
		hostMapList.add(map);
		if(hostList != null){
			for(Host host : hostList){
				Map<String, String> _map = new HashMap<String, String>();
				_map.put("value", host.getId());
				_map.put("text", host.getHostName());
				hostMapList.add(_map);
			}
		}
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject(ATTR_HOST_LIST, JSONArray.fromObject(hostMapList, getJsonConfig()).toString());
		return mav;
	} 
	
	@RequestMapping(value="/query", method=RequestMethod.POST)
	@ResponseBody
	public String findPagedData(Pager pager){
		String hostUuid = JSONArray.fromObject(pager.getSearch()).getJSONObject(0).getString("value");
		List<DetailConfigBackupRecord> data = recordMapper.findPagedData(pager, hostUuid);
		pager.setRows(data);
		pager.setTotal(recordMapper.getRecordCount(hostUuid));
		return JSONObject.fromObject(pager, getJsonConfig()).toString();
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView findDetail(@PathVariable String id){
		DetailConfigBackupRecord record = recordMapper.findById(id);
		Map<String, Object> map = BeanHelper.convertBeanToMap(record);
		map.put("configContent", readFile(webRuntimeData.getSystemOptionBean().getBackupPath() + File.separator + record.getBackupPath() + File.separator + record.getFileName(), "<br>"));

		ModelAndView mav = new ModelAndView(VIEW_DETAIL);
		mav.addObject(ATTR_DATA, map);
		return mav;
	}
	
	@RequestMapping("/compare")
	public ModelAndView compare(){
		List<Host> hostList = hostMapper.findAll();
		
		ModelAndView mav = new ModelAndView(VIEW_COMPARE);
		mav.addObject(ATTR_HOST_LIST, hostList);
		return mav;
	}
	
	@RequestMapping(value="/compare/getBackupFileList", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBackupFile(String id){
		BackupConfigurationDevice device = confMapper.findByHostId(id);
		List<ConfigBackupRecord> recordList = recordMapper.findByHost(id);
		List<BackupFile> fileList = new ArrayList<BackupFile>();
		if(recordList != null) {
			for(ConfigBackupRecord record : recordList){
				if(record.getSuccess()){
					BackupFile file = new BackupFile();
					file.setBackupTime(record.getBackupTime());
					file.setFileName(record.getFileName());
					file.setFullFilePath(WebRuntimeData.instance.getSystemOptionBean().getBackupPath()+File.separator+device.getContent()+File.separator+file.getFileName());
					file.setId(record.getId());
					fileList.add(file);
				}
			}
		}
		return JSONArray.fromObject(fileList).toString();
	}
	
	@RequestMapping(value="/compare/getFileContent", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFileContent(String basePath, String comparePath){
		String baseContent = readFile(basePath, "\n");
		String compareContent = readFile(comparePath, "\n");
		Map<String, String> map = new HashMap<String, String>();
		map.put("baseContent", baseContent);
		map.put("compareContent", compareContent);
		return JSONObject.fromObject(map).toString();
	}
	
	/**
	 * 读取文件
	 * @param filePath
	 * @return
	 */
	private String readFile(String filePath, String seperator){
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			StringBuffer sb = new StringBuffer(1000);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
				while(br.ready()){
					sb.append(new String(br.readLine().getBytes(System.getProperty("file.encoding")), "UTF-8"));
					sb.append(seperator);
				}
				return sb.toString();
			} catch (FileNotFoundException e) {
				ZNMSLogger.error(e);
			} catch (IOException e) {
				ZNMSLogger.error(e);
			} finally {
				if(br != null){
					try {
						br.close();
					} catch (IOException e) {
						ZNMSLogger.error(e);
					}
				}
			}
		}
		return "";
	}
	
	public JsonConfig getJsonConfig(){
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换备份周期
		jsonCfg.registerJsonValueProcessor("backupTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					return sdf.format((Date)o);
				} else {
					return "";
				}
			}
		});
		return jsonCfg;
	}
}
