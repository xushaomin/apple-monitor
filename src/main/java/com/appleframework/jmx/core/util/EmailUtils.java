/**
* Copyright (c) 2004-2005 jManage.org
*
* This is a free software; you can redistribute it and/or
* modify it under the terms of the license at
* http://www.jmanage.org.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.appleframework.jmx.core.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @author Bhavana
 * @author Rakesh Kalra
 */
public class EmailUtils {

    private static final Logger logger = Loggers.getLogger(EmailUtils.class);

    public static void sendEmail(String to, String subject, String content)
            throws MessagingException{

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
