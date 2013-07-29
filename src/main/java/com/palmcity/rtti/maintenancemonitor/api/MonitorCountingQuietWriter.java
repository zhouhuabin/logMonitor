/**
 * <p>文件名:		MonitorCountingQuietWriter.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.api;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.ErrorHandler;

/**
 * <p>
 * MonitorCountingQuietWriter
 * </p>
 * <p>
 * 用途：TODO
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-6-16
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
 *          <td>2011-6-16 下午04:35:52</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-6-16 下午04:35:52</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MonitorCountingQuietWriter extends CountingQuietWriter {

	/** 写入的次数 */
	protected long line;

	/**
	 * 构造器
	 * 
	 * @param writer
	 * @param eh
	 */
	public MonitorCountingQuietWriter(Writer writer, ErrorHandler eh) {
		super(writer, eh);

	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.helpers.CountingQuietWriter#write(java.lang.String)
	 */
	public void write(String string) {
		try {
			out.write(string);
			count += string.length();
			line ++;
			out.flush();
		} catch (IOException e) {
			errorHandler.error("Write failure.", e, ErrorCode.WRITE_FAILURE);
		}
	}

	/**
	 * 获取变量<code>line</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getLine() {
		return line;
	}

	/**
	 * 设置变量<code> line</code> 的值
	 * @param line  <code>line</code> 参数类型是<code>long</code>
	 */
	public void setLine(long line) {
		this.line = line;
	}

	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
