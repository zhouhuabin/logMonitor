package com.palmcity.rtti.maintenancemonitor.service;

import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.dao.impl.BaseXmlDaoService;
import com.caits.lbs.framework.utils.ClassUtils;
import com.caits.lbs.framework.utils.StringUtils;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;
import com.palmcity.rtti.maintenancemonitor.service.LogFileScanSchedule;

import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;


/**
 * <p>MonitorModuleService</p>
 * <p>TODO</p>
 *
 * @author		冯明亮(fengmingliang@ctfo.com, 996036006@qq.com)
 * @version		0.0.0
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.0</td><td>创建类</td><td>fml</td><td>2011-12-19 上午9:25:25</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public class LogFileConfigureService extends BaseXmlDaoService {
	private LogFileConfigureDAO logFileConfigureDAO;
	
	private AlarmHistoryDAO alarmHistoryDAO;
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

	/** 配置类 */
	private MaintenanceMonitorConfigure configure;
	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	


	int i = 0;
	@Override
		public Object SysOperatorLogin(Element eRequest, Element resData,
				SessionMap session) throws IllegalArgumentException,
				SecurityException, IllegalAccessException,
				InvocationTargetException, NoSuchMethodException,
				ClassNotFoundException {
			// call DAO
					return session;
		}

	/**
	 *  系统模块增加
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void addLogFileConfigure(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "insertLogFileConfigure" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		updateCacheConfList("add",eRequest,paramMethod, paramXML, resData,sessio);
	}
	
	/**
	 * 模块修改
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void modifyLogFileConfigure(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "updateByModuleCode" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		updateCacheConfList("update",eRequest,paramMethod, paramXML, resData,sessio);
		
	}
	
	/**
	 * 模块删除
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void deleteLogFileConfigure(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "deleteByModuleCode" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		updateCacheConfList("delete",eRequest,paramMethod, paramXML, resData,sessio);
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
	
	
	public void updateCacheConfList(String method,Element eRequest,String[] paramMethod,String[] paramXML,Element resData,SessionMap sessio) throws Exception
	{
		//表单参数生成logFileConfigure
		
		Map<String, Object> map= getParamMap(eRequest);
		LogFileConfigure logFileConfigure = (LogFileConfigure) ClassUtils.map2Bean(map,
				LogFileConfigure.class);
			AbstractLogFileScan scan = LogFileScanSchedule.LogFileScanMap.get(logFileConfigure.getModuleType());
			List<LogFileConfigure> confList = null;
			confList = scan.getConfList();
			//如果类型相符合，将新的参数添加进list
			if(method.equals("add"))
			{
				callDAOToMapXML(logFileConfigureDAO, paramMethod, paramXML, eRequest, resData);
				
				System.out.println(resData.element("LogFileConfigure").asXML().toString());
				System.out.println(resData.element("LogFileConfigure").attributeValue("result").toString());
				if(!(resData.element("LogFileConfigure").attributeValue("result").toString()).equals("0"))
				{
					confList.add(logFileConfigure);
				}
			}
			else
			{
				for(int i=0; i<confList.size();i++){
					LogFileConfigure logFile=(LogFileConfigure)confList.get(i);
					if(method.equals("update")&&logFileConfigure.getModuleCode().equals(logFile.getModuleCode()))
					{
						//如果用户拖动则只修改坐标
						if(logFileConfigure.getMapCode()==null)
						{
							
							logFile.setHot_x(logFileConfigure.getHot_x());
							logFile.setHot_y(logFileConfigure.getHot_y());
							Map<String, Object> logFileMap=logFile.bean2map();
							Map<String, Object> resultMap=getLogFileConfigureDAO().updateByModuleCode(logFileMap);
							resData.add(MapToElement(eRequest,resultMap));
							confList.set(i, logFile);
							logFile.getLastObj().setHot_x(logFileConfigure.getHot_x());
							logFile.getLastObj().setHot_y(logFileConfigure.getHot_y());

							log.info(String.format("模块%s坐标已经更新",logFile.getModuleDesc()));
						}
						else
						{
							confList.set(i, logFileConfigure);
							callDAOToMapXML(logFileConfigureDAO, paramMethod, paramXML, eRequest, resData);
							if(!(resData.element("LogFileConfigure").attributeValue("result").toString()).equals("0"))
							{
								logFile.getLastObj().setModuleCode(logFileConfigure.getModuleCode());
								logFile.getLastObj().setModuleDesc(logFileConfigure.getModuleDesc());
								logFile.getLastObj().setUrl(logFileConfigure.getUrl());
								logFile.setSmsAlarm(logFileConfigure.getSmsAlarm());
								logFile.setMailAlarm(logFileConfigure.getMailAlarm());
								logFile.setEncoding(logFileConfigure.getEncoding());
								logFile.setCarCount_max(logFileConfigure.getCarCount_max());
							}
							
						}
					}
					if(method.equals("delete")&&logFileConfigure.getModuleCode().equals(logFile.getModuleCode()))
					{
						confList.remove(i);
						callDAOToMapXML(logFileConfigureDAO, paramMethod, paramXML, eRequest, resData);
						getAlarmHistoryDAO().deleteAlarmByModuleCode(logFile.getModuleCode());
					}
				}
			}
			/*System.out.println("confList  ************   "+confList.size());
*/
			//修改程序运行进程缓存list
			scan.setConfList(confList);
	}

	/**
	 * 字段 configure 获取函数
	 * @return the configure : MaintenanceMonitorConfigure
	 */
	public MaintenanceMonitorConfigure getConfigure() {
		return configure;
	}

	/**
	 * 字段 configure 设置函数 : MaintenanceMonitorConfigure
	 * @param configure the configure to set
	 */
	public void setConfigure(MaintenanceMonitorConfigure configure) {
		this.configure = configure;
	}
	
	public static Element MapToElement(Element eRequest,Map<String, Object> resultMap)
	{
		Element element = DocumentHelper.createElement(eRequest.getName());
		Set<String> keySet = resultMap.keySet();
			for (String key : keySet) {
				element.addAttribute(key.toLowerCase(),
						String.valueOf(resultMap.get(key)));
			}
			return element;
	}
	
}
