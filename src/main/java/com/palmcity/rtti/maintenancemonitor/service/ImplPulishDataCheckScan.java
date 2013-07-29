/**
 * <p>文件名:		ImplPulishDataCheckScan.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

/*import groovy.LogDataPublishDataCheck
*/import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import com.caits.lbs.framework.utils.GlobalTime;
import com.palmcity.rtti.base.DTICity;
import com.palmcity.rtti.excode.DTIExcoding;
import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.util.DateUtil;

/**
 * <p>
 * ImplPulishDataCheckScan
 * </p>
 * <p>
 * 用途：发布模块结果数据检测类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-11-24
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
 *          <td>2011-11-24 下午5:21:35</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-11-24 下午5:21:35</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class ImplPulishDataCheckScan extends AbstractLogFileScan {

	/** dti发布数据b2c格式的起始位置 */
	protected static final int PUBLISH_DTI_STARTPOS_B2C = 68;
	/** dti发布数据b2b格式的起始位置 */
	protected static final int PUBLISH_DTI_STARTPOS_B2B = 0;
	/** 日志记录器 */
	protected static Logger log = Logger.getLogger(ImplPulishDataCheckScan.class);
	
	/** dti格式解析类 */
	protected static DTIExcoding dtiexcoding = new DTIExcoding();
	
	/* (non-Javadoc)
	 * @see com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan#scanFile(java.net.URL, java.lang.String, com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure)
	 */
	@Override
	public void scanFile(URL url, String moduleType, LogFileConfigure conf)
			throws MaintenanceMonitorException {
		try {
			if(conf.getOutputFile()==null)
			conf.setOutputFile(getSchedule().getConfigure().getOutputData_filepath());
			URLConnection  urlCon = url.openConnection();
			InputStream is = urlCon.getInputStream();
			if (urlCon.getContentLength() != conf.getContentLength()) {
				log.info(String.format("模块%s日志文件%s有更新，length=%d",
						conf.getModuleCode(), url,urlCon.getContentLength()));
				conf.setContentLength(urlCon.getContentLength());
				byte[] data = new byte[urlCon.getContentLength()];
				byte[] temp = new byte[1024];
				int count = 0 , destPos = 0;
				while ((count = is.read(temp,0,temp.length)) != -1) {
					System.arraycopy(temp, 0, data, destPos, count);
					destPos +=count;
				}
				is.close();
				try {
					MonitorLogData log = convertDTI2Bean(data,conf);
					if (conf.compareLastObj(log)) {
						alarmAnalyse(log, conf);
						conf.setLastObj(log);
					}
				} catch (IllegalStateException e) {
					log.error(String.format("模块%s非法的状态异常," + e.getLocalizedMessage(),conf.getModuleCode()));
				}
				
			} else {
				log.warn(String.format("模块%s日志文件%s无更新，直接跳过.",
						conf.getModuleCode(), url));
			}
			selfCheck(conf);
		} catch (MalformedURLException e) {
			String error_msg = String.format("模块%s日志url错误." , conf.getModuleCode());
			String ret_msg = dealErrorTimes(error_msg, moduleType, conf);
			throw new MaintenanceMonitorException(ret_msg, e);
		} catch (IOException e) {
			String error_msg = String.format("模块%s访问日志url出错.",conf.getModuleCode());
			String ret_msg = dealErrorTimes(error_msg, moduleType, conf);
			throw new MaintenanceMonitorException(ret_msg, e);
		}catch (Exception e) {
			String error_msg = String.format("模块%s访问日志url出错.",conf.getModuleCode());
			String ret_msg = dealErrorTimes(error_msg, moduleType, conf);
			throw new MaintenanceMonitorException(ret_msg, e);
		}
	}
	/**
	 * 将二进制数据转换为检测对象 
	 * @param data
	 * @return
	 */
	private   MonitorLogData convertDTI2Bean(byte[] data,LogFileConfigure conf) {
		File f = new File(conf.getOutputFile());
		BufferedWriter output = null;
		DTICity dti = null;
//		LogDataPublishDataCheck logData = null;
		MonitorLogData logData = null;
		try {
			output = new BufferedWriter(new FileWriter(f, false));
		} catch (IOException e) {
			log.error("建立发布数据文件出错,将采用标准输出,msg="+e.getLocalizedMessage(),e);
			output = new BufferedWriter(new PrintWriter(System.out, true));
		}
		String cityCode = dtiexcoding.excoding(data, output,PUBLISH_DTI_STARTPOS_B2B);
		dti = dtiexcoding.getListCityData().get(cityCode);
		if(dti !=null){
			log.info(String.format("解析到城市%s的发布数据,timeid=%d",dti.getCityCode(),dti.getPublishTime()));
			try {
				logData = (MonitorLogData)conf.getGroovyConditionClass().newInstance();
				PropertyUtils.setProperty(logData, "cityCode", dti.getCityCode());
				PropertyUtils.setProperty(logData, "publishTime", dti.getPublishTime());
			} catch (InstantiationException e) {
				log.error("实例化类错误,class="+conf.getGroovyConditionClass().getCanonicalName(),e);
			} catch (IllegalAccessException e) {
				log.error("实例化类非法访问错误,class="+conf.getGroovyConditionClass().getCanonicalName(),e);
			} catch (InvocationTargetException e) {
				log.error("调用实例对象错误,msg="+e.getLocalizedMessage(),e);
			} catch (NoSuchMethodException e) {
				log.error("调用实例属性方法错误,msg="+e.getLocalizedMessage(),e);;
			}
//			logData = new LogDataPublishDataCheck();
			logData.setModuleType(conf.getModuleType());
			logData.setModuleCode(conf.getModuleCode());
			logData.setDateTime(GlobalTime.globalTimeUtc());
			logData.setText("成功收到数据.city="+dti.getCityCode()+",timeid="+dti.getPublishTime()+",系统timeid="+DateUtil.getCurrentTimeID(new Date()));
			logData.setAlarm_status(AlarmHistory.ALARM_STATUS_NOWARN);
//			logData.setCityCode(dti.getCityCode());
//			logData.setPublishTime(dti.getPublishTime());
			return logData;
		}
		
		return null;
	}
	/* (non-Javadoc)
	 * @see com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan#selfCheck(com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure)
	 */
	@Override
	protected void selfCheck(LogFileConfigure conf) {
		MonitorLogData  lastObj = conf.getLastObj();
		Assert.notNull(lastObj, String.format("模块%s日志对象不能为空",conf.getModuleCode()));
		if (Math.abs(lastObj.getDateTime() - GlobalTime.systemTimeUtc()) > 60*5*3) {
			lastObj.setAlarm_report_way(AlarmHistory.ALARM_REPORT_WAY_LINENOTUPDATED);
			lastObj.setText("日志内容15分钟没有更新");
			alarmAnalyse(lastObj, conf);
		}
		
	}
	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ImplPulishDataCheckScan scan = new ImplPulishDataCheckScan();
		LogFileConfigure conf = new LogFileConfigure();
		String moduleType = "PublishDataCheck";
		conf.setModuleType(moduleType);
		conf.setModuleCode(moduleType+"_beijing");
		conf.setModuleDesc("北京数据发布解析检测");
/*		conf.setGroovyConditionClass(groovy.LogDataPublishDataCheck.class);
*/		String httpPath = "http://58.83.237.73:8000/RTTI.IF/traffex.do?PIN=765880&LocationRefType=DTI&EncodeType=10&CITYCODE=1100&CONTENTTYPE=1&GEOMETRY=&MESHIDS=&PREDICTETIME=&ZIP=0";
		conf.setOutputFile("d:/var/rtti/maitenancemonitor/data");
		URL url=null;
		try {
			
			
			url = new URL(httpPath);
			scan.scanFile(url, moduleType, conf);
/*			groovy.LogDataPublishDataCheck log = (LogDataPublishDataCheck) conf.getLastObj();
			scan.log.info(String.format("模块%s本机timeid=%d,DTI数据timeid=%d",conf.getModuleCode(),log.getCurrentTimeID(),log.getPublishTime()));*/
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MaintenanceMonitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
