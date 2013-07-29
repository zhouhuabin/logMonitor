/**
 * <p>文件名:		MonitorLogData.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.api;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.caits.lbs.framework.utils.Base64Codec;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;

/**
 * <p>
 * MonitorLogData
 * </p>
 * <p>
 * 用途：运维监控日志内容基类，其他模块写入日志时必须扩展此类 子类中不要有map和list等复杂类型，都采用int和string
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-6-16
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
 *          <td>2011-6-16 下午05:15:27</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-6-16 下午05:15:27</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
@JsonAutoDetect  
/**  
* 在此标记不生成json对象的属性，
* 如果你想转换的时候忽略某个属性，可以在后面继续加上  
*/  
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "log"})   
public class MonitorLogData extends ETBase implements Serializable {

	/** 序列号 */
	private static final long serialVersionUID = 1L;
	
	/** 日志记录器 */
	protected transient Logger log = Logger.getLogger(getClass());

	/** 日志数据时间utc */
	private long dateTime = 0;

	/** 模块类型 */
	private String moduleType = "Receive";

	/** 模块标识 */
	private String moduleCode = "RECEIVE_BEIJING";

	/** 模块名称 */
	private String moduleDesc = "xx接收";
	/** 日志类型0-普通信息 1-统计信息 2-报警信息 */
	private int logType = 0;

	/** 事件描述，不要使用Map类型 */
	private String text = "未连接";

	/** 报警开始时间 */
	private long alarm_time = 0; 
	
	/** 配置信息 */
	private String configure = "无";
	
	/** 报警的产生状态
	 * @see com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory
	 * */
	private int alarm_report_way = AlarmHistory.ALARM_REPORT_WAY_NOWARN;

	/** 报警处理人id */
	private int deal_opid = 0;
	/** 报警的当前处理状态 
	 *  @see com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory
	 * */
	private int alarm_status = AlarmHistory.ALARM_STATUS_NOTREAD;

	/** 日志类型：基本配置信息 */
	public static final int LOGTYPE_BASE = 0;

	/** 日志类型：实时信息 */
	public static final int LOGTYPE_INFO = 1;

	/** 日志类型：实时告警信息 */
	public static final int LOGTYPE_WARN = 2;

	/** 日志字段关键字-时间 */
	public static final String LOG_FILEDKEY_DATE_TIME = "date_time";
	/** 日志字段关键字-日志类型 */
	public static final String LOG_FILEDKEY_LOGTYPE = "logtype";
	/** 日志字段关键字-报警方式 */
	public static final String LOG_FILEDKEY_REPORT_WAY = "alarm_report_way";
	/** 日志字段关键字-报警状态 */
	public static final String LOG_FILEDKEY_ALARM_STATUS = "alarm_status";
	/** 日志字段关键字-模块类型 */
	public static final String LOG_FILEDKEY_MODULETYPE = "moduletype";
	/** 日志字段关键字-模块标识 */
	public static final String LOG_FILEDKEY_MODULECODE = "modulecode";

	/** 模块类型码字-接收 */
	public static final String LOG_MODULETYPE_RECEIVE = "RECEIVE";
	/** 模块类型码字-存储 */
	public static final String LOG_MODULETYPE_STORAGE = "STORAGE";
	/** 模块类型码字-仿真 */
	public static final String LOG_MODULETYPE_SIMULATE = "SIMULATE";
	/** 模块类型码字-发布 */
	public static final String LOG_MODULETYPE_PUBLISH = "PUBLISH";
	/** 模块类型码字-处理 */
	public static final String LOG_MODULETYPE_PROCESS = "PROCESS";
	/** 模块类型码字-预测 */
	public static final String LOG_MODULETYPE_PREDICTION = "PREDICTION";
	/** 模块类型码字-转发 */
	public static final String LOG_MODULETYPE_FORWARD = "FORWARD";
	
	/** 在拓扑图上的X坐标 */
	private int hot_x;
	
	/** 在拓扑图上的y坐标 */
	private int hot_y;
	/** 在拓扑图上的配置信息 */
	private String url;
	/**
	 * 字段 url 获取函数
	 * @return the url : String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 字段 url 设置函数 : String
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	

	/**
	 * 字段 encoding 获取函数
	 * @return the encoding : String
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 字段 encoding 设置函数 : String
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 字段 smsAlarm 获取函数
	 * @return the smsAlarm : int
	 */
	public int getSmsAlarm() {
		return smsAlarm;
	}

	/**
	 * 字段 smsAlarm 设置函数 : int
	 * @param smsAlarm the smsAlarm to set
	 */
	public void setSmsAlarm(int smsAlarm) {
		this.smsAlarm = smsAlarm;
	}

	/**
	 * 字段 mailAlarm 获取函数
	 * @return the mailAlarm : int
	 */
	public int getMailAlarm() {
		return mailAlarm;
	}

	/**
	 * 字段 mailAlarm 设置函数 : int
	 * @param mailAlarm the mailAlarm to set
	 */
	public void setMailAlarm(int mailAlarm) {
		this.mailAlarm = mailAlarm;
	}

	/**
	 * 字段 erroValue 获取函数
	 * @return the erroValue : int
	 */
	public int getErroValue() {
		return erroValue;
	}

	/**
	 * 字段 erroValue 设置函数 : int
	 * @param erroValue the erroValue to set
	 */
	public void setErroValue(int erroValue) {
		this.erroValue = erroValue;
	}

	/**
	 * 字段 carCount_max 获取函数
	 * @return the carCount_max : int
	 */
	public int getCarCount_max() {
		return carCount_max;
	}

	/**
	 * 字段 carCount_max 设置函数 : int
	 * @param carCount_max the carCount_max to set
	 */
	public void setCarCount_max(int carCount_max) {
		this.carCount_max = carCount_max;
	}

	/**
	 * 字段 relationAlarm 获取函数
	 * @return the relationAlarm : String
	 */
	public String getRelationAlarm() {
		return relationAlarm;
	}

	/**
	 * 字段 relationAlarm 设置函数 : String
	 * @param relationAlarm the relationAlarm to set
	 */
	public void setRelationAlarm(String relationAlarm) {
		this.relationAlarm = relationAlarm;
	}

	/**
	 * 字段 mapCode 获取函数
	 * @return the mapCode : String
	 */
	public String getMapCode() {
		return mapCode;
	}

	/**
	 * 字段 mapCode 设置函数 : String
	 * @param mapCode the mapCode to set
	 */
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	private int smsAlarm;
	private int mailAlarm;
	private int erroValue;
	private String encoding;
	private int carCount_max;
	private String relationAlarm;
	private String mapCode;
	private String alarmCondition;
	/**
	 * 字段 alarmCondition 获取函数
	 * @return the alarmCondition : String
	 */
	public String getAlarmCondition() {
		return alarmCondition;
	}

	/**
	 * 字段 alarmCondition 设置函数 : String
	 * @param alarmCondition the alarmCondition to set
	 */
	public void setAlarmCondition(String alarmCondition) {
		this.alarmCondition = alarmCondition;
	}

	/**
	 * 字段 hot_x 获取函数
	 * @return the hot_x : int
	 */
	public int getHot_x() {
		return hot_x;
	}

	/**
	 * 字段 hot_x 设置函数 : int
	 * @param hot_x the hot_x to set
	 */
	public void setHot_x(int hot_x) {
		this.hot_x = hot_x;
	}

	/**
	 * 字段 hot_y 获取函数
	 * @return the hot_y : int
	 */
	public int getHot_y() {
		return hot_y;
	}

	/**
	 * 字段 hot_y 设置函数 : int
	 * @param hot_y the hot_y to set
	 */
	public void setHot_y(int hot_y) {
		this.hot_y = hot_y;
	}

	/**
	 * 获取变量<code>date</code>的值
	 * 
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getDateTime() {
		return dateTime;
	}

	/**
	 * 设置变量<code> date</code> 的值
	 * 
	 * @param date
	 *            <code>date</code> 参数类型是<code>long</code>
	 */
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * 获取变量<code>moduleType</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleType() {
		return moduleType;
	}

	/**
	 * 设置变量<code> moduleType</code> 的值
	 * 
	 * @param moduleType
	 *            <code>moduleType</code> 参数类型是<code>String</code>
	 */
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	/**
	 * 获取变量<code>logType</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getLogType() {
		return logType;
	}

	/**
	 * 设置变量<code> logType</code> 的值
	 * 
	 * @param logType
	 *            <code>logType</code> 参数类型是<code>int</code>
	 */
	public void setLogType(int logType) {
		this.logType = logType;
	}

	/**
	 * 获取变量<code>moduleCode</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * 设置变量<code> moduleCode</code> 的值
	 * 
	 * @param moduleCode
	 *            <code>moduleCode</code> 参数类型是<code>String</code>
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toJSONString() {
		setDateTime(System.currentTimeMillis() / 1000);
		return super.toJSONString();
	}

	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * 获取变量<code>alarm_report_way</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getAlarm_report_way() {
		return alarm_report_way;
	}

	/**
	 * 设置变量<code> alarm_report_way</code> 的值
	 * 
	 * @param alarm_report_way
	 *            <code>alarm_report_way</code> 参数类型是<code>int</code>
	 */
	public void setAlarm_report_way(int alarm_report_way) {
		this.alarm_report_way = alarm_report_way;
	}

	/**
	 * 获取变量<code>alarm_status</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getAlarm_status() {
		return alarm_status;
	}

	/**
	 * 设置变量<code> alarm_status</code> 的值
	 * 
	 * @param alarm_status
	 *            <code>alarm_status</code> 参数类型是<code>int</code>
	 */
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}

	/**
	 * 原始内容是否报警
	 * 
	 * @return
	 */
	public boolean checkContentAlarm() {
		return logType == LOGTYPE_WARN;
	}

	/**
	 * 软件报警分析，必须由子类覆盖
	 * 
	 * @return
	 * @throws MaintenanceMonitorException
	 */
	public boolean checkConditionAlarm() throws MaintenanceMonitorException {
		if(this instanceof MonitorLogData){
			log.warn("调用MonitorLogData的报警分析没有意义，直接退出.");
			return false;
		}
		else
		throw new MaintenanceMonitorException("错误:软报警分析必须在子类中实现");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// 采用默认的复制
		return super.clone();
		/*
		 * Object new_obj = null; try { new_obj = getClass().newInstance(); }
		 * catch (InstantiationException e1) { throw new
		 * CloneNotSupportedException("复制对象时错误，无默认构造函数" +
		 * e1.getLocalizedMessage()); } catch (IllegalAccessException e1) {
		 * throw new CloneNotSupportedException("复制对象时错误，非法访问" +
		 * e1.getLocalizedMessage()); } Method[] methods =
		 * getClass().getDeclaredMethods(); for (Method method : methods) { if
		 * (!method.getName().startsWith("get")) continue; try { Object obj =
		 * method.invoke(this, new Object[0]); if (obj == null) obj = ""; Method
		 * setMethod = getClass().getDeclaredMethod( "set" +
		 * method.getName().substring(3), obj.getClass()); if (setMethod !=
		 * null) { Object invoke = method.invoke(this, obj); } } catch
		 * (SecurityException e) { throw new
		 * CloneNotSupportedException("复制对象时错误，不安全的访问" +
		 * e.getLocalizedMessage()); } catch (NoSuchMethodException e) { throw
		 * new CloneNotSupportedException("复制对象时错误，无此方法" +
		 * e.getLocalizedMessage()); } catch (IllegalArgumentException e) {
		 * throw new CloneNotSupportedException("复制对象时错误，非法参数" +
		 * e.getLocalizedMessage()); } catch (IllegalAccessException e) { throw
		 * new CloneNotSupportedException("复制对象时错误，非法访问" +
		 * e.getLocalizedMessage()); } catch (InvocationTargetException e) {
		 * throw new CloneNotSupportedException("复制对象时错误，对象目标异常" +
		 * e.getLocalizedMessage()); }
		 * 
		 * } return new_obj;
		 */
	}

	/**
	 * 获取变量<code>configure</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getConfigure() {
		return configure;
	}

	/**
	 * 设置变量<code> configure</code> 的值
	 * @param configure  <code>configure</code> 参数类型是<code>String</code>
	 */
	public void setConfigure(String configure) {
		this.configure = configure;
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
	 * 获取变量<code>moduleDesc</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleDesc() {
		return moduleDesc;
	}

	/**
	 * 设置变量<code> moduleDesc</code> 的值
	 * @param moduleDesc  <code>moduleDesc</code> 参数类型是<code>String</code>
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	/**
	 * 无日志文件级别的报警 
	 * @return
	 */
	public boolean noAnalyseAlarm() {
		return getAlarm_report_way() < AlarmHistory.ALARM_REPORT_WAY_LINENOTUPDATED;
	}

	/**
	 * 获取变量<code>alarm_time</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getAlarm_time() {
		return alarm_time;
	}

	/**
	 * 设置变量<code> alarm_time</code> 的值
	 * @param alarm_time  <code>alarm_time</code> 参数类型是<code>long</code>
	 */
	public void setAlarm_time(long alarm_time) {
		this.alarm_time = alarm_time;
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

}
