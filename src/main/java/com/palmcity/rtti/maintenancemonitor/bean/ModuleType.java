package com.palmcity.rtti.maintenancemonitor.bean;

public class ModuleType {
	/** 模板ID */
	private int Module_Type_Id;
	/** 模板名称 */
	private String Module_Type_Name;
	/** 模板字段 */
	private String Module_Type_Field;
	/** 模板字段中文名称 */
	private String Module_Type_Field_Zn;
	/** 统计字段 */
	private String Module_Type_Static;
	
	/** 默认报警时间段 */
	private String module_type_alarmtime;
	
	/** 线程休眠时间 */
	private int threadInterval;
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
	 * 获取变量<code>module_Type_Name</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Type_Name() {
		return Module_Type_Name;
	}
	/**
	 * 设置变量<code> Module_Type_Name</code> 的值
	 * @param module_Type_Name  <code>module_Type_Name</code> 参数类型是<code>String</code>
	 */
	public void setModule_Type_Name(String module_Type_Name) {
		Module_Type_Name = module_Type_Name;
	}
	/**
	 * 获取变量<code>module_Type_Field</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Type_Field() {
		return Module_Type_Field;
	}
	/**
	 * 设置变量<code> Module_Type_Field</code> 的值
	 * @param module_Type_Field  <code>module_Type_Field</code> 参数类型是<code>String</code>
	 */
	public void setModule_Type_Field(String module_Type_Field) {
		Module_Type_Field = module_Type_Field;
	}
	/**
	 * 获取变量<code>module_Type_Field_Zn</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Type_Field_Zn() {
		return Module_Type_Field_Zn;
	}
	/**
	 * 设置变量<code> Module_Type_Field_Zn</code> 的值
	 * @param module_Type_Field_Zn  <code>module_Type_Field_Zn</code> 参数类型是<code>String</code>
	 */
	public void setModule_Type_Field_Zn(String module_Type_Field_Zn) {
		Module_Type_Field_Zn = module_Type_Field_Zn;
	}
	
	/**
	 * 获取变量<code>module_Type_Static</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_Type_Static() {
		return Module_Type_Static;
	}
	/**
	 * 设置变量<code> Module_Type_Static</code> 的值
	 * @param module_Type_Static  <code>module_Type_Static</code> 参数类型是<code>String</code>
	 */
	public void setModule_Type_Static(String module_Type_Static) {
		Module_Type_Static = module_Type_Static;
	}
	/**
	 * 获取变量<code>module_type_alarmtime</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_type_alarmtime() {
		return module_type_alarmtime;
	}
	/**
	 * 设置变量<code> module_type_alarmtime</code> 的值
	 * @param module_type_alarmtime  <code>module_type_alarmtime</code> 参数类型是<code>String</code>
	 */
	public void setModule_type_alarmtime(String module_type_alarmtime) {
		this.module_type_alarmtime = module_type_alarmtime;
	}
	/**
	 * 获取变量<code>threadInterval</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getThreadInterval() {
		return threadInterval;
	}
	/**
	 * 设置变量<code> threadInterval</code> 的值
	 * @param threadInterval  <code>threadInterval</code> 参数类型是<code>int</code>
	 */
	public void setThreadInterval(int threadInterval) {
		this.threadInterval = threadInterval;
	}
	
}
