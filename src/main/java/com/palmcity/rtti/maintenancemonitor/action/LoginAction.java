package com.palmcity.rtti.maintenancemonitor.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.caits.lbs.framework.Constants;
import com.caits.lbs.framework.action.BaseAction;
import com.caits.lbs.framework.utils.XmlUtil;
import com.common.ajax.server.IRequest;
import com.common.ajax.server.RequestLocalParser;
import com.common.ajax.server.SessionMap;
import com.palmcity.rtti.maintenancemonitor.bean.MonitorUser;

/**
 * <p>
 * LoginAction
 * </p>
 * <p>
 * 用途：登录业务实现动作类 尽量调用BaseAction的getRequestDoc和parseXmlString进行处理，页面端解析xml数据
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-9-6
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
 *          <td>2011-9-6 上午10:06:41</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-9-6 上午10:06:41</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class LoginAction extends BaseAction {
	/** 序列号 */
	private static final long serialVersionUID = 1L;

	/** 内容编码 */
	private final static String ContentType = "UTF-8";

	/** 内类型 */
	private final static String ContentTypeXML = "text/xml";

	/** 用户名 */
	private String userName;

	/** 密码 */
	private String password;

	/** 验证码 */
	private String imgCode;

	/** 消息 */
	private String msg;

	/** 是否成功 */
	private boolean success;

	/**
	 * 登录操作
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType(ContentType);
		response.setContentType(ContentTypeXML);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			log.debug("系统错误" + e);
		}
		msg = "请输入完整!";
		MonitorUser user = new MonitorUser();
		Document requestDoc = getRequestDoc();
		Element root = requestDoc.getRootElement();
		if (getUserName() != null && !"".equals(getUserName())
				&& getPassword() != null && !"".equals(getPassword())
				&& getImgCode() != null && !"".equals(getImgCode())) {
			if (!checkCode(imgCode)) {
				msg = "验证码错误,请重新输入!";
				Element base = root.element(IRequest.XML_DATA).element(getModelName());
				base.addAttribute("result", "-3");
				base.addAttribute("msg", msg);
				pw.print(requestDoc.asXML());
				return;
			}
			Document doc = getServiceParser().request(requestDoc,
					RequestLocalParser.getSession(getRequest()));
			log.info("返回的结果：" + prettyStringLengh(doc.asXML()));
			@SuppressWarnings("unused")
			String loginRe = responseLoginDoc(doc, user);
			pw.print(doc.asXML());
			
		}

	}

	/**
	 * 退出操作
	 * 
	 * @return
	 */
	public String logout() {
		setSuccess(false);
		msg = "";
		getSession().clear();
		if (!getSession().isEmpty())
			getSession().clear();
		if (getRequest().getSession() != null) {
			getRequest().getSession().invalidate();
		}
		if (getRequest().getHeader("x-requested-with") != null
				&& getRequest().getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest")) {
			getResponse().setHeader("isLogined", "noLogin");
			setSuccess(true);
		} else {
			setSuccess(true);
		}
		return SUCCESS;
	}

	/**
	 * 解析登录处理结果
	 * 
	 * @param requestDoc
	 * @return
	 */
	public String responseLoginDoc(Document requestDoc, MonitorUser user) {
		String reString = "未知错误!";

		String httpPath = ((UserConstants) getConstants()).getInternet_path();
		Element root = requestDoc.getRootElement();
		try {
			Element base = root.element("data").element("monitoruser");
			if (base != null) {
				if (base.attributeValue("result").equals("1")) {
					// 加入http访问地址
					base.addAttribute("internet_path", httpPath);
					String xmlhttp = XmlUtil.documentToString(requestDoc);
					getSession().put("LoginXml", xmlhttp);
					if (base != null) {
						Object obj = getSession().get(Constants.SESSION_NAME);
						log.info(String.format("用户%s登录成功.",
								((MonitorUser)obj).getAccount()));
						SessionMap session = RequestLocalParser.getSession(getRequest());
						session.setAttribute(Constants.SESSION_NAME, obj);
						getSession().put(Constants.WEB_SESSION,session);
					}
					return null;
				} else {
					return base.attributeValue("msg");

				}
			}
		} catch (Exception e) {
			log.error("返回参数错误");
		}

		return reString;
	}

	/**
	 * 输出登录结果xml
	 */
	public void getLoginXml() {
		HttpServletResponse response = getResponse();
		response.setContentType(ContentType);
		response.setContentType(ContentTypeXML);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			log.debug("系统错误" + e);
		}
		Object xml = getSession().get("LoginXml");
		if (xml != null)
			log.info("返回的结果:"
					+ xml.toString().substring(
							0,
							Math.min(UserConstants.max_log_output, xml
									.toString().length())));

		pw.print(xml);

	}

	/**
	 * 获取变量<code>userName</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置变量<code> userName</code> 的值
	 * 
	 * @param userName
	 *            <code>userName</code> 参数类型是<code>String</code>
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取变量<code>password</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置变量<code> password</code> 的值
	 * 
	 * @param password
	 *            <code>password</code> 参数类型是<code>String</code>
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取变量<code>success</code>的值
	 * 
	 * @return 返回的数据类型是<code>boolean</code>
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置变量<code> success</code> 的值
	 * 
	 * @param success
	 *            <code>success</code> 参数类型是<code>boolean</code>
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取变量<code>imgCode</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getImgCode() {
		return imgCode;
	}

	/**
	 * 设置变量<code> imgCode</code> 的值
	 * 
	 * @param imgCode
	 *            <code>imgCode</code> 参数类型是<code>String</code>
	 */
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	/**
	 * 获取变量<code>msg</code>的值
	 * 
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置变量<code> msg</code> 的值
	 * 
	 * @param msg
	 *            <code>msg</code> 参数类型是<code>String</code>
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
