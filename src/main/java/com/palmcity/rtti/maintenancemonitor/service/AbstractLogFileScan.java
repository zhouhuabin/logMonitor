/**
 * <p>文件名:		LogFileScanImpl.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.quartz.jobs.ee.mail.SendMailJob;
import org.springframework.util.Assert;

import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.log.CommonLogFactory;
import com.caits.lbs.framework.services.jmx.ILogSetable;
import com.caits.lbs.framework.services.sms.MessageSMS;
import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.GlobalTime;
import com.caits.lbs.framework.utils.StringUtils;
import com.caits.lbs.framework.utils.SystemHelper;
import com.caits.lbs.framework.utils.TimeTools;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.api.ModuleData;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleInfo;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleType;
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
	/** 日志记录器 */
	Logger errolog = CommonLogFactory.getErrdatalog();
	/** 日志模板 */
	private	ModuleType moduleType;
	/** 日志配置列表 */
	private List<ModuleInfo> moduleInfoList;

	/** 错误计数器 */
	protected ErrorValues errorValues = new ErrorValues();

	/** 短信消息体 */
	private MessageSMS messages = new MessageSMS();

	public static  Vector<MessageSMS> messageVector=new Vector<MessageSMS>();
	
	private ArrayList<LogFileScanThread> Threadlist = new ArrayList<LogFileScanThread>();
	
	/** 当模块数大于这个数字时会启动多线程 */
	private int THREAD_MODULE=10;
	
	/*//调用js组件
		private ScriptEngineManager mgr = new ScriptEngineManager();
		private ScriptEngine engine = mgr.getEngineByName("JavaScript");
		private Invocable inv=null;*/
	
	/** 模板字段map */
	private HashMap<String, String> ModuleTypeFieldMap=new HashMap<String,String>();
	
	/** 模板统计字段map */
	private HashMap<String, String> staticMap=new HashMap<String,String>();
	
	private SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	
	static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 构造器
	 */
	public AbstractLogFileScan() {
		
	}

	/**
	 * 日志文件扫描线程初始化方法
	 * 
	 * @param moduleTypeId
	 * @param threaNum
	 * @param confList
	 * @param scan_interval
	 * @param logTypeConfigure
	 */
	public void init(ModuleType moduleType,
			List<ModuleInfo> ModuleInfoList, int scan_interval) {
		setModuleType(moduleType);
		setModuleInfoList(ModuleInfoList);
		setModuleTypeFieldMap(moduleType,ModuleTypeFieldMap);
		setStaticMap(moduleType,staticMap);
		
		WRITE_INTERVAL = scan_interval;
		log.info("开始启动日志扫描线程,moduleType=" + moduleType.getModule_Type_Name());
		//当模块数大于10个时启动多线程，线程数为CPU核数
		int threaNum=1;
		if(ModuleInfoList.size()>=THREAD_MODULE*2)
		  threaNum=2;
		if(ModuleInfoList.size()>=THREAD_MODULE*3) 
		  threaNum=3;
		int MaxModuleInfoSize=ModuleInfoList.size()/threaNum;
		for (int i = 0; i < threaNum; i++) 
		{
			 //调用js组件
			 ScriptEngineManager mgr = new ScriptEngineManager();
			 ScriptEngine engine = mgr.getEngineByName("JavaScript");
			 Invocable inv=null;
			 try {
					engine.eval(new FileReader(LogFileScanSchedule.jsurl));
					inv = (Invocable) engine;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			LogFileScanThread thread = new LogFileScanThread(moduleType.getModule_Type_Name()+i);
			thread.setInv(inv);
			List<ModuleInfo> moduleList=ModuleInfoList.subList(i*MaxModuleInfoSize, i==threaNum-1?ModuleInfoList.size():MaxModuleInfoSize*(i+1));
			thread.setModuleList(moduleList);
			thread.start();
			Threadlist.add(thread);
		}
		log.info(String.format("启动日志扫描线程结束,moduleType=%s，共启用了%d个线程", moduleType.getModule_Type_Name(),Threadlist.size()));
	}

	
	/**
	 * 
	 * 读取远程日志文件，然后进行日志解析并且分析报警
	 *
	 */
	@Override
	public void scanFile(URL url,ModuleInfo info,Invocable inv)
			throws MaintenanceMonitorException {
		try {
//			UrlResource resource = new UrlResource(url);
			URLConnection  urlCon = url.openConnection();
			urlCon.setConnectTimeout(3000);
			InputStream is = urlCon.getInputStream();
			if(info.getModule_ID()==19)
				log.info("进入调试模式");
			/**判断日志文件的最后修改时间**/
			if (urlCon.getContentLength() != info.getContentLength()) {
				log.info(String.format("模块%s日志文件%s有更新，time=%d",
						info.getModule_Desc(), url,urlCon.getLastModified()/1000));
				info.setContentLength(urlCon.getContentLength());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is, info.getEncoding()));
				
				Scanner sc=new Scanner(br);
				String line = null;
				while((sc.hasNextLine()&&(line=sc.nextLine())!=null)){
				    if(!sc.hasNextLine())
				    	parseLogLine(line, info,inv);
				}
				
				br.close();
				is.close();
			} else {
				long now=System.currentTimeMillis()/1000;
				log.warn(String.format("模块%s日志文件超过%d秒无更新，直接跳过.",info.getModule_Desc(), now-info.getLastDateTime()));
				
				if(info.getModule_ID()==19)
					log.info("进入调试模式");
				if(info.getLastDateTime()>0&&(now-info.getLastDateTime())>=info.getTimeValue())
				{
					String errro_msg=String.format("超过%d秒没有更新",(now-info.getLastDateTime()));
					if(LogFileScanSchedule.moduleDateMap.containsKey(info.getCityName())&&LogFileScanSchedule.moduleDateMap.get(info.getCityName()).containsKey(info.getModule_ID()))
					{
						LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setStatus(ModuleData.ALARM_STATUS_ALARMING);
						LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setText(errro_msg);
						//发现未更新报警
						if(errorValues.getValues(info.getModule_ID())<=info.getErrorValve())
						{
							errorValues.setValues(info.getModule_ID(), info.getErrorValve()+1);
							sendMailSms(info,info.getModule_Desc()+errro_msg+",请速处理.");
							ModuleData data=LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID());
							writeAlarmDao(data,info,AlarmHistory.ALARM_REPORT_WAY_NOWARN);
						}
					}
				}
			}
			/**selfCheck为设置文件1小时以上无更新报警
			selfCheck(conf);*/
		} catch (Exception e) {
			/**监测到文件不存在报警，并且进行报警处理**/
			String error_msg = String.format("模块%s日志不存在." , info.getModule_Desc());
			setLogNotExist(info);
			//如果在此之前是运行正常的模块
			if(LogFileScanSchedule.moduleDateMap.containsKey(info.getCityName())&&LogFileScanSchedule.moduleDateMap.get(info.getCityName()).containsKey(info.getModule_ID()))
			{
				LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setStatus(ModuleData.ALARM_STATUS_NOTREAD);
				LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setText(error_msg);
				//如果此前没有发过报警邮件
				if(errorValues.getValues(info.getModule_ID())<=info.getErrorValve())
				{
					errorValues.setValues(info.getModule_ID(), info.getErrorValve()+1);
					ModuleData data=LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID());
					sendMailSms(info,info.getModule_Desc()+error_msg+",请速处理.");
					writeAlarmDao(data,info,AlarmHistory.ALARM_REPORT_WAY_FILENOTEXISTS);
				}
			}
			//String ret_msg = dealErrorTimes(error_msg, getModuleType().getModule_Type_Name(), info);
			throw new MaintenanceMonitorException(error_msg, e);
		}
	}


	private void setLogNotExist(ModuleInfo info)
	{
		if(LogFileScanSchedule.logNotExistMap.containsKey(info.getModule_Type_Id()))
		{
			if(!LogFileScanSchedule.logNotExistMap.get(info.getModule_Type_Id()).contains(info.getModule_Desc()))
			{
				LogFileScanSchedule.logNotExistMap.put(info.getModule_Type_Id(), LogFileScanSchedule.logNotExistMap.get(info.getModule_Type_Id())+info.getModule_Desc()+",");
			}
		}
		else {
			LogFileScanSchedule.logNotExistMap.put(info.getModule_Type_Id(),"");
		}
	}
	/**
	 * 解析日志内容，并分析报警
	 * 
	 * @param line
	 * @param moduleType
	 * @param info
	 */
	public void parseLogLine(String line,ModuleInfo info,Invocable inv) {
		log.debug(String.format("模块%s开始处理日志行%s" ,info.getModule_Desc(), line));
		org.springframework.util.Assert.state(StringUtils.notNullOrBlank(line),
				String.format("模块%s日志行为空，直接跳过.url=%s" ,info.getModule_Desc(), info.getUrl()));
		// log.debug("JSONObject entry(key:value)=" + obj.toString());
		
		if(info.getModule_Desc().equals("北京接收"))
		{
			log.info("调试模式");
		}
		try {
			long dateTime = Math.round((Double) inv.invokeFunction("getProInfo", line, "dateTime"));
			if(dateTime>info.getLastDateTime())
			{
				if(!LogFileScanSchedule.moduleDateMap.containsKey(info.getCityName()))
					LogFileScanSchedule.moduleDateMap.put(info.getCityName(), new HashMap<Integer,ModuleData>());
				if(!LogFileScanSchedule.moduleDateMap.get(info.getCityName()).containsKey(info.getModule_ID()))
					LogFileScanSchedule.moduleDateMap.get(info.getCityName()).put(info.getModule_ID(), new ModuleData());
				
				Object StaticInfo = (Object) inv.invokeFunction("StaticInfo", line,getStaticStr(staticMap));
				Object AnalyInfo = (Object) inv.invokeFunction("SoftAnalysis", line, getSoftAnalysis(Base64Codec.decode(info.getAlarmCondition()),ModuleTypeFieldMap));
				Double logType = (Double) inv.invokeFunction("getProInfo", line, "logType");
				Object text = (Object) inv.invokeFunction("getProInfo", line, "text");
				
				//正常
				ModuleData data=new ModuleData();
				data.setStatus(ModuleData.ALARM_STATUS_NOWARN);
				data.setStsticInfo(StaticInfo.toString());
				data.setText((AnalyInfo.toString().equals("")?text+"":AnalyInfo).toString());
				data.setModuleName(info.getModule_Desc());
				data.setDateTime(dateformat.format(TimeTools.utc2date(dateTime)));
				
				//软分析报警
				if(!AnalyInfo.equals(""))
				{
					errorValues.addValues(info.getModule_ID());
					data.setStatus(ModuleData.ALARM_STATUS_WARNNING);
				}
				//日志报警
				if(errorValues.getValues(info.getModule_ID())==info.getErrorValve()||logType==2)
				{
					data.setStatus(ModuleData.ALARM_STATUS_ALARMING);
					if(logType==2)
					{
						
						//发送报警邮件和短信
						data.setText("程序运行异常报警logType==2");
						sendMailSms(info,data.getModuleName()+data.getText()+",请速处理.");
						//写入历史报警数据库
						writeAlarmDao(data,info,AlarmHistory.ALARM_REPORT_WAY_LOGLINEALARM);
					}
					else if(errorValues.getValues(info.getModule_ID())==info.getErrorValve())
					{
						if(!data.getText().equals("程序运行异常报警logType==2"))
							data.setText(String.format("异常次数超过%d次", info.getErrorValve()));
						errorValues.addValues(info.getModule_ID());
						writeAlarmDao(data,info,AlarmHistory.ALARM_REPORT_WAY_LINEANALYSEALARM);
						sendMailSms(info,data.getModuleName()+data.getText()+",请速处理.");
					}
					
				}
				if(data.getStatus().equals(ModuleData.ALARM_STATUS_NOWARN)&&errorValues.getValues(info.getModule_ID())>=info.getErrorValve())
				{
					errorValues.setValues(info.getModule_ID(), 0);
					sendMailSms(info,data.getModuleName()+"恢复正常");
				}
				setLastData(info,data);
				info.setLastDateTime(dateTime);
				data=null;
			}
			
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			errolog.info(String.format("执行js脚本过程中错误%S", e.getLocalizedMessage()));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			errolog.info(String.format("执行js脚本过程中错误%S", e.getLocalizedMessage()));
		}
		 catch (Exception e) {
				// TODO Auto-generated catch block
				errolog.info(String.format("出现异常错误%S", e.getLocalizedMessage()));
			}
	}
	public void setLastData(ModuleInfo info,ModuleData data)
	{
		LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setStatus(data.getStatus());
		LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setStsticInfo(data.getStsticInfo());
		LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setText(data.getText());
		LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setModuleName(data.getModuleName());			
		LogFileScanSchedule.moduleDateMap.get(info.getCityName()).get(info.getModule_ID()).setDateTime(data.getDateTime());
	}
	/**
     * 软分析报警表达式，多个表达式中间以;间隔 
     * @param SoftStr
     * return js表达式
     * 
     */
    public static String getSoftAnalysis(String SoftStr,HashMap<String, String> FiledMap)
    {
    	String Analysis="";
    	String[] soft=SoftStr.split(";");
    	for(int i=0;i<soft.length;i++)
    	{
    		if(soft[i].equals(""))
    			continue;
    			if(soft[i].contains(">"))
    			{
    				String softPro=soft[i].split(">")[0];
    				String softVal=soft[i].split(">")[1];
        			Analysis+="((newObj."+soft[i]+")?(ret="+'"'+FiledMap.get(softPro)+"大于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(soft[i].contains("<"))
    			{
    				String softPro=soft[i].split("<")[0];
    				String softVal=soft[i].split("<")[1];
        			Analysis+="((newObj."+soft[i]+")?(ret="+'"'+FiledMap.get(softPro)+"小于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(soft[i].contains("=="))
    			{
    				String softPro=soft[i].split("==")[0];
    				String softVal=soft[i].split("==")[1];
        			Analysis+="((newObj."+soft[i]+")?(ret="+'"'+FiledMap.get(softPro)+"等于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(i!=soft.length-1&&!Analysis.equals(""))
    				Analysis+="||";
    	}
		return Analysis;
    }
	/**
     * 统计字段获取表达式 
     * @param staticMap统计字段MAP，key为英文字段名，value为字段中文
     *
     */
    public static String getStaticStr(HashMap<String, String> staticMap)
    {
    	String staticStr="";
    	for(String key:staticMap.keySet())
    	{
    		if(!staticStr.equals(""))
    			staticStr+="+";
    		staticStr+='"'+" "+staticMap.get(key)+":"+'"'+"+newObj."+key;
    	}
		return staticStr;
    }
	
    /**
     * 判断当前时间是否是是否处于非报警时间段，如果是则发送报警邮件/短信
     * @param NO_AlarmTimeStr，报警时间段格式06:30:30-07:30:30,1,0;开始-结束,邮件，短信多个时间段中间以分号间隔
     * @return 如果是处于非报警时间段返回true,否则返回false
     */
    
    protected  void sendMailSms(ModuleInfo info,String message)
    {
    	String[] AlarmTime=info.getModule_Info_AlarmTime().split(";");
    	Date date=new Date();
    	String hours=date.getHours() < 10 ? "0"+date.getHours() : date.getHours()+"";
    	String minutes=date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes()+"";
    	String seconds=date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds()+"";
    	String dateStr=hours+minutes+seconds;
    	int dateInt=Integer.parseInt(dateStr);
    	for(String alarmTimeStr:AlarmTime)
    	{
    		String time=alarmTimeStr.split(",")[0];
    		String mail=alarmTimeStr.split(",")[1];
    		String sms=alarmTimeStr.split(",")[2];
    		int startTime=Integer.parseInt(time.split("-")[0].replaceAll(":", ""));
    		int endTime=Integer.parseInt(time.split("-")[1].replaceAll(":", ""));
    		if(dateInt>=startTime&&dateInt<=endTime)
    		{
    			if(mail.equals("1"))
        		{
    				SendMail(LogFileScanSchedule.receiveEmail,info.getModule_Desc(),message);
        		}
        		if(sms.equals("1"))
        		{
        			SendSMS(LogFileScanSchedule.receiveMobile,info.getModule_Desc(),message);
        		}
    		}
    	}
    }
    
    
    /**
     * 邮件发送方法 
     * @param receiveEmail
     * @param subject
     * @param message
     */
    protected void SendMail(String receiveEmail,String subject,String message)
    {
    	try
		{
    		LogFileScanSchedule.getMailService().sendTextMail(receiveEmail,
					subject,
					message);
			
			log.info(String.format("模块%s发送邮件成功:%s,",
					subject, message));
		}
		catch(Exception e)
		{
			log.error(String.format("模块%s发送邮件失败:%s,",
					subject, message));
		}
    }
    
    /**
     * 短信发送方法 
     * @param receiveMobile
     * @param subject
     * @param message
     */
    protected void SendSMS(String receiveMobile,String subject,String message)
    {
    	try
		{
			messages.setHead(receiveMobile);
			messages.setBody(subject + message);
			LogFileScanSchedule.getMessageService().sendMessage(messages);
			log.info(String.format("模块%s发送短信成功:%s,",
					subject, message));
		}
		catch(Exception e)
		{
	    	MessageSMS sms=new MessageSMS();
			sms.setHead(receiveMobile);
			sms.setBody(subject + message
				+ ",请速处理.");
			messageVector.add(sms);
			log.error(String.format("模块%s发送短信失败:%s,",
					subject, message));
		}
    }
	
	/**
	 * 根据日志信息写入报警历史库
	 * 
	 * @param logData
	 * 
	 */
	private void writeAlarmDao(ModuleData logData,ModuleInfo info,int reportway) {
			AlarmHistory record;
			try {
				record = logData2AlarmHistory(logData,info,reportway);
				DaoTool.getAlarmHistoryDao().insertDetail(record);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(String.format("存入历史报警数据库中错误%S", e.getLocalizedMessage()));
			}
			log.info(String.format("模块%s插入报警记录到数据库成功." ,logData.getModuleName()));
	}

	/**
	 * 日志对象转换为历史报警对象
	 * 
	 * @param logData
	 * @return
	 * @throws ParseException 
	 */
	protected AlarmHistory logData2AlarmHistory(ModuleData logData,ModuleInfo info,int reportway) throws ParseException {
		AlarmHistory record = new AlarmHistory();
		record.setModule_Desc(info.getModule_Desc());
		record.setModule_Type_Id(info.getModule_Type_Id());
		record.setModule_ID(info.getModule_ID());
		record.setData_time(TimeTools.date2utc(formatDate.parse(logData.getDateTime())));
		record.setStatus(AlarmHistory.ALARM_STATUS_WARNNING);
		record.setReport_way(reportway);
		record.setText(logData.getText());
		record.setUpdate_time(GlobalTime.systemTimeUtc());
		return record;
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
		private String no = "";
		
		private List<ModuleInfo> moduleList;
		
		/** js调用 */
		private Invocable inv;
		/**
		 * 构造器
		 * 
		 * @param i
		 */
		public LogFileScanThread(String str) {
			no = str;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (isbRun()) {
				log.info(String.format("日志扫描线程%s启动", no));
				long start = System.currentTimeMillis();
				for ( int i=0;i<getModuleList().size(); i++) {
					ModuleInfo info=getModuleList().get(i);
					if(!info.getScanState().equals("1"))
						continue;
					if (!info.isCanScan())
						continue;
					info.setCanScan(false);
					log.info(String.format("模块%s开始扫描" ,info.getModule_Desc()));
					try {
						URL url = null;
						try {
							url = new URL(Base64Codec.decode(info.getUrl()));							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scanFile(url,info,getInv());
					} catch (MaintenanceMonitorException e) {
						log.error(String.format("模块%s扫描出错" ,info.getModule_Desc(), info.getUrl()), e);
					}
				}
				long elapse = System.currentTimeMillis() - start;
				log.info(String.format("线程%s扫描%d个日志文件结束，耗时%d秒", no,
						getModuleList().size(), elapse/1000));
				if (no.contains("0"))
					doClearCanScan();
				synchronized (this) {
					try {
						wait(WRITE_INTERVAL*1000);
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
			for (ModuleInfo conf : getModuleList()) {
				conf.setCanScan(true);
			}
			SystemHelper.printSystemMemory(null, log);
		}
		/**
		 * 获取变量<code>moduleList</code>的值
		 * @return 返回的数据类型是<code>ArrayList<ModuleInfo></code>
		 */
		public List<ModuleInfo> getModuleList() {
			return moduleList;
		}
		/**
		 * 设置变量<code> moduleList</code> 的值
		 * @param moduleList  <code>moduleList</code> 参数类型是<code>ArrayList<ModuleInfo></code>
		 */
		public void setModuleList(List<ModuleInfo> moduleList) {
			this.moduleList = moduleList;
		}
		/**
		 * 获取变量<code>bRun</code>的值
		 * @return 返回的数据类型是<code>boolean</code>
		 */
		public boolean isbRun() {
			return bRun;
		}
		/**
		 * 设置变量<code> bRun</code> 的值
		 * @param bRun  <code>bRun</code> 参数类型是<code>boolean</code>
		 */
		public void setbRun(boolean bRun) {
			this.bRun = bRun;
		}
		/**
		 * 获取变量<code>inv</code>的值
		 * @return 返回的数据类型是<code>Invocable</code>
		 */
		public Invocable getInv() {
			return inv;
		}
		/**
		 * 设置变量<code> inv</code> 的值
		 * @param inv  <code>inv</code> 参数类型是<code>Invocable</code>
		 */
		public void setInv(Invocable inv) {
			this.inv = inv;
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
	protected class ErrorValues {
		/** 各模块错误类型连续发生的次数 */
		private Map<Integer, Integer> errorValuesMap = new HashMap<Integer, Integer>();

		/**
		 * 获得指定类型的次数
		 * 
		 * @param key
		 * @return
		 */
		public int getValues(Integer key) {
			Integer ret = errorValuesMap.get(key);
			if (ret == null) {
				errorValuesMap.put(key, 0);
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
		public void addValues(Integer key) {
			errorValuesMap.put(key, errorValues.getValues(key)+1);
		}
		/**
		 * 设置指定类型的参数
		 * 
		 * @param key
		 * @param value
		 */
		public void setValues(Integer key, int value) {
			errorValuesMap.put(key, value);
		}
	}

	/**
	 * FIXME
	 * 
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * 获取变量<code>moduleInfoList</code>的值
	 * @return 返回的数据类型是<code>List<ModuleInfo></code>
	 */
	public List<ModuleInfo> getModuleInfoList() {
		return moduleInfoList;
	}

	/**
	 * 设置变量<code> moduleInfoList</code> 的值
	 * @param moduleInfoList  <code>moduleInfoList</code> 参数类型是<code>List<ModuleInfo></code>
	 */
	public void setModuleInfoList(List<ModuleInfo> moduleInfoList) {
		this.moduleInfoList = moduleInfoList;
	}

	/**
	 * 获取变量<code>moduleType</code>的值
	 * @return 返回的数据类型是<code>ModuleType</code>
	 */
	public ModuleType getModuleType() {
		return moduleType;
	}

	/**
	 * 设置变量<code> moduleType</code> 的值
	 * @param moduleType  <code>moduleType</code> 参数类型是<code>ModuleType</code>
	 */
	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

	/**
	 * 获取变量<code>moduleTypeFieldMap</code>的值
	 * @return 返回的数据类型是<code>HashMap<String,String></code>
	 */
	public HashMap<String, String> getModuleTypeFieldMap() {
		return ModuleTypeFieldMap;
	}

	/**
	 * 设置变量<code> ModuleTypeFieldMap</code> 的值
	 * @param moduleTypeFieldMap  <code>moduleTypeFieldMap</code> 参数类型是<code>HashMap<String,String></code>
	 */
	public void setModuleTypeFieldMap(ModuleType moduleType,HashMap<String, String> ModuleTypeFieldMap) {
		String[] field=moduleType.getModule_Type_Field().split(",");
		String[] field_zn=moduleType.getModule_Type_Field_Zn().split(",");
		for(int i=0;i<field.length;i++)
			ModuleTypeFieldMap.put(field[i], field_zn[i]);
	}

	/**
	 * 获取变量<code>staticMap</code>的值
	 * @return 返回的数据类型是<code>HashMap<String,String></code>
	 */
	public HashMap<String, String> getStaticMap() {
		return staticMap;
	}

	/**
	 * 设置变量<code> staticMap</code> 的值
	 * @param staticMap  <code>staticMap</code> 参数类型是<code>HashMap<String,String></code>
	 */
	public void setStaticMap(ModuleType moduleType,HashMap<String, String> staticMap) {
		String[] staticField=moduleType.getModule_Type_Static().split(",");
		for(int i=0;i<staticField.length;i++)
			staticMap.put(staticField[i], ModuleTypeFieldMap.get(staticField[i]));
	}

	/**
	 * 获取变量<code>threadlist</code>的值
	 * @return 返回的数据类型是<code>ArrayList<LogFileScanThread></code>
	 */
	public ArrayList<LogFileScanThread> getThreadlist() {
		return Threadlist;
	}
	
}
