/**
 * <p>文件名:		MonitorRealTimeService.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.caits.lbs.framework.action.BaseAction;
import com.caits.lbs.framework.log.CommonLogFactory;
import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.JsonUtil;
import com.caits.lbs.framework.utils.StringUtils;
import com.palmcity.rtti.maintenancemonitor.bean.AlarmHistory;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleInfo;
import com.palmcity.rtti.maintenancemonitor.bean.ModuleType;
import com.palmcity.rtti.maintenancemonitor.dao.impl.ModuleTypeDAO;
import com.palmcity.rtti.maintenancemonitor.dao.impl.MonitorUserDAO;
import com.palmcity.rtti.maintenancemonitor.service.DaoTool;
/**
 * <p>
 * MonitorRealTimeService
 * </p>
 * <p>
 * 用途：日志数值类型扫描服务实现类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-9-1
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
 *          <td>2011-9-1 上午11:18:57</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-9-1 上午11:18:57</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class AlarmService extends BaseAction{

	/** TODO */
	private static final long serialVersionUID = 1L;

	

	/** 日志记录器 */
	Logger log = CommonLogFactory.getLog();
	
	public void commonActionParser() {
		try {
			Map<String, Object> paramMap = buildParamMap2String(getRequest().getParameterMap());
			
			String method=paramMap.get("method").toString();
			
			if(method.equals("list"))
			{
				HashMap<String, Object> resultMap=new HashMap<String, Object>();
				
				List<AlarmHistory> resultList=DaoTool.getAlarmHistoryDao().queryListByCondition(paramMap);
				resultMap.put("total", resultList.size());
				resultMap.put("rows", resultList);
				String array = JsonUtil.getJsonStringFromObject(resultMap);
				sendJsonText(array.toString());
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			System.out.println(""+e.getLocalizedMessage());
			return;
		}
	}
	/**
	 * 直接发送json内容，不是附件的方式  
	 * @param response
	 * @param resultString
	 * @throws IOException
	 */
	protected void sendJsonText(String resultString) throws  IOException {
		getResponse().setContentType(ContentTypeJson);
		PrintWriter pw = getResponse().getWriter();
		pw.write(resultString);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 复制参数map，并把数组转换成字符串
	 * 
	 * @param paramMap
	 * @return
	 */
	protected Map<String, Object> buildParamMap2String(Map<String, String> paramMap) {
		Map<String, Object> cloneMap = new HashMap<String, Object>();
		for (Object key : paramMap.keySet()) {
			Object val = paramMap.get(key);
			String value = "";
			if (val instanceof String) {
				value = (String) val;
			} else {
				String[] arr = (String[]) val;
				value = StringUtils.joinArrays(arr, ",");
			}

			cloneMap.put(key.toString().toLowerCase(), value);
		}
		return cloneMap;
	}
}
