/**
 * <p>文件名:		LogFileScanImpl.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.caits.lbs.framework.log.CommonLogFactory;
import com.caits.lbs.framework.services.jmx.JMXManager;
import com.caits.lbs.framework.services.mail.IMailService;
import com.caits.lbs.framework.services.sms.IMessageService;
import com.palmcity.rtti.maintenancemonitor.api.ModuleData;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleInfo;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleType;
import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;
import com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan.LogFileScanThread;

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
	public static Logger log = CommonLogFactory.getLog();

	/** 是否运行标志 */
	private boolean bRun = true;

	/** 配置类 */
	private static MaintenanceMonitorConfigure configure = null;

	/** 报警记录数据访问接口 */
	private static AlarmHistoryDAO alarmDao;

	/** 短信服务接口 */
	private static IMessageService messageService;
	
	/** 短信服务接口 */
	private static IMailService mailService;
	
	/** 正在运行的LogFileScan实例map */
	static Map<Integer,AbstractLogFileScan> LogFileScanMap= new HashMap<Integer,AbstractLogFileScan>();
	
	
	/** 模块map,key为模板ID，value为模板下的模块列表 */
	static HashMap<Integer, ArrayList<ModuleInfo>> moduleInfoMapByTypeId=new HashMap<Integer, ArrayList<ModuleInfo>>();
	
	/** 模板map,key为模板ID,value为模板对象 */
	static HashMap<Integer, ModuleType> moduleTypeMap=new HashMap<Integer, ModuleType>();
	
	/** 缓存各城市实时监测数据的map,key为城市名.数据体key为模块id,value为实时数据 */
	static	ConcurrentHashMap <String, HashMap<Integer,ModuleData>> moduleDateMap=new ConcurrentHashMap <String, HashMap<Integer,ModuleData>>() ;
	
	
	/** js文件地址 */
	static String jsurl;
	
	
	/** 短信，邮件收件人 */
	static String receiveMobile;
	static String receiveEmail;
	
	
	/** 日志文件不存在报警map,key为模板ID,value为模板下找不到日志文件的各模块名称 */
	static HashMap<Integer, String> logNotExistMap=new HashMap<Integer,String>();
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
	public static  boolean addLogFileScan(int moduleTypeId,AbstractLogFileScan logFileScan) {
			LogFileScanMap.put(moduleTypeId, logFileScan);
			return true;
	}
	/**
	 * FIXME 
	 */
	@SuppressWarnings("unchecked")
	public static void init() {
		log.debug("任务线程开始扫描日志文件");
		setModuleTypeMap();
		setModuleInfoMap();
		for(int moduleTypeId:moduleTypeMap.keySet())
		{
			AbstractLogFileScan scan = new ImplLogFileScan();
			LogFileScanMap.put(moduleTypeId, scan);
			if(moduleInfoMapByTypeId.containsKey(moduleTypeId)&&moduleInfoMapByTypeId.get(moduleTypeId).size()>0)
				scan.init(moduleTypeMap.get(moduleTypeId), moduleInfoMapByTypeId.get(moduleTypeId),moduleTypeMap.get(moduleTypeId).getThreadInterval());
			JMXManager.getInstance().addObject(scan);
		}
		log.debug("扫描日志线程启动完毕");
	}
	
	public static void init(int moduleTypeId) {
		log.debug(String.format("%d任务线程开始扫描日志文件", moduleTypeId));
		setModuleTypeMap();
		setModuleInfoMap();
		AbstractLogFileScan scan = new ImplLogFileScan();
		LogFileScanMap.put(moduleTypeId, scan);
		if(moduleInfoMapByTypeId.containsKey(moduleTypeId)&&moduleInfoMapByTypeId.get(moduleTypeId).size()>0)
			scan.init(moduleTypeMap.get(moduleTypeId), moduleInfoMapByTypeId.get(moduleTypeId),moduleTypeMap.get(moduleTypeId).getThreadInterval());
		JMXManager.getInstance().addObject(scan);
		log.debug(String.format("%d扫描日志线程启动完毕", moduleTypeId));
	}
	
	/**
	 * 取得各个类别的模块列表
	 * @return
	 */
	public static void setModuleTypeMap()
	{
		try {
			List<ModuleType> moduleTypeList=DaoTool.getModuleTypeDao().queryModuleTypeList(null);			
			//如果是更新缓存
			if(!moduleTypeMap.isEmpty())
			{
				moduleTypeMap.clear();
			}
			for(int i=0;i<moduleTypeList.size();i++)
			{
				moduleTypeMap.put(moduleTypeList.get(i).getModule_Type_Id(), moduleTypeList.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 取得各个类别的模块列表
	 * @return
	 */
	public static void setModuleInfoMap()
	{
		try {
			List<ModuleInfo> moduleInfoList=DaoTool.getModuleInfoDao().queryModuleInfoList(null);
				moduleInfoMapByTypeId.clear();
			for(int i=0;i<moduleInfoList.size();i++)
			{
				int modeleTypeId=moduleInfoList.get(i).getModule_Type_Id();
				if(moduleInfoMapByTypeId.get(modeleTypeId)==null)
				{
					ArrayList<ModuleInfo> ModuleInfolist=new ArrayList<ModuleInfo>();
					moduleInfoMapByTypeId.put(modeleTypeId, ModuleInfolist);
				}
				moduleInfoMapByTypeId.get(modeleTypeId).add(moduleInfoList.get(i));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关闭现有线程并且重新启动所有线程 
	 */
	public static void restartScanThread()
	{
		for(int moduleTypeId:LogFileScanMap.keySet())
		{
			ArrayList<LogFileScanThread> threadList=LogFileScanMap.get(moduleTypeId).getThreadlist();
			for(int i=0;i<threadList.size();i++)
			{
				try {
					threadList.get(i).setbRun(false);
					threadList.get(i).interrupt();
				} catch (Exception e) {
					log.error("终端线程出错"+e.getLocalizedMessage());
				}
			}
		}
		LogFileScanSchedule.moduleDateMap.clear();
		LogFileScanSchedule.init();
	}
	
	
	/**
	 * 重新启动指定ID的线程 
	 * @param moduleTypeId
	 */
	public static void restartScanThread(int moduleTypeId)
	{
		ArrayList<LogFileScanThread> threadList=LogFileScanMap.get(moduleTypeId).getThreadlist();
		for(int i=0;i<threadList.size();i++)
		{
			try {
				threadList.get(i).setbRun(false);
				threadList.get(i).interrupt();
			} catch (Exception e) {
				log.error("终端线程出错"+e.getLocalizedMessage());
			}
		}
		LogFileScanSchedule.moduleDateMap.clear();
		LogFileScanSchedule.init(moduleTypeId);
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
	public static AlarmHistoryDAO getAlarmDao() {
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
	public static IMessageService getMessageService() {
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
	public static IMailService getMailService() {
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

	/**
	 * 获取变量<code>receiveMobile</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getReceiveMobile() {
		return receiveMobile;
	}



	/**
	 * 设置变量<code> receiveMobile</code> 的值
	 * @param receiveMobile  <code>receiveMobile</code> 参数类型是<code>String</code>
	 */
	public void setReceiveMobile(String receiveMobile) {
		LogFileScanSchedule.receiveMobile = receiveMobile;
	}



	/**
	 * 获取变量<code>receiveEmail</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getReceiveEmail() {
		return receiveEmail;
	}



	/**
	 * 设置变量<code> receiveEmail</code> 的值
	 * @param receiveEmail  <code>receiveEmail</code> 参数类型是<code>String</code>
	 */
	public void setReceiveEmail(String receiveEmail) {
		LogFileScanSchedule.receiveEmail = receiveEmail;
	}



	/**
	 * 获取变量<code>jsurl</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public  String getJsurl() {
		return jsurl;
	}



	/**
	 * 设置变量<code> jsurl</code> 的值
	 * @param jsurl  <code>jsurl</code> 参数类型是<code>String</code>
	 */
	public void setJsurl(String jsurl) {
		LogFileScanSchedule.jsurl = jsurl;
	}

	
}
