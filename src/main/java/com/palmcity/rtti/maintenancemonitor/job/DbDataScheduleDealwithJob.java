/**
 * <p>文件名:	DbDataScheduleDealwithJob.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		CompanyTag</p>
 * @author		周华彬(zhouhuabin@ctfo.com)
 */
package com.palmcity.rtti.maintenancemonitor.job;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.utils.JsonUtil;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan;

/**
 * <p>
 * DbDataScheduleDealwithJob.java
 * </p>
 * <p>
 * 定期清理数据库历史数据
 * </p>
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
 *          <td>2011-9-20 上午11:24:18</td>
 *          </tr>
 *          <tr>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          </tr>
 *          </table>
 */
public class DbDataScheduleDealwithJob {
	public final static String _KEY_NAME = "KEY";
	public final static String _VALUE_NAME = "VALUE";

	/** 日志记录类 */
	private Logger log = Logger.getLogger(getClass());
	/** 服务地址列表 */
	private List<String> sqlIds;

	private IBusinessDAO dao;
	/** 初始化时，是否同步 */
	private boolean synchronizeUp = true;

	/** 保留历史数据的天数 */
	private int remainDays;

	/** 配置类 */
	private MaintenanceMonitorConfigure configure = null;

	/** 所有模块集合代码 */
	private Set<String> moduleCodeSet = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void init() throws Exception {
		log.info("定期清理历史数据库任务实例启动。");
		Map<String, AbstractLogFileScan> map = getConfigure().getCodeConfMap();
		moduleCodeSet = map.keySet();
		if (synchronizeUp) {
			taskJob();
		}
	}

	/**
	 * 定时执行的任务方法
	 * 
	 * @return
	 */
	public boolean taskJob() {
		boolean ok = true;
		GregorianCalendar calStart = new GregorianCalendar();
		calStart.add(GregorianCalendar.DATE, -(getRemainDays() + 10));
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.add(GregorianCalendar.DATE, -getRemainDays());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", calStart.getTimeInMillis() / 1000);
		paramMap.put("endTime", calEnd.getTimeInMillis() / 1000);
		for (String moduleCode : moduleCodeSet) {
			paramMap.put("module_code", moduleCode);
			for (String sqlid : sqlIds) {
				try {

					Map<String, Object> datas = dao.deleteBusiness(sqlid,
							paramMap);
					if (datas != null && !datas.isEmpty()) {
						log.info("sql执行结果"
								+ JsonUtil.getJsonStringFromMap(datas));
					}
				} catch (SQLException e) {
					ok = false;
					log.error("执行sql语句出错sqlid=" + sqlid, e);
				}
			}
		}
		return ok;
	}

	/**
	 * @return the sqlIds
	 */
	public List<String> getSqlIds() {
		return sqlIds;
	}

	/**
	 * @param sqlIds
	 *            the sqlIds to set
	 */
	public void setSqlIds(List<String> sqlIds) {
		this.sqlIds = sqlIds;
	}

	/**
	 * @return the dao
	 */
	public IBusinessDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(IBusinessDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the synchronizeUp
	 */
	public boolean isSynchronizeUp() {
		return synchronizeUp;
	}

	/**
	 * @param synchronizeUp
	 *            the synchronizeUp to set
	 */
	public void setSynchronizeUp(boolean synchronizeUp) {
		this.synchronizeUp = synchronizeUp;
	}

	/**
	 * 获取变量<code>remainDays</code>的值
	 * 
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getRemainDays() {
		return remainDays;
	}

	/**
	 * 设置变量<code> remainDays</code> 的值
	 * 
	 * @param remainDays
	 *            <code>remainDays</code> 参数类型是<code>int</code>
	 */
	public void setRemainDays(int remainDays) {
		this.remainDays = remainDays;
	}

	/**
	 * 获取变量<code>configure</code>的值
	 * 
	 * @return 返回的数据类型是<code>MaintenanceMonitorConfigure</code>
	 */
	public MaintenanceMonitorConfigure getConfigure() {
		return configure;
	}

	/**
	 * 设置变量<code> configure</code> 的值
	 * 
	 * @param configure
	 *            <code>configure</code> 参数类型是
	 *            <code>MaintenanceMonitorConfigure</code>
	 */
	public void setConfigure(MaintenanceMonitorConfigure configure) {
		this.configure = configure;
	}

}
