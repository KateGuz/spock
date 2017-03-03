package ua.spock.spock.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@Service
public class EmailSender {
    @Resource(name = "mailSenderProperties")
    private Properties mailSenderProperties;
    @Resource(name = "applicationProperties")
    private Properties applicationProperties;
    private String username;
    private String password;
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @PostConstruct
    private void init() {
        username = mailSenderProperties.getProperty("username");
        password = mailSenderProperties.getProperty("password");
    }

    public void sendEmail(String toEmail, int reportId, String documentName) {
        Session session = Session.getDefaultInstance(mailSenderProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Report");
            message.setText(actionLink(reportId, documentName));

            Transport.send(message);
            logger.info("Report id={} notification message was sent to {}", reportId, toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String actionLink(int reportId, String documentName) {
        String host;
        try {
            host = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String port = applicationProperties.getProperty("server.port");
        String result;
        if ("80".equals(port)) {
            result = "http://" + host + "/report/" + reportId + "/" + documentName + ".xlsx";
        } else {
            result = "http://" + host + ":" + port + "/report/" + reportId + "/" + documentName + ".xlsx";
        }

        return result;
    }
}