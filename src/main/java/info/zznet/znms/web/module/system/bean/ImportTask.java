package info.zznet.znms.web.module.system.bean;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.dao.ImportResultMapper;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.ImportResult;
import info.zznet.znms.base.util.IpUtil;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.util.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportTask {
	private String id;
	private String adminName;
	private String description;
	private File sourceFile;
	private Date registerTime;
	private Date finishTime;
	private String fileName;
	private long total;
	private long imported;
	
	private boolean running;
	
	private HostMapper hostMapper = (HostMapper) SpringContextUtil.getBean("hostMapper");
	private ImportResultMapper importResultMapper = (ImportResultMapper) SpringContextUtil.getBean("importResultMapper");
	
	public void doImport(){
		
		setRunning(true);
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(sourceFile));
		} catch (IOException e) {
			ZNMSLogger.error(e);
		}
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		total = sheet.getLastRowNum() - 1;
		boolean ok = true;
        // 循环行Row
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            Host host = new Host();
            if(assembleAndValid(row, host)) {
            	try{
            		hostMapper.insert(host);
            	} catch (Throwable e){
            		ZNMSLogger.error(e);
            		ok = false;
            		addResult(row, "未正常入库，请查看系统日志", false);
            		//设置上传后的excel每行背景色
                    fillRowBackColor(workbook, row, false);
            		continue;
            	}
            	addResult(row, "已正常入库", false);
            	//设置上传后的excel每行背景色
                fillRowBackColor(workbook, row, true);
            } else {
            	ok = false;
            	addResult(row, "未正常入库", false);
            	//设置上传后的excel每行背景色
                fillRowBackColor(workbook, row, false);
            }
            imported++;
        }
        
        OutputStream out = null;
        try {
            out = new FileOutputStream(sourceFile);
            workbook.write(out);
        } catch(Throwable e){
        	ZNMSLogger.error(e);
        } finally {
        	if(out != null) {
        		try {
					out.close();
				} catch (IOException e) {
					ZNMSLogger.error(e);
				}            	            		
        	}
        }
        finishTime = new Date();
        
        ImportResult result = new ImportResult();
        result.setAdminName(adminName);
        result.setFinishTime(finishTime);
        result.setRegisterTime(registerTime);
        result.setId(id);
        result.setResult(ok);
        importResultMapper.insert(result);
	}
	
	/**
	 * 设置excel每行背景颜色
	 * @param workbook
	 * @param row
	 */
	private void fillRowBackColor(XSSFWorkbook workbook, XSSFRow row, boolean isNormal) {
		CellStyle style = workbook.createCellStyle();
		if(isNormal){
			style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		}else{
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
		}
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		for(int i=0;i<=14;i++){
			Cell rowCell= row.getCell(i);
			if(null==rowCell){
				Cell rc = row.createCell(i);
				rc.setCellStyle(style);
			}else{
				rowCell.setCellStyle(style);
			}
		}
	}

	private boolean assembleAndValid(XSSFRow row, Host host){
		if(null != row.getCell(14)){
        		row.getCell(14).setCellValue("");
        	}
		boolean dataOk = true;
        
        String hostId = UUIDGenerator.getGUID();
        host.setId(hostId);
        
        // host名称
        String hostName = ExcelUtil.getValue(row.getCell(0));
        if(StringUtils.isEmpty(hostName)){
        	dataOk = false;
        	addResult(row, "错误：[主机名称]未填写");
        } else if(hostName.length() > 250) {
        	addResult(row, "警告：[主机名称]超出指定位数，超出部分将被抛弃");
        	hostName = StringUtils.left(hostName, 250);
        }
        if(hostMapper.checkHostName(hostName, hostId) != null){
        	dataOk = false;
        	addResult(row, "错误：[主机名称]已存在");
        }
        host.setHostName(hostName);
        
        // host ip
        String hostIp = ExcelUtil.getValue(row.getCell(1));
        if(StringUtils.isEmpty(hostIp)){
        	dataOk = false;
        	addResult(row, "错误：[主机IP]未填写");
        } else if(hostIp.length() > 150) {
        	addResult(row, "警告：[主机IP]超出指定位数，超出部分将被抛弃");
        	hostIp = StringUtils.left(hostIp, 150);
        }
        if(!IpUtil.isIP(hostIp)){
        	dataOk = false;
        	addResult(row, "错误：[主机IP]格式错误");
        }
        if(hostMapper.checkHostIp(hostIp, hostId) != null){
        	dataOk = false;
        	addResult(row, "错误：[主机IP]已存在");
        }
        host.setHostIp(hostIp);
        
        // 主机状态
        String activate = ExcelUtil.getValue(row.getCell(2));
        host.setStatus((StringUtils.isEmpty(activate) || activate.equals("启用"))?(byte)1:(byte)0);
        
        // 主机类型
        String type = ExcelUtil.getValue(row.getCell(3));
        if(type.equalsIgnoreCase("出口")) {
        	host.setType(1);
        } else if(type.equalsIgnoreCase("核心")){
        	host.setType(2);
        } else if(type.equalsIgnoreCase("无线控制器")){
        	host.setType(3);
        } else if(type.isEmpty() || type.equalsIgnoreCase("接入")){
        	host.setType(4);
        } else if(type.equalsIgnoreCase("汇聚")){
        	host.setType(5);
        } else {
        	host.setType(6);
        }
        
        // 检测方法 默认为ping
        String availabilityMethod = ExcelUtil.getValue(row.getCell(4));
        if(StringUtils.isEmpty(availabilityMethod) || availabilityMethod.equals("Ping")){
        	host.setAvailabilityMethod((short) 2);                	
        } else if(availabilityMethod.equals("SNMP")) {
        	host.setAvailabilityMethod((short) 1);
        }
        
        // snmp版本 默认为v2
        String snmpVersion = ExcelUtil.getValue(row.getCell(5));
        if(snmpVersion.equals("-")) {
        	host.setSnmpVersion(0);
        } else if(snmpVersion.equals("v1")) {
        	host.setSnmpVersion(1);
        } else if(StringUtils.isEmpty(snmpVersion) || snmpVersion.equals("v2")) {
        	host.setSnmpVersion(2);
        } else {
        	host.setSnmpVersion(3);
        }
        
        // 团体名
        String snmpCommunity = ExcelUtil.getValue(row.getCell(6));
        if(host.getSnmpVersion().compareTo(1)==0||host.getSnmpVersion().compareTo(2)==0) {
        	if(host.getSnmpVersion().compareTo(1)==0 && StringUtils.isEmpty(snmpCommunity)){
        		dataOk = false;
            	addResult(row, "错误：[版本]为v1时，[团体名]必须填写");
        	}else if(host.getSnmpVersion().compareTo(2)==0 && StringUtils.isEmpty(snmpCommunity)){
        		snmpCommunity = "public";
        	}else if(snmpCommunity.length()>100){
        		addResult(row, "警告：[团体名]超出指定位数，超出部分将被抛弃");
        		snmpCommunity = StringUtils.left(snmpCommunity, 100);
        	}
        	host.setSnmpCommunity(snmpCommunity);
        }
        
        // 说明
        String notes = ExcelUtil.getValue(row.getCell(7));
        if(notes.length() > 255) {
        	addResult(row, "警告：[上下文]超出指定位数，超出部分将被抛弃");
        	notes = StringUtils.left(notes, 255);
        }
        host.setNotes(notes);
        
        // SNMP V3版本用户名
        String snmpUserName = ExcelUtil.getValue(row.getCell(8));
        if(host.getSnmpVersion().compareTo(3)==0) {
        	if(StringUtils.isEmpty(snmpUserName)){
        		dataOk = false;
            	addResult(row, "错误：[版本]为v3时，[用户名]必须填写");
        	} else if(snmpUserName.length()>50){
        		addResult(row, "警告：[用户名]超出指定位数，超出部分将被抛弃");
        		snmpUserName = StringUtils.left(snmpUserName, 50);
        	}
        	host.setSnmpUserName(snmpUserName);
        }
        
        // SNMP V3版本密码
        String snmpPassword = ExcelUtil.getValue(row.getCell(9));
        if(host.getSnmpVersion().compareTo(3)==0) {
        	if(StringUtils.isEmpty(snmpPassword)){
        		dataOk = false;
            	addResult(row, "错误：[版本]为v3时，[密码]必须填写");
        	} else if(snmpPassword.length()>50){
        		addResult(row, "警告：[密码]超出指定位数，超出部分将被抛弃");
        		snmpPassword = StringUtils.left(snmpPassword, 50);
        	}
        	host.setSnmpPassword(snmpPassword);
        }
        
        // 验证协议
        String snmpAuthProtocol = ExcelUtil.getValue(row.getCell(10));
        if(host.getSnmpVersion().compareTo(3)==0){
        	if(StringUtils.isEmpty(snmpAuthProtocol) || StringUtils.startsWithIgnoreCase(snmpAuthProtocol, "md5")) {
            	host.setSnmpAuthProtocol("MD5(default)");
            } else {
            	host.setSnmpAuthProtocol("SHA");
            }
        }
        
        // 私有密码短语
        String snmpPrivPassphrase = ExcelUtil.getValue(row.getCell(11));
        if(host.getSnmpVersion().compareTo(3)==0) {
        	if(StringUtils.isEmpty(snmpPrivPassphrase)){
        		dataOk = false;
            	addResult(row, "错误：[版本]为v3时，[私有密码短语]必须填写");
        	} else if(snmpPrivPassphrase.length()>200){
        		addResult(row, "警告：[私有密码短语]超出指定位数，超出部分将被抛弃");
        		snmpPrivPassphrase = StringUtils.left(snmpPrivPassphrase, 200);
        	}
        	host.setSnmpPrivPassphrase(snmpPrivPassphrase);
        }
        
        // 私有协议
        String snmpPrivProtocol = ExcelUtil.getValue(row.getCell(12));
        if(host.getSnmpVersion().compareTo(3)==0){
        	if(StringUtils.isEmpty(snmpPrivProtocol) || StringUtils.startsWithIgnoreCase(snmpPrivProtocol, "des")) {
            	host.setSnmpPrivProtocol("DES(默认)");
            } else if(snmpPrivProtocol.equals("-")){
            	host.setSnmpPrivProtocol("[无]");
            } else {
            	host.setSnmpPrivProtocol("AES");
            }
        }
        
        
        // 上下文
        String snmpContext = ExcelUtil.getValue(row.getCell(13));
        if(host.getSnmpVersion().compareTo(3)==0) {
        	if(StringUtils.isEmpty(snmpContext)){
        		dataOk = false;
            	addResult(row, "错误：[版本]为v3时，[上下文]必须填写");
        	} else if(snmpContext.length()>200){
        		addResult(row, "警告：[上下文]超出指定位数，超出部分将被抛弃");
        		snmpContext = StringUtils.left(snmpContext, 64);
        	}
        	host.setSnmpContext(snmpContext);
        }
        
        //工作状态默认为1即可达
        host.setHostWorkStatus(1);
        
        return dataOk;
	}
	
	private void addResult(XSSFRow row, String msg) {
		addResult(row, msg, true);
	}
	private void addResult(XSSFRow row, String msg, boolean isTail){
		Cell cell = row.getCell(14);
		String text = "";
		if(cell == null) {
			cell = row.createCell(14, Cell.CELL_TYPE_STRING);
		}
		text = cell.getStringCellValue();
		if(StringUtils.isEmpty(text)){
			cell.setCellValue(msg);
		} else {
			if(isTail) {
				cell.setCellValue(new XSSFRichTextString(text + "\r\n" + msg));
			} else {
				cell.setCellValue(new XSSFRichTextString(msg + "\r\n" + text));
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getImported() {
		return imported;
	}

	public void setImported(long imported) {
		this.imported = imported;
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
