package com.palmcity.rtti.maintenancemonitor.api;

import com.caits.lbs.bean.dbmodel.ETBase;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import java.io.Serializable;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties({"hibernateLazyInitializer", "log"})
public class MonitorLogData extends ETBase
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected transient Logger log = Logger.getLogger(getClass());

  private long dateTime = 0L;

  private String moduleType = "Receive";

  private String moduleCode = "RECEIVE_BEIJING";

  private String moduleDesc = "xx接收";

  private int logType = 0;

  private String text = "未连接";

  private long alarm_time = 0L;

  private String configure = "无";

  private int alarm_report_way = 0;

  private int deal_opid = 0;

  private int alarm_status = -1;
  public static final int LOGTYPE_BASE = 0;
  public static final int LOGTYPE_INFO = 1;
  public static final int LOGTYPE_WARN = 2;
  public static final String LOG_FILEDKEY_DATE_TIME = "date_time";
  public static final String LOG_FILEDKEY_LOGTYPE = "logtype";
  public static final String LOG_FILEDKEY_REPORT_WAY = "alarm_report_way";
  public static final String LOG_FILEDKEY_ALARM_STATUS = "alarm_status";
  public static final String LOG_FILEDKEY_MODULETYPE = "moduletype";
  public static final String LOG_FILEDKEY_MODULECODE = "modulecode";
  public static final String LOG_MODULETYPE_RECEIVE = "RECEIVE";
  public static final String LOG_MODULETYPE_STORAGE = "STORAGE";
  public static final String LOG_MODULETYPE_SIMULATE = "SIMULATE";
  public static final String LOG_MODULETYPE_PUBLISH = "PUBLISH";
  public static final String LOG_MODULETYPE_PROCESS = "PROCESS";
  public static final String LOG_MODULETYPE_PREDICTION = "PREDICTION";
  public static final String LOG_MODULETYPE_FORWARD = "FORWARD";

  public long getDateTime()
  {
    return this.dateTime;
  }

  public void setDateTime(long dateTime)
  {
    this.dateTime = dateTime;
  }

  public String getModuleType()
  {
    return this.moduleType;
  }

  public void setModuleType(String moduleType)
  {
    this.moduleType = moduleType;
  }

  public int getLogType()
  {
    return this.logType;
  }

  public void setLogType(int logType)
  {
    this.logType = logType;
  }

  public String getModuleCode()
  {
    return this.moduleCode;
  }

  public void setModuleCode(String moduleCode)
  {
    this.moduleCode = moduleCode;
  }

  public String toJSONString()
  {
    setDateTime(System.currentTimeMillis() / 1000L);
    return super.toJSONString();
  }

  public static void main(String[] args)
  {
  }

  public int getAlarm_report_way()
  {
    return this.alarm_report_way;
  }

  public void setAlarm_report_way(int alarm_report_way)
  {
    this.alarm_report_way = alarm_report_way;
  }

  public int getAlarm_status()
  {
    return this.alarm_status;
  }

  public void setAlarm_status(int alarm_status)
  {
    this.alarm_status = alarm_status;
  }

  public boolean checkContentAlarm()
  {
    return this.logType == 2;
  }

  public boolean checkConditionAlarm()
    throws MaintenanceMonitorException
  {
    if ((this instanceof MonitorLogData)) {
      this.log.warn("调用MonitorLogData的报警分析没有意义，直接退出.");
      return false;
    }

    throw new MaintenanceMonitorException("错误:软报警分析必须在子类中实现");
  }

  protected Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }

  public String getConfigure()
  {
    return this.configure;
  }

  public void setConfigure(String configure)
  {
    this.configure = configure;
  }

  public String getText()
  {
    return this.text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public String getModuleDesc()
  {
    return this.moduleDesc;
  }

  public void setModuleDesc(String moduleDesc)
  {
    this.moduleDesc = moduleDesc;
  }

  public boolean noAnalyseAlarm()
  {
    return getAlarm_report_way() < 3;
  }

  public long getAlarm_time()
  {
    return this.alarm_time;
  }

  public void setAlarm_time(long alarm_time)
  {
    this.alarm_time = alarm_time;
  }

  public int getDeal_opid()
  {
    return this.deal_opid;
  }

  public void setDeal_opid(int deal_opid)
  {
    this.deal_opid = deal_opid;
  }
}