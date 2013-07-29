package com.palmcity.rtti.maintenancemonitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;

/**
 * <p>MonitorModuleDao</p>
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
 * <td>0.0.0</td><td>创建类</td><td>fml</td><td>2011-12-1 下午4:19:49</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public interface LogFileConfigureDAO {
	/**
	 * 系统模块添加
	 * 
	 * @param record
	 */
	Map<String, Object> insertLogFileConfigure(Map<String, Object> map) throws SQLException;

	/**
	 * 系统模块修改
	 * 
	 * @param record
	 * @return
	 */
	Map<String, Object> updateByModuleCode(Map<String, Object> map) throws SQLException;

	/**
	 * 系统模块详情
	 * 
	 * @param funId
	 * @return
	 */
	LogFileConfigure detailByPrimaryKey(Map<String, Object> map) throws SQLException;

	/**
	 * 系统模块删除
	 * 
	 * @param funId
	 * @return
	 */
	Map<String, Object> deleteByModuleCode(Map<String, Object> map);

	/**
	 * 系统模块列表
	 * 
	 * @return
	 */
	List<LogFileConfigure> queryListByCondition(Map<String, Object> map) throws SQLException;
	 
	/**
	 * 取得模块类型取得系统模块列表
	 * 
	 * @return
	 */
	List<LogFileConfigure> queryListByModuleType(Map<String, Object> map) throws SQLException;	 

	List<LogFileConfigure> getFileListByModuleType(String moduleType)
			throws SQLException;
}
