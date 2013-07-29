/**
 * <p>文件名:		MonitorRealTimeService.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.Constants;
import com.common.ajax.server.IRequest;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.LogTypeConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;
import com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO;

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
public class MonitorReportExcel implements IRequest {

	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());

	private AlarmHistoryDAO alarmHistoryDAO;
	
	private MaintenanceMonitorConfigure configure = null;

	private Map<String, String> ModuleTypeMap =new HashMap<String, String>();
	private Map<String, String> ModuleCodeMap=new HashMap<String, String>();

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
	/** 模块处理 */
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
			setModuleMap();
			if("GetHistoryFile".equalsIgnoreCase(type))
			{
				try {
					GetHistoryFile(data, resData, session);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Document responseDoc = DocumentHelper.createDocument(resResponse);
		return responseDoc;
	}
	/**
	 * 根据mapCode获取所有模块的最新状态 
	 * @param resData
	 * @throws IOException
	 */
	protected void GetHistoryFile(Element eRequest, Element resData, SessionMap sessio) throws IOException {
		String reportExcel="类型名,模块名,报警时间,报警内容,状态\r\n";
		String moduletype = eRequest.attributeValue("moduletype");
		String startTime = eRequest.attributeValue("startTime");
		String endTime = eRequest.attributeValue("endTime");
		String module_code = eRequest.attributeValue("module_code");
		String status = eRequest.attributeValue("status");
		String report_way = eRequest.attributeValue("report_way");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("moduletype", moduletype);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("module_code", module_code);
		map.put("status", status);
		map.put("report_way", report_way);
		List<AlarmHistory> list = null;
		try {
			list = getAlarmHistoryDAO().queryListByCondition(map);
		} catch (DataAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(AlarmHistory alarm:list)
		{
			reportExcel+=ModuleTypeMap.get(alarm.getModuletype())+","+ModuleCodeMap.get(alarm.getModule_code())+","+alarm.getAlarm_time()+","+(alarm.getAlarm_content().replace(",",";"))+","+getStatusName(alarm.getStatus())+",\r\n";
		}
		MonitorUser user = (MonitorUser) sessio.getAttribute(Constants.SESSION_NAME);
		String path=System.getProperty("user.dir")+"/";
		String fileName=user.getAccount()+".csv";
		File file = new File("webapps\\maintenanceMonitor\\"+fileName);
		try {
			createFile(file);
			writeTxtFile(reportExcel,file);
			log.info(String.format("生成历史记录文件%s成功",(file.toURI()).toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resData.addAttribute("msg",fileName);
	}
	
	/*
	 * (non-Javadoc)
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

	 public String getStatusName(int value)
		{
		String rv ="";
		if(value==0){
			rv = "无报警";
		}else if(value==1){
			rv = "报警中";
		}else if(value==2){
			rv = "已填写故障单";
		}else if(value==3){
			rv = "恢复正常";
		}else{
			rv = "误报"; 
		}
		return rv;
		};
	 /**
	 * 新建文件 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public  boolean createFile(File fileName)throws Exception{
		  boolean flag=false;
		  try{
			  if(fileName.exists()){
				   fileName.delete();
			   }
				fileName.createNewFile();
				flag=true;
		  }catch(Exception e){
		   e.printStackTrace();
			log.debug(String.format("创建Excel文件%s失败",(fileName.toURI()).toString()));
		  }
		  log.info(String.format("创建Excel文件%s成功",(fileName.toURI()).toString()));
		  return true;
		 } 
	
	/**
	 * 写入内容 
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public  boolean writeTxtFile(String content,File  fileName)throws Exception{
		  RandomAccessFile mm=null;
		  boolean flag=false;
		  FileOutputStream o=null;
		  try {
		   o = new FileOutputStream(fileName);
		      o.write(content.getBytes("GBK"));
		      o.close();
		   flag=true;
		  } catch (Exception e) {
		   // TODO: handle exception
				log.debug(String.format("写入文件%s失败",(fileName.toURI()).toString()));
		  }finally{
		   if(mm!=null){
		    mm.close();
		   }
		  }
		  log.info(String.format("写入文件%s成功",(fileName.toURI()).toString()));
		  return flag;
		 }	
	
	public void setModuleMap()
	{
		//获得模块类型的map
		ModuleTypeMap = getConfigure().getModuleNameMap();
		//获得模块的map
		Map<String,LogTypeConfigure> confMap = getConfigure().getConfMap();
		for(String moduleType : confMap.keySet()){
			LogTypeConfigure logType = confMap.get(moduleType);
			List<LogFileConfigure> list = logType.getFileList();
			if(list.size()>0)
			{
				for(LogFileConfigure conf : list){
					ModuleCodeMap.put(conf.getModuleCode(), conf.getModuleDesc());
				}
			}
		}
	}
}
