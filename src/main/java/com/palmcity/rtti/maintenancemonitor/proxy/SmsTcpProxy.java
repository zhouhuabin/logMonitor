/**
 * <p>文件名:		SmsTcpProxy.java</p>
 * <p>版权:		CopyrightTag</p>
 * <p>公司:		千方集团CTFO</p>
 * @author		周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 */

package com.palmcity.rtti.maintenancemonitor.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.caits.lbs.framework.app.LbsPlatformMain;
import com.caits.lbs.framework.services.sms.IMessageService;
import com.caits.lbs.framework.services.sms.MessageSMS;
import com.caits.lbs.framework.utils.StringUtils;

/**
 * <p>
 * SmsTcpProxy
 * </p>
 * <p>
 * 用途：TODO
 * </p>
 * 
 * @author 周华彬(zhouhuabin@ctfo.com, zhou_hua_bin@163.com)
 * @version 0.0.1 2012-7-5
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
 *          <td>2012-7-5 下午4:13:53</td>
 *          </tr>
 *          <tr>
 *          <td>0.0.1</td>
 *          <td>创建类</td>
 *          <td>zhb</td>
 *          <td>2012-7-5 下午4:13:53</td>
 *          <td>0.0.2</td>
 *          <td>修改类</td>
 *          <td>xxx</td>
 *          <td>x年x月x日</td>
 *          </tr>
 *          </table>
 */
public class SmsTcpProxy implements Runnable,InitializingBean {

	public static final int READDATA_BUFFERSIZE = 1024;

	/** 日志记录器 */
	private Logger log = Logger.getLogger(getClass());
	
	/** 短信服务接口 */
	private IMessageService messageService;
	/** 服务端口 */
	private int port;
	/**
	 * 调度线程池
	 */
	private ScheduledExecutorService scheduledPool;

	/**
	 * 任务
	 */
	private ScheduledFuture<Runnable> scheduledFuture1;

	/** 网络套接字 */
	private ServerSocket serverSocket;
	
	/** 接收流的字符编码 */
	private String charSet = "gbk";
	
	public void afterPropertiesSet() throws Exception {
		try {
			serverSocket = new ServerSocket(port);
			log.info("短信代理tcp转发服务启动，侦听端口:" + port);
		} catch (IOException e) {
			log.error("创建短信代理服务实例错误," + e.getLocalizedMessage(), e);
			return;
		}
		schedule();
	}
	/**
	 * 启动执行器
	 * 
	 */
	@SuppressWarnings("unchecked")
	void schedule() {
		if (scheduledPool == null) {
			scheduledPool = Executors.newScheduledThreadPool(10);
			scheduledFuture1 = (ScheduledFuture<Runnable>) scheduledPool
					.scheduleWithFixedDelay(this, 1, 1, TimeUnit.SECONDS);
		} else {
			if (scheduledPool.isShutdown()) {
				scheduledPool = Executors.newScheduledThreadPool(10);
				scheduledFuture1 = (ScheduledFuture<Runnable>) scheduledPool
						.scheduleWithFixedDelay(this, 1, 1, TimeUnit.SECONDS);
			}
		}
	}
	/**
	 * 任务方法，创建与客户端socket连接
	 */
	public void run() {
		try {
			Socket socket = serverSocket.accept();
			String ip = socket.getInetAddress().getHostAddress();
			int port = socket.getPort();
			log.info("客户端连接到达," + socket.getInetAddress());
			socket.setSoTimeout(60 * 1000);
			Handler handler = new Handler(socket);
			handler.start();
		} catch (IOException e) {
			log.error("socket处理错误," + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			log.error("线程处理错误," + e.getLocalizedMessage(), e);
		}
	}
	/**
	 * FIXME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LbsPlatformMain.startApplicationContext("classpath:applicationContext-smsmail.xml");
			while(true){
				Thread.sleep(10*1000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 获取变量<code>messageService</code>的值
	 * @return 返回的数据类型是<code>IMessageService</code>
	 */
	public IMessageService getMessageService() {
		return messageService;
	}
	/**
	 * 设置变量<code> messageService</code> 的值
	 * @param messageService  <code>messageService</code> 参数类型是<code>IMessageService</code>
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * 获取变量<code>port</code>的值
	 * @return 返回的数据类型是<code>int</code>
	 */
	public int getPort() {
		return port;
	}
	/**
	 * 设置变量<code> port</code> 的值
	 * @param port  <code>port</code> 参数类型是<code>int</code>
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取变量<code>charSet</code>的值
	 * @return 返回的数据类型是<code>String</code>
	 */
	public String getCharSet() {
		return charSet;
	}
	/**
	 * 设置变量<code> charSet</code> 的值
	 * @param charSet  <code>charSet</code> 参数类型是<code>String</code>
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/**
	 * 向客户端写路况数据
	 * 
	 * @author Administrator
	 * 
	 */
	class Handler extends Thread {
		private Socket incoming;
		/** 该客户端发送记录数 */
		private long total = 0;
		/** 短信消息体 */
		private MessageSMS message = new MessageSMS();

		Handler(Socket s) {
			this.incoming = s;
		}

		public void run() {
			OutputStream out = null;
			InputStream in = null;
			byte[] readData = new byte[READDATA_BUFFERSIZE];
			try {
				String ip = incoming.getInetAddress().getHostAddress();
				int port = incoming.getPort();
				out = incoming.getOutputStream();
				in = incoming.getInputStream();
				// 建立数据流
				try {
					boolean ok = true;
					int batch = 0;
					while (ok) {
						Arrays.fill(readData, (byte)0);
						int length = in.read(readData);
						if(length>0){
							String read_line = new String(readData, 0, length,charSet);
							log.info("从客户端"+ip+"收到数据,长度(Bytes)=" + length);
							parseLine(read_line);
						}
					}
				} finally {
					if (out != null)
						out.close(); // 关闭输出流
					if (in != null)
						in.close(); // 关闭输入流
					if (incoming != null)
						incoming.close();
					log.info("关闭客户端连接" + incoming.getRemoteSocketAddress()
							+ ",发送记录总数" + total);
				}
			} catch (IOException e) {
				log.error("socket客户端处理错误," + e.getLocalizedMessage(), e);
			} catch (Exception e) {
				log.error("客户端处理错误," + e.getLocalizedMessage(), e);
			}
		}
		/**
		 * 解析收到的数据行
		 * 13900000001 xxxxxx 
		 * @param read_line
		 */
		private void parseLine(String read_line) {
			if(StringUtils.isNullOrBlank(read_line)){
				log.warn("要解析的行为空，直接返回.");
				return ;
			}
			StringTokenizer st = new StringTokenizer(read_line);
			if(st.hasMoreTokens()){
				String mobile=st.nextToken();
				String text = null;
				try{
					text = st.nextToken();
					sendMessage(mobile,text);
				}catch(Exception e){
					log.error("拆分短信内容错误", e);
					return;
				}
			}
		}

		/**
		 * 发送短信 
		 * @param mobile
		 * @param text
		 */
		public void sendMessage(String mobile,String text){
			message.setHead(mobile);
			message.setBody(text);
			getMessageService().sendMessage(message);
		}
		

	}
	
}
