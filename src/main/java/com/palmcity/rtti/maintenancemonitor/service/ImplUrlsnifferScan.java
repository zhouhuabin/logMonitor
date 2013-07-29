/**
 * <p>文件名:		ImplReceiveLogFileScan.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.caits.lbs.framework.utils.GlobalTime;
import com.caits.lbs.framework.utils.JsonUtil;
import com.caits.lbs.framework.utils.StringUtils;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;

/**
 * <p>
 * ImplReceiveLogFileScan
 * </p>
 * <p>
 * 用途：转发日志文件行处理实现类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-7-18
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
 *          <td>2011-7-18 下午3:44:25</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-18 下午3:44:25</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class ImplUrlsnifferScan extends AbstractLogFileScan {

	/**
	 * 构造器 
	 */
	public ImplUrlsnifferScan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImplUrlsnifferScan(List<LogFileConfigure> cfList) {
		super(cfList);
	}
	@Override
	public void scanFile(URL url, String moduleType, LogFileConfigure conf)
			throws MaintenanceMonitorException {
		try {
//			UrlResource resource = new UrlResource(url);
			URLConnection  urlCon = url.openConnection();
			urlCon.setConnectTimeout(3000);
			InputStream is = urlCon.getInputStream();
			/**判断日志文件的最后修改时间**/
			if (urlCon.getContentLength() != conf.getContentLength()) {
				log.info(String.format("模块%s日志文件%s有更新，time=%d",
						conf.getModuleDesc(), url,urlCon.getLastModified()/1000));
				conf.setContentLength(urlCon.getContentLength());
				String line="{"+'"'+"fileSize"+'"'+':'+urlCon.getContentLength()+"}";
				parseLogLine(line, conf.getModuleType(), conf);
				is.close();
			} else {
				log.warn(String.format("模块%s日志文件%s无更新，直接跳过.",
						conf.getModuleDesc(), url));
			}
			/**selfCheck为设置文件1小时以上无更新报警**/
			selfCheck(conf);
		} catch (MalformedURLException e) {
			/**监测到文件不存在报警，并且进行报警处理**/
			String error_msg = String.format("模块%s日志url错误." , conf.getModuleDesc());
			String ret_msg = dealErrorTimes(error_msg, conf.getModuleType(), conf);

			throw new MaintenanceMonitorException(ret_msg, e);
		} catch (IOException e) {
			String error_msg = String.format("模块%s访问日志url出错.",conf.getModuleDesc());
			String ret_msg = dealErrorTimes(error_msg, conf.getModuleType(), conf);
			throw new MaintenanceMonitorException(ret_msg, e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan#parseLogLine
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void parseLogLine(String line, String moduleType,LogFileConfigure conf) {
		log.debug(String.format("模块%s开始处理日志行%s" ,conf.getModuleDesc(), line));
		org.springframework.util.Assert.state(StringUtils.notNullOrBlank(line),
				String.format("模块%s日志行为空，直接跳过.url=%s" ,conf.getModuleDesc(), conf.getUrl()));
		// log.debug("JSONObject entry(key:value)=" + obj.toString());
		MonitorLogData data = buildBean(line,conf);
		/**按照datetime判断模块是否更新,更新返回true**/
		if (conf.compareLastObj(data)) {
			alarmAnalyse(data, conf);
			/*if(conf.getLastObj().getAlarm_status()==1&&data.getAlarm_status()==1)
			{
				data.setAlarm_time((int)conf.getLastObj().getAlarm_time());
			}*/
			conf.setLastObj(setDataStatus(data, conf));
		}
	}
	
	public MonitorLogData buildBean(String obj, LogFileConfigure conf) {
		if (conf.isEnable()) {
			MonitorLogData data = (MonitorLogData) JsonUtil.getObjectFromJsonString(obj,
					conf.groovyConditionClass);
			if(data == null){
				conf._log.error(String.format("模块%s解析日志行出错,将采用模默认类型代替,line=%s",conf.getModuleCode(),obj));
				data = new MonitorLogData();
			}
			data.setModuleType(conf.getModuleType());
			data.setModuleCode(conf.getModuleCode());
			data.setModuleDesc(conf.getModuleDesc());
			data.setHot_x(conf.getHot_x());
			data.setHot_y(conf.getHot_y());
			data.setSmsAlarm(conf.getSmsAlarm());
			data.setMailAlarm(conf.getMailAlarm());
			data.setErroValue(conf.getErroValue());
			data.setEncoding(conf.getEncoding());
			data.setCarCount_max(conf.getCarCount_max());
			data.setRelationAlarm(conf.getRelationAlarm());
			data.setMapCode(conf.getMapCode());
			data.setUrl(conf.getUrl());
			data.setAlarmCondition(conf.getAlarmCondition());
			data.setDateTime(GlobalTime.systemTimeUtc());
			data.setText("运行正常");
			return data;
		} else{
			conf._log.error(String.format("模块%s的日志配置类不能正常工作，将转换为通用类",conf.getModuleCode()));
			MonitorLogData data = (MonitorLogData) JsonUtil.getObjectFromJsonString(obj,
					MonitorLogData.class);
			data.setModuleType(conf.getModuleType());
			data.setModuleCode(conf.getModuleCode());
			data.setModuleDesc(conf.getModuleDesc());
			return data;
		}
	}
	

}
