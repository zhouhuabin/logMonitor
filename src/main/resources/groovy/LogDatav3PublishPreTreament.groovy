/**
 * <p>文件名:		LogDataPreTreament.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package groovy;

import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
import java.util.HashMap;
/**
 * <p>
 * LogDataStorage
 * </p>
 * <p>
 * 用途：发布预处理模块的日志实体类
 * </p>
 */ 
public class LogDatav3PublishPreTreament extends MonitorLogData {

	/** TODO */
	private static final long serialVersionUID = 1L;
	
	
/*	HashMap<String, groovy.LogDatav3PublishPreTreament_FusionPluginStatusInfo> fusionMap = new HashMap<String,groovy.LogDatav3PublishPreTreament_FusionPluginStatusInfo>();
*/	/** 缓存地址   */
	groovy.LogDatav3PublishPreTreament_MemCacheInfo memStatusInfo=new groovy.LogDatav3PublishPreTreament_MemCacheInfo();
	
	def debug = false;
	public boolean checkConditionAlarm() throws MaintenanceMonitorException {
		boolean conditionAlarm = false;
		if(debug){
			return 1<0;
		}else{
				conditionAlarm = memStatusInfo.checkConditionAlarm();
				if(conditionAlarm){
					setText(" 连接不上缓存 " + memStatusInfo.getServer() + " 产生报警 "); 
				}
			return conditionAlarm||(ALARM_CONDITION);
		}
	}
}
