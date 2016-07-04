package com.appleframework.monitor.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellUtil {

	public static String exec(String... cmd) throws Exception {
		StringBuffer resultBuffer = new StringBuffer();
		Process pr = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			resultBuffer.append(line + "<br />");
		}
		in.close();
		pr.waitFor();
		return resultBuffer.toString();
	}

}