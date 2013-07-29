package com.palmcity.rtti.maintenancemonitor.bean;

import com.caits.lbs.bean.dbmodel.ETBase;

public class MonitorMap extends ETBase{
	
	/** TODO */
	private static final long serialVersionUID = 1L;
	/** 拓扑图名称 */
	private String mapName;
	/** 背景图片URL */
	private String mapImgUrl;
	/** 拓扑图代码 */
	private String mapCode;
	/** 此拓扑图上的模块总数 */
	private Long  ModuleCount;
	/**
	 * 字段 moduleCount 获取函数
	 * @return the moduleCount : String
	 */
	public Long getModuleCount() {
		return ModuleCount;
	}
	/**
	 * 字段 ModuleCount 设置函数 : String
	 * @param moduleCount the moduleCount to set
	 */
	public void setModuleCount(Long moduleCount) {
		ModuleCount = moduleCount;
	}
	/**
	 * 字段 mapName 获取函数
	 * @return the mapName : String
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * 字段 mapName 设置函数 : String
	 * @param mapName the mapName to set
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * 字段 mapImgUrl 获取函数
	 * @return the mapImgUrl : String
	 */
	public String getMapImgUrl() {
		return mapImgUrl;
	}
	/**
	 * 字段 mapImgUrl 设置函数 : String
	 * @param mapImgUrl the mapImgUrl to set
	 */
	public void setMapImgUrl(String mapImgUrl) {
		this.mapImgUrl = mapImgUrl;
	}
	/**
	 * 字段 mapCode 获取函数
	 * @return the mapCode : String
	 */
	public String getMapCode() {
		return mapCode;
	}
	/**
	 * 字段 mapCode 设置函数 : String
	 * @param mapCode the mapCode to set
	 */
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}
}
