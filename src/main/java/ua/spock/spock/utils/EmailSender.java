package ua.spock.spock.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {
    @Autowired
    private String username;
    @Autowired
    private String password;
    private Properties props;

    public EmailSender() {
        props = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mailSender.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String toEmail, String documentName) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Report");
            message.setText(actionLink(documentName));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String actionLink(String documentName) {
        String host;
        try {
            host = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String result;
        result = "http://" + host + ":8080/spock/report/" + documentName+".xls";
        return result;
    }
}