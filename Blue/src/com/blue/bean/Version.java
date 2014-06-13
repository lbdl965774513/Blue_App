package com.blue.bean;

import com.google.gson.annotations.SerializedName;

public class Version {
	/**
	 * 版本号
	 */
	@SerializedName("version")
	public String VersionString;
    /**
     * 更新apk下载的地址
     */
	@SerializedName("url")
	public String ApkUrlString;
}
