/**
 * <p>文件名:		LogTypeConfigure.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;

/**
 * <p>
 * LogTypeConfigure
 * </p>
 * <p>
 * 用途：日志处理不同类型的配置类
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
 *          <td>2011-7-18 下午2:51:55</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-18 下午2:51:55</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class LogTypeConfigure extends ETBase {

	/** TODO */
	private static final long serialVersionUID = 1L;

	/** 插件处理类名称 */
	private String processClass;
	/** 线程个数 */
	private int threadNum;
	/**日志配置列表 */
	private List<LogFileConfigure> fileList = new LinkedList<LogFileConfigure>();
	
	/** 日志实体groovy扩展类 */
	private String groovyFilePath;

	/**
	 * 获取变量<code>processClass</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getProcessClass() {
		return processClass;
	}
	/**
	 * 设置变量<code> processClass</code> 的值
	 * @param processClass  <code>processClass</code> 参数类型是<code>String</code>
	 */
	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}
	/**
	 * 获取变量<code>threadNum</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getThreadNum() {
		return threadNum;
	}
	/**
	 * 设置变量<code> threadNum</code> 的值
	 * @param threadNum  <code>threadNum</code> 参数类型是<code>int</code>
	 */
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	/**
	 * 获取变量<code>fileList</code>的值
	 * @return 返回的数据类型是<code>List<LogFileConfigure></code>
	 */
	public List<LogFileConfigure> getFileList() {
		return fileList;
	}
	/**
	 * 设置变量<code> fileList</code> 的值
	 * @param fileList  <code>fileList</code> 参数类型是<code>List<LogFileConfigure></code>
	 */
	public void setFileList(List<LogFileConfigure> fileList) {
		this.fileList = fileList;
	}
	/**
	 * 获取变量<code>groovyFilePath</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getGroovyFilePath() {
		return groovyFilePath;
	}

	/**
	 * 设置变量<code> groovyFilePath</code> 的值
	 * @param groovyFilePath  <code>groovyFilePath</code> 参数类型是<code>String</code>
	 */
	public void setGroovyFilePath(String groovyFilePath) {
		this.groovyFilePath = groovyFilePath;
	}
	
}
