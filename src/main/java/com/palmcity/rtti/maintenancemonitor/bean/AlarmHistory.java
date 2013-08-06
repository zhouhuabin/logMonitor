/**
 * <p>文件名:		AlarmHistory.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;

import com.caits.lbs.bean.dbmodel.ETBase;

/**
 * <p>
 * AlarmHistory
 * </p>
 * <p>
 * 用途：报警历史记录对象表
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-7-13
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th>
 *          <th width="100px">动作</th>
 *          <th width="100px">修改人</th>
 *          <th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午5:56:34</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午5:56:34</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class AlarmHistory extends ETBase {

	private static final long serialVersionUID = 1L;

	/** 模块类型代码 */
	private int Module_Type_Id;
	/** 模块名称*/
	private String Module_Desc;
	/** 模块标识代码 */
	private int Module_ID;
	/** 数据的原始时间/报警开始时间 */
	private long data_time;
	/** 报警处理时间UTC */
	private long deal_time=0;
	/** 报警结束时间UTC */
	private long finish_time=0;
	/** 报警处理状态0-报警中 1-处理中 2-处理结束 */
	private int status;
	/** 报警发现方式0-文件不存在 1-模块内容报警 2-监控条件报警 */
	private int report_way;
	/** 报警内容 */
	private String text;
	/** 入库时间UTC */
	private long update_time;
	/** 报警处理人姓名 */
	private String deal_opname="系统";
	
	
	/** 文件不存在报警 */
	public final static int ALARM_REPORT_WAY_FILENOTEXISTS = 0; 
	/** 模块程序报警 */
	public final static int ALARM_REPORT_WAY_LOGLINEALARM = 1; 
	/** 软件条件报警 */
	public final static int ALARM_REPORT_WAY_LINEANALYSEALARM = 2; 
	/** 报警方式-超时报警 */
	public final static int ALARM_REPORT_WAY_NOWARN = 3; 
	
	
	
	
	
	/** 报警处理状态-未读取 */
	public final static int ALARM_STATUS_NOTREAD = -1;
	/** 报警处理状态-无报警 */
	public final static int ALARM_STATUS_NOWARN = 0;
	/** 报警处理状态-报警中（未填写故障单） */
	public final static int ALARM_STATUS_WARNNING = 1;
	/** 报警处理状态-处理中（已填写故障单） */
	public final static int ALARM_STATUS_DEALING = 2;
	/** 报警处理状态-处理结束 （处理完成，系统恢复正常）*/
	public final static int ALARM_STATUS_FINISH = 3;
	/** 报警处理状态-误报*/
	public final static int ALARM_STATUS_ErrorAlarm = 4;
	/** 报警状态--警告状态 */
	public final static int ALARM_STATUS_Warning = 5;
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
	 * 获取变量<code>data_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public long getData_time() {
		return data_time;
	}
	/**
	 * 设置变量<code> data_time</code> 的值
	 * @param data_time  <code>data_time</code> 参数类型是<code>int</code>
	 */
	public void setData_time(long data_time) {
		this.data_time = data_time;
	}
	/**
	 * 获取变量<code>deal_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public long getDeal_time() {
		return deal_time;
	}
	/**
	 * 设置变量<code> deal_time</code> 的值
	 * @param deal_time  <code>deal_time</code> 参数类型是<code>int</code>
	 */
	public void setDeal_time(long deal_time) {
		this.deal_time = deal_time;
	}
	/**
	 * 获取变量<code>finish_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public long getFinish_time() {
		return finish_time;
	}
	/**
	 * 设置变量<code> finish_time</code> 的值
	 * @param finish_time  <code>finish_time</code> 参数类型是<code>int</code>
	 */
	public void setFinish_time(long finish_time) {
		this.finish_time = finish_time;
	}
	/**
	 * 获取变量<code>status</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 设置变量<code> status</code> 的值
	 * @param status  <code>status</code> 参数类型是<code>int</code>
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 获取变量<code>report_way</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getReport_way() {
		return report_way;
	}
	/**
	 * 设置变量<code> report_way</code> 的值
	 * @param report_way  <code>report_way</code> 参数类型是<code>int</code>
	 */
	public void setReport_way(int report_way) {
		this.report_way = report_way;
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
	 * 获取变量<code>update_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public long getUpdate_time() {
		return update_time;
	}
	/**
	 * 设置变量<code> update_time</code> 的值
	 * @param update_time  <code>update_time</code> 参数类型是<code>int</code>
	 */
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	/**
	 * 获取变量<code>deal_opname</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getDeal_opname() {
		return deal_opname;
	}
	/**
	 * 设置变量<code> deal_opname</code> 的值
	 * @param deal_opname  <code>deal_opname</code> 参数类型是<code>String</code>
	 */
	public void setDeal_opname(String deal_opname) {
		this.deal_opname = deal_opname;
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

	
}
