package com.appleframework.monitor.utils;

import java.net.InetAddress;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 集合转字符串
 */
public class StringUtil {

	/**
	 * 将集合转成字符串
	 * @param lists
	 * @return
	 */
	public static String turnString(List<String> lists) {
		StringBuilder builder = new StringBuilder();
		for (String list : lists) {
			builder.append("'");
			builder.append(list);
			builder.append("'");
			builder.append(",");
		}
		return substring(builder.toString());
	}

	public static boolean isArrayStr(String arrayStr) {
		if (StringUtils.isBlank(arrayStr)) {
			return false;
		}
		String start = arrayStr.substring(0, 1);
		String end = arrayStr.substring(arrayStr.length() - 1, arrayStr.length());
		if (start.equals("[") && end.equals("]")) {
			return true;
		}
		return false;
	}

	/**
	 * 将集合转成字符串
	 * @param lists
	 * @return
	 */
	public static String turnInt(List<Integer> lists) {
		StringBuilder builder = new StringBuilder();
		for (Integer list : lists) {
			builder.append(list);
			builder.append(",");
		}
		return substring(builder.toString());
	}

	/**
	 * 截取字符串(去掉最后一个逗号符号位)
	 * @param str
	 * @return
	 */
	public static String substring(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		if (str.endsWith(",")) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 截取字符串(去掉最后一个符号位)
	 * @param str
	 * @param symbol
	 * @return
	 */
	public static String substring(String str, String symbol) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		if (str.endsWith(symbol)) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 截取字符串(0,length-1)
	 * @param str
	 * @return
	 */
	public static String substringLast(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 构建全like查询字符串
	 * @param cloumn
	 * @return
	 */
	public static String likeStr(String cloumn) {
		StringBuilder builder = new StringBuilder();
		builder.append("LIKE CONCAT");
		builder.append("(CONCAT('%',#{");
		builder.append(cloumn);
		builder.append("}),'%')");
		return builder.toString();
	}

	/**
	 * 构建后like查询字符串
	 * @param cloumn
	 * @return
	 */
	public static String likeLastStr(String cloumn) {
		StringBuilder builder = new StringBuilder();
		builder.append("LIKE CONCAT");
		builder.append("(#{");
		builder.append(cloumn);
		builder.append("},'%')");
		return builder.toString();
	}

	/**
	 * byte[]类型的字符串转为byte数组
	 * @param byteStr
	 * @return
	 */
	public static byte[] parseStrByte(String byteStr) {
		String[] split = byteStr.substring(1, byteStr.length() - 1).split(",");
		byte[] buff = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			buff[i] = Byte.parseByte(split[i]);
		}
		return buff;
	}

	/**
	 * int[]类型的字符串转为int数组
	 * @param byteStr
	 * @return
	 */
	public static int[] parseStrInt(String byteStr) {
		String[] split = byteStr.substring(1, byteStr.length() - 1).split(",");
		int[] buff = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			buff[i] = Integer.parseInt(split[i]);
		}
		return buff;
	}

	/**
	 * byte数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String StringToByteStr(String[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			//NOTE YeBing 如果传过来的值非-127~127则会抛异常
			builder.append((byte) Integer.valueOf(buff[i]).intValue() + ",");
			//builder.append(Byte.parseByte(buff[i])+",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * byte数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String StringToIntStr(String[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			builder.append(Integer.parseInt(buff[i]) + ",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * byte数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String byteToStr(byte[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			builder.append(buff[i] + ",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * byte数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String byteToStr(Byte[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			builder.append(buff[i] + ",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * int数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String intToStr(int[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			builder.append(buff[i] + ",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * integer数组转为字符串[2,1,3]
	 * @param buff
	 * @return
	 */
	public static String integerToStr(Integer[] buff) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buff.length; i++) {
			builder.append(buff[i] + ",");
		}
		return "[" + StringUtil.substring(builder.toString()) + "]";
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^(1\\d{10,11})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 中文解析指令内容
	 * @param type 指令类型
	 * @param content 指令内容
	 * @param model 设备型号
	 * @param wifi 设备类型
	 * @return 返回[0]=msg,[1]=contentType
	 */
	public static String[] handerCmdMsg(Integer type, String content, String model, Integer wifi) {
		String[] returnMessage = new String[2];
		if (type == 1) {
			if (wifi != null && wifi == 0) {
				String[] splitContents = content.split("#");
				returnMessage[0] = splitContents[0] + "秒一次";
				returnMessage[1] = "常规模式";
				return returnMessage;
			}
			if (StringUtils.isNotBlank(model) && "010".equals(model)) {
				if (content.indexOf("1#") == 0 || content.indexOf("#") == 0) {
					String result = assembleDayHourSecondStr(content);
					returnMessage[0] = result.toString() + "1次";
					returnMessage[1] = "常规模式";
					return returnMessage;

				} else {
					returnMessage[0] = "1分钟1次";
					returnMessage[1] = "追踪模式";
					return returnMessage;
				}
			} else {
				if (regexMatcher("^\\d+$*#[^#]*##", content)) {
					String[] splitContents = content.split("#");
					if (StringUtils.isNotBlank(model) && "026".equals(model)) {
						returnMessage[0] = (Integer.valueOf(splitContents[1]) / 60) + "分一次";
						returnMessage[1] = "追踪模式";
						return returnMessage;
					}
					/*if(StringUtils.isNotBlank(model)&&model.equals("027")&&splitContents.length==2){
						returnMessage[0] = (Integer.valueOf(splitContents[1])/60)+"分一次" ;
						returnMessage[1] = "追踪模式" ;
						return returnMessage ;
					}*/
					if (splitContents.length == 2 && Integer.valueOf(splitContents[1]) <= 300) {
						//追踪模式
						returnMessage[0] = "持续时间" + splitContents[0] + "分钟";
						returnMessage[1] = "追踪模式";
						return returnMessage;
					} else if (splitContents.length == 2 && Integer.valueOf(splitContents[1]) > 300) {
						String result = assembleDayHourSecondStr(content);
						returnMessage[0] = result.toString() + "1次";
						returnMessage[1] = "常规模式";
						return returnMessage;
					} else {
						returnMessage[0] = "";
						returnMessage[1] = "追踪模式";
						return returnMessage;
					}
				} else if (regexMatcher("#+\\d+[0-9]*##", content) || regexMatcher("#+\\d+[0-9]*#", content)) {
					String result = assembleDayHourSecondStr(content);
					returnMessage[0] = result.toString() + "1次";
					returnMessage[1] = "027".equals(model) ? "追踪模式" : "常规模式";
					return returnMessage;
				} else if (content.indexOf("###") == 0) {
					//固定回传点
					String[] cont = content.split("#");
					if (cont.length == 4) {
						if (cont[3].length() == 4) {
							returnMessage[0] = cont[3].substring(0, 2) + "时" + cont[3].substring(2) + "分";
						} else if (cont[3].length() == 8) {
							String c1 = cont[3].substring(0, 2) + "时" + cont[3].substring(2, 4) + "分";
							String c2 = cont[3].substring(4, 6) + "时" + cont[3].substring(6, 8) + "分";
							returnMessage[0] = c1 + "," + c2;
						} else if (cont[3].length() == 12) {
							String c1 = cont[3].substring(0, 2) + "时" + cont[3].substring(2, 4) + "分";
							String c2 = cont[3].substring(4, 6) + "时" + cont[3].substring(6, 8) + "分";
							String c3 = cont[3].substring(8, 10) + "时" + cont[3].substring(10, 12) + "分";
							returnMessage[0] = c1 + "," + c2 + "," + c3;
						} else if (cont[3].length() == 16) {
							String c1 = cont[3].substring(0, 2) + "时" + cont[3].substring(2, 4) + "分";
							String c2 = cont[3].substring(4, 6) + "时" + cont[3].substring(6, 8) + "分";
							String c3 = cont[3].substring(8, 10) + "时" + cont[3].substring(10, 12) + "分";
							String c4 = cont[3].substring(12, 14) + "时" + cont[3].substring(14, 16) + "分";
							returnMessage[0] = c1 + "," + c2 + "," + c3 + "," + c4;
						} else {
							returnMessage[0] = "";
						}
						returnMessage[1] = "固定回传模式";
						return returnMessage;

					} else {
						returnMessage[0] = "";
						returnMessage[1] = "固定回传模式";
						return returnMessage;
					}

					/*	if(regexFirstFind("[0-9]{4}", content)){
							String matcher = regexFirstMatcherStr("[0-9]{4}", content);
							returnMessage[0] = matcher.substring(0,2)+"时"+matcher.substring(2,4)+"分";
							returnMessage[1] = "固定回传模式" ;
							return returnMessage ;
						}else{
							returnMessage[0] = "";
							returnMessage[1] = "固定回传模式" ;
							return returnMessage ;
						}*/
				} else if (content.indexOf("##") == 0) {
					if (content.equals("##0000#")) {
						returnMessage[0] = "取消心跳";
						returnMessage[1] = "心跳模式";
						return returnMessage;
					} else if ("027".equals(model) && content.indexOf(":") > 0) {
						StringBuilder builder = new StringBuilder("星期模式:");
						if (regexFirstFind("[0-9]{1}", content.split("#")[2])) {
							String matcher = regexFirstMatcherStr("[0-9]{1}", content.split("#")[2]);
							builder.append(matcher);
						}
						builder.append("时间:");
						builder.append(content.split("#")[3]);
						returnMessage[0] = builder.toString();
						returnMessage[1] = "星期模式";
						return returnMessage;
					} else if ("026".equals(model)) {
						StringBuilder builder = new StringBuilder("固定时间点:");
						if (regexFirstFind("[0-9]{2}", content.split("#")[2])) {
							String matcher = regexFirstMatcherStr("[0-9]{2}", content.split("#")[2]);
							builder.append(matcher + ":");
						}
						returnMessage[0] = builder.toString();
						returnMessage[1] = "固定回传模式";
						return returnMessage;
					} else {
						String re = "心跳:" + Integer.parseInt(String.valueOf(content.split("#")[2]), 16) + "分钟一次";
						returnMessage[0] = re;
						returnMessage[1] = "心跳模式";
						return returnMessage;
					}
				}
			}
		} else if (type == 3) {
			if (content.equals("0")) {
				returnMessage[0] = "断油电";
			} else {
				returnMessage[0] = "恢复断油电";
			}
			returnMessage[1] = "无";
		} else if (type == 4) {
			returnMessage[0] = "设备重启";
			returnMessage[1] = "无";
		} else {
			returnMessage[0] = "";
			returnMessage[1] = "无";
		}
		return returnMessage;
	}

	private static String assembleDayHourSecondStr(String content) {
		String[] splitContents = content.split("#");
		int seconds = Integer.valueOf(splitContents[1]);
		int day = seconds / (60 * 60 * 24);//换成天 
		int hour = (seconds - (60 * 60 * 24 * day)) / 3600;//总秒数-换算成天的秒数=剩余的秒数    剩余的秒数换算为小时
		int minute = (seconds - 60 * 60 * 24 * day - 3600 * hour) / 60;//总秒数-换算成天的秒数-换算成小时的秒数=剩余的秒数    剩余的秒数换算为分
		StringBuilder result = new StringBuilder();
		if (day != 0) {
			result.append(day + "天");
		}
		if (hour != 0) {
			result.append(hour + "小时");
		}
		if (minute != 0) {
			result.append(minute + "分钟");
		}
		return result.toString();
	}
	
	/**
	 * 验证是否为正整数
	 * @return
	 */
	public static boolean regexPositiveValue(String value){
		return regexMatcher("^([1-9]*[1-9][0-9]*)|0$", value);
	}
	
	/**
	 * 判断正则表达式是否匹配
	 * @param regexPattern
	 * @param value
	 * @return true:匹配
	 */
	public static boolean regexMatcher(String regexPattern, String value) {
		Pattern pattern = Pattern.compile(regexPattern);
		return pattern.matcher(value).matches();
	}

	public static boolean regexFirstFind(String regexPattern, String value) {
		Pattern pattern = Pattern.compile(regexPattern);
		return pattern.matcher(value).find();
	}

	public static String regexFirstMatcherStr(String regexPattern, String value) {
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(value);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * int类型是否有效且大于0
	 * @param number
	 * @return
	 */
	public static boolean isIntGt0(Integer number) {
		return number != null && number > 0;
	}

	public static String getWindowHostIp() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			return ip;
		} catch (Exception e) {
			throw new RuntimeException("获取本机ip异常");
		}
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean hasText(String str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}
}

