package com.palmcity.rtti.maintenancemonitor.api;


public class ModuleData {
	
	/** 报警处理状态-未读取 */
	public final static String ALARM_STATUS_NOTREAD = "<img src='images/Gray.png'/>";
	/** 报警处理状态-无报警/误报  */
	public final static String ALARM_STATUS_NOWARN = "<img src='images/green.png'/>";
	/** 报警处理状态-报警中 */
	public final static String ALARM_STATUS_ALARMING = "<img src='images/aed.png'/>";
	/** 报警处理状态-警告中 */
	public final static String ALARM_STATUS_WARNNING = "<img src='images/yello.png'/>";
	
	
	/** 模块名称 */
	private String moduleName;
	/** 日志时间 */
	private String dateTime;
	/** 统计信息 */
	private String ststicInfo;
	
	/** 模块状态 */
	private String status;
	
	/** 状态描述 */
	private String text;
	/**
	 * 获取变量<code>ststicInfo</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getStsticInfo() {
		return ststicInfo;
	}

	/**
	 * 设置变量<code> ststicInfo</code> 的值
	 * @param ststicInfo  <code>ststicInfo</code> 参数类型是<code>String</code>
	 */
	public void setStsticInfo(String ststicInfo) {
		this.ststicInfo = ststicInfo;
	}

	/**
	 * 获取变量<code>status</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置变量<code> status</code> 的值
	 * @param status  <code>status</code> 参数类型是<code>String</code>
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取变量<code>text</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置变量<code> text</code> 的值
	 * @param text  <code>text</code> 参数类型是<code>String</code>
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获取变量<code>moduleName</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 设置变量<code> moduleName</code> 的值
	 * @param moduleName  <code>moduleName</code> 参数类型是<code>String</code>
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * 获取变量<code>dateTime</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * 设置变量<code> dateTime</code> 的值
	 * @param dateTime  <code>dateTime</code> 参数类型是<code>String</code>
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	
}
