/**
 * <p>文件名:		ImplReceiveLogFileScan.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.util.List;

import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;

/**
 * <p>
 * ImplReceiveLogFileScan
 * </p>
 * <p>
 * 用途：仿真日志文件行处理实现类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-7-18
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th>
 *          <th width="100px">动作</th>
 *          <th width="100px">修改人</th>
 *          <th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-18 下午3:44:25</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-18 下午3:44:25</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class ImplSimulateLogFileScan extends AbstractLogFileScan {

	/**
	 * 构造器 
	 */
	public ImplSimulateLogFileScan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImplSimulateLogFileScan(List<LogFileConfigure> cfList) {
		super(cfList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan#parseLogLine
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void parseLogLine(String line, String moduleType,LogFileConfigure conf) {
		super.parseLogLine(line, conf.getModuleType(), conf);
	}

}
