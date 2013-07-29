package com.palmcity.rtti.maintenancemonitor.service;

import java.util.List;

import com.palmcity.rtti.maintenancemonitor.bean.LogFileConfigure;

public class ImplPublishPretreamentScan extends AbstractLogFileScan{

	/**
	 * 构造器 
	 */
	public ImplPublishPretreamentScan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImplPublishPretreamentScan(List<LogFileConfigure> cfList) {
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
