/**
 * <p>文件名:		LogFileScanImpl.java</p>
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.log.CommonLogFactory;
import com.caits.lbs.framework.services.jmx.ILogSetable;
import com.caits.lbs.framework.services.sms.MessageSMS;
import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.GlobalTime;
import com.caits.lbs.framework.utils.StringUtils;
import com.caits.lbs.framework.utils.SystemHelper;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.LogTypeConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

/**
 * <p>
 * LogFileScanImpl
 * </p>
 * <p>
 * 用途：某类模块的日志文件扫描抽象实现类
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
public abstract class AbstractLogFileScan implements ILogFileScan,ILogSetable {

	/** 数据无效时间阀值秒 */
	public static final int INVALID_DATATIME_THRESHOLD =60*60;

	/** 写入文件间隔时间 */
	private int WRITE_INTERVAL = 60 * 1000;

	/** 日志记录器 */
	Logger log = CommonLogFactory.getLog();

	/** 日志配置列表 */
	private List<LogFileConfigure> confList;

	/** 模块类型 */
	private String moduleType;

	/** 错误类型计数器 */
	protected ErrorTimes errorTimes = new ErrorTimes();

	/** 记录每个moduleCode的索引位置 */
	private Map<String, Integer> indexMap = new HashMap<String, Integer>();

	/** 日志类型配置类 */
	private LogTypeConfigure logTypeConfigure;

	/** 调度类对象，以获取其他服务对象 */
	private LogFileScanSchedule schedule;

	/** 短信消息体 */
	private MessageSMS message = new MessageSMS();

	private AbstractLogFileScan  abstractLogFileScan;
	
	public static  Vector<MessageSMS> messageVector=new Vector<MessageSMS>();
	
	/**
	 * 构造器
	 */
	public AbstractLogFileScan() {
	}

	/**
	 * 构造器
	 * 
	 * @param conf_process
	 */
	public AbstractLogFileScan(List<LogFileConfigure> cfList) {
		setConfList(cfList);

	}

	/**
	 * 日志文件扫描线程初始化方法
	 * 
	 * @param moduleType
	 * @param threaNum
	 * @param confList
	 * @param scan_interval
	 * @param logTypeConfigure
	 */
	public void init(String moduleType, int threaNum,
			List<LogFileConfigure> confList, int scan_interval,
			LogTypeConfigure logTypeConfigure) {
		setModuleType(moduleType);
		setLogTypeConfigure(logTypeConfigure);
		setConfList(confList);
		WRITE_INTERVAL = scan_interval;
		log.debug("开始启动日志扫描线程,moduleType=" + moduleType);

		for (int i = 0; i < threaNum; i++) 
		{
			LogFileScanThread thread = new LogFileScanThread(i);
			thread.start();
		}

		log.debug("启动日志扫描线程结束,moduleType=" + moduleType);

	}

	
	/**
	 * 
	 * 读取远程日志文件，然后进行日志解析并且分析报警
	 *
	 */
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
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is, conf.getEncoding()));
				String line = null;
				while ((line = br.readLine()) != null) {
					try {
						parseLogLine(line, conf.getModuleType(), conf);
					} catch (IllegalStateException e) {
						log.error(String.format("模块%s非法的状态异常," + e.getLocalizedMessage(),conf.getModuleDesc()));
					}
				}
				br.close();
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

	/**
	 * 读完模块的所有日志后检查最新的数据设置文件无更新报警
	 * 
	 * @param conf
	 */
	protected void selfCheck(LogFileConfigure conf) {
		if(conf.selfCheck())
		{
			alarmAnalyse(conf.getLastObj(), conf);
		}
	}

	/**
	 * 发现文件不存在报警，进行处理，封装报警json进行处理
	 * 
	 * @param url
	 * @param moduleType
	 * @param conf
	 * @return
	 */
	protected String dealErrorTimes(final String error_msg, String moduleType,
			LogFileConfigure conf) {
		// 处理错误次数
		int times = errorTimes.getTimes(conf.getModuleCode()) + 1;
		errorTimes.setTimes(conf.getModuleCode(), times);
		String ret_msg = error_msg + ",次数" + times;
		log.error(ret_msg);
		/**文件不存在报警超过错误码(默认为3)次，进行报警处理**/
		if (times > conf.getErroValue()) {
			parseLogLine(buildErrorLog(ret_msg, conf.getModuleType(), conf), conf.getModuleType(),
					conf);
			errorTimes.setTimes(conf.getModuleCode(), 0);
		}
		return ret_msg;
	}

	/**
	 * 监测到文件不存在报警，并且生成实体JSON字符串
	 * 
	 * @param error_msg
	 * @param moduleType2
	 * @param conf
	 * @return
	 */
	protected String buildErrorLog(String error_msg, String moduleType,
			LogFileConfigure conf) {
		MonitorLogData data = new MonitorLogData();
		data.setDateTime(GlobalTime.systemTimeUtc());
		data.setModuleType(getModuleType());
		data.setModuleCode(conf.getModuleCode());
		data.setModuleDesc(conf.getModuleDesc());
		data.setHot_x(conf.getHot_x());
		data.setHot_y(conf.getHot_y());	
		data.setMailAlarm(conf.getMailAlarm());
		data.setErroValue(conf.getErroValue());
		data.setEncoding(conf.getEncoding());
		data.setCarCount_max(conf.getCarCount_max());
		data.setRelationAlarm(conf.getRelationAlarm());
		data.setMapCode(conf.getMapCode());
		data.setUrl(conf.getUrl());
		data.setAlarmCondition(conf.getAlarmCondition());
		data.setLogType(MonitorLogData.LOGTYPE_WARN);
		data.setAlarm_status(AlarmHistory.ALARM_STATUS_WARNNING);
		data.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_FILENOTEXISTS);
		data.setText(error_msg);
		log.info(String.format("模块%s构造错误日志行%s",conf.getModuleDesc(),data.toJSONString()));
		return data.toJSONString();
	}

	/**
	 * 解析日志内容，并分析报警
	 * 
	 * @param line
	 * @param moduleType
	 * @param conf
	 */
	public void parseLogLine(String line, String moduleType,
			LogFileConfigure conf) {
		log.debug(String.format("模块%s开始处理日志行%s" ,conf.getModuleDesc(), line));
		org.springframework.util.Assert.state(StringUtils.notNullOrBlank(line),
				String.format("模块%s日志行为空，直接跳过.url=%s" ,conf.getModuleDesc(), conf.getUrl()));
		// log.debug("JSONObject entry(key:value)=" + obj.toString());
		MonitorLogData data = conf.buildBean(line);
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

	/**
	 * 依据报警次数设置模块状态 ，并且发送短信、邮件
	 * @param data
	 * @param conf
	 */
	public MonitorLogData setDataStatus(MonitorLogData data,LogFileConfigure conf)
	{
		String key=conf.getModuleCode()+"errroCount";
		if(data.getAlarm_report_way()==AlarmHistory.ALARM_REPORT_WAY_FILENOTEXISTS)
		{
			data.setAlarm_status(AlarmHistory.ALARM_STATUS_NOTREAD);
			//如果上一条信息是报警，设置此次报警的时间为上一次报警的时间
			if(conf.getLastObj().getAlarm_status()==AlarmHistory.ALARM_STATUS_WARNNING)
			{
				//data.setAlarm_time(conf.getLastObj().getAlarm_time());
			}
			else
			{
				//data.setAlarm_time(GlobalTime.systemTimeUtc());
				//发现新报警并且发送邮件
				/*sendAlarmByConf(data, conf);
				writeAlarmDao(data);*/
			}
			data.setText(String.format("模块%s Log文件不存在" ,conf.getModuleDesc()));
			log.info(String.format("模块%s文件不存在报警" ,conf.getModuleDesc()));
			
			/**根据配置发送短信和邮件报警**/
			return data;
		}
		else
		{
			if(errorTimes.getTimes(key)>=conf.getErroValue())
			{
					
					data.setAlarm_status(AlarmHistory.ALARM_STATUS_WARNNING);
					//已经处理的故障状态设置
					if(conf.getLastObj().getAlarm_status()==AlarmHistory.ALARM_STATUS_DEALING||(conf.getLastObj().getAlarm_status())==(AlarmHistory.ALARM_STATUS_ErrorAlarm))
					{
						data.setAlarm_status(conf.getLastObj().getAlarm_status());
					}
					else if(conf.getLastObj().getAlarm_status()==AlarmHistory.ALARM_STATUS_WARNNING)
					{
						data.setAlarm_time(conf.getLastObj().getAlarm_time());
					}
					else
					{
						data.setAlarm_time(GlobalTime.systemTimeUtc());
						sendAlarmByConf(data, conf);
						//发现新报警并且发送邮件
					}
					log.info(String.format("模块%s报警超过%s次" ,conf.getModuleDesc(),conf.getErroValue()));
					/**根据配置发送短信和邮件报警**/
					writeAlarmDao(data);
			}
			else if(errorTimes.getTimes(key)>=1&&errorTimes.getTimes(key)<conf.getErroValue())
			{
				data.setAlarm_status(AlarmHistory.ALARM_STATUS_Warning);
				log.info(String.format("模块%s出现异常报警" ,conf.getModuleDesc()));
			}
			else
			{
				/**运行到此证明此模块是正常的**/
				data.setAlarm_status(AlarmHistory.ALARM_STATUS_NOWARN);
				/**如果此前有报警,则状态修改为恢复正常**/
				if(conf.getLastObj().getAlarm_status()>0&&conf.getLastObj().getAlarm_status()==AlarmHistory.ALARM_STATUS_WARNNING)
				{
					data.setAlarm_status(AlarmHistory.ALARM_STATUS_FINISH);
					finishAlarmByConf(data, conf);
					log.info(String.format("模块%s恢复正常" ,conf.getModuleDesc()));
				}
				finishDealAlarm(conf);
				errorTimes.setTimes(key, 0);
			}
			return data;
		}
		
	}
	
	
	/**
	 * 分析日志是否有报警
	 * 
	 * @param logData
	 *            单条数据
	 * @param conf
	 *            模块配置对象
	 */
	protected void alarmAnalyse(MonitorLogData logData, LogFileConfigure conf) {
		Assert.notNull(logData,String.format("模块%s被分析的日志内容不能为空",conf.getModuleDesc()));
		/**报警次数key**/
		String key=conf.getModuleCode()+"errroCount";
		/**模块已经发现的报警次数**/
		int times = errorTimes.getTimes(key);
		
		/**日志无更新报警**/
		long now=GlobalTime.systemTimeUtc();
		long dateT=logData.getDateTime();
		long duibi=Math.abs(dateT- now);
		if (Math.abs(logData.getDateTime() - GlobalTime.systemTimeUtc()) > INVALID_DATATIME_THRESHOLD
				&& logData.noAnalyseAlarm()) {
			logData.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_LINENOTUPDATED);
			errorTimes.setTimes(key, times+1);
			logData.setText(String.format("模块%s日志内容是>1小时以前的，直接跳过." ,logData.getModuleDesc()));
			log.warn(String.format("模块%s日志内容是>1小时以前的，直接跳过." ,logData.getModuleDesc()));
			return;
		}
		// 2.日志内容报警
		if (logData.checkContentAlarm()) {
			// 如果无报警则设置报警
				errorTimes.setTimes(key, times+1);
				logData.setText(logData.getText());
				/**过滤日志URL报警**/
				if(logData.getAlarm_report_way()!=AlarmHistory.ALARM_REPORT_WAY_FILENOTEXISTS)
				{
					logData.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_LOGLINEALARM);
				}
				log.info(String.format("模块%s有内容报警%s", logData.getModuleDesc(),
						logData.getText()));
			return;
		}
		// 3.软分析后报警
		try {
			if (logData.checkConditionAlarm()) {
				if (logData.getAlarm_status() <= AlarmHistory.ALARM_STATUS_NOWARN) {
					errorTimes.setTimes(key, times+1);
					logData.setText(logData.getText()+String.format(" 模块%s软分析报警",
							logData.getModuleDesc()));
					logData.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_LINEANALYSEALARM);
					log.info(String.format("模块%s软分析报警,条件为%s",
							logData.getModuleDesc(), Base64Codec.decode(conf.getAlarmCondition())));
				}
				return;
			}
			errorTimes.setTimes(key, 0);			
		} catch (MaintenanceMonitorException e) {
			log.error(String.format("模块%s软报警分析错误%s" ,conf.getModuleDesc(), e.getLocalizedMessage()), e);
		}catch (Exception e){
			log.error(String.format("模块%s软报警分析错误%s" ,conf.getModuleDesc(), e.getLocalizedMessage()), e);
		}
	}
	/**
	 * 根据配置发送报警短信和报警邮件
	 * 
	 * @param logData
	 * @param conf
	 */
	protected void sendAlarmByConf(MonitorLogData logData, LogFileConfigure conf) {
		String receiveMobile=getSchedule().getConfigure().getSms_receivers();
		String receiveEmail=getSchedule().getConfigure().getMail_receivers();
		if (conf.getSmsAlarm() > 0) {
			message.setHead(receiveMobile);
			message.setBody(conf.getModuleDesc() + logData.getText()
					+ ",请速处理.");
			try
			{
				getSchedule().getMessageService().sendMessage(message);
				log.info(String.format("模块%s发现报警:%s,发送报警短信成功.",
						conf.getModuleDesc(), logData.getText()));
			}
			catch(Exception e)
			{
				MessageSMS sms=new MessageSMS();
				sms.setHead(receiveMobile);
				sms.setBody(conf.getModuleDesc() + logData.getText()
					+ ",请速处理.");
				messageVector.add(sms);
				log.error(String.format("模块%s发现报警:%s,发送报警短信时失败.",
						conf.getModuleDesc(), logData.getText()));
			}
		}
		if (conf.getMailAlarm() > 0) {
			try
			{
				getSchedule().getMailService().sendTextMail(receiveEmail,
						conf.getModuleDesc(),
						conf.getModuleDesc() + logData.getText() + ",请速处理.");
				
				log.info(String.format("模块%s发现报警:%s,发送报警邮件成功.",
						conf.getModuleDesc(), logData.getText()));
			}
			catch(Exception e)
			{
				log.error(String.format("模块%s发现报警:%s,发送报警邮件失败.",
						conf.getModuleDesc(), logData.getText()));
			}
		}
		
	}

	/**
	 * 模块恢复时发送短信通知 
	 * @param logData
	 * @param conf
	 */
	protected void finishAlarmByConf(MonitorLogData logData, LogFileConfigure conf) {
		if (conf.getSmsAlarm() > 0) {
			// 短信发送
			message.setHead(getSchedule().getConfigure().getSms_receivers());
			message.setBody(conf.getModuleDesc() + " 已恢复正常,"
					+ ",特此通知.");
			getSchedule().getMessageService().sendMessage(message);
			
			try
			{
				getSchedule().getMessageService().sendMessage(message);
				
			}
			catch(Exception e)
			{
				MessageSMS sms=new MessageSMS();
				sms.setHead(getSchedule().getConfigure().getSms_receivers());
				sms.setBody(conf.getModuleDesc() + " 已恢复正常,"
						+ ",特此通知.");
				messageVector.add(sms);
				log.error(String.format("模块%s发现报警:%s,发送报警短信时失败.",
						conf.getModuleDesc(), logData.getText()));
			}
		}
		if (conf.getMailAlarm() > 0) {
			try
			{
				getSchedule().getMailService().sendTextMail(
						getSchedule().getConfigure().getMail_receivers(),
						conf.getModuleDesc(),
						conf.getModuleDesc() +  " 已恢复正常," + ",特此通知.");
			}
			catch(Exception e)
			{
				log.error(String.format("模块%s发现报警:%s,发送报警邮件失败.",
						conf.getModuleDesc(), logData.getText()));
			}
		}
		log.info(String.format("模块%s恢复正常:%s,发送恢复短信和邮件.",
				conf.getModuleDesc(), logData.getText()));
	}
	/**
	 * 根据日志信息写入报警历史库
	 * 
	 * @param logData
	 * 
	 */
	private void writeAlarmDao(MonitorLogData logData) {
		try {
			AlarmHistory record = logData2AlarmHistory(logData);
			getSchedule().getAlarmDao().insertDetail(record);
			log.info(String.format("模块%s插入报警记录到数据库成功." ,logData.getModuleDesc()));
		} catch (SQLException e) {
			log.error(String.format("模块%s插入报警记录到数据库出错:%s" ,logData.getModuleDesc(), e.getLocalizedMessage()), e);
		}
	}

	/**
	 * 日志对象转换为历史报警对象
	 * 
	 * @param logData
	 * @return
	 */
	protected AlarmHistory logData2AlarmHistory(MonitorLogData logData) {
		AlarmHistory record = new AlarmHistory();
		record.setAlarm_time((int)logData.getAlarm_time());
		record.setAlarm_content(logData.getText());
		record.setModuletype(logData.getModuleType());
		record.setModule_code(logData.getModuleCode());
		record.setReport_way(logData.getAlarm_status());
		record.setStatus(logData.getAlarm_status());
		record.setData_time((int) logData.getDateTime());
		record.setUpdate_time(GlobalTime.systemTimeUtc());
		return record;
	}

	/**
	 * 开始报警处理
	 * 
	 * @param conf 配置文件
	 * @param session 处理人信息
	 */
	public void startDealAlarm(LogFileConfigure conf, SessionMap session,String flag) {
		MonitorLogData logData = conf.getLastObj();
		//flag为0是正常报警，1为误报
		logData.setAlarm_status(flag.equals("0")?AlarmHistory.ALARM_STATUS_DEALING:AlarmHistory.ALARM_STATUS_ErrorAlarm);
		try {
			  AlarmHistory record = logData2AlarmHistory(logData);
			  MonitorUser user = (MonitorUser) session.getAttribute(Constants.SESSION_NAME);
			  
			  logData.setDeal_opid(user.getOp_id());
			  record.setDeal_opid(user.getOp_id());
			  getSchedule().getAlarmDao().updateByPrimaryKey(record);
			log.info(String.format("模块%s更新报警处理到数据库成功." , logData.getModuleDesc()));
		} catch (SQLException e) {
			log.error(String.format("模块%s更新报警处理到数据库出错%s",logData.getModuleDesc(), e.getLocalizedMessage()), e);
		}
	}

	/**
	 * 结束报警处理
	 * 
	 * @param conf
	 */
	public void finishDealAlarm(LogFileConfigure conf) {
		MonitorLogData logData = conf.getLastObj();
		logData.setAlarm_status(AlarmHistory.ALARM_STATUS_FINISH);
		try {
			AlarmHistory record = logData2AlarmHistory(logData);
			getSchedule().getAlarmDao().finishByPrimaryKey(record);
			log.info(String.format("模块%s结束报警处理到数据库成功." , logData.getModuleDesc()));
		} catch (SQLException e) {
			log.error(String.format("模块%s结束报警处理到数据库出错%s" ,logData.getModuleDesc(), e.getLocalizedMessage()), e);
		}
	}

	/**
	 * 获取变量<code>confList</code>的值
	 * 
	 * @return 返回的数据类型是<code>List<LogFileConfigure></code>
	 */
	public List<LogFileConfigure> getConfList() {
		return confList;
	}

	/**
	 * 设置变量<code> confList</code> 的值
	 * 
	 * @param confList
	 *            <code>confList</code> 参数类型是<code>List<LogFileConfigure></code>
	 */
	public void setConfList(List<LogFileConfigure> confList) {
		this.confList = confList;
		Map<String, String> nameMap = getSchedule().getConfigure()
				.getModuleNameMap();
		
		for (LogFileConfigure conf : confList) {
			indexMap.put(conf.getModuleCode(), confList.indexOf(conf));
			conf.setModuleTypeName(nameMap.get(conf.getModuleType()));
			conf.setTypeConf(getLogTypeConfigure());
			getSchedule().getConfigure().putLogFileScan(conf.getModuleCode(),
					this);
		}
	}

	/**
	 * 获取变量<code>moduleType</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleType() {
		return moduleType;
	}

	/**
	 * 设置变量<code> moduleType</code> 的值
	 * 
	 * @param moduleType
	 *            <code>moduleType</code> 参数类型是<code>String</code>
	 */
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	/**
	 * 获取变量<code>logTypeConfigure</code>的值
	 * 
	 * @return 返回的数据类型是<code>LogTypeConfigure</code>
	 */
	public LogTypeConfigure getLogTypeConfigure() {
		return logTypeConfigure;
	}
	/**
	 * 设置变量<code> logTypeConfigure</code> 的值
	 * 
	 * @param logTypeConfigure
	 *            <code>logTypeConfigure</code> 参数类型是
	 *            <code>LogTypeConfigure</code>
	 */
	public void setLogTypeConfigure(LogTypeConfigure logTypeConfigure) {
		this.logTypeConfigure = logTypeConfigure;
	}

	/**
	 * 获取变量<code>scaner</code>的值
	 * 
	 * @return 返回的数据类型是<code>LogFileScanSchedule</code>
	 */
	public LogFileScanSchedule getSchedule() {
		return schedule;
	}

	/**
	 * 设置变量<code> scaner</code> 的值
	 * 
	 * @param scaner
	 *            <code>scaner</code> 参数类型是<code>LogFileScanSchedule</code>
	 */
	public void setSchedule(LogFileScanSchedule scaner) {
		this.schedule = scaner;
	}

	/**
	 * 设置日志级别 
	 * @param level
	 */
	public String setLevel(int level){
		org.apache.log4j.Level le = org.apache.log4j.Level.INFO;
		switch (level) {
		case 0:
			le = org.apache.log4j.Level.DEBUG;
			break;
		case 1:
			le = org.apache.log4j.Level.INFO;
			break;
		case 2:
			le = org.apache.log4j.Level.WARN;
			break;
		case 3:
			le = org.apache.log4j.Level.ERROR;
			break;
		case 4:
			le = org.apache.log4j.Level.FATAL;
			break;
		case 5:
			le = org.apache.log4j.Level.OFF;
			break;
		default:
			break;
		}
		log.warn(String.format("类型%s线程设置日志级别%s",getModuleType(),le.toString()));
		log.setLevel(le);
		return log.getLevel().toString();
	}
	/**
	 * <p>
	 * LogFileScanThread
	 * </p>
	 * <p>
	 * 用途：文件扫描线程
	 * </p>
	 * 
	 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
	 * @version 0.0.1 2011-7-15
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
	 *          <td>2011-7-15 下午5:45:00</td>
	 *          </tr>
	 *          <tr>
	 *          <td>0.0.1</td>
	 *          <td>创建类</td>
	 *          <td>zhb</td>
	 *          <td>2011-7-15 下午5:45:00</td>
	 *          <td>0.0.2</td>
	 *          <td>修改类</td>
	 *          <td>xxx</td>
	 *          <td>x年x月x日</td>
	 *          </tr>
	 *          </table>
	 */
	public class LogFileScanThread extends Thread {
		/** 是否运行标志 */
		private boolean bRun = true;

		/** 线程序号 */
		private int no = 0;

		/**
		 * 构造器
		 * 
		 * @param i
		 */
		public LogFileScanThread(int i) {
			no = i;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (bRun) {
					if(messageVector.size()>0)
					{
						log.info("开始处理失败的短信报警，条数="+messageVector.size());
						while(messageVector.size()>0)
						{
							try
							{
								getSchedule().getMessageService().sendMessage(messageVector.get(0));
								messageVector.remove(0);
							}
							catch(Exception e){
								log.error("尝试再次发送短信报警失败"+messageVector.get(0).toString()+e.getLocalizedMessage());
							}
						}
						log.info("处理失败的短信报警结束，处理失败的条数="+messageVector.size());
					}
				log.info(String.format("类型%s日志扫描线程%d启动", getModuleType(), no));
				long start = System.currentTimeMillis();
				for (LogFileConfigure conf : getConfList()) {
					if (!conf.isCanScan())
						continue;
					conf.setCanScan(false);
					log.info(String.format("模块%s开始扫描%s" ,conf.getModuleDesc(),Base64Codec.decode(conf.getUrl())));
					try {
						URL url = null;
						try {
							url = new URL(Base64Codec.decode(conf.getUrl()));							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scanFile(url, conf.getModuleType(), conf);
					} catch (MaintenanceMonitorException e) {
						log.error(String.format("模块%s扫描出错" ,conf.getModuleDesc(), conf.getUrl()), e);
					}
				}
				long elapse = System.currentTimeMillis() - start;
				log.info(String.format("类型%s扫描%d个日志文件结束，耗时%d毫秒", getModuleType(),
						confList.size(), elapse));
				if (no == 0)
					doClearCanScan();
				synchronized (this) {
					try {
						wait(WRITE_INTERVAL);
					} catch (InterruptedException e) {
						log.error(String.format("类型%s日志记录线程被打断异常",getModuleType()),e);
					}
				}
			}
		}

		/**
		 * 重置扫描标记
		 */
		private void doClearCanScan() {
			for (LogFileConfigure conf : confList) {
				conf.setCanScan(true);
			}
			SystemHelper.printSystemMemory(null, log);
		}
	}

	/**
	 * <p>
	 * ErrorTimes
	 * </p>
	 * <p>
	 * 用途：错误类型计数器类
	 * </p>
	 * 
	 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
	 * @version 0.0.1 2011-7-26
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
	 *          <td>2011-7-26 下午5:54:50</td>
	 *          </tr>
	 *          <tr>
	 *          <td>0.0.1</td>
	 *          <td>创建类</td>
	 *          <td>zhb</td>
	 *          <td>2011-7-26 下午5:54:50</td>
	 *          <td>0.0.2</td>
	 *          <td>修改类</td>
	 *          <td>xxx</td>
	 *          <td>x年x月x日</td>
	 *          </tr>
	 *          </table>
	 */
	protected class ErrorTimes {
		/** 各模块错误类型连续发生的次数 */
		private Map<String, Integer> errorTimesMap = new HashMap<String, Integer>();

		/**
		 * 获得指定类型的次数
		 * 
		 * @param key
		 * @return
		 */
		public int getTimes(String key) {
			Integer ret = errorTimesMap.get(key);
			if (ret == null) {
				errorTimesMap.put(key, 0);
				return 0;
			} else {
				return ret.intValue();
			}
		}

		/**
		 * 设置指定类型的参数
		 * 
		 * @param key
		 * @param value
		 */
		public void setTimes(String key, int value) {
			errorTimesMap.put(key, value);
		}
	}

	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url=Base64Codec.decode("aHR0cDovLzIxOS4yMzIuMTk2LjEyOjgwODAvUHVibGljVHJpcFByb3ZpZGUvbG9hZFVwZGF0ZVRpbWU/YXNrPTQ4NmVmYTI0ZTRmNWMyMDMwMyZkYXRhdHlwZT1ESVNUVElNRQ");
		System.out.println(url);
	}

}
