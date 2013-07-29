/**
 * <p>文件名:		AlarmHistoryDAOImpl.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;

/**
 * <p>
 * AlarmHistoryDAOImpl
 * </p>
 * <p>
 * 用途：报警记录处理类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-7-24
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
 *          <td>2011-7-24 下午11:14:39</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-24 下午11:14:39</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class AlarmHistoryDAOImpl implements AlarmHistoryDAO {

	/** 通用业务访问接口 */
	private IBusinessDAO businessDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#insertDetail
	 * (com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory)
	 */
	@Override
	public Map<String, Object> insertDetail(AlarmHistory record)
			throws SQLException {
		return getBusinessDAO().addBusiness("alarmHistory_insert", record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * updateByPrimaryKey
	 * (com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory)
	 */
	@Override
	public Map<String, Object> updateByPrimaryKey(AlarmHistory record)
			throws SQLException {
		return getBusinessDAO().modifyBusiness("alarmHistory_updateByPrimaryKey", record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * finishByPrimaryKey
	 * (com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory)
	 */
	@Override
	public Map<String, Object> finishByPrimaryKey(AlarmHistory record)
			throws SQLException {
		return getBusinessDAO().modifyBusiness("alarmHistory_finishByPrimaryKey", record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * detailByPrimaryKey(java.lang.String, int)
	 */
	public AlarmHistory detailByPrimaryKey(String module_code, int alarm_time)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("module_code", module_code);
		map.put("alarm_time", alarm_time);
		List<AlarmHistory> AlarmList=null;
		AlarmList=getBusinessDAO().queryList("alarmHistory_selectByPrimaryKey",map);
		return (AlarmHistory)AlarmList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * deleteByPrimaryKey(java.lang.String, int)
	 */
	@Override
	public Map<String, Object> deleteByPrimaryKey(String module_code,
			int alarm_time) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("module_code", module_code);
		map.put("alarm_time", alarm_time);
		return getBusinessDAO().deleteBusiness("alarmHistory_deleteByPrimaryKey", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * queryListByCondition(java.util.Map)
	 */
	@Override
	public List<AlarmHistory> queryListByCondition(Map<String, Object> map)
			throws SQLException {
		return getBusinessDAO().queryList("alarmHistory_queryListSelect",map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.AlarmHistoryDAO#
	 * statisListByCondition(java.util.Map)
	 */
	@Override
	public List<Map<String, String>> statisListByCondition(Map<String, Object> map)
			throws SQLException {
		return getBusinessDAO().queryList("alarmHistory_statisListSelect",map);
	}

	/**
	 * 获取变量<code>businessDAO</code>的值
	 * @return 返回的数据类型是<code>IBusinessDAO</code>
	 */
	public IBusinessDAO getBusinessDAO() {
		return businessDAO;
	}

	/**
	 * 设置变量<code> businessDAO</code> 的值
	 * @param businessDAO  <code>businessDAO</code> 参数类型是<code>IBusinessDAO</code>
	 */
	public void setBusinessDAO(IBusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
	}

	@Override
	public Map<String, Object> deleteAlarmByModuleCode(String module_code)
			throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("module_code", module_code);
		return getBusinessDAO().deleteBusiness("alarmHistory_deleteAlarmByModule", map);
	}

}
