package com.palmcity.rtti.maintenancemonitor.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.caits.lbs.framework.dao.impl.BaseXmlDaoService;
import com.caits.lbs.framework.utils.JsonUtil;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorMapDAO;


/**
 * <p>MonitorModuleService</p>
 * <p>拓扑图处理</p>
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
public class MonitorMapDaoService extends BaseXmlDaoService {
	private MonitorMapDAO monitorMapDAO;
	
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
	 *  系统拓扑图增加
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void insertMap(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "insertMap" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorMapDAO, paramMethod, paramXML, eRequest, resData);
	}
	
	/**
	 * 拓扑图修改
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void updateMap(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "updateMapByMapCode" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorMapDAO, paramMethod, paramXML, eRequest, resData);
		
	}
	
	/**
	 * 拓扑图删除
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void deleteMap(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		String[] paramMethod = { "deleteMapByMapCode" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorMapDAO, paramMethod, paramXML, eRequest, resData);
	}
	/**
	 * 拓扑图列表查询，返回JSON对象
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws Exception 
	 */
	public void queryMapList(Element eRequest, Element resData, SessionMap sessio) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		eRequest=(Element) eRequest.elements().get(0);
		List<MonitorMap> MapList=null;
		MapList=getMonitorMapDAO().queryMapList(map);
		
		String array = JsonUtil.getJsonStringFromObject(MapList);
		resData.addText(array.toString());
	}
	

	/**
	 * 字段 logFileConfigureDAO 获取函数
	 * @return the logFileConfigureDAO : LogFileConfigureDAO
	 */
	public MonitorMapDAO getMonitorMapDAO() {
		return monitorMapDAO;
	}

	/**
	 * 字段 logFileConfigureDAO 设置函数 : LogFileConfigureDAO
	 * @param logFileConfigureDAO the logFileConfigureDAO to set
	 */
	public void setMonitorMapDAO(MonitorMapDAO monitorMapDAO) {
		this.monitorMapDAO = monitorMapDAO;
	}
	
}
