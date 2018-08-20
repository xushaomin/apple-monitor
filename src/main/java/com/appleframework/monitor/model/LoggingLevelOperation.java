package com.appleframework.monitor.model;

public class LoggingLevelOperation {

	public static String INFO = "assignInfoLevel";

	public static String WARN = "assignWarnLevel";

	public static String ERROR = "assignErrorLevel";

	public static String DEBUG = "assignDebugLevel";

	public static String FATAL = "assignFatalLevel";

	public static String TRACE = "assignTraceLevel";

	public static String toLevelOperation(String sArg) {
		String s = sArg.toUpperCase();
		if (s.equals("DEBUG")) return LoggingLevelOperation.DEBUG;
		if (s.equals("INFO")) return LoggingLevelOperation.INFO;
		if (s.equals("WARN")) return LoggingLevelOperation.WARN;
		if (s.equals("ERROR")) return LoggingLevelOperation.ERROR;
		if (s.equals("FATAL")) return LoggingLevelOperation.FATAL;
		if (s.equals("TRACE")) return LoggingLevelOperation.TRACE;
		return null;
	}
	
	public static String toLevelOperation(short sArg) {
		if (sArg == LoggingLevelType.DEBUG.getIndex()) return LoggingLevelOperation.DEBUG;
		if (sArg == LoggingLevelType.INFO.getIndex()) return LoggingLevelOperation.INFO;
		if (sArg == LoggingLevelType.WARN.getIndex()) return LoggingLevelOperation.WARN;
		if (sArg == LoggingLevelType.ERROR.getIndex()) return LoggingLevelOperation.ERROR;
		if (sArg == LoggingLevelType.FATAL.getIndex()) return LoggingLevelOperation.FATAL;
		if (sArg == LoggingLevelType.TRACE.getIndex()) return LoggingLevelOperation.TRACE;
		return null;
	}

}
