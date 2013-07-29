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
public class LogDatav3PublishPreTreament_FusionPluginStatusInfo {
	/** 最后一次插件运行是否成功 */
	def result;
	/** 最后一次数据生成的日期，秒为单位 */
	def dataTime;
	/** 最后一次数据生成的时间id */
	def timeId;
	/** 最后一次数据生成的时间戳，单位为毫秒 */
	def buildTime;
	
	
	def id;
	def configCode;
	def message;
}