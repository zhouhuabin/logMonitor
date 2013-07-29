/**
 * <p>文件名:		MaintenanceMonitorConfigure.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;
import com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan;

/**
 * <p>
 * MaintenanceMonitorConfigure
 * </p>
 * <p>
 * 用途：运维监控配置对象类
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
 *          <td>2011-7-13 下午5:27:26</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午5:27:26</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MaintenanceMonitorConfigure extends ETBase {

	/** default */
	private static final long serialVersionUID = 1L;
	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	/** 模块获取 */
	private LogFileConfigureDAO logFileConfigureDAO;

	/** 输出数据备份路径 */
	private String outputData_filepath;
	
	/** 日志文件扫描间隔s */
	private int scan_interval = 30*1000;
	
	/** 邮件接收人列表 */
	private String mail_receivers="a@ctfo.com,b@ctfo.com,c@ctfo.com";
	/** 短信接收人列表 */
	private String sms_receivers="13900000001,13900000002,13900000003";

	/**日志配置列表,key是moduleType */
	private Map<String,LogTypeConfigure> confMap = new HashMap<String,LogTypeConfigure>();
	
	/** 日志类型名称映射表 */
	private Map<String,String> moduleNameMap = new HashMap<String,String>();
	
	/**日志配置列表,key是moduleCode */
	private Map<String,AbstractLogFileScan> codeConfMap = new HashMap<String,AbstractLogFileScan>();
	
	
	/** 获取用户邮箱列表和短信列表 */
	private MonitorUserDAO monitorUserDao;

	/**
	 * 字段 monitorUserDao 获取函数
	 * @return the monitorUserDao : MonitorUserDAO
	 */
	public MonitorUserDAO getMonitorUserDao() {
		return monitorUserDao;
	}


	/**
	 * 字段 monitorUserDao 设置函数 : MonitorUserDAO
	 * @param monitorUserDao the monitorUserDao to set
	 */
	public void setMonitorUserDao(MonitorUserDAO monitorUserDao) {
		this.monitorUserDao = monitorUserDao;
	}


	/**
	 * 获取变量<code>mail_receivers</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getMail_receivers() {
		return mail_receivers;
	}


	/**
	 * 设置变量<code> mail_receivers</code> 的值，多个接收人以逗号分隔
	 * @param mail_receivers  <code>mail_receivers</code> 参数类型是<code>String</code>
	 */
	public void setMail_receivers(String mail_receivers) {
		this.mail_receivers = this.getMailOrSmsStr("mail_receivers");
	}


	/**
	 * 获取变量<code>sms_receivers</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getSms_receivers() {
		return sms_receivers;
	}


	/**
	 * 设置变量<code> sms_receivers</code> 的值
	 * @param sms_receivers  <code>sms_receivers</code> 参数类型是<code>String</code>
	 */
	public void setSms_receivers(String sms_receivers) {
		this.sms_receivers =this.getMailOrSmsStr("sms_receivers");
	}


	/**
	 * 获取变量<code>confMap</code>的值
	 * @return 返回的数据类型是<code>Map<String,LogTypeConfigure></code>
	 */
	public Map<String, LogTypeConfigure> getConfMap() {
		return confMap;
	}


	/**
	 * 设置变量<code> confMap</code> 的值
	 * @param confMap  <code>confMap</code> 参数类型是<code>Map<String,LogTypeConfigure></code>
	 */
	public void setConfMap(Map<String, LogTypeConfigure> confMap) {
		this.confMap = confMap;
	}


	/**
	 * 获取变量<code>scan_interval</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getScan_interval() {
		return scan_interval;
	}


	/**
	 * 设置变量<code> scan_interval</code> 的值
	 * @param scan_interval  <code>scan_interval</code> 参数类型是<code>int</code>
	 */
	public void setScan_interval(int scan_interval) {
		this.scan_interval = scan_interval;
	}


	/**
	 * 获取变量<code>moduleNameMap</code>的值
	 * @return 返回的数据类型是<code>Map<String,String></code>
	 */
	public Map<String,String> getModuleNameMap() {
		return moduleNameMap;
	}


	/**
	 * 设置变量<code> moduleNameMap</code> 的值
	 * @param moduleNameMap  <code>moduleNameMap</code> 参数类型是<code>Map<String,String></code>
	 */
	public void setModuleNameMap(Map<String,String> moduleNameMap) {
		this.moduleNameMap = moduleNameMap;
	}



	/**
	 * 设置文件扫描线程实例 
	 * @param code
	 * @param scan
	 */
	public void putLogFileScan(String code,AbstractLogFileScan scan){
		codeConfMap.put(code, scan);
	}
	/**
	 * 获取日志扫描线程实例 
	 * @param code
	 * @return
	 */
	public AbstractLogFileScan  getLogFileScan(String code){
		return codeConfMap.get(code);
	}
	
	/**
	 * 获取所有模块扫描实例map 
	 * @return
	 */
	public Map<String, AbstractLogFileScan> getCodeConfMap(){
		return Collections.unmodifiableMap(codeConfMap);
	}
	
	/**
	 * 字段 logFileConfigureDAO 获取函数
	 * @return the logFileConfigureDAO : LogFileConfigureDAO
	 */
	public LogFileConfigureDAO getLogFileConfigureDAO() {
		return logFileConfigureDAO;
	}
	/**
	 * 字段 logFileConfigureDAO 设置函数 : LogFileConfigureDAO
	 * @param logFileConfigureDAO the logFileConfigureDAO to set
	 */
	public void setLogFileConfigureDAO(LogFileConfigureDAO logFileConfigureDAO) {
		this.logFileConfigureDAO = logFileConfigureDAO;
	}
	
	/**
	 * FIXME 
	 * @param type
	 * @return 系统用户登陆邮箱/手机号码列表
	 */
	public String getMailOrSmsStr(String type)
	{
		String str="";
		List<MonitorUser> userList=null;
		try {
			userList = getMonitorUserDao().queryListByCondition(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(MonitorUser user:userList)
		{
			if(type.equals("sms_receivers"))
			{
				if(user.getMobile()!=null&&!user.getMobile().equals(""))
					str+=user.getMobile()+",";
			}
			if(type.equals("mail_receivers"))
			{
				if(user.getAccount()!=null&&!user.getAccount().equals(""))
					str+=user.getAccount()+",";
			}
		}
		return str;
	}
	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}


	public void setOutputData_filepath(String outputData_filepath) {
		this.outputData_filepath = outputData_filepath;
	}


	public String getOutputData_filepath() {
		return outputData_filepath;
	}

}
