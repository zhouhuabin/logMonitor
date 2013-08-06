package com.palmcity.rtti.maintenancemonitor.bean;

import com.palmcity.rtti.maintenancemonitor.api.ModuleData;

public class ModuleInfo {
	/** 模块ID */
	private int Module_ID;
	/** 模块名称 */
	private String Module_Desc;
	/** 日志地址 */
	private String url;
	/** 软分析报警字符串 */
	private String alarmCondition;
	/** 错误次数 */
	private int errorValve;
	/** 超时时间（秒） */
	private int timeValue;
	/** 扫描状态 */
	private String ScanState;
	/** 城市名称 */
	private String cityName;
	
	/** 日志编码 */
	private String encoding;
	
	/** 模板ID */
	private int Module_Type_Id;
	
	
	/** 模块是否处于被扫描状态 */
	private boolean canScan=true;
	
	/**报警时间段(模板的默认报警时间段有修改时此处也应改动) **/
	private String module_Info_AlarmTime;
	
	
	/** 最后一次读取的内容长度 */
	private long contentLength=0;
	
	/** 最后一次读取的时间戳 */
	private long lastDateTime=0;
	
	/** 最新的缓存数据 */
	private ModuleData lastData;
	/**
	 * 获取变量<code>module_ID</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getModule_ID() {
		return Module_ID;
	}
	/**
	 * 设置变量<code> Module_ID</code> 的值
	 * @param module_ID  <code>module_ID</code> 参数类型是<code>int</code>
	 */
	public void setModule_ID(int module_ID) {
		Module_ID = module_ID;
	}
	/**
	 * 获取变量<code>module_Desc</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Desc() {
		return Module_Desc;
	}
	/**
	 * 设置变量<code> Module_Desc</code> 的值
	 * @param module_Desc  <code>module_Desc</code> 参数类型是<code>String</code>
	 */
	public void setModule_Desc(String module_Desc) {
		Module_Desc = module_Desc;
	}
	/**
	 * 获取变量<code>url</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置变量<code> url</code> 的值
	 * @param url  <code>url</code> 参数类型是<code>String</code>
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/**
	 * 获取变量<code>alarmCondition</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getAlarmCondition() {
		return alarmCondition;
	}
	/**
	 * 设置变量<code> alarmCondition</code> 的值
	 * @param alarmCondition  <code>alarmCondition</code> 参数类型是<code>String</code>
	 */
	public void setAlarmCondition(String alarmCondition) {
		this.alarmCondition = alarmCondition;
	}
	/**
	 * 获取变量<code>errorValve</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getErrorValve() {
		return errorValve;
	}
	/**
	 * 设置变量<code> errorValve</code> 的值
	 * @param errorValve  <code>errorValve</code> 参数类型是<code>int</code>
	 */
	public void setErrorValve(int errorValve) {
		this.errorValve = errorValve;
	}
	/**
	 * 获取变量<code>timeValue</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getTimeValue() {
		return timeValue;
	}
	/**
	 * 设置变量<code> timeValue</code> 的值
	 * @param timeValue  <code>timeValue</code> 参数类型是<code>int</code>
	 */
	public void setTimeValue(int timeValue) {
		this.timeValue = timeValue;
	}
	/**
	 * 获取变量<code>scanState</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getScanState() {
		return ScanState;
	}
	/**
	 * 设置变量<code> ScanState</code> 的值
	 * @param scanState  <code>scanState</code> 参数类型是<code>String</code>
	 */
	public void setScanState(String scanState) {
		ScanState = scanState;
	}
	/**
	 * 获取变量<code>cityName</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置变量<code> cityName</code> 的值
	 * @param cityName  <code>cityName</code> 参数类型是<code>String</code>
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取变量<code>module_Type_Id</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getModule_Type_Id() {
		return Module_Type_Id;
	}
	/**
	 * 设置变量<code> Module_Type_Id</code> 的值
	 * @param module_Type_Id  <code>module_Type_Id</code> 参数类型是<code>int</code>
	 */
	public void setModule_Type_Id(int module_Type_Id) {
		Module_Type_Id = module_Type_Id;
	}
	/**
	 * 获取变量<code>canScan</code>的值
	 * @return 返回的数据类型是<code>boolean</code>
	 */
	public boolean isCanScan() {
		return canScan;
	}
	/**
	 * 设置变量<code> canScan</code> 的值
	 * @param canScan  <code>canScan</code> 参数类型是<code>boolean</code>
	 */
	public void setCanScan(boolean canScan) {
		this.canScan = canScan;
	}
	/**
	 * 获取变量<code>contentLength</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getContentLength() {
		return contentLength;
	}
	/**
	 * 设置变量<code> contentLength</code> 的值
	 * @param contentLength  <code>contentLength</code> 参数类型是<code>long</code>
	 */
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	/**
	 * 获取变量<code>encoding</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * 设置变量<code> encoding</code> 的值
	 * @param encoding  <code>encoding</code> 参数类型是<code>String</code>
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * 获取变量<code>module_Info_AlarmTime</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Info_AlarmTime() {
		return module_Info_AlarmTime;
	}
	/**
	 * 设置变量<code> module_Info_AlarmTime</code> 的值
	 * @param module_Info_AlarmTime  <code>module_Info_AlarmTime</code> 参数类型是<code>String</code>
	 */
	public void setModule_Info_AlarmTime(String module_Info_AlarmTime) {
		this.module_Info_AlarmTime = module_Info_AlarmTime;
	}
	/**
	 * 获取变量<code>lastData</code>的值
	 * @return 返回的数据类型是<code>ModuleData</code>
	 */
	public ModuleData getLastData() {
		return lastData;
	}
	/**
	 * 设置变量<code> lastData</code> 的值
	 * @param lastData  <code>lastData</code> 参数类型是<code>ModuleData</code>
	 */
	public void setLastData(ModuleData lastData) {
		this.lastData = lastData;
	}
	/**
	 * 获取变量<code>lastDateTime</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getLastDateTime() {
		return lastDateTime;
	}
	/**
	 * 设置变量<code> lastDateTime</code> 的值
	 * @param lastDateTime  <code>lastDateTime</code> 参数类型是<code>long</code>
	 */
	public void setLastDateTime(long lastDateTime) {
		this.lastDateTime = lastDateTime;
	}
	
}
