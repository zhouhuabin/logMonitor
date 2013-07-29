package com.palmcity.rtti.maintenancemonitor.action;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.caits.lbs.framework.Constants;

/**
 * <p>
 * Constants
 * </p>
 * <p>
 * 业务模块的常量类，从通用类中继承而来
 * </p>
 * 
 * @version 0.0.0
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
 *          <td>周华彬</td>
 *          <td>2011-4-21 下午03:50:54</td>
 *          </tr>
 *          <tr>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          </tr>
 *          </table>
 */
public class UserConstants extends Constants{
	
	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	
	/** 程序的编码 */
	public static final String SouceCodeEncoding = Charset.defaultCharset().name();

	
	/** 公网地址 */
	private String internet_path;
	
	/**
	 * 获取变量<code>internet_path</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getInternet_path() {
		return internet_path;
	}

	/**
	 * 设置变量<code> internet_path</code> 的值
	 * @param internet_path  <code>internet_path</code> 参数类型是<code>String</code>
	 */
	public void setInternet_path(String internet_path) {
		this.internet_path = internet_path;
	}
	

}