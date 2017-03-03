package ua.spock.spock.utils;

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
    private String username;
    private String password;

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
        String result;
        result = "http://" + host + ":8080/report/" + reportId + "/" + documentName + ".xlsx";
        return result;
    }
}