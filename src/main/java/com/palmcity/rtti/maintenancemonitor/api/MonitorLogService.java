/**
 * <p>文件名:		MonitorLogService.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.api;

import org.apache.log4j.Logger;

/**
 * <p>
 * MonitorLogService
 * </p>
 * <p>
 * 用途：日志服务类，包装为统一的接口
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-6-16
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
 *          <td>2011-6-16 下午05:24:45</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-6-16 下午05:24:45</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MonitorLogService extends Thread{
	
	/** 序列号 */
	public static final long serialVersionUID = 1L;

	/** 写入文件间隔时间 */
	private  int WRITE_INTERVAL = 60*1000;
	
	/** 日志记录器 */
	private Logger log = Logger.getLogger(getClass());
	
	
	/** 是否运行标志 */
	private boolean bRun = true;

	/** 最后一次数据 */
	private MonitorLogData lastData = null;
	
	/** 日志产生器实例 */
	private IMonitorLogGenerator generator = null;
	/**
	 * 构造器 
	 */
	public MonitorLogService(IMonitorLogGenerator logGenerator,int interval) {
		generator = logGenerator;
		if(interval>0) WRITE_INTERVAL = interval;
		start();
	}


	/**
	 * 构造器
	 * @param generator 
	 * @param interval
	 * @param appenderName TODO
	 */
	public MonitorLogService(IMonitorLogGenerator generator, int interval, String appenderName) {
		if(interval>0) WRITE_INTERVAL = interval;
		this.generator = generator;
		if(appenderName!=null) log = Logger.getLogger(appenderName);
		MonitorLogError.log.info("获取命名日志写入器,"+appenderName);
		start();
	}


	/**
	 * 写入日志 
	 * @param data
	 */
	public void writeLog(MonitorLogData data){
		log.info(data.toJSONString());
		lastData = (data);
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(bRun){
			log.info((lastData = generator.buildData()).toJSONString());
			synchronized (this) {
				try {
					wait(WRITE_INTERVAL);
				} catch (InterruptedException e) {
					MonitorLogError.log.error("日志记录线程被打断异常",e);
				}
			}
		}
	}


	/**
	 * 获取变量<code>lastData</code>的值
	 * @return 返回的数据类型是<code>MonitorLogData</code>
	 */
	public MonitorLogData getLastData() {
		return lastData;
	}

	
	/**
	 * 停止日志服务 
	 */
	public void stopService(){
		bRun = false;
		synchronized (this) {
			notify();
		}
		MonitorLogError.log.error("日志服务已停止.log.name="+log.getName());
	}
}
