package com.appleframework.monitor.plus.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.core.utils.StringUtility;
import com.appleframework.monitor.plus.ThirdPlus;

public class SmsPlusOfChanzor implements ThirdPlus {

	private final static Logger logger = LoggerFactory.getLogger(SmsPlusOfChanzor.class);
	
	//http://sms.chanzor.com:8001/sms.aspx?action=send&account=账号&password=密码&mobile=手机号&content=内容&sendTime=
	
	public String SMS_URL = "http://sms.chanzor.com:8001/sms.aspx?action=send";
	
	public String account;
	public String password;

	public String sendSMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

	public boolean doSend(String mobile, String content) {
		String postData = getSmsMessageParams(mobile, content);
		try {
			String resp = sendSMS(postData, SMS_URL);
			if(!StringUtility.isEmpty(resp) && resp.trim().indexOf("<returnstatus>Success</returnstatus>") > -1) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	private String getSmsMessageParams(String mobiles, String message) {
		StringBuffer postData = new StringBuffer("?userid=");
		postData.append("&account=" + account);
		postData.append("&password=" + password);
		postData.append("&mobile=" + mobiles);
		postData.append("&sendTime=");
		postData.append("&content=" + URLEncoder.encode(message));
		return postData.toString();
	}

	@Override
	public void setThirdKey(String thirdKey) {
		this.account = thirdKey;
	}

	@Override
	public void setThirdSecret(String thirdSecret) {
		this.password = thirdSecret;
	}

	@Override
	public void setThirdExtend(String thirdExtend) {
		
	}
	
}