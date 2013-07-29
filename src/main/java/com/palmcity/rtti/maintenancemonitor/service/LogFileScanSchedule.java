/**
 * <p>文件名:		LogFileScanImpl.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.caits.lbs.framework.services.jmx.JMXManager;
import com.caits.lbs.framework.services.mail.IMailService;
import com.caits.lbs.framework.services.sms.IMessageService;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.LogTypeConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;
import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;

/**
 * <p>
 * LogFileScanImpl
 * </p>
 * <p>
 * 用途：日志文件扫描实现类
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
 *          <td>2011-7-13 下午7:39:20</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午7:39:20</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class LogFileScanSchedule extends Thread {

	/** 写入文件间隔时间 */
	private int WRITE_INTERVAL = 30 * 1000;

	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());

	/** 是否运行标志 */
	private boolean bRun = true;

	/** 配置类 */
	private MaintenanceMonitorConfigure configure = null;

	/** 报警记录数据访问接口 */
	private AlarmHistoryDAO alarmDao;

	/** 短信服务接口 */
	private IMessageService messageService;
	
	/** 短信服务接口 */
	private IMailService mailService;
	
	private LogFileConfigureDAO logFileConfigureDAO;
	

	/** 正在运行的LogFileScan实例map */
	static Map<String,AbstractLogFileScan> LogFileScanMap= new HashMap<String,AbstractLogFileScan>();
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
	 * 构造器
	 * 
	 * @param conf
	 */
	public LogFileScanSchedule(MaintenanceMonitorConfigure conf) {
		configure = conf;
//		init();
	}



	/**
	 * 存储logFileScan
	 */
	public static  boolean addLogFileScan(String moduleType,AbstractLogFileScan logFileScan) {
			LogFileScanMap.put(moduleType, logFileScan);
			return true;
	}
	/**
	 * FIXME 
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		log.debug("任务线程开始扫描日志文件");

		for (Entry<String, LogTypeConfigure> entry : configure
				.getConfMap().entrySet()) {
			String moduleType = entry.getKey();
			String className = entry.getValue().getProcessClass();
			int threadNum = entry.getValue().getThreadNum();
			Class<AbstractLogFileScan> classScan = null;
			try {
				classScan = (Class<AbstractLogFileScan>) Class
						.forName(className);
			} catch (ClassNotFoundException e1) {
				log.error("构造日志处理线程异常", e1);
				continue;
			}
			List<LogFileConfigure> confList = null;
			try {
				confList = getLogFileConfigureDAO().getFileListByModuleType(moduleType);
				entry.getValue().setFileList(confList);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.error("构造模块列表异常", e1);
			}
			AbstractLogFileScan scan = null;
			try {
				scan = classScan.newInstance();
				scan.setSchedule(this);
				LogFileScanMap.put(moduleType, scan);
				scan.init(moduleType, threadNum, confList,configure.getScan_interval(),entry.getValue());
				JMXManager.getInstance().addObject(scan);
			} catch (InstantiationException e) {
				log.error("构造日志处理线程异常", e);
			} catch (IllegalAccessException e) {
				log.error("构造日志处理线程异常", e);
			}

		}
		log.debug("扫描日志线程启动完毕");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (bRun) {
			whattodo();
			synchronized (this) {
				try {
					wait(WRITE_INTERVAL);
				} catch (InterruptedException e) {
					log.error("日志记录线程被打断异常", e);
				}
			}
		}
	}

	private void whattodo() {
		log.debug("调度线程无事可做.");

	}

	/**
	 * 获取变量<code>configure</code>的值
	 * @return 返回的数据类型是<code>MaintenanceMonitorConfigure</code>
	 */
	public MaintenanceMonitorConfigure getConfigure() {
		return configure;
	}

	/**
	 * 设置变量<code> configure</code> 的值
	 * @param configure  <code>configure</code> 参数类型是<code>MaintenanceMonitorConfigure</code>
	 */
	public void setConfigure(MaintenanceMonitorConfigure configure) {
		this.configure = configure;
	}

	/**
	 * 获取变量<code>alarmDao</code>的值
	 * @return 返回的数据类型是<code>AlarmHistoryDAO</code>
	 */
	public AlarmHistoryDAO getAlarmDao() {
		return alarmDao;
	}

	/**
	 * 设置变量<code> alarmDao</code> 的值
	 * @param alarmDao  <code>alarmDao</code> 参数类型是<code>AlarmHistoryDAO</code>
	 */
	public void setAlarmDao(AlarmHistoryDAO alarmDao) {
		this.alarmDao = alarmDao;
	}

	/**
	 * 获取变量<code>messageService</code>的值
	 * @return 返回的数据类型是<code>IMessageService</code>
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * 设置变量<code> messageService</code> 的值
	 * @param messageService  <code>messageService</code> 参数类型是<code>IMessageService</code>
	 */
	public void setMessageService(IMessageService messageService) {
		
		this.messageService = messageService;
	}

	/**
	 * 获取变量<code>mailService</code>的值
	 * @return 返回的数据类型是<code>IMailService</code>
	 */
	public IMailService getMailService() {
		return mailService;
	}

	/**
	 * 设置变量<code> mailService</code> 的值
	 * @param mailService  <code>mailService</code> 参数类型是<code>IMailService</code>
	 */
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}

}
