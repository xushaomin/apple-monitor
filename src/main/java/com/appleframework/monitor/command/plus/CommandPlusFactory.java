package com.appleframework.monitor.command.plus;

import java.util.HashMap;
import java.util.Map;

import com.appleframework.monitor.command.plus.shell.ShellCommandPlus;
import com.appleframework.monitor.command.plus.ssh.SshCommandPlus;

public class CommandPlusFactory {

	private static Map<String, CommandPlus> deployPlusMap = new HashMap<String, CommandPlus>();

	public static CommandPlus create(String plus) {
		CommandPlus deployPlus = deployPlusMap.get(plus);
		if (null == deployPlus) {
			if (plus.equals("shell")) {
				deployPlus = new ShellCommandPlus();
			}
			else {
				deployPlus = new SshCommandPlus();
			}
			deployPlusMap.put(plus, deployPlus);
		}
		return deployPlus;
	}

}