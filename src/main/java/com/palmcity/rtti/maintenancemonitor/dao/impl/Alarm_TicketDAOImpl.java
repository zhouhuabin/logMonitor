package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.utils.ClassUtils;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.Alarm_Ticket;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;

public class Alarm_TicketDAOImpl implements Alarm_TicketDAO {
	/** 通用业务访问接口 */
	private IBusinessDAO businessDAO;

	/**
	 * 字段 businessDAO 获取函数
	 * @return the businessDAO : IBusinessDAO
	 */
	public IBusinessDAO getBusinessDAO() {
		return businessDAO;
	}

	/**
	 * 字段 businessDAO 设置函数 : IBusinessDAO
	 * @param businessDAO the businessDAO to set
	 */
	public void setBusinessDAO(IBusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
	}

	@Override
	public Map<String, Object> insertDetail(Map<String, Object> paramMap)
			throws SQLException {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		Long count = getBusinessDAO().queryBusinessCount(
				"alarm_Ticket_selectCountByIAlarm_code", paramMap);
		if (count > 0) {
			retMap.put("result", 0);
			retMap.put("msg", "添加失败!该故障单编号已存在");
			retMap.put("id", -1);
			return retMap;
		}
		try
		{
			 getBusinessDAO().addBusiness(
						"alarm_Ticket_insert", paramMap);
			 retMap.put("result", 1);
			 retMap.put("msg", "新增故障单成功!");
			 retMap.put("id", -1);
			 return retMap;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "新增故障单失败!");
			retMap.put("id", -1);
			return retMap;
		}	
	}
	@Override
	public List<Alarm_Ticket> detailByPrimaryKey(Map<String, Object> paramMap)
			throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		return  getBusinessDAO().queryList("alarm_Ticket_selectByPrimaryKey", paramMap);
	}

}
