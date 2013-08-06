package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.utils.ClassUtils;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleInfo;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleType;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorMap;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

public class ModuleTypeDAOImpl implements ModuleTypeDAO{

	/** 通用业务访问接口 */
	private IBusinessDAO businessDAO;
	@Override
	public List<ModuleType> queryModuleTypeList(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return getBusinessDAO().queryList("moduleType_queryListSelect", map);
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
	public Map<String, Object> insertDetail(Map<String, Object> paramMap)
			throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		try
		{
			 getBusinessDAO().addBusiness(
						"moduleType_insert", paramMap);
			 retMap.put("result", 1);
			 retMap.put("msg", "新增模板成功!");
			 retMap.put("id", -1);
			 return retMap;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "新增模板失败!");
			retMap.put("id", -1);
			return retMap;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.caits.lbs.dataservice.dao.SysFunctionDAO#deleteByPrimaryKey(java.
	 * lang.String)
	 */
	@Override
	public Map<String, Object> deleteModuleTypeById(Map<String, Object> map)
			throws SQLException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try
		{
			getBusinessDAO().deleteBusiness(
					"moduleType_deleteById", map);
			 retMap.put("result", 1);
			 retMap.put("msg", "删除模板成功!");
			 retMap.put("id", -1);
			 return retMap;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			retMap.put("result", 0);
			retMap.put("msg", "删除模板失败!");
			retMap.put("id", -1);
			return retMap;
		}
	}
}
