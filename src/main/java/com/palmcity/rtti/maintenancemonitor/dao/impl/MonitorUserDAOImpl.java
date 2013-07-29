package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.caits.lbs.framework.dao.IBusinessDAO;
import com.caits.lbs.framework.dao.impl.BusinessDAOImpl;
import com.caits.lbs.framework.utils.ClassUtils;
import com.caits.lbs.framework.utils.DataBaseUtils;
import com.caits.lbs.framework.utils.GlobalTime;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

/**
 * <p>
 * MonitorUserDAOImpl.java 用户管理数据访问接口实现类
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
 *          <td>2011-7-18 上午11:43:39</td>
 *          </tr>
 *          </table>
 */
public class MonitorUserDAOImpl implements MonitorUserDAO {

	/** 日志记录器 */
	protected static Logger log = Logger.getLogger(MonitorUserDAOImpl.class);

	/** 通用业务访问接口 */
	private IBusinessDAO businessDAO;

	/**
	 * 用户登录
	 * 
	 * @param paramMap
	 * @exception SQLException
	 * @pdOid 7a6e6c52-8d6f-418c-9e79-13b9a9b61f0c
	 */
	public List<MonitorUser> SysOperatorLogin(Map<String, Object> paramMap)
			throws SQLException {
		List<MonitorUser> list = getBusinessDAO().queryList("SysOperatorLogin",
				paramMap);
		return list;
	}

	/**
	 * FIXME
	 * 
	 * @throws SQLException
	 */
	void testConnection() throws SQLException {
		BusinessDAOImpl dao = (BusinessDAOImpl) getBusinessDAO();
		DataSource ds = dao.getSqlMapClientTemplate().getSqlMapClient()
				.getDataSource();
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("select * from man_tb_monitoruser");
		DataBaseUtils.closeConnection(rset, stmt, conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.caits.lbs.dataservice.dao.SysFunctionDAO#insert(com.caits.lbs.dataservice
	 * .bean.SysFunction)
	 */
	@Override
	public Map<String, Object> insertDetail(Map<String, Object> map)
			throws SQLException {
		//帐户有效性检查
		Long count = getBusinessDAO().queryBusinessCount(
				"monitorUser_selectCountByIdAccount", map);
		if (count > 0) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("result", 0);
			retMap.put("msg", "添加失败!该帐户已存在,请换个帐户再试.");
			retMap.put("id", -1);
			return retMap;
		}
		//帐户序列获取
		List<Object> list = getBusinessDAO().queryList("monitorUser_opid_seq");
		if (list.size() > 0) {
			int maxOpid = (Integer) list.get(0);
			MonitorUser record = (MonitorUser) ClassUtils.map2Bean(map,
					MonitorUser.class);
			record.setOp_id(maxOpid + 1);
			record.setUpdate_time(GlobalTime.systemTimeUtc());
			return getBusinessDAO().addBusiness("monitorUser_insert", record);
		} else {
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("result", 0);
			retMap.put("msg", "添加失败!无法获取现有帐户最大id,请联系管理员.");
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
	public Map<String, Object> deleteByPrimaryKey(Map<String, Object> map)
			throws SQLException {
		return getBusinessDAO().deleteBusiness(
				"monitorUser_deleteByPrimaryKey", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.caits.lbs.dataservice.dao.SysFunctionDAO#updateByPrimaryKey(com.caits
	 * .lbs.dataservice.bean.SysFunction)
	 */
	@Override
	public Map<String, Object> updateByPrimaryKey(Map<String, Object> map)
			throws SQLException {
		MonitorUser record = (MonitorUser) ClassUtils.map2Bean(map,
				MonitorUser.class);
		record.setUpdate_time(GlobalTime.systemTimeUtc());
		Long count = getBusinessDAO().queryBusinessCount(
				"monitorUser_selectCountByIdAccount", map);
		if (count > 0) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("result", 0);
			retMap.put("msg", "更新失败!该帐户已存在,请换个帐户再试.");
			retMap.put("id", -1);
			return retMap;
		} else
			return getBusinessDAO().modifyBusiness(
					"monitorUser_updateByPrimaryKey", record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO#
	 * changePasswordByPrimaryKey(java.util.Map)
	 */
	public Map<String, Object> changePasswordByPrimaryKey(
			Map<String, Object> map) throws SQLException {
		map.put("update_time", GlobalTime.systemTimeUtc());
		return getBusinessDAO().modifyBusiness(
				"monitorUser_changePasswordByPrimaryKey", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.caits.lbs.dataservice.dao.SysFunctionDAO#selectByPrimaryKey(java.
	 * lang.String)
	 */
	@Override
	public MonitorUser detailByPrimaryKey(Map<String, Object> map)
			throws SQLException {
		return (MonitorUser) getBusinessDAO().queryBusiness(
				"monitorUser_selectByPrimaryKey", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.caits.lbs.dataservice.dao.SysFunctionDAO#queryListSysFunction(java
	 * .util.Map)
	 */
	@Override
	public List<MonitorUser> queryListByCondition(Map<String, Object> map)
			throws SQLException {
		return getBusinessDAO().queryList("monitorUser_queryListSelect", map);
	}

	/**
	 * @return the businessDAO
	 */
	public IBusinessDAO getBusinessDAO() {
		return businessDAO;
	}

	/**
	 * @param businessDAO
	 *            the businessDAO to set
	 */
	public void setBusinessDAO(IBusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
	}

	/**
	 * 将 map转换为指定类的实例
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static ETBase map2BeanBak(Map<String, Object> map,
			Class<? extends ETBase> clazz) {
		Assert.notNull(map, "传入的map不能为空");
		try {
			ETBase obj = clazz.newInstance();
			for (String key : map.keySet()) {
				try {
					Class<?> type = PropertyUtils.getPropertyType(obj, key);
					if (type == null) {
						log.warn("属性类型为空,key=" + key);
						continue;
					}
					if (type.equals(String.class)) {
						PropertyUtils.setProperty(obj, key, map.get(key));
					} else if (type.equals(Long.class)
							|| type.equals(long.class)) {
						PropertyUtils.setProperty(obj, key,
								Long.parseLong("" + map.get(key)));
					} else if (type.equals(Integer.class)
							|| type.equals(int.class)) {
						PropertyUtils.setProperty(obj, key,
								Integer.parseInt("" + map.get(key)));
					} else {
						log.warn("属性类型不支持," + key + ",type="
								+ type.getCanonicalName());
					}
				} catch (InvocationTargetException e) {
					log.error(
							"设置属性目标错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				} catch (NoSuchMethodException e) {
					log.error(
							"设置属性方法不存在错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				} catch (IllegalArgumentException e) {
					log.error(
							"设置属性非法参数错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				}
			}
			return obj;
		} catch (InstantiationException e) {
			log.error(
					"实例化类错误," + clazz.getCanonicalName() + ",msg="
							+ e.getLocalizedMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(
					"实例化类非法访问错误," + clazz.getCanonicalName() + ",msg="
							+ e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * 将 bean转换为map的实例
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Map<String, Object> bean2mapBak(ETBase obj) {
		Assert.notNull(obj, "传入的bean不能为空");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PropertyDescriptor[] pds = PropertyUtils
					.getPropertyDescriptors(obj);
			for (PropertyDescriptor pd : pds) {
				String key = pd.getName();

				try {
					Class<?> type = PropertyUtils.getPropertyType(obj, key);
					if (type == null) {
						log.warn("属性类型为空,key=" + key);
						continue;
					}
					if (type.equals(String.class) || type.equals(Long.class)
							|| type.equals(long.class)
							|| type.equals(Integer.class)
							|| type.equals(int.class)) {
						map.put(key, PropertyUtils.getProperty(obj, key));
					} else {
						log.warn("属性类型不支持," + key + ",type="
								+ type.getCanonicalName());
					}
				} catch (InvocationTargetException e) {
					log.error(
							"获取属性目标错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				} catch (NoSuchMethodException e) {
					log.error(
							"获取属性方法不存在错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				} catch (IllegalArgumentException e) {
					log.error(
							"获取属性非法参数错误," + key + ",msg="
									+ e.getLocalizedMessage(), e);
				}
			}
			return map;
		} catch (IllegalAccessException e) {
			log.error(
					"转换实例到map错误," + obj.getId() + ",msg="
							+ e.getLocalizedMessage(), e);
		}
		return null;
	}

}
