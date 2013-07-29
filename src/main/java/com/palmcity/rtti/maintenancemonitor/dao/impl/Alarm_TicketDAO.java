package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.Alarm_Ticket;

public interface Alarm_TicketDAO {

	/**
	 * 故障单记录添加
	 * 
	 * @param record
	 */
	Map<String, Object> insertDetail(Map<String, Object> paramMap)
			throws SQLException;
	/**
	 * 故障单记录详情
	 * 
	 * @param module_code  模块标识
	 * @param alarm_time   报警时间
	 * @return
	 */
	List<Alarm_Ticket> detailByPrimaryKey(Map<String, Object> paramMap)
			throws SQLException, DataAccessException;
}
