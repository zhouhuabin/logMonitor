/**
 * <p>文件名:		MonitorUser.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.bean;

import com.caits.lbs.bean.dbmodel.ETBase;

/**
 * <p>
 * MonitorUser
 * </p>
 * <p>
 * 用途：运维监控用户对象类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-7-13
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
 *          <td>2011-7-13 下午5:27:26</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午5:27:26</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MonitorUser extends ETBase {

	/** default */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取变量<code>op_id</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getOp_id() {
		return op_id;
	}

	/**
	 * 设置变量<code> op_id</code> 的值
	 * @param op_id  <code>op_id</code> 参数类型是<code>int</code>
	 */
	public void setOp_id(int op_id) {
		this.op_id = op_id;
	}

	/**
	 * 获取变量<code>account</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置变量<code> account</code> 的值
	 * @param account  <code>account</code> 参数类型是<code>String</code>
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取变量<code>password</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置变量<code> password</code> 的值
	 * @param password  <code>password</code> 参数类型是<code>String</code>
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取变量<code>real_name</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getReal_name() {
		return real_name;
	}

	/**
	 * 设置变量<code> real_name</code> 的值
	 * @param real_name  <code>real_name</code> 参数类型是<code>String</code>
	 */
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	/**
	 * 获取变量<code>role_id</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getRole_id() {
		return role_id;
	}

	/**
	 * 设置变量<code> role_id</code> 的值
	 * @param role_id  <code>role_id</code> 参数类型是<code>int</code>
	 */
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	/**
	 * 获取变量<code>update_time</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getUpdate_time() {
		return update_time;
	}

	/**
	 * 设置变量<code> update_time</code> 的值
	 * @param update_time  <code>update_time</code> 参数类型是<code>int</code>
	 */
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}

	/** 用户id */
	private int op_id;
	
	/** 登录账户 */
	private String  account;
	
	/** 登录密码 */
	private String password;
	
	private String mobile;
	
	/**
	 * 字段 mobile 获取函数
	 * @return the mobile : String
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 字段 mobile 设置函数 : String
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 真是姓名 */
	private String real_name;
	
	/** 角色id */
	private int role_id;
	
	/** 更新时间UTC */
	private int update_time;

	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
