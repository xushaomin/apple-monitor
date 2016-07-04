package com.appleframework.monitor.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	private static String BOOT_KEY_SERVICE_SUCCESS   = "com.appleframework.boot.Main->main(121)";
	private static String BOOT_KEY_SERVICE_SUCCESS2  = "APPLE-BOOT-SUCCESS";
	private static String BOOT_KEY_SERVICE_FAILURE   = "com.taobao.diamond.client.impl.DefaultDiamondSubscriber->close(421)";
	
	private static String BOOT_KEY_WEB_JETTY_SUCCESS = "Started SelectChannelConnector@";
	private static String BOOT_KEY_WEB_JETTY_FAILURE = "APPLE-BOOT-FAILURE";
	
	public static List<String> BOOT_KEY_SUCCESS_LIST = new ArrayList<String>();
	public static List<String> BOOT_KEY_FAILURE_LIST = new ArrayList<String>();
	
	public static Map<Integer, Boolean> BOOT_STATUS_MAP = new HashMap<Integer, Boolean>();
	
	static {
		BOOT_KEY_SUCCESS_LIST.add(BOOT_KEY_SERVICE_SUCCESS);
		BOOT_KEY_SUCCESS_LIST.add(BOOT_KEY_SERVICE_SUCCESS2);
		BOOT_KEY_SUCCESS_LIST.add(BOOT_KEY_WEB_JETTY_SUCCESS);
		
		BOOT_KEY_FAILURE_LIST.add(BOOT_KEY_SERVICE_FAILURE);
		BOOT_KEY_FAILURE_LIST.add(BOOT_KEY_WEB_JETTY_FAILURE);
	}

}
