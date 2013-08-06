package com.palmcity.rtti.maintenancemonitor.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.dao.impl.BaseXmlDaoService;
import com.caits.lbs.framework.utils.JsonUtil;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.Alarm_Ticket;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;
import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.Alarm_TicketDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;

public class MonitorAlarm_TicketService  extends BaseXmlDaoService {
	
	private Alarm_TicketDAO alarm_TicketDAO;
	private AlarmHistoryDAO alarmHistoryDAO;
	
	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	/**
	 * 字段 alarmHistoryDAO 获取函数
	 * @return the alarmHistoryDAO : AlarmHistoryDAO
	 */
	public AlarmHistoryDAO getAlarmHistoryDAO() {
		return alarmHistoryDAO;
	}

	/**
	 * 字段 alarmHistoryDAO 设置函数 : AlarmHistoryDAO
	 * @param alarmHistoryDAO the alarmHistoryDAO to set
	 */
	public void setAlarmHistoryDAO(AlarmHistoryDAO alarmHistoryDAO) {
		this.alarmHistoryDAO = alarmHistoryDAO;
	}

	int i = 0;
	@Override
		public Object SysOperatorLogin(Element eRequest, Element resData,
				SessionMap session) throws IllegalArgumentException,
				SecurityException, IllegalAccessException,
				InvocationTargetException, NoSuchMethodException,
				ClassNotFoundException {
			
					return session;
		}

	/**
	 *  故障单增加
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public void addAlarm_Ticket(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "insertDetail" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		String flag=eRequest.attributeValue("flag");
		String moduleCode = eRequest.attributeValue("moduleCode");
		String moduleType=eRequest.attributeValue("moduleType");
		String alarm_time=eRequest.attributeValue("alarm_time");
		
		AbstractLogFileScan scan = LogFileScanSchedule.LogFileScanMap.get(moduleType);
		List<MonitorLogData> dataList = new ArrayList<MonitorLogData>();
		//List<LogFileConfigure> confList=scan.getConfList();
		/*List<LogFileConfigure1> confList=null;

		*//**0为正常报警，1为误报**//*
		if(flag.equals("0"))
		{
			callDAOToMapXML(alarm_TicketDAO, paramMethod, paramXML, eRequest, resData);
		}
		for(LogFileConfigure1 logFile:confList)
		{
			if((logFile.getModuleCode()).equalsIgnoreCase(moduleCode))
			{
				//scan.startDealAlarm(logFile, sessio,flag);
				dataList.add(logFile.getLastObj());
			}
		}*/
		String array = JsonUtil.getJsonStringFromObject(dataList);
		resData.addText(array.toString());
	}
	/**
	 * 修改历史故障表状态
	 * 
	public void setAlarmStatus(String alarmTime ,String moduleCode)
	{
		try {
			AlarmHistory alarm=getAlarmHistoryDAO().detailByPrimaryKey(moduleCode, Integer.parseInt(alarmTime));
			alarm.setStatus(AlarmHistory.ALARM_STATUS_DEALING);
			getAlarmHistoryDAO().updateByPrimaryKey(alarm);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	/**
	 * 字段 alarm_TicketDAO 获取函数
	 * @return the alarm_TicketDAO : Alarm_TicketDAO
	 */
	public Alarm_TicketDAO getAlarm_TicketDAO() {
		return alarm_TicketDAO;
	}

	/**
	 * 字段 alarm_TicketDAO 设置函数 : Alarm_TicketDAO
	 * @param alarm_TicketDAO the alarm_TicketDAO to set
	 */
	public void setAlarm_TicketDAO(Alarm_TicketDAO alarm_TicketDAO) {
		this.alarm_TicketDAO = alarm_TicketDAO;
	}

	/**
	 * 故障单详情
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public void detailAlarm_Ticket(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		eRequest=(Element) eRequest.elements().get(0);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("alarm_time", eRequest.attributeValue("alarm_time"));
		paramMap.put("moduleCode", eRequest.attributeValue("moduleCode"));
		List<Alarm_Ticket> Alarm_TicketList=null;
		try {
			Alarm_TicketList=getAlarm_TicketDAO().detailByPrimaryKey(paramMap);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String array = JsonUtil.getJsonStringFromObject(Alarm_TicketList);
		resData.addText(array.toString());
	}
}
