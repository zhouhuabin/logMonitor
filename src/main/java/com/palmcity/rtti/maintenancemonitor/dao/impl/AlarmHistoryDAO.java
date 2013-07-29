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

import org.springframework.dao.DataAccessException;

import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;

/**
 * <p>
 * AlarmHistoryDAO.java
 * </p>
 * 报警历史访问接口
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
public interface AlarmHistoryDAO {
	/**
	 * 报警记录添加
	 * 
	 * @param record
	 */
	Map<String, Object> insertDetail(AlarmHistory record) throws SQLException,DataAccessException;

	/**
	 * 开始报警处理记录修改
	 * 
	 * @param record
	 * @return
	 */
	Map<String, Object> updateByPrimaryKey(AlarmHistory record) throws SQLException,DataAccessException;

	/**
	 * 结束报警处理修改
	 * 
	 * @param record
	 * @return
	 */
	Map<String, Object> finishByPrimaryKey(AlarmHistory record) throws SQLException,DataAccessException;

	/**
	 * 报警记录详情
	 * 
	 * @param module_code  模块标识
	 * @param alarm_time   报警时间
	 * @return
	 */
	AlarmHistory detailByPrimaryKey(String module_code,int alarm_time) throws SQLException,DataAccessException;

	/**
	 * 报警记录删除
	 * 
	 * @param funId
	 * @return
	 */
	Map<String, Object> deleteByPrimaryKey(String module_code,int alarm_time) throws SQLException,DataAccessException;
	/**
	 * 模块报警删除
	 * 
	 * @param funId
	 * @return
	 */
	Map<String, Object> deleteAlarmByModuleCode(String module_code) throws SQLException,DataAccessException;

	/**
	 * 按条件查询报警记录 
	 * 
	 * @return
	 */
	List<AlarmHistory> queryListByCondition(Map<String, Object> map) throws SQLException,DataAccessException;

	/**
	 * 按条件统计报警总数
	 * 
	 * @param paramMap
	 * @exception SQLException
	 * @pdOid 254db2ff-e99b-4b0d-bec2-570c57ad82f4
	 */
	List<Map<String, String>> statisListByCondition(Map<String, Object> paramMap)
			throws SQLException,DataAccessException;
	
	}
