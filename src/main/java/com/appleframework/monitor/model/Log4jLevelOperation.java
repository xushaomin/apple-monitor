package com.appleframework.monitor.model;

public class Log4jLevelOperation {

	public static String INFO = "assignInfoLevel";

	public static String WARN = "assignWarnLevel";

	public static String ERROR = "assignErrorLevel";

	public static String DEBUG = "assignDebug";

	public static String FATAL = "assignFatalLevel";

	public static String TRACE = "assignTraceLevel";

	public static String toLevelOperation(String sArg) {
		String s = sArg.toUpperCase();
		if (s.equals("DEBUG")) return Log4jLevelOperation.DEBUG;
		if (s.equals("INFO")) return Log4jLevelOperation.INFO;
		if (s.equals("WARN")) return Log4jLevelOperation.WARN;
		if (s.equals("ERROR")) return Log4jLevelOperation.ERROR;
		if (s.equals("FATAL")) return Log4jLevelOperation.FATAL;
		if (s.equals("TRACE")) return Log4jLevelOperation.TRACE;
		return null;
	}
	
	public static String toLevelOperation(short sArg) {
		if (sArg == Log4jLevelType.DEBUG.getIndex()) return Log4jLevelOperation.DEBUG;
		if (sArg == Log4jLevelType.INFO.getIndex()) return Log4jLevelOperation.INFO;
		if (sArg == Log4jLevelType.WARN.getIndex()) return Log4jLevelOperation.WARN;
		if (sArg == Log4jLevelType.ERROR.getIndex()) return Log4jLevelOperation.ERROR;
		if (sArg == Log4jLevelType.FATAL.getIndex()) return Log4jLevelOperation.FATAL;
		if (sArg == Log4jLevelType.TRACE.getIndex()) return Log4jLevelOperation.TRACE;
		return null;
	}

}
