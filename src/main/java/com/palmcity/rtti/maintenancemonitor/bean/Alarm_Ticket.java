package com.palmcity.rtti.maintenancemonitor.bean;

import com.caits.lbs.bean.dbmodel.ETBase;

public class Alarm_Ticket extends ETBase{
	/** TODO */
	private static final long serialVersionUID = 1L;
	/** 故障单编号 */
	private String alarm_code;
	/** 模块类型 */
	private String moduleType;
	/** 模块代码 */
	private String moduleCode;
	/** 模块名称 */
	private String moduleDesc;
	/** 城市 */
	private String city;
	/** 监测方法(默认为系统程序自监控) */
	private String monitorMethod;
	/** 报警内容 */
	private String alarm_content;
	/** 诊断 */
	private String diagnosis;
	/** 措施 */
	private String measures;
	/** 结果 */
	private String result;
	/** 故障单附件路径 */
	private String alarmFilePath;
	/** 操作员姓名 */
	private String userRealName;
	/** 内部故障修复时间 */
	private int finish_time;
	/** 故障原因分类（0文件不存在 1模块内容报警 2监控条件报警）*/
	private int report_way;
	/** 是否影响业务，0不影响，1影响 */
	private int influence;
	/** 故障发生日期 */
	private int date_Time;
	/** 故障时间 */
	private int alarm_time;
	/**  故障处理时间 */
	private int deal_time;
	/** TODO */
	private int out_finish_time;
	/** 填写故障单时间 */
	private int form_date;
	/**
	 * 字段 alarm_code 获取函数
	 * @return the alarm_code : String
	 */
	public String getAlarm_code() {
		return alarm_code;
	}
	/**
	 * 字段 alarm_code 设置函数 : String
	 * @param alarm_code the alarm_code to set
	 */
	public void setAlarm_code(String alarm_code) {
		this.alarm_code = alarm_code;
	}
	/**
	 * 字段 moduleType 获取函数
	 * @return the moduleType : String
	 */
	public String getModuleType() {
		return moduleType;
	}
	/**
	 * 字段 moduleType 设置函数 : String
	 * @param moduleType the moduleType to set
	 */
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	/**
	 * 字段 moduleCode 获取函数
	 * @return the moduleCode : String
	 */
	public String getModuleCode() {
		return moduleCode;
	}
	/**
	 * 字段 moduleCode 设置函数 : String
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	/**
	 * 字段 moduleDesc 获取函数
	 * @return the moduleDesc : String
	 */
	public String getModuleDesc() {
		return moduleDesc;
	}
	/**
	 * 字段 moduleDesc 设置函数 : String
	 * @param moduleDesc the moduleDesc to set
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	/**
	 * 字段 city 获取函数
	 * @return the city : String
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 字段 city 设置函数 : String
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 字段 alarm_content 获取函数
	 * @return the alarm_content : String
	 */
	public String getAlarm_content() {
		return alarm_content;
	}
	/**
	 * 字段 alarm_content 设置函数 : String
	 * @param alarm_content the alarm_content to set
	 */
	public void setAlarm_content(String alarm_content) {
		this.alarm_content = alarm_content;
	}


	/**
	 * 字段 alarmFilePath 获取函数
	 * @return the alarmFilePath : String
	 */
	public String getAlarmFilePath() {
		return alarmFilePath;
	}
	/**
	 * 字段 alarmFilePath 设置函数 : String
	 * @param alarmFilePath the alarmFilePath to set
	 */
	public void setAlarmFilePath(String alarmFilePath) {
		this.alarmFilePath = alarmFilePath;
	}
	/**
	 * 字段 userRealName 获取函数
	 * @return the userRealName : String
	 */
	public String getUserRealName() {
		return userRealName;
	}
	/**
	 * 字段 userRealName 设置函数 : String
	 * @param userRealName the userRealName to set
	 */
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	/**
	 * 字段 finish_time 获取函数
	 * @return the finish_time : int
	 */
	public int getFinish_time() {
		return finish_time;
	}
	/**
	 * 字段 finish_time 设置函数 : int
	 * @param finish_time the finish_time to set
	 */
	public void setFinish_time(int finish_time) {
		this.finish_time = finish_time;
	}
	/**
	 * 字段 report_way 获取函数
	 * @return the report_way : int
	 */
	public int getReport_way() {
		return report_way;
	}
	/**
	 * 字段 report_way 设置函数 : int
	 * @param report_way the report_way to set
	 */
	public void setReport_way(int report_way) {
		this.report_way = report_way;
	}
	/**
	 * 字段 monitorMethod 获取函数
	 * @return the monitorMethod : String
	 */
	public String getMonitorMethod() {
		return monitorMethod;
	}
	/**
	 * 字段 monitorMethod 设置函数 : String
	 * @param monitorMethod the monitorMethod to set
	 */
	public void setMonitorMethod(String monitorMethod) {
		this.monitorMethod = monitorMethod;
	}
	/**
	 * 字段 diagnosis 获取函数
	 * @return the diagnosis : String
	 */
	public String getDiagnosis() {
		return diagnosis;
	}
	/**
	 * 字段 diagnosis 设置函数 : String
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	/**
	 * 字段 measures 获取函数
	 * @return the measures : String
	 */
	public String getMeasures() {
		return measures;
	}
	/**
	 * 字段 measures 设置函数 : String
	 * @param measures the measures to set
	 */
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	/**
	 * 字段 result 获取函数
	 * @return the result : String
	 */
	public String getResult() {
		return result;
	}
	/**
	 * 字段 result 设置函数 : String
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 字段 influence 获取函数
	 * @return the influence : int
	 */
	public int getInfluence() {
		return influence;
	}
	/**
	 * 字段 influence 设置函数 : int
	 * @param influence the influence to set
	 */
	public void setInfluence(int influence) {
		this.influence = influence;
	}
	/**
	 * 字段 date_Time 获取函数
	 * @return the date_Time : int
	 */
	public int getDate_Time() {
		return date_Time;
	}
	/**
	 * 字段 date_Time 设置函数 : int
	 * @param date_Time the date_Time to set
	 */
	public void setDate_Time(int date_Time) {
		this.date_Time = date_Time;
	}
	/**
	 * 字段 alarm_time 获取函数
	 * @return the alarm_time : int
	 */
	public int getAlarm_time() {
		return alarm_time;
	}
	/**
	 * 字段 alarm_time 设置函数 : int
	 * @param alarm_time the alarm_time to set
	 */
	public void setAlarm_time(int alarm_time) {
		this.alarm_time = alarm_time;
	}
	/**
	 * 字段 deal_time 获取函数
	 * @return the deal_time : int
	 */
	public int getDeal_time() {
		return deal_time;
	}
	/**
	 * 字段 deal_time 设置函数 : int
	 * @param deal_time the deal_time to set
	 */
	public void setDeal_time(int deal_time) {
		this.deal_time = deal_time;
	}
	/**
	 * 字段 out_finish_time 获取函数
	 * @return the out_finish_time : int
	 */
	public int getOut_finish_time() {
		return out_finish_time;
	}
	/**
	 * 字段 out_finish_time 设置函数 : int
	 * @param out_finish_time the out_finish_time to set
	 */
	public void setOut_finish_time(int out_finish_time) {
		this.out_finish_time = out_finish_time;
	}
	/**
	 * 字段 form_date 获取函数
	 * @return the form_date : int
	 */
	public int getForm_date() {
		return form_date;
	}
	/**
	 * 字段 form_date 设置函数 : int
	 * @param form_date the form_date to set
	 */
	public void setForm_date(int form_date) {
		this.form_date = form_date;
	}
}
