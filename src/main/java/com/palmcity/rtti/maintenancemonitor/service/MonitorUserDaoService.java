package com.palmcity.rtti.maintenancemonitor.service;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.dao.impl.BaseXmlDaoService;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;
/**
 * <p>
 * MonitorUserDaoService.java
 * </p>
 * 用户管理持久层服务接入类
 * @author <a href="mailto:zhouhuabin@ctfo.com">周华彬</a>
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th><th width="100px">动作</th><th
 *          width="100px">修改人</th><th width="100px">修改时间</th>
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
public class MonitorUserDaoService extends BaseXmlDaoService {
	
	private MonitorUserDAO monitorUserDAO;
	/** 配置类 */
	private MaintenanceMonitorConfigure configure = null;
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
					log.info(eRequest.asXML());
					Map<String, Object> paramMap = getParamMap((Element) eRequest.elements().get(0));
					//Map<String, Object> paramMap = getParamMap(eRequest);
					Class<? extends Object> daoClass = getMonitorUserDAO().getClass();
					@SuppressWarnings("unchecked")
					List<MonitorUser> listbase = (List<MonitorUser>) daoClass.getDeclaredMethod(
							Constants.SYS_OPERATOR_LOGIN, Map.class).invoke(getMonitorUserDAO(), paramMap);
					String password=(String) paramMap.get("password");
					
					if (null != listbase && listbase.size() > 0) {
						for (MonitorUser base : listbase) {
							if(password.equals(base.getPassword())){
								Element element0 = base.toElement();
								element0.addAttribute("result", "1");
								element0.addAttribute("msg", "登陆成功!");
								resData.add(element0);
								session.setAttribute(Constants.SESSION_NAME, base);
							}else{
								Element element = DocumentHelper.createElement(eRequest.getName().toLowerCase());
								element.addAttribute("result", "-2");
								element.addAttribute("msg", "登陆失败，密码错误!");
								resData.add(element);
								return null;
							}
						}
					} else {
						Element element = DocumentHelper.createElement(eRequest.getName().toLowerCase());
						element.addAttribute("result", "-1");
						element.addAttribute("msg", "登陆失败，帐户不存在!");
						resData.add(element);
						return null;
					}
					return session;
		}

	/**
	 *  系统用户增加
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
	public void addMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "insertDetail" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
		//更新邮件、短信接收列表
		getConfigure().setSms_receivers("");
		getConfigure().setMail_receivers("");
	}
	
	/**
	 * 系统用户修改
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
	public void modifyMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "updateByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
		//更新邮件、短信接收列表
		getConfigure().setSms_receivers("");
		getConfigure().setMail_receivers("");
	}
	/**
	 * 修改当前用户密码 
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
	public void changePasswordMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "changePasswordByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
	}
	
	/**
	 * 系统用户删除
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
	public void deleteMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "deleteByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToMapXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
		//更新邮件、短信接收列表
		getConfigure().setSms_receivers("");
		getConfigure().setMail_receivers("");
	}
	
	/**
	 * 系统用户详情
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
	public void detailMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "detailByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToBeanXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
	}
	
	/**
	 * 系统用户列表
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
	public void queryListMonitorUser(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "queryListByCondition" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToListBeanJson(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
	}
	
	/**
	 * 系统用户是否存在
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
	public void checkMonitorUserId(Element eRequest, Element resData, SessionMap sessio) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		String[] paramMethod = { "detailByPrimaryKey" };// daomethod
		String[] paramXML = {};// 输出xml属性
		eRequest=(Element) eRequest.elements().get(0);
		callDAOToBeanXML(monitorUserDAO, paramMethod, paramXML, eRequest, resData);
		//应该检测是否查询到记录
		//TODO
	}
	
	

	/**
	 * 获取变量<code>monitorUserDAO</code>的值
	 * @return 返回的数据类型是<code>MonitorUserDAO</code>
	 */
	public MonitorUserDAO getMonitorUserDAO() {
		return monitorUserDAO;
	}

	/**
	 * 设置变量<code> monitorUserDAO</code> 的值
	 * @param monitorUserDAO  <code>monitorUserDAO</code> 参数类型是<code>MonitorUserDAO</code>
	 */
	public void setMonitorUserDAO(MonitorUserDAO monitorUserDAO) {
		this.monitorUserDAO = monitorUserDAO;
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

}
