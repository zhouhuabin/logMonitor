package com.palmcity.rtti.maintenancemonitor.api;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>
 * MonitorLogger
 * </p>
 * <p>
 * 用途：运维监控系统专用日志操作类
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2011-6-15
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
 *          <td>2011-6-15 下午05:59:05</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2011-6-15 下午05:59:05</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class MonitorRollingAppender extends FileAppender {

	
	/**
	 * 默认文件写入次数值 10.每次一行
	 */
	protected long maxFileLine = 10;

	/**
	 * 默认备份一个.
	 */
	protected int maxBackupIndex = 1;

	/** 写词切换大小 */
	private long nextRollover = 0;

	/**
	 * 默认构造器 {@link FileAppender#FileAppender parents constructor}.
	 */
	public MonitorRollingAppender() {
		super();
	}

	/**
	 * 构造器实例并打开文件 <code>filename</code>. 打开之后写入器写入.
	 * 
	 * <p>
	 * 如果 参数<code>append</code>为true, 追加方式写入文件.否则, 文件 <code>filename</code> 打开之前被截取.
	 */
	public MonitorRollingAppender(Layout layout, String filename, boolean append)
			throws IOException {
		super(layout, filename, append);
	}

	/**
	 * 以<code>filename</code>实例化. 写入器使用此打开的文件.
	 * 
	 * <p>
	 * 文件被追加.
	 */
	public MonitorRollingAppender(Layout layout, String filename)
			throws IOException {
		super(layout, filename);
	}

	/**
	 * 返回 <b>MaxBackupIndex</b> 的值.
	 */
	public int getMaxBackupIndex() {
		return maxBackupIndex;
	}

	/**
	 * 获取变量<code>maxFileLine</code>的值
	 * @return 返回的数据类型是<code>long</code>
	 */
	public long getMaxFileLine() {
		return maxFileLine;
	}

	/**
	 * 设置变量<code> maxFileLine</code> 的值
	 * @param maxFileLine  <code>maxFileLine</code> 参数类型是<code>long</code>
	 */
	public void setMaxFileLine(long maxFileLine) {
		this.maxFileLine = maxFileLine;
	}

	/**
	 * 切换动作的实现.
	 * 
	 * <p>
	 * 如果 <code>MaxBackupIndex</code>为正数, 这些文件 {
	 * <code>File.1</code>, ..., <code>File.MaxBackupIndex -1</code>} 被改名为 {<code>File.2</code>, ..., <code>File.MaxBackupIndex</code>} .
	 * 接着, <code>File</code> 被重命名 为<code>File.1</code> 同时被关闭. 
	 * 新 <code>File</code> 被创建接收写入.
	 * 
	 * <p>
	 * 如果 <code>MaxBackupIndex</code> 等于zero, 则
	 * <code>File</code> 直接被截取无备份.
	 */
	public// synchronization not necessary since doAppend is alreasy synched
	void rollOver() {
		File target;
		File file;

		if (qw != null) {
			long size = ((MonitorCountingQuietWriter) qw).getLine();
			LogLog.debug("rolling over line=" + size);
			// if operation fails, do not roll again until
			// maxFileSize more bytes are written
			nextRollover = size + maxFileLine;
		}
		LogLog.debug("maxBackupIndex=" + maxBackupIndex);

		boolean renameSucceeded = true;
		// If maxBackups <= 0, then there is no file renaming to be done.
		if (maxBackupIndex > 0) {
			// Delete the oldest file, to keep Windows happy.
			file = new File(fileName + '.' + maxBackupIndex);
			if (file.exists())
				renameSucceeded = file.delete();

			// Map {(maxBackupIndex - 1), ..., 2, 1} to {maxBackupIndex, ..., 3,
			// 2}
			for (int i = maxBackupIndex - 1; i >= 1 && renameSucceeded; i--) {
				file = new File(fileName + "." + i);
				if (file.exists()) {
					target = new File(fileName + '.' + (i + 1));
					LogLog.debug("Renaming file " + file + " to " + target);
					renameSucceeded = file.renameTo(target);
				}
			}

			if (renameSucceeded) {
				// Rename fileName to fileName.1
				target = new File(fileName + "." + 1);

				this.closeFile(); // keep windows happy.

				file = new File(fileName);
				LogLog.debug("Renaming file " + file + " to " + target);
				renameSucceeded = file.renameTo(target);
				//
				// if file rename failed, reopen file with append = true
				//
				if (!renameSucceeded) {
					try {
						this.setFile(fileName, true, bufferedIO, bufferSize);
					} catch (IOException e) {
						LogLog.error("setFile(" + fileName
								+ ", true) call failed.", e);
					}
				}
			}
		}

		//
		// if all renames were successful, then
		//
		if (renameSucceeded) {
			try {
				// This will also close the file. This is OK since multiple
				// close operations are safe.
				this.setFile(fileName, false, bufferedIO, bufferSize);
				nextRollover = 0;
			} catch (IOException e) {
				LogLog.error("setFile(" + fileName + ", false) call failed.", e);
			}
		}
	}

	public synchronized void setFile(String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {
		long line = qw!=null?((MonitorCountingQuietWriter) qw).getLine():0;
		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
		if (append) {
			File f = new File(fileName);
			((MonitorCountingQuietWriter) qw).setCount(f.length());
			((MonitorCountingQuietWriter) qw).setLine(line);
		}
	}

	/**
	 * 设置保留备份的文件数.
	 * 
	 * <p>
	 * 参数 <b>MaxBackupIndex</b> 决定最老的文件被丢弃前保留的备份个数. 设置为正整数.如果为0则不备份
	 * 正常切换在文件行数到达 <code>MaxFileLine</code>后进行.
	 */
	public void setMaxBackupIndex(int maxBackups) {
		this.maxBackupIndex = maxBackups;
	}



	/* (non-Javadoc)
	 * @see org.apache.log4j.FileAppender#setQWForFiles(java.io.Writer)
	 */
	protected void setQWForFiles(Writer writer) {
		this.qw = new MonitorCountingQuietWriter(writer, errorHandler);
	}

	/**
	 * 该方法将覆盖父类 RollingFileAppender的实现.
	 * 
	 * @since 0.9.0
	 */
	protected void subAppend(LoggingEvent event) {
		if (fileName != null && qw != null) {
//			long size = ((MonitorCountingQuietWriter) qw).getCount();
//			if (size >= maxFileSize && size >= nextRollover) {
//				rollOver();
//			}
			long line = ((MonitorCountingQuietWriter) qw).getLine();
			if (line >= maxFileLine && line >= nextRollover) {
				rollOver();
			}
			//这个逻辑保证最新日志中至少有一条记录
			super.subAppend(event);
			
		}
	}
}