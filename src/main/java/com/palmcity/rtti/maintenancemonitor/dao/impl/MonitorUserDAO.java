/**
 * <p>文件名:	SysFunctionDAO.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		CompanyTag</p>
 * @author		郭训常(guoxunchang@ctfo.com)
 */
package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

/**
 * <p>
 * MonitorUserDAO.java
 * </p>
 * 
 * @author <a href="mailto:zhouhuabin@ctfo.com">周华彬</a>
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th><th width="100px">动作</th><th
 *          width="100px">修改人</th><th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>周华彬</td>
 *          <td>2011-7-18 上午11:40:44</td>
 *          </tr>
 *          </table>
 */
public interface MonitorUserDAO {
	/**
	 * 系统用户添加
	 * 
	 * @param record
	 */
	Map<String, Object> insertDetail(Map<String, Object> map) throws SQLException;

	/**
	 * 系统用户修改
	 * 
	 * @param record
	 * @return
	 */
	Map<String, Object> updateByPrimaryKey(Map<String, Object> map) throws SQLException;

	/**
	 * 系统用户密码修改
	 * 
	 * @param record
	 * @return
	 */
	Map<String, Object> changePasswordByPrimaryKey(Map<String, Object> map) throws SQLException;

	/**
	 * 系统用户详情
	 * 
	 * @param funId
	 * @return
	 */
	MonitorUser detailByPrimaryKey(Map<String, Object> map) throws SQLException;

	/**
	 * 系统用户删除
	 * 
	 * @param funId
	 * @return
	 */
	Map<String, Object> deleteByPrimaryKey(Map<String, Object> map) throws SQLException;

	/**
	 * 系统用户列表
	 * 
	 * @return
	 */
	List<MonitorUser> queryListByCondition(Map<String, Object> map) throws SQLException;

	/**
	 * 用户登录
	 * 
	 * @param paramMap
	 * @exception SQLException
	 * @pdOid 254db2ff-e99b-4b0d-bec2-570c57ad82f4
	 */
	List<MonitorUser> SysOperatorLogin(Map<String, Object> paramMap)
			throws SQLException;
	
	}
