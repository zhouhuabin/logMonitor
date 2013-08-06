package com.palmcity.rtti.maintenancemonitor.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.palmcity.rtti.maintenancemonitor.bean.ModuleInfo;

public class Test {

	/**
	 * FIXME 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//{"alarm_report_way":0,"alarm_status":-1,"alarm_time":0,"configure":"CityCode=650000\r\nCompany=新疆掌城\r\nCompanyForShort=xjzc\r\nSourceIp=218.202.221.252\r\nSourcePort=65502\r\nDbIp=192.168.2.188\r\nDbPort=1521\r\nDbId=rtti\r\nDbUserName=rtti\r\nDbUserPassword=rtti\r\nProtocol=TCP/IP\r\nSenderServerPort=10036\r\nPrimalSenderServerPort=10046\r\nAreaCode=350100\r\nNamingBindPort=30003\r\nSrcDataClassName=com.yootu.rtti.datasource.bean.CarGps\r\nReceiveDataClassName=com.yootu.rtti.datasource.datareader.SocketClientXinjiang\r\nForwardDataClassName=com.yootu.rtti.datasource.datasender.ForwardDataSender\r\nForwardServerAddress=192.168.4.190:7000[(10.0/10.0-170.0/60.0)],192.168.4.217:0\r\nStoragePath=D:\r\nClient_threadNumber=1","dataSource":"218.202.221.252:65502","dateTime":1333096933,"deal_opid":0,"id":"","invalidAmount":0,"logType":0,"moduleCode":"RECEIVE_xjzc","moduleDesc":"xx接收","moduleType":"RECEIVE","status":"","text":"","totalAmount":0,"totalVehicle":0,"validAmount":0}
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		HashMap<String, String> staticMap=new HashMap<String,String>();
		staticMap.put("aaa", "撒旦是");
		staticMap.put("bbb", "德辅道");
		
		String SoftStr="aaa>10;bbb<100;bbb==100";
		
		
		String jsonStr="{'aaa':'9','bbb':'100'}";
		
		
		
		String NO_SmsAlarm="06:30:30-07:30:30,1,0;" +
						   "12:30:30-13:30:30,0,1;" +
						   "14:30:30-17:30:30,1,0;";
		
		try {
			ModuleInfo info=new ModuleInfo();
			info.setModule_Info_AlarmTime("00:00:00-07:00:00,1,0;07:00:00-22:00:00,0,1;22:00:00-23:59:59,1,0;");
			
			sendMailSms(info,"测试啊");
			//System.out.println(getSoftAnalysis(SoftStr,staticMap));
			//System.out.println(getProInfo(engine,jsonStr,"aaa"));
			
			//System.out.println(getStaticStr(staticMap));
			//System.out.println(StaticInfo(engine,jsonStr,staticMap));
			//System.out.println(SoftAnalysis(engine,jsonStr,SoftStr,staticMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	 protected static  void sendMailSms(ModuleInfo info,String message)
	  {
	    	String[] AlarmTime=info.getModule_Info_AlarmTime().split(";");
	    	
	    	Date date=new Date();
	    	String hours=date.getHours()<10?"0"+date.getHours():date.getHours()+"";
	    	String minutes=date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes()+"";
	    	String seconds=date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds()+"";
	    	String dateStr=hours+minutes+seconds;
	    	int dateInt=Integer.parseInt(dateStr);
	    	
	    	for(String alarmTimeStr:AlarmTime)
	    	{
	    		String time=alarmTimeStr.split(",")[0];
	    		String mail=alarmTimeStr.split(",")[1];
	    		String sms=alarmTimeStr.split(",")[2];
	    		int startTime=Integer.parseInt(time.split("-")[0].replaceAll(":", ""));
	    		int endTime=Integer.parseInt(time.split("-")[1].replaceAll(":", ""));
	    		if(dateInt>=startTime&&dateInt<=endTime)
	    		{
	    			if(mail.equals("1"))
	        		{
	    				System.out.println("邮件:"+message);
	        		}
	        		if(sms.equals("1"))
	        		{
	        			System.out.println("短信:"+message);
	        		}
	    		}
	    	}
	    }
	
	/**
     * 软分析报警表达式，多个表达式中间以;间隔 
     * @param SoftStr
     * return js表达式
     * 
     */
    public static String getSoftAnalysis(String SoftStr,HashMap<String, String> FiledMap)
    {
    	String Analysis="";
    	String[] soft=SoftStr.split(";");
    	for(int i=0;i<soft.length;i++)
    	{
    		if(soft[i].equals(""))
    			continue;
    			if(soft[i].contains(">"))
    			{
    				String softPro=soft[i].split(">")[0];
    				String softVal=soft[i].split(">")[1];
        			Analysis+="((newObj."+soft[i]+")?(aaaa="+'"'+FiledMap.get(softPro)+"大于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(soft[i].contains("<"))
    			{
    				String softPro=soft[i].split("<")[0];
    				String softVal=soft[i].split("<")[1];
        			Analysis+="((newObj."+soft[i]+")?(aaaa="+'"'+FiledMap.get(softPro)+"小于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(soft[i].contains("=="))
    			{
    				String softPro=soft[i].split("==")[0];
    				String softVal=soft[i].split("==")[1];
        			Analysis+="((newObj."+soft[i]+")?(aaaa="+'"'+FiledMap.get(softPro)+"等于"+softVal+'"'+"):"+'"'+'"'+")";
    			}
    			if(i!=soft.length-1&&!Analysis.equals(""))
    				Analysis+="||";
    	}
		return Analysis;
    }

	 /**  
     * 演示如何在Java中调用脚本语言的方法，通过JDK平台给script的方法中的形参赋值  
     *  
     * @param engine ScriptEngine实例  
     * @return void  
     * */          
    private static Object StaticInfo(ScriptEngine engine,String jsonStr,HashMap<String, String> staticMap) throws Exception {   
        //String script = "function helloFunction(name) { eval(name)   return  name;}";
        //engine.eval(script);
        engine.eval(new FileReader("W:\\templet_ws\\maintenanceMonitor\\src\\main\\java\\com\\palmcity\\rtti\\maintenancemonitor\\service\\test.js"));
        Invocable inv = (Invocable) engine;
        Object res = (Object) inv.invokeFunction("StaticInfo", jsonStr,getStaticStr(staticMap));
        return res;
    }
    /**  
     * 演示如何在Java中调用脚本语言的方法，通过JDK平台给script的方法中的形参赋值  
     *  
     * @param engine ScriptEngine实例  
     * @return void  
     * */          
    private static Object SoftAnalysis(ScriptEngine engine,String jsonStr,String SoftStr,HashMap<String, String> staticMap) throws Exception {   
        //String script = "function helloFunction(name) { eval(name)   return  name;}";
        //engine.eval(script);
        engine.eval(new FileReader("W:\\templet_ws\\maintenanceMonitor\\src\\main\\java\\com\\palmcity\\rtti\\maintenancemonitor\\service\\test.js"));
        Invocable inv = (Invocable) engine;
        Object res = (Object) inv.invokeFunction("SoftAnalysis", jsonStr, getSoftAnalysis(SoftStr,staticMap));
        return res;
    }
    
    /**  
     * 取得对象的属性值 
     *  
     * @param engine ScriptEngine实例  
     * @return void  
     * */          
    private static Object getProInfo(ScriptEngine engine,String jsonStr,String pro) throws Exception {   
        //String script = "function helloFunction(name) { eval(name)   return  name;}";
        //engine.eval(script);
        engine.eval(new FileReader("W:\\templet_ws\\maintenanceMonitor\\src\\main\\java\\com\\palmcity\\rtti\\maintenancemonitor\\service\\test.js"));
        Invocable inv = (Invocable) engine;
        Object res = (Object) inv.invokeFunction("getProInfo", jsonStr, pro);
        return res;
    }
    

    /**
     * 统计字段获取表达式 
     * @param staticMap统计字段MAP，key为英文字段名，value为字段中文
     *
     */
    public static String getStaticStr(HashMap<String, String> staticMap)
    {
    	String staticStr="";
    	for(String key:staticMap.keySet())
    	{
    		if(!staticStr.equals(""))
    			staticStr+="+";
    		staticStr+='"'+" "+staticMap.get(key)+":"+'"'+"+newObj."+key;
    	}
		return staticStr;
    }

    
    

    
}
