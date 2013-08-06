package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleType;

public interface ModuleTypeDAO {
	/**
	 * FIXME 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	List<ModuleType> queryModuleTypeList(Map<String, Object> map) throws SQLException;
	/**
	 * 模板记录添加
	 * 
	 * @param record
	 */
	Map<String, Object> insertDetail(Map<String, Object> paramMap) throws SQLException,DataAccessException;

	Map<String, Object> deleteModuleTypeById(Map<String, Object> map)
			throws SQLException;

}
