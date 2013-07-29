package groovy;

import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;

import org.apache.log4j.Logger;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
/**
 * <p>
 * MonitorLogDataReceive
 * </p>
 * <p>
 * 用途：接收模块日志对象类 
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
 *          <td>2011-7-13 下午8:10:09</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-7-13 下午8:10:09</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
class LogDataReceive extends MonitorLogData{

	 /** TODO */
	private static final long serialVersionUID = 1L;
	/** 日志记录器 */
	public Logger log = Logger.getLogger(getClass());
	/** 状态正常 */
	public static final int STATUS_NORMAL = 1;
	/** 状态异常 */
	public static final int STATUS_FAILURE = -1; 
	/** 数据源地址 */
	String dataSource;
	/** 数据源连接状态正常(1-正常,-1失败) */
	String status;
	/**  总记录数  */
	int totalAmount=0;
	/**  总车辆数  */
	int totalVehicle=0;
	/**  有效GPS数  */
	int validAmount=0;
	/**  无效GPS数 */
	int invalidAmount=0;
	
	/** 是否调试状态 */
	def debug = false;
	
	/** 
	 * 软件报警分析，必须覆盖父类
	 * 
	 * @return
	 * @throws MaintenanceMonitorException
	 */
	public boolean checkConditionAlarm() throws MaintenanceMonitorException {
//		log.debug(' totalAmount howto: '+totalAmount);
		if(debug){
			return totalAmount<500;
		}else{
//			return false;
			return ALARM_CONDITION;
		}
	}

	/** 
	* 测试类
	*/
  static void main(args) {
	  LogDataReceive data = new LogDataReceive();
	  data.setDebug(true);
	  data.setTotalAmount(1000);
	  data.log.debug("checkConditionAlarm()="+data.checkConditionAlarm());
	  data.setTotalAmount(499);
	  data.log.debug("checkConditionAlarm()="+data.checkConditionAlarm());
  }

}
