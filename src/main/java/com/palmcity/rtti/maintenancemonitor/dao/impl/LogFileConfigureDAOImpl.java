package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.utils.ClassUtils;
import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

/**
 * <p>MonitorModuleDAOImpl</p>
 * <p>TODO</p>
 *
 * @author		冯明亮(fengmingliang@ctfo.com, 996036006@qq.com)
 * @version		0.0.0
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.0</td><td>创建类</td><td>fml</td><td>2011-12-1 下午4:42:23</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public class LogFileConfigureDAOImpl implements LogFileConfigureDAO{

	/** 日志记录器 */
	protected static Logger log = Logger.getLogger(LogFileConfigureDAOImpl.class);

	/** 通用业务访问接口 */
	private IBusinessDAO businessDAO;
	@Override
	public Map<String, Object> insertLogFileConfigure(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		//帐户有效性检查
				Long count = getBusinessDAO().queryBusinessCount(
						"LogFileConfigure_selectCountByModuleCode", map);
				if (count > 0) {
					Map<String, Object> retMap = new HashMap<String, Object>();
					retMap.put("result", 0);
					retMap.put("msg", "添加失败!该模块已存在,请换个ModuleCode再试.");
					retMap.put("id", -1);
					return retMap;
				}
				//帐户序列获取
				List<Object> list = getBusinessDAO().queryList("LogFileConfigure_queryListSelect");
				if (list.size() > 0) {
					LogFileConfigure record = (LogFileConfigure) ClassUtils.map2Bean(map,
							LogFileConfigure.class);
					
					 getBusinessDAO().addBusiness("LogFileConfigure_insert", record);
					 Map<String, Object> retMap = new HashMap<String, Object>();
						retMap.put("result", 1);
						retMap.put("msg", "添加成功!");
						retMap.put("id", -1);
					 return retMap;
				} else {
					Map<String, Object> retMap = new HashMap<String, Object>();
					retMap.put("result", 0);
					retMap.put("msg", "添加失败!无法获取现有模块最大ID,请联系管理员.");
					retMap.put("id", -1);
					return retMap;
				}
	}

	@Override
	public Map<String, Object> updateByModuleCode(Map<String, Object> map) throws SQLException
			 {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		LogFileConfigure record = (LogFileConfigure) ClassUtils.map2Bean(map,
				LogFileConfigure.class);
		try
		{
			 getBusinessDAO().modifyBusiness(
						"LogFileConfigure_updateByModuleCode", record);
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
	public LogFileConfigure detailByPrimaryKey(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 字段 log 获取函数
	 * @return the log : Logger
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * 字段 log 设置函数 : Logger
	 * @param log the log to set
	 */
	public static void setLog(Logger log) {
		LogFileConfigureDAOImpl.log = log;
	}

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
	public Map<String, Object> deleteByModuleCode(Map<String, Object> map)
	{
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
	    try {
			getBusinessDAO().deleteBusiness("LogFileConfigure_deleteByModuleCode", map);
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
	public List<LogFileConfigure> queryListByCondition(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return getBusinessDAO().queryList(
				"LogFileConfigure_queryListSelect", map);
	}

	@Override
	public List<LogFileConfigure> queryListByModuleType(Map<String, Object> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return getBusinessDAO().queryList(
				"LogFileConfigure_selectByModuleType", map);
	}

	@Override
	public List<LogFileConfigure> getFileListByModuleType(String moduleType)
			throws SQLException {
		// TODO Auto-generated method stub
    	/***新版本<数据库配置>List获取*/
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	map.put("moduleType",moduleType);
    	List<LogFileConfigure> list = null;
    	try {
    		list = this.queryListByModuleType(map);
    	} catch (SQLException e1) {
    		// TODO Auto-generated catch block
    		log.error("构造日志处理应用列表异常", e1);
    	}
		return list;
	}
}
