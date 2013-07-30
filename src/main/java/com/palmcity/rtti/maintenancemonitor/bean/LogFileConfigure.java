/**
 * <p>文件名:		LogFileConfigure.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.FileHelper;
import com.caits.lbs.framework.utils.GlobalTime;
import com.caits.lbs.framework.utils.JsonUtil;
import com.palmcity.rtti.maintenancemonitor.action.UserConstants;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan;

/**
 * <p>
 * LogFileConfigure
 * </p>
 * <p>
 * 用途：模块日志配置类
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
 *          <td>2011-7-13 下午8:05:25</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午8:05:25</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class LogFileConfigure extends ETBase {

	/** default */
	private static final long serialVersionUID = 1L;

	/** 报警条件语句关键字 */
	private String ALARM_CONDITION = "ALARM_CONDITION";
	
	/** common日志记录器 */
	public Log _log = LogFactory.getLog(getClass());
	
	/** 日志文件最后修改的时间戳毫秒 */
	private long contentLength = 0;

	/** 模块类型 */
	private String moduleType;
	/** 类型名称 */
	private String moduleTypeName;
	/** 模块标识 */
	private String moduleCode;
	/** 模块描述 */
	private String moduleDesc;
	/** 日志文件URL */
	private String url;
	/** 是否短信报警0-不报警 1-报警 */
	private int smsAlarm = 0;
	/** 是否邮件报警0-不报警 1-报警 */
	private int mailAlarm = 0;
	/** 报警条件 */
	private String alarmCondition;

	/** 是否可以扫描 */
	private boolean canScan = true;
	/** 最新的日志数据JSON格式 */
	private MonitorLogData lastObj = new MonitorLogData();
	/** 同类错误允许发生次数-见AlarmHistory.ALARM_REPORT_WAY */
	private int erroValue;

	/** 日志文件的编码 */
	private String encoding = "UTF-8";

	/** 所属类型配置实例 */
	private LogTypeConfigure typeConf;

	/** 报警条件扩展执行类 */
	public Class<?> groovyConditionClass;
	
	//ClassLoader cl = getClass().getClassLoader();
	ClassLoader cl= LogFileConfigure.class.getClassLoader();
	/** groovy类装载器 */
	//GroovyClassLoader groovyCl = new GroovyClassLoader(cl);

	/** 本配置是否可以工作 */
	private boolean enable = true;

	/** 打印信息输出路径,只有部分模块有用 */
	private String outputFile;
	
	/** 在拓扑图上的X坐标 */
	private int hot_x;
	
	/** 在拓扑图上的y坐标 */
	private int hot_y;
	

	/** 关联报警标识 */
	private String relationAlarm;
	
	/** 拓扑图ID */
	private String mapCode;
	
	/** 车辆总数 */
	private int carCount_max;

	/**
	 * 字段 carCount_max 获取函数
	 * @return the carCount_max : int
	 */
	public int getCarCount_max() {
		return carCount_max;
	}

	/**
	 * 字段 carCount_max 设置函数 : int
	 * @param carCount_max the carCount_max to set
	 */
	public void setCarCount_max(int carCount_max) {
		this.carCount_max = carCount_max;
	}

	/**
	 * 字段 hot_x 获取函数
	 * @return the hot_x : int
	 */
	public int getHot_x() {
		return hot_x;
	}

	/**
	 * 字段 hot_x 设置函数 : int
	 * @param hot_x the hot_x to set
	 */
	public void setHot_x(int hot_x) {
		this.hot_x = hot_x;
	}

	/**
	 * 字段 hot_y 获取函数
	 * @return the hot_y : int
	 */
	public int getHot_y() {
		return hot_y;
	}

	/**
	 * 字段 hot_y 设置函数 : int
	 * @param hot_y the hot_y to set
	 */
	public void setHot_y(int hot_y) {
		this.hot_y = hot_y;
	}

	/**
	 * 字段 relationAlarm 获取函数
	 * @return the relationAlarm : String
	 */
	public String getRelationAlarm() {
		return relationAlarm;
	}

	/**
	 * 字段 relationAlarm 设置函数 : String
	 * @param relationAlarm the relationAlarm to set
	 */
	public void setRelationAlarm(String relationAlarm) {
		this.relationAlarm = relationAlarm;
	}

	/**
	 * 字段 mapCode 获取函数
	 * @return the mapCode : String
	 */
	public String getMapCode() {
		return mapCode;
	}

	/**
	 * 字段 mapCode 设置函数 : String
	 * @param mapCode the mapCode to set
	 */
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	
	
	
	/**
	 * 构造器
	 */
	public LogFileConfigure() {
		super();

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
	 * 获取变量<code>moduleCode</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * 设置变量<code> moduleCode</code> 的值
	 * 
	 * @param moduleCode
	 *            <code>moduleCode</code> 参数类型是<code>String</code>
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * 获取变量<code>moduleDesc</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleDesc() {
		return moduleDesc;
	}

	/**
	 * 设置变量<code> moduleDesc</code> 的值
	 * 
	 * @param moduleDesc
	 *            <code>moduleDesc</code> 参数类型是<code>String</code>
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	/**
	 * 获取变量<code>url</code>的值
	 * 
	 * @return 返回的数据类型是<code>URL</code>
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置变量<code> url</code> 的值
	 * 
	 * @param url
	 *            <code>url</code> 参数类型是<code>URL</code>
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取变量<code>smsAlarm</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getSmsAlarm() {
		return smsAlarm;
	}

	/**
	 * 设置变量<code> smsAlarm</code> 的值
	 * 
	 * @param smsAlarm
	 *            <code>smsAlarm</code> 参数类型是<code>int</code>
	 */
	public void setSmsAlarm(int smsAlarm) {
		this.smsAlarm = smsAlarm;
	}

	/**
	 * 获取变量<code>mailAlarm</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getMailAlarm() {
		return mailAlarm;
	}

	/**
	 * 设置变量<code> mailAlarm</code> 的值
	 * 
	 * @param mailAlarm
	 *            <code>mailAlarm</code> 参数类型是<code>int</code>
	 */
	public void setMailAlarm(int mailAlarm) {
		this.mailAlarm = mailAlarm;
	}

	/**
	 * 获取变量<code>alarmCondition</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getAlarmCondition() {
		return alarmCondition;
	}

	/**
	 * 设置变量<code> alarmCondition</code> 的值
	 * 
	 * @param alarmCondition
	 *            <code>alarmCondition</code> 参数类型是<code>String</code>
	 */
	public void setAlarmCondition(String alarmCondition) {
		Assert.notNull(alarmCondition,String.format("模块%s报警条件不能为空",getModuleCode()) );
		this.alarmCondition= alarmCondition;

	}

	/**
	 * 获取变量<code>canScan</code>的值
	 * 
	 * @return 返回的数据类型是<code>boolean</code>
	 */
	public boolean isCanScan() {
		return canScan;
	}

	/**
	 * 设置变量<code> canScan</code> 的值
	 * 
	 * @param canScan
	 *            <code>canScan</code> 参数类型是<code>boolean</code>
	 */
	public void setCanScan(boolean canScan) {
		this.canScan = canScan;
	}

	/**
	 * 获取变量<code>lastObj</code>的值
	 * 
	 * @return 返回的数据类型是<code>JSONObject</code>
	 */
	public MonitorLogData getLastObj() {
		return lastObj;
	}

	/**
	 * 接收日志文件解析后的数据
	 * 
	 * @param lastObj
	 *            <code>lastObj</code> 参数类型是<code>JSONObject</code>
	 */
	public boolean setLastObj(MonitorLogData obj) {
		Assert.notNull( obj,String.format("模块%s日志对象不能为空",getModuleCode()));
		lastObj = obj;
		_log.debug(String.format("模块%s更新日志数据成功,new date_time="
				, getModuleCode() , obj.getDateTime()));
		lastObj.setAlarm_time(obj.getAlarm_time());
		lastObj.setModuleType(getModuleType());
		lastObj.setModuleCode(getModuleCode());
		lastObj.setModuleDesc(getModuleDesc());
		lastObj.setHot_x(getHot_x());
		lastObj.setHot_y(getHot_y());
		lastObj.setSmsAlarm(getSmsAlarm());
		lastObj.setMailAlarm(getMailAlarm());
		lastObj.setErroValue(getErroValue());
		lastObj.setEncoding(getEncoding());
		lastObj.setCarCount_max(getCarCount_max());
		lastObj.setRelationAlarm(getRelationAlarm());
		lastObj.setMapCode(getMapCode());
		lastObj.setUrl(getUrl());
		lastObj.setAlarmCondition(getAlarmCondition());
		
		
		return true;

	}

	/**
	 * lastObj与目标对象比较，是否满足更新条件
	 * 
	 * @param data
	 * @return
	 */
	public boolean compareLastObj(MonitorLogData obj) {
		if (lastObj == null || lastObj.getDateTime() == 0) {
			_log.debug(String.format("模块%s满足更新条件,new date_time="
					, getModuleCode(), obj.getDateTime()));
			return true;
		} else {
			if (obj.getDateTime() > lastObj.getDateTime()
					&& Math.abs(obj.getDateTime() - GlobalTime.systemTimeUtc()) < AbstractLogFileScan.INVALID_DATATIME_THRESHOLD) {
				_log.debug(String.format("模块%s满足更新条件,new date_time="
						, getModuleCode(), obj.getDateTime()));
				return true;
			} else {
				_log.debug(String.format("模块%s不满足更新条件,new date_time="
						, getModuleCode(), obj.getDateTime()));
				return false;
			}
		}
	}

	/**
	 * 获取变量<code>errorValve</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getErroValue() {
		return erroValue;
	}

	/**
	 * 设置变量<code> errorValve</code> 的值
	 * 
	 * @param errorValve
	 *            <code>errorValve</code> 参数类型是<code>int</code>
	 */
	public void setErroValue(int erroValue) {
		this.erroValue = erroValue;
	}

	/**
	 * 获取变量<code>typeConf</code>的值
	 * 
	 * @return 返回的数据类型是<code>LogTypeConfigure</code>
	 */
	public LogTypeConfigure getTypeConf() {
		return typeConf;
	}

	/**
	 * 设置变量<code> typeConf</code> 的值
	 * 
	 * @param typeConf
	 *            <code>typeConf</code> 参数类型是<code>LogTypeConfigure</code>
	 */
	public void setTypeConf(LogTypeConfigure typeConf) {
		this.typeConf = typeConf;
		if (groovyConditionClass == null) {
			try {
				File groovyFile = new File(getTypeConf().getGroovyFilePath());
				String str_file = FileHelper.fileToString(groovyFile,UserConstants.SouceCodeEncoding);
				str_file = str_file.replace(ALARM_CONDITION,
						Base64Codec.decode(getAlarmCondition()));
				//groovyConditionClass = groovyCl.parseClass(str_file);// 这个返回true
				lastObj = (MonitorLogData) groovyConditionClass.newInstance();
				lastObj.setModuleType(getModuleType());
				lastObj.setModuleCode(getModuleCode());
				lastObj.setModuleDesc(getModuleDesc());
				lastObj.setHot_x(getHot_x());
				lastObj.setHot_y(getHot_y());
				lastObj.setSmsAlarm(getSmsAlarm());
				lastObj.setMailAlarm(getMailAlarm());
				lastObj.setErroValue(getErroValue());
				lastObj.setEncoding(getEncoding());
				lastObj.setCarCount_max(getCarCount_max());
				lastObj.setRelationAlarm(getRelationAlarm());
				lastObj.setUrl(getUrl());
				lastObj.setMapCode(getMapCode());
				lastObj.setAlarmCondition(getAlarmCondition());
				_log.info(String.format("模块%s创建groovy实例%s成功",
						getModuleCode(), groovyConditionClass.getSimpleName()));
			} catch (InstantiationException e) {
				_log.error(String.format("模块%s创建groovy实例%s错误", getModuleCode(), typeConf.getProcessClass()), e);
				setEnable(false);
			} catch (IllegalAccessException e) {
				_log.error(String.format("模块%s创建groovy实例%s错误,非法访问" , getModuleCode(), typeConf.getProcessClass()),
						e);
				setEnable(false);
			}  catch (Exception e) {
				_log.error(
						String.format("模块%s创建groovy实例%s错误,未知错误" , getModuleCode(), typeConf.getGroovyFilePath()),
						e);
				setEnable(false);
			}
		}
	}

	/**
	 * 将json对象转换为实体bean
	 * 
	 * @param obj
	 * @return
	 */
	public MonitorLogData buildBean(String obj) {
		if (isEnable()) {
			MonitorLogData data = (MonitorLogData) JsonUtil.getObjectFromJsonString(obj,
					groovyConditionClass);
			if(data == null){
				_log.error(String.format("模块%s解析日志行出错,将采用模默认类型代替,line=%s",getModuleCode(),obj));
				data = new MonitorLogData();
			}
			data.setModuleType(getModuleType());
			data.setModuleCode(getModuleCode());
			data.setModuleDesc(getModuleDesc());
			data.setHot_x(getHot_x());
			data.setHot_y(getHot_y());
			data.setSmsAlarm(getSmsAlarm());
			data.setMailAlarm(getMailAlarm());
			data.setErroValue(getErroValue());
			data.setEncoding(getEncoding());
			data.setCarCount_max(getCarCount_max());
			data.setRelationAlarm(getRelationAlarm());
			data.setMapCode(getMapCode());
			data.setUrl(getUrl());
			data.setAlarmCondition(getAlarmCondition());
			return data;
		} else{
			_log.error(String.format("模块%s的日志配置类不能正常工作，将转换为通用类",moduleCode));
			MonitorLogData data = (MonitorLogData) JsonUtil.getObjectFromJsonString(obj,
					MonitorLogData.class);
			data.setModuleType(getModuleType());
			data.setModuleCode(getModuleCode());
			data.setModuleDesc(getModuleDesc());
			return data;
		}
	}

	/**
	 * 日志对象的自检
	 * @return true --日志无更新
	 * 	 	   false --日志有更新(正常)
	 */
	public boolean selfCheck() {
		Assert.notNull(lastObj, String.format("模块%s日志对象不能为空",getModuleCode()));
		if (Math.abs(lastObj.getDateTime() - GlobalTime.systemTimeUtc()) > AbstractLogFileScan.INVALID_DATATIME_THRESHOLD) {
			lastObj.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_LINENOTUPDATED);
			lastObj.setText("日志内容1小时以上没有更新");
			return true;
		}
		return false;
	}

	/**
	 * 获取变量<code>enable</code>的值
	 * 
	 * @return 返回的数据类型是<code>boolean</code>
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * 设置变量<code> enable</code> 的值
	 * 
	 * @param enable
	 *            <code>enable</code> 参数类型是<code>boolean</code>
	 */
	private void setEnable(boolean enable) {
		this.enable = enable;
		if(!enable){
			lastObj.setText("模块配置出错，请检查配置后重新启动.");
		}
	}

	/**
	 * 获取变量<code>encoding</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 设置变量<code> encoding</code> 的值
	 * @param encoding  <code>encoding</code> 参数类型是<code>String</code>
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 获取变量<code>moduleTypeName</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getModuleTypeName() {
		return moduleTypeName;
	}

	/**
	 * 设置变量<code> moduleTypeName</code> 的值
	 * @param moduleTypeName  <code>moduleTypeName</code> 参数类型是<code>String</code>
	 */
	public void setModuleTypeName(String moduleTypeName) {
		this.moduleTypeName = moduleTypeName;
	}

	/**
	 * 获取变量<code>lastModified</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getContentLength() {
		return contentLength;
	}

	/**
	 * 设置变量<code> lastModified</code> 的值
	 * @param lastModified  <code>lastModified</code> 参数类型是<code>long</code>
	 */
	public void setContentLength(long lastModified) {
		this.contentLength = lastModified;
	}

	/**
	 * 获取变量<code>groovyConditionClass</code>的值
	 * @return 返回的数据类型是<code>Class<?></code>
	 */
	public Class<?> getGroovyConditionClass() {
		return groovyConditionClass;
	}

	/**
	 * 设置变量<code> groovyConditionClass</code> 的值
	 * @param groovyConditionClass  <code>groovyConditionClass</code> 参数类型是<code>Class<?></code>
	 */
	public void setGroovyConditionClass(Class<?> groovyConditionClass) {
		this.groovyConditionClass = groovyConditionClass;
	}

	/**
	 * 获取变量<code>outputFile</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * 设置变量<code> outputFile</code> 的值
	 * @param outputFile  <code>outputFile</code> 参数类型是<code>String</code>
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	
}
