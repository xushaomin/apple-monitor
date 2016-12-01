package com.appleframework.monitor.plus.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.alibaba.fastjson.JSON;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.plus.mail.bean.MailAuthenticator;

public class EMailPlus implements ThirdPlus {
	
	private String smtpHost;
	private Boolean smtpAuth;
	private String username;
	private String password;
	
	private String mailFrom;
	private String personalName;

	@Override
	public void setThirdKey(String thirdKey) {
		this.username = thirdKey;
	}

	@Override
	public void setThirdSecret(String thirdSecret) {
		this.password = thirdSecret;
	}

	@Override
	public void setThirdExtend(String thirdExtend) {
		MailAuthenticator mailAuthenticator = JSON.parseObject(thirdExtend, MailAuthenticator.class);
		this.smtpHost = mailAuthenticator.getSmtpHost();
		this.mailFrom = mailAuthenticator.getMailFrom();
		this.personalName = mailAuthenticator.getPersonalName();
		this.smtpAuth = mailAuthenticator.getSmtpAuth();
	}
	
	@Override
	public boolean doSend(String mailTo, String mailBody) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.auth", smtpAuth.toString());
			Session mailSession = Session.getInstance(props);

			// 设置session,和邮件服务器进行通讯。
			mailSession.setDebug(false);
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject("监控提醒"); // 设置邮件主题
			//message.setText(mailBody); // 设置邮件正文
			message.setContent(mailBody,"text/html; charset=utf-8");  
			// message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题

			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mailFrom, personalName);
			message.setFrom(address); // 设置邮件发送者的地址
			InternetAddress toAddress = new InternetAddress(mailTo); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport transport = null;
			transport = mailSession.getTransport("smtp");

			message.saveChanges();
			transport.connect(smtpHost, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
	        
			return true;
		} catch (Exception e) {
			//throw MessageException.create("ERROR", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	

}
