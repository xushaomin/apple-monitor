package com.appleframework.monitor.command.plus.ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.appleframework.monitor.command.plus.CommandPlus;
import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.CommandExeType;
import com.appleframework.monitor.websocket.WebSocketServer;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshCommandPlus implements CommandPlus {
	
	private static String APPEND = "----------";
		
	@Override
	public void doLog(AppCommandParam param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doExe(AppCommandParam param, CommandExeType exeType) {
		
		Session session = null;
		ChannelExec openChannel = null;
		String host = param.getHost();
		try {
			JSch jsch = new JSch();
			jsch.addIdentity("/root/.ssh/id_dsa");
			session = jsch.getSession("root", host, 22);
			java.util.Properties config = new java.util.Properties();
			//设置第一次登陆的时候提示，可选值：(ask | yes | no)
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			//session.setPassword("123456");
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(param.getInstallPath() + "/bin/" + exeType.name().toLowerCase() + ".sh");
			int exitStatus = openChannel.getExitStatus();
			if(exitStatus != -1) {
				WebSocketServer.sendMessage(param.getId(), host + APPEND + "认证失败:" + exitStatus);
			}
			openChannel.connect();
			InputStream in = openChannel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				String result = new String(buf.getBytes("utf-8"), "UTF-8") + "\r\n";

				System.out.print(host + "-------------" + result);
				
				WebSocketServer.sendMessage(param.getId(), host + "-------------" + result);
				
				
			}
		} catch (Exception e) {
			//result += e.getMessage();
			WebSocketServer.sendMessage(param.getId(), host + APPEND + e.getMessage());
		} finally {
			if (openChannel != null && !openChannel.isClosed()) {
				openChannel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		
	}

}
