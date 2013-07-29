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
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.caits.lbs.framework.action.BaseAction;
import com.caits.lbs.framework.utils.Base64Codec;
import com.caits.lbs.framework.utils.JsonUtil;
import com.caits.lbs.framework.utils.StringUtils;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorConfigure;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import com.palmcity.rtti.maintenancemonitor.dao.impl.LogFileConfigureDAO;

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
public class LogVluesScan extends BaseAction{

	/** TODO */
	private static final long serialVersionUID = 1L;

	

	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	
	
	/** 数值类型 */
	static Pattern numberPattern = Pattern.compile("^[0-9]\\d*$");
	
	/** 浮点类型 */
	static Pattern doublePattern = Pattern.compile("[\\d]+\\.[\\d]+");
	
	private final static String MARK="|";
	/** LONG类型标识 */
	private final static String LONG_TYPE="1";
	
	private final static String DOUBLE_TYPE="2";
	
	/** STRING类型标识 */
	private final static String STRING_TYPE="3";
	
	/** MAP类型标识 */
	private final static String MAP_TYPE="4";
	
	/** LIST类型标识 */
	private final static String LIST_TYPE="5";
	
	/** OBJECT类型标识 */
	private final static String OBJECT_TYPE="6";
	/** BOOLEAN类型标识 */
	private final static String BOOLEAN_TYPE="7";
	

	public void commonActionParser() {
		try {
			Map<String, String> paramMap = buildParamMap2String(getRequest().getParameterMap());
			getLogVlues(paramMap);
			System.out.print("");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			System.out.println(""+e.getLocalizedMessage());
			return;
		}
	}
	
	/**
	 * 获取所有模块的配置信息 
	 * @param response
	 * @throws IOException 
	 * @throws MaintenanceMonitorException 
	 */
	protected void getLogVlues(Map<String, String> paramMap) throws IOException, MaintenanceMonitorException {
		String logUrl=Base64Codec.decode((String)paramMap.get("logurl"));
		URL url = new URL(logUrl);
		String jsonStr=scanFile(url);
		Map<String, Object> resultMap=getLogValuesMap(jsonStr);
		log.info("获取所有模块的配置文件成功");
		String array = JsonUtil.getJsonStringFromMap(resultMap);
		sendJsonText(array.toString());
	}
	
	public static Map<String, Object> getLogValuesMap(String jsonStr) throws MalformedURLException, MaintenanceMonitorException
	{
			Map<String,Object> resultMap=new HashMap<String,Object>();
 				Map<String, Object> test=JsonUtil.getMapFromJsonString(jsonStr);
 				for(String key:test.keySet())
 				{
 					if(test.get(key)==null)
 						test.put(key, "无");
 					Object obj=test.get(key);
 					String type=getObjType(obj);
 					if(type.equals(LIST_TYPE))
 					{
 							Map<String,String> listMap=new HashMap<String,String>();
 							String listJsonStr = JsonUtil.getJsonStringFromObject(obj);
 							ArrayList lit=JsonUtil.getObjectArrayFromJsonString(listJsonStr, ArrayList.class);
 							Object litObj=lit.get(0);
 							String litStr = JsonUtil.getJsonStringFromObject(litObj);
 							Map<String, Object> litAttri = JsonUtil.getMapFromJsonString(litStr);
 							for(String attri:litAttri.keySet())
 							{
 								listMap.put(attri+MARK+getObjType(litAttri.get(attri)), "无");
 							}
 							resultMap.put(key+"@"+LIST_TYPE, listMap);
 					}
 					else if(type.equals(MAP_TYPE))
 					{
 							Map<String,String> mapMap=new HashMap<String,String>();
 							String mapJsonStr = JsonUtil.getJsonStringFromObject(obj);
 							Map<String, Object> newMap = JsonUtil.getMapFromJsonString(mapJsonStr);
 							for(String mapKey:newMap.keySet())
 							{
 								Object map2 =  newMap.get(mapKey);
 								String str2 = JsonUtil.getJsonStringFromObject(map2);
 								Map<String, Object> ObjMap = JsonUtil.getMapFromJsonString(str2);
 								for(String objKey:ObjMap.keySet())
 								{
 									mapMap.put(objKey+MARK+getObjType(ObjMap.get(objKey)), "无");
 								}
 								break;
 							}
 							resultMap.put(key+MARK+MAP_TYPE, mapMap);
 					}
 					else if(type.equals(OBJECT_TYPE))
 					{
 						Map<String,String> objMap=new HashMap<String,String>();
 						
 						String objJsonStr = JsonUtil.getJsonStringFromObject(obj);
 						Map<String, Object> ObjMap = JsonUtil.getMapFromJsonString(objJsonStr);
 						for(String objKey:ObjMap.keySet())
 						{
 							objMap.put(objKey+MARK+getObjType(objMap.get(objKey)), "无");
 						}
 						resultMap.put(key+MARK+OBJECT_TYPE, objMap);
 					}
 					else
 					{
 						resultMap.put(key+MARK+type,"无");
 					}
 				}
				return resultMap;
	}

	public static String getObjType(Object object)
	{
		String obj="无";
		if(object!=null)
		   obj=object.toString();;
		
		if(numberPattern.matcher(obj).matches())
		{
			return LONG_TYPE;
		}
		else if(doublePattern.matcher(obj).matches())
		{
			return DOUBLE_TYPE;
		}
		else
		{
			String str = JsonUtil.getJsonStringFromObject(object);
			if(str.startsWith("[{"))
			{
				return LIST_TYPE;
			}
			else if(str.startsWith("{")&&!str.endsWith("}}"))
			{
				return OBJECT_TYPE;
			}
			else if(str.startsWith("{")&&str.endsWith("}}"))
			{
				return MAP_TYPE;
			}
			else
			{
				if(str.equals("true")||str.equals("false"))
					return BOOLEAN_TYPE;
				return STRING_TYPE;
			}
		}
	}
	public static String scanFile(URL url)throws MaintenanceMonitorException {
		String line = null;
		try {
			URLConnection  urlCon = url.openConnection();
			urlCon.setConnectTimeout(3000);
			InputStream is = urlCon.getInputStream();
			/**判断日志文件的最后修改时间**/
			BufferedReader br = new BufferedReader(new InputStreamReader(
				is, "utf-8"));
			while (br.readLine()!= null&&!br.readLine().equals("")) {
				line=br.readLine();
				return line;
			}
			br.close();
			is.close();
		} catch (MalformedURLException e) {
	
		} catch (IOException e) {
	
		}
		return line;
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
	protected Map<String, String> buildParamMap2String(Map<String, String> paramMap) {
		Map<String, String> cloneMap = new HashMap<String, String>();
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
