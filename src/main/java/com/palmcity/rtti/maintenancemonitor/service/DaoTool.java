package com.palmcity.rtti.maintenancemonitor.service;

import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.Alarm_TicketDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.ModuleInfoDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.ModuleTypeDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorMapDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;

public class DaoTool {

	private static Alarm_TicketDAO alarm_TicketDao;
	
	private static AlarmHistoryDAO alarmHistoryDao;
		
	private static ModuleInfoDAO moduleInfoDao;
	
	private static ModuleTypeDAO moduleTypeDao;
	
	private static MonitorMapDAO monitorMapDao;
	
	private static MonitorUserDAO monitorUserDao;

	/**
	 * 获取变量<code>alarm_TicketDao</code>的值
	 * @return 返回的数据类型是<code>Alarm_TicketDAO</code>
	 */
	public static Alarm_TicketDAO getAlarm_TicketDao() {
		return alarm_TicketDao;
	}

	/**
	 * 设置变量<code> alarm_TicketDao</code> 的值
	 * @param alarm_TicketDao  <code>alarm_TicketDao</code> 参数类型是<code>Alarm_TicketDAO</code>
	 */
	public void setAlarm_TicketDao(Alarm_TicketDAO alarm_TicketDao) {
		this.alarm_TicketDao = alarm_TicketDao;
	}

	/**
	 * 获取变量<code>alarmHistoryDao</code>的值
	 * @return 返回的数据类型是<code>AlarmHistoryDAO</code>
	 */
	public static AlarmHistoryDAO getAlarmHistoryDao() {
		return alarmHistoryDao;
	}

	/**
	 * 设置变量<code> alarmHistoryDao</code> 的值
	 * @param alarmHistoryDao  <code>alarmHistoryDao</code> 参数类型是<code>AlarmHistoryDAO</code>
	 */
	public void setAlarmHistoryDao(AlarmHistoryDAO alarmHistoryDao) {
		this.alarmHistoryDao = alarmHistoryDao;
	}

	/**
	 * 获取变量<code>moduleInfoDao</code>的值
	 * @return 返回的数据类型是<code>ModuleInfoDAO</code>
	 */
	public static ModuleInfoDAO getModuleInfoDao() {
		return moduleInfoDao;
	}

	/**
	 * 设置变量<code> moduleInfoDao</code> 的值
	 * @param moduleInfoDao  <code>moduleInfoDao</code> 参数类型是<code>ModuleInfoDAO</code>
	 */
	public void setModuleInfoDao(ModuleInfoDAO moduleInfoDao) {
		this.moduleInfoDao = moduleInfoDao;
	}

	/**
	 * 获取变量<code>moduleTypeDao</code>的值
	 * @return 返回的数据类型是<code>ModuleTypeDAO</code>
	 */
	public static ModuleTypeDAO getModuleTypeDao() {
		return moduleTypeDao;
	}

	/**
	 * 设置变量<code> moduleTypeDao</code> 的值
	 * @param moduleTypeDao  <code>moduleTypeDao</code> 参数类型是<code>ModuleTypeDAO</code>
	 */
	public void setModuleTypeDao(ModuleTypeDAO moduleTypeDao) {
		this.moduleTypeDao = moduleTypeDao;
	}

	/**
	 * 获取变量<code>monitorMapDao</code>的值
	 * @return 返回的数据类型是<code>MonitorMapDAO</code>
	 */
	public static MonitorMapDAO getMonitorMapDao() {
		return monitorMapDao;
	}

	/**
	 * 设置变量<code> monitorMapDao</code> 的值
	 * @param monitorMapDao  <code>monitorMapDao</code> 参数类型是<code>MonitorMapDAO</code>
	 */
	public void setMonitorMapDao(MonitorMapDAO monitorMapDao) {
		this.monitorMapDao = monitorMapDao;
	}

	/**
	 * 获取变量<code>monitorUserDao</code>的值
	 * @return 返回的数据类型是<code>MonitorUserDAO</code>
	 */
	public static MonitorUserDAO getMonitorUserDao() {
		return monitorUserDao;
	}

	/**
	 * 设置变量<code> monitorUserDao</code> 的值
	 * @param monitorUserDao  <code>monitorUserDao</code> 参数类型是<code>MonitorUserDAO</code>
	 */
	public void setMonitorUserDao(MonitorUserDAO monitorUserDao) {
		this.monitorUserDao = monitorUserDao;
	}

	
}
