/**
 * <p>文件名:		MonitorRealTimeService.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.Assert;

import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.ClassUtils;
import com.caits.lbs.framework.utils.GlobalTime;
import com.caits.lbs.framework.utils.JsonUtil;
import com.common.ajax.server.IRequest;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.LogTypeConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;

/**
 * <p>
 * MonitorRealTimeService
 * </p>
 * <p>
 * 用途：实时检测本地服务实现类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-9-1
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
 *          <td>2011-9-1 上午11:18:57</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-9-1 上午11:18:57</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MonitorRealTimeService implements IRequest {

	/** 配置类 */
	private MaintenanceMonitorConfigure configure = null;

	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	/** 模块处理 */
	private LogFileConfigureDAO logFileConfigureDAO;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.common.ajax.server.IRequest#request(org.dom4j.Document,
	 * com.common.ajax.server.SessionMap)
	 */
	@Override
	public Document request(Document doc, SessionMap session)
			throws SecurityException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, SQLException,
			RuntimeException {
		// request
			Element eRequest = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> lstRequestChild = eRequest.elements();
			Element data = (Element) lstRequestChild.get(0).elements().get(0);
			String type = lstRequestChild.get(0).attributeValue(IRequest.XML_TYPE);
			String server = eRequest.attributeValue(IRequest.XML_SERVER);
			// response
			Element resResponse = DocumentHelper.createElement(IRequest.XML_RESPONSE);
			Element resData = DocumentHelper.createElement(IRequest.XML_DATA);
			resResponse.add(resData);
			resData.addAttribute(IRequest.XML_TYPE, type);
			resData.addAttribute("result", "1");
			resResponse.addAttribute(IRequest.XML_SERVER, server);
			Assert.notNull(getConfigure(),"实时监测处理时配置类不能为空.请检查配置文件applicationContext-maintenanceMonitor.xml");
			if("getLastStatus".equalsIgnoreCase(type)){
				getLastStatus(resData);
			}else if("getConfigure".equalsIgnoreCase(type)){
				getConfigure(resData);
			}
			else if("getLastStatusByMapCode".equalsIgnoreCase(type))
			{
				getLastStatusByMapCode(data, resData, session);
			}
			else if("getLastStatusByModuleCode".equalsIgnoreCase(type))
			{
				getLastStatusByModuleCode(data, resData, session);
			}
			Document responseDoc = DocumentHelper.createDocument(resResponse);
		return responseDoc;
	}
	/**
	 * 获取所有模块的配置信息 
	 * @param resData
	 */
	protected void getConfigure(Element resData) {
		Map<String,LogTypeConfigure> confMap = getConfigure().getConfMap();
		
		for(String moduleType : confMap.keySet()){
			LogTypeConfigure logType = confMap.get(moduleType);
			List<LogFileConfigure> list = logType.getFileList();
			for(LogFileConfigure conf : list){
				Element confElement = conf.toElement();
				resData.add(confElement);
			}
			log.info("获取所有模块的配置文件成功");
		}
	}
	/**
	 * 根据mapCode获取所有模块的最新状态 
	 * @param resData
	 */
	protected void getLastStatusByMapCode(Element eRequest, Element resData, SessionMap sessio) {
		String mapCode = eRequest.attributeValue("mapCode");
		Map<String,LogTypeConfigure> confMap = getConfigure().getConfMap();
		List<MonitorLogData> dataList = new ArrayList<MonitorLogData>();
		for(String moduleType : confMap.keySet()){
			LogTypeConfigure logType = confMap.get(moduleType);
			
			List<LogFileConfigure> list = logType.getFileList();
			for(LogFileConfigure conf : list){
//				Element monitorLogData = conf.getLastObj().toElement();
				if(conf.getMapCode().equals(mapCode))
				{
					dataList.add(conf.getLastObj());
				}
//				resData.add(monitorLogData);
			}
		}
		//json数组格式输出
		String array = JsonUtil.getJsonStringFromObject(dataList);
		resData.addText(array.toString());
	}
	
	/**
	 * 获取单个模块的最新状态 
	 * @param resData
	 */
	protected void getLastStatusByModuleCode(Element eRequest, Element resData, SessionMap sessio) {
		String moduleCode = eRequest.attributeValue("moduleCode");
		String moduleType = eRequest.attributeValue("moduleType");

		AbstractLogFileScan scan = LogFileScanSchedule.LogFileScanMap.get(moduleType);

		Map<String,LogTypeConfigure> confMap = getConfigure().getConfMap();
		List<MonitorLogData> dataList = new ArrayList<MonitorLogData>();

			LogTypeConfigure logType = confMap.get(moduleType);
			List<LogFileConfigure> list = logType.getFileList();
			
			for(LogFileConfigure conf : list){
//				Element monitorLogData = conf.getLastObj().toElement();
				if(conf.getModuleCode().equals(moduleCode))
				{
					try {
						scan.scanFile(new URL(Base64Codec.decode(conf.getUrl())), conf.getModuleType(), conf);
					}
					 catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						log.error(String.format("模块%s扫描出错" ,conf.getModuleDesc(), conf.getUrl()), e);
					}
					dataList.add(conf.getLastObj());
				}
			}
			
		//json数组格式输出
		String array = JsonUtil.getJsonStringFromObject(dataList);
		resData.addText(array.toString());
	}
	/**
	 * 获取所有模块的最新状态 
	 * @param resData
	 */
	protected void getLastStatus(Element resData) {
		Map<String,LogTypeConfigure> confMap = getConfigure().getConfMap();
		List<MonitorLogData> dataList = new ArrayList<MonitorLogData>();
		for(String moduleType : confMap.keySet()){
			LogTypeConfigure logType = confMap.get(moduleType);
			List<LogFileConfigure> list = logType.getFileList();
			
			for(LogFileConfigure conf : list){
//				Element monitorLogData = conf.getLastObj().toElement();
				dataList.add(conf.getLastObj());
//				resData.add(monitorLogData);
			}
		}
		//json数组格式输出
		String array = JsonUtil.getJsonStringFromObject(dataList);
		resData.addText(array.toString());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.common.ajax.server.IRequest#request(java.lang.String,
	 * com.common.ajax.server.SessionMap)
	 */
	@Override
	public String request(String xmlStr, SessionMap session)
			throws SecurityException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, SQLException,
			RuntimeException {
		throw new IllegalAccessException("暂时未实现");
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
