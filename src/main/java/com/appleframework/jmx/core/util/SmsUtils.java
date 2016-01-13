package com.appleframework.jmx.core.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @author cruise.xu
 */
public class SmsUtils {

	private static final Logger logger = Loggers.getLogger(SmsUtils.class);

	public static void sendSms(String to, String subject, String content) throws MessagingException {
		logger.info("Sending email to: " + to);
		Properties properties = new Properties();
		properties.put("mail.user", JManageProperties.getAlertEmailFromName());
		properties.put("mail.host", JManageProperties.getEmailHost());
		properties.put("mail.from", JManageProperties.getAlertEmailFrom());
		properties.put("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.addRecipients(Message.RecipientType.TO, to);
		message.setSubject(subject);
		message.setText(content);
		Transport.send(message);
	}
}
