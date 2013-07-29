package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;

public interface MonitorMapDAO {
	Map<String, Object> insertMap(Map<String, Object> map) throws SQLException;
	Map<String, Object> updateMapByMapCode(Map<String, Object> map) throws SQLException;
	Map<String, Object> deleteMapByMapCode(Map<String, Object> map) throws SQLException;
	List<MonitorMap> queryMapList(Map<String, Object> map) throws SQLException;
}
