/**
 * <p>文件名:		LBSException.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		CompanyTag</p>
 * @author		xxx(xxx@xxx.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;

import com.caits.lbs.exception.LBSException;

/**
 * <p>
 * MaintenanceMonitorException
 * </p>
 * <p>
 * 运维监控平台所有异常的根，所有业务相关的异常必须继承此类，必须
 * </p>
 * 
 * @author zhb(zhouhuabin@ctfo.com)
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
 *          <td>2011-2-11 下午03:58:11</td>
 *          </tr>
 *          <tr>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          <td>XXX</td>
 *          </tr>
 *          </table>
 */
public class MaintenanceMonitorException extends LBSException {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 构造器
	 */
	public MaintenanceMonitorException() {
		super();
	}

	/**
	 * 构造器
	 * 
	 * @param code
	 */
	public MaintenanceMonitorException(int code) {
		super(code);
	}

	/**
	 * 构造器
	 * 
	 * @param message
	 */
	public MaintenanceMonitorException(String message) {
		super(message);
	}

	/**
	 * 构造器
	 * 
	 * @param code
	 * @param message
	 */
	public MaintenanceMonitorException(int code, String message) {
		super(message);
		setCode(code);
	}

	/**
	 * 构造器
	 * 
	 * @param cause
	 */
	public MaintenanceMonitorException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造器
	 * 
	 * @param code
	 * @param cause
	 */
	public MaintenanceMonitorException(int code, Throwable cause) {
		super(cause);
		setCode(code);
	}

	/**
	 * 构造器
	 * 
	 * @param message
	 * @param cause
	 */
	public MaintenanceMonitorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造器
	 * 
	 * @param code
	 * @param message
	 * @param cause
	 */
	public MaintenanceMonitorException(int code, String message, Throwable cause) {
		super(message, cause);
		setCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// 如果输出异常的类信息，会让事情变得复杂并且不可预料，所以还是老老实实输出改输出的吧
		// if (Framework.Debug) {
		// return new JSONObject(this, true).toString();
		// } else {
		// return super.toString();
		// }
		return super.toString();
	}

	
	
	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			throw new MaintenanceMonitorException("gase");
		} catch (MaintenanceMonitorException e) {
			System.out.println("GASException.main():"
					+ toStacksString(new Exception("hh", e)));
		}
	
	}

}
