package com.palmcity.rtti.maintenancemonitor.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.dao.impl.BaseXmlDaoService;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;

/**
 * <p>
 * MonitorUserDaoService.java
 * </p>
 * 报警记录持久层服务接入类
 * 
 * @author <a href="mailto:zhouhuabin@ctfo.com">周华彬</a>
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th>
 *          <th width="100px">动作</th>
 *          <th * width="100px">修改人</th>
 *          <th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>周华彬</td>
 *          <td>2011-7-18 下午01:04:27</td>
 *          </tr>
 *          </table>
 */
public class AlarmHistoryDaoService extends BaseXmlDaoService {

	private AlarmHistoryDAO alarmHistoryDAO;

	/* (non-Javadoc)
	 * @see com.caits.lbs.framework.dao.impl.BaseXmlDaoService#SysOperatorLogin(org.dom4j.Element, org.dom4j.Element, com.common.ajax.server.SessionMap)
	 */
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
	 * 报警记录增加
	 * 
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
	public void addAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "insertDetail" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
	}

	/**
	 * 开始处理报警修改
	 * 
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
	public void modifyAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "updateByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
	}

	/**
	 * 结束报警处理修改
	 * 
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
	public void finishAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "finishByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
	}

	/**
	 * 报警记录删除
	 * 
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
	public void deleteAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "deleteByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
	}

	/**
	 * 获取报警记录详情
	 * 
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
	public void detailAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "detailByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
	}

	/**
	 * 查询报警记录列表
	 * 
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
	public void queryListAlarmHistory(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "queryListByCondition" };// daomethod
		String[] paramXML = {"moduletype","module_code","alarm_content","alarm_time","status"};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToListBeanJson(alarmHistoryDAO, paramMethod, paramXML, eRequest, resData);
		//TODO 将对象以json格式放在xml中返回.
		
	}

	/**
	 * 系统用户是否存在
	 * 
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
	public void checkMonitorUserId(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "detailByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);
		// 应该检测是否查询到记录
		// TODO
	}

	/**
	 * 根据条件统计报警总数
	 * 
	 * @param eRequest
	 * @param resData
	 * @param sessio
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	public void statisListByCondition(Element eRequest, Element resData,
			SessionMap sessio) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String[] paramMethod = { "statisListByCondition" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToListMapXML(alarmHistoryDAO, paramMethod, paramXML, eRequest,
				resData);

	}

	/**
	 * 获取变量<code>alarmHistoryDAO</code>的值
	 * 
	 * @return 返回的数据类型是<code>AlarmHistoryDAO</code>
	 */
	public AlarmHistoryDAO getAlarmHistoryDAO() {
		return alarmHistoryDAO;
	}

	/**
	 * 设置变量<code> alarmHistoryDAO</code> 的值
	 * 
	 * @param alarmHistoryDAO
	 *            <code>alarmHistoryDAO</code> 参数类型是<code>AlarmHistoryDAO</code>
	 */
	public void setAlarmHistoryDAO(AlarmHistoryDAO alarmHistoryDAO) {
		this.alarmHistoryDAO = alarmHistoryDAO;
	}

}
