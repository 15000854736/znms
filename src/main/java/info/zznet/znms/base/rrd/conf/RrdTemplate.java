package info.zznet.znms.base.rrd.conf;

import info.zznet.znms.base.rrd.exception.RrdConfigException;
import info.zznet.znms.base.util.SpringContextUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.rrd4j.ConsolFun;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * rrd配置模板
 */
public class RrdTemplate {
	
	/**
	 * 模板名
	 */
	private String rrdTemplateName;
	/**
	 * 模板描述
	 */
	private String rrdTemplateDesc;
	/**
	 * 在spring注册的数据通道的service名
	 */
	private String rrdDataChannelName;
	
	/**
	 * ds定义列表
	 */
	List<RrdDsDef> dsDefList;
	
	/**
	 * 是否每小时持久化
	 * 默认开启
	 */
	private boolean hourly = true;
	/**
	 * 是否每天持久化
	 * 默认开启
	 */
	private boolean daily = true;
	/**
	 * 是否每周持久化
	 * 默认开启
	 */
	private boolean weekly = true;
	/**
	 * 是否每月持久化
	 * 默认开启
	 */
	private boolean monthly = true;
	/**
	 * 是否每年持久化
	 * 默认开启
	 */
	private boolean yearly = true;
	
	/**
	 * 持久化方法 
	 * 默认取平均值
	 */
	private ConsolFun cf = ConsolFun.AVERAGE;

	public String getRrdTemplateName() {
		return rrdTemplateName;
	}

	public void setRrdTemplateName(String rrdTemplateName) {
		this.rrdTemplateName = rrdTemplateName;
	}

	public String getRrdTemplateDesc() {
		return rrdTemplateDesc;
	}

	public void setRrdTemplateDesc(String rrdTemplateDesc) {
		this.rrdTemplateDesc = rrdTemplateDesc;
	}

	public String getRrdDataChannelName() {
		return rrdDataChannelName;
	}

	public void setRrdDataChannelName(String rrdDataChannelName) {
		this.rrdDataChannelName = rrdDataChannelName;
	}

	public List<RrdDsDef> getDsDefList() {
		return dsDefList;
	}

	public void setDsDefList(List<RrdDsDef> dsDefList) {
		this.dsDefList = dsDefList;
	}

	public boolean getHourly() {
		return hourly;
	}

	public void setHourly(boolean hourly) {
		this.hourly = hourly;
	}

	public boolean getDaily() {
		return daily;
	}

	public void setDaily(boolean daily) {
		this.daily = daily;
	}

	public boolean getWeekly() {
		return weekly;
	}

	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	public boolean getMonthly() {
		return monthly;
	}

	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}

	public boolean getYearly() {
		return yearly;
	}

	public void setYearly(boolean yearly) {
		this.yearly = yearly;
	}

	public ConsolFun getCf() {
		return cf;
	}

	public void setCf(ConsolFun cf) {
		this.cf = cf;
	}

	@Override
	public String toString() {
		return "RrdConfig [rrdTemplateName=" + rrdTemplateName + ", rrdDataChannelName="
				+ rrdDataChannelName + ", hourly=" + hourly + ", daily="
				+ daily + ", weekly=" + weekly + ", monthly=" + monthly
				+ ", yearly=" + yearly + ", cf=" + cf + "]";
	}
	
	public void checkParam() throws RrdConfigException{
		if(StringUtils.isEmpty(rrdTemplateName)){
			throw new RrdConfigException("RrdName cannot be empty");
		}
		if(dsDefList == null || dsDefList.isEmpty()){
			throw new RrdConfigException("At least one ds definition should be set");
		}
	}
}
