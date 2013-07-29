package com.palmcity.rtti.maintenancemonitor.service;

import java.util.List;

import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;

public class ImplV3PublishPretreamentScan extends AbstractLogFileScan{

	/**
	 * 构造器 
	 */
	public ImplV3PublishPretreamentScan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImplV3PublishPretreamentScan(List<LogFileConfigure> cfList) {
		super(cfList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.palmcity.rtti.maintenancemonitor.service.AbstractLogFileScan#parseLogLine
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void parseLogLine(String line, String moduleType,LogFileConfigure conf) {
		super.parseLogLine(line, conf.getModuleType(), conf);
	}

}
