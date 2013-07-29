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
	private String moduletype;
	/** 模块标识代码 */
	private String module_code;
	/** 报警开始时间UTC */
	private int alarm_time;
	/** 报警处理时间UTC */
	private int deal_time;
	/** 报警结束时间UTC */
	private int finish_time;
	/** 报警处理状态0-报警中 1-处理中 2-处理结束 */
	private int status;
	/** 报警发现方式0-文件不存在 1-模块内容报警 2-监控条件报警 */
	private int report_way;
	/** 报警内容 */
	private String alarm_content;
	/** 更新时间UTC */
	private int update_time;

	/** 报警处理人id */
	private int deal_opid;
	
	/** 报警处理人姓名 */
	private String deal_opname;
	
	/** 数据的原始时间 */
	private int data_time;
	
	/** 报警方式-无报警 */
	public final static int ALARM_REPORT_WAY_NOWARN = 0; 
	/** 模块内容报警 */
	public final static int ALARM_REPORT_WAY_LOGLINEALARM = 1; 
	/** 软件条件报警 */
	public final static int ALARM_REPORT_WAY_LINEANALYSEALARM = 2; 
	/** 日志内容无更新报警 */
	public final static int ALARM_REPORT_WAY_LINENOTUPDATED = 3; 
	/** 文件不存在报警 */
	public final static int ALARM_REPORT_WAY_FILENOTEXISTS = 4; 
	
	
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
	 * 获取变量<code>moduletype</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuletype() {
		return moduletype;
	}

	/**
	 * 设置变量<code> moduletype</code> 的值
	 * 
	 * @param moduletype
	 *            <code>moduletype</code> 参数类型是<code>String</code>
	 */
	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}

	/**
	 * 获取变量<code>module_code</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModule_code() {
		return module_code;
	}

	/**
	 * 设置变量<code> module_code</code> 的值
	 * 
	 * @param module_code
	 *            <code>module_code</code> 参数类型是<code>String</code>
	 */
	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}

	/**
	 * 获取变量<code>alarm_time</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getAlarm_time() {
		return alarm_time;
	}

	/**
	 * 设置变量<code> alarm_time</code> 的值
	 * 
	 * @param alarm_time
	 *            <code>alarm_time</code> 参数类型是<code>int</code>
	 */
	public void setAlarm_time(int alarm_time) {
		this.alarm_time = alarm_time;
	}

	/**
	 * 获取变量<code>deal_time</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getDeal_time() {
		return deal_time;
	}

	/**
	 * 设置变量<code> deal_time</code> 的值
	 * 
	 * @param deal_time
	 *            <code>deal_time</code> 参数类型是<code>int</code>
	 */
	public void setDeal_time(int deal_time) {
		this.deal_time = deal_time;
	}

	/**
	 * 获取变量<code>finish_time</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getFinish_time() {
		return finish_time;
	}

	/**
	 * 设置变量<code> finish_time</code> 的值
	 * 
	 * @param finish_time
	 *            <code>finish_time</code> 参数类型是<code>int</code>
	 */
	public void setFinish_time(int finish_time) {
		this.finish_time = finish_time;
	}

	/**
	 * 获取变量<code>status</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 设置变量<code> status</code> 的值
	 * 
	 * @param status
	 *            <code>status</code> 参数类型是<code>int</code>
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获取变量<code>report_way</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getReport_way() {
		return report_way;
	}

	/**
	 * 设置变量<code> report_way</code> 的值
	 * 
	 * @param report_way
	 *            <code>report_way</code> 参数类型是<code>int</code>
	 */
	public void setReport_way(int report_way) {
		this.report_way = report_way;
	}

	/**
	 * 获取变量<code>alarm_content</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getAlarm_content() {
		return alarm_content;
	}

	/**
	 * 设置变量<code> alarm_content</code> 的值
	 * 
	 * @param alarm_content
	 *            <code>alarm_content</code> 参数类型是<code>String</code>
	 */
	public void setAlarm_content(String alarm_content) {
		this.alarm_content = alarm_content;
	}

	/**
	 * 获取变量<code>update_time</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getUpdate_time() {
		return update_time;
	}

	/**
	 * 设置变量<code> update_time</code> 的值
	 * 
	 * @param update_time
	 *            <code>update_time</code> 参数类型是<code>int</code>
	 */
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}

	/**
	 * 获取变量<code>deal_opid</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getDeal_opid() {
		return deal_opid;
	}

	/**
	 * 设置变量<code> deal_opid</code> 的值
	 * @param deal_opid  <code>deal_opid</code> 参数类型是<code>int</code>
	 */
	public void setDeal_opid(int deal_opid) {
		this.deal_opid = deal_opid;
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
	 * 获取变量<code>data_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getData_time() {
		return data_time;
	}

	/**
	 * 设置变量<code> data_time</code> 的值
	 * @param data_time  <code>data_time</code> 参数类型是<code>int</code>
	 */
	public void setData_time(int data_time) {
		this.data_time = data_time;
	}

}
