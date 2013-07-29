package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.utils.ClassUtils;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;

public class MonitorMapDAOImpl implements MonitorMapDAO{

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
	public Map<String, Object> insertMap(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		MonitorMap record = (MonitorMap) ClassUtils.map2Bean(map,
				MonitorMap.class);
		try
		{
			 getBusinessDAO().addBusiness(
						"MonitorMap_InsertMap", record);
			 retMap.put("result", 1);
			 retMap.put("msg", "新增拓扑图成功!");
			 retMap.put("id", -1);
			 return retMap;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "新增拓扑图失败!");
			retMap.put("id", -1);
			return retMap;
		}	
	}

	@Override
	public Map<String, Object> updateMapByMapCode(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try
		{
			 getBusinessDAO().modifyBusiness(
						"MonitorMap_updateMap", map);
			 retMap.put("result", 1);
			 retMap.put("msg", "更新成功!");
			 retMap.put("id", -1);
			 return retMap;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "更新失败!");
			retMap.put("id", -1);
			return retMap;
		}	
	}

	@Override
	public Map<String, Object> deleteMapByMapCode(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
	    try {
			getBusinessDAO().deleteBusiness("MonitorMap_deleteMap", map);
			 retMap.put("result", 1);
			 retMap.put("msg", "删除成功!");
			 retMap.put("id", -1);
			 return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "删除失败!");
			retMap.put("id", -1);
			return retMap;
		}
	}

	@Override
	public List<MonitorMap> queryMapList(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		List<MonitorMap> MapList=null;
		MapList=getBusinessDAO().queryList(
				"MonitorMap_queryMapListSelect", map);
		for(int i=0;i<MapList.size();i++)
		{
			MonitorMap monitorMap=(MonitorMap)MapList.get(i);
			Map<String, Object> parmMap = new HashMap<String, Object>();
					parmMap.put("mapCode", monitorMap.getMapCode());
			Long ModuleCount=getBusinessDAO().queryBusinessCount("LogFileConfigure_selectModuleCountByMapCode",parmMap);
			monitorMap.setModuleCount(ModuleCount);
			MapList.set(i, monitorMap);
		}
		return 	MapList;
		
	}
}
