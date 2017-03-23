package ua.spock.spock.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;

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
    @Value("#{mailSenderProperties.username}")
    private String username;
    @Value("#{mailSenderProperties.password}")
    private String password;
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private String host;
    @Value("#{applicationProperties['server.port']}")
    private String port;

    @PostConstruct
    private void init() {
        try {
            host = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
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

    public void sendBiddingStartNotificationEmail(Lot lot) {
        Session session = Session.getDefaultInstance(mailSenderProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(lot.getUser().getEmail()));
            message.setSubject("Bidding started for " + lot.getTitle());
            message.setText(biddingStartMessage(lot));

            Transport.send(message);
            logger.info("Bidding start for lotId={} notification message was sent to {}", lot.getId(), lot.getUser().
                    getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLotClosingNotificationToOwner(Lot lot) {
        Session session = Session.getDefaultInstance(mailSenderProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(lot.getUser().getEmail()));
            message.setSubject("Bidding closed for " + lot.getTitle());
            message.setText(biddingCloseMessageToOwner(lot));
            Transport.send(message);
            logger.info("Bidding close for lotId={} notification message was sent to {}", lot.getId(), lot.getUser().
                    getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLotClosingNotificationToBuyer(Lot lot) {
        Session session = Session.getDefaultInstance(mailSenderProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(lot.getUser().getEmail()));
            message.setSubject("Lot " + lot.getTitle() + " purchased");
            message.setText(biddingCloseMessageToBuyer(lot));
            Transport.send(message);
            logger.info("Bidding close for lotId={} notification message was sent to {}", lot.getId(),
                    lot.getMaxBid().getUser().getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String actionLink(int reportId, String documentName) {
        String result;
        if ("80".equals(port)) {
            result = "http://" + host + "/report/" + reportId + "/" + documentName + ".xlsx";
        } else {
            result = "http://" + host + ":" + port + "/report/" + reportId + "/" + documentName + ".xlsx";
        }

        return result;
    }

    private String biddingStartMessage(Lot lot) {
        String lotLink = getLotLink(lot);
        return String.format("Начаты торги для лота \"%s\"%n%s", lot.getTitle(), lotLink);
    }

    private String biddingCloseMessageToOwner(Lot lot) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("Торги для лота \"%s\" окончены.%n", lot.getTitle()));
        if (lot.getMaxBid() == null) {
            message.append("Лот не был куплен.");
        } else {
            String buyerName = lot.getMaxBid().getUser().getName();
            String buyerEmail = lot.getMaxBid().getUser().getEmail();
            double maxBidValue = lot.getMaxBid().getValue();
            message.append(String.format("Лот был куплен %s за %.2f UAH.%nСвязаться с покупателем: %s%n",
                    buyerName, maxBidValue, buyerEmail));
        }
        message.append(getLotLink(lot));
        return message.toString();
    }

    private String biddingCloseMessageToBuyer(Lot lot) {
        String lotTitle = lot.getTitle();
        String sellerName = lot.getUser().getName();
        String sellerEmail = lot.getUser().getEmail();
        double maxBidValue = lot.getMaxBid().getValue();
        String lotLink = getLotLink(lot);
        return String.format("Вы купили лот \"%s\" у продавца %s за %.2f UAH.%nСвяжитесь с продавцом: %s%n%s",
                lotTitle, sellerName, maxBidValue, sellerEmail, lotLink);
    }

    private String getLotLink(Lot lot) {
        String lotLink;
        if ("80".equals(port)) {
            lotLink = "http://" + host + "/lot/" + lot.getId();
        } else {
            lotLink = "http://" + host + ":" + port + "/lot/" + lot.getId();
        }
        return lotLink;
    }
}