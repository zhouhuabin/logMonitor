/**
 * <p>文件名:		LogDataPublishPreTreament_MemCacheInfo.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package groovy;

import com.palmcity.rtti.maintenancemonitor.api.MonitorLogData;
import com.palmcity.rtti.maintenancemonitor.bean.MaintenanceMonitorException;
/**
 * <p>
 * LogDataStorage
 * </p>
 * <p>
 * 用途：发布预处理模块的缓存实体类. 注释两头必须有空格
 * </p>
 */ 
public class LogDatav3PublishPreTreament_MemCacheInfo {
	 
	 /** IP地址+端口方式 */	
	 def server;
	 /** 数据源状态  正常-1 失败- -1  */	
	 def status=1;
	 
  
	
	/** 单个数据源报警条件 */
	boolean checkConditionAlarm() {
		return status<0;
	}
}