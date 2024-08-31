package services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

public class EmailSender {

    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_USERNAME = "abdelkader.abidi0000@gmail.com";
    public static final String SMTP_PASSWORD = "yztb eliy udwh iypb";
    public static final int SMTP_PORT = 587; // Adjust port number as needed
    public static String code;

    public static void VerificationCodeSender(String to,String nom) {

        String subject = "GYM HOUSE!";
        String body  = "Cher client,:\n\n"
                + "Nous sommes ravis de vous informer qu'une promotion spéciale est actuellement en cours sur certains de nos produits sur la plateforme. :\n\n"
                + "Consultez dès maintenant notre application pour découvrir les produits "+nom+"concernés par la promotion et profiter des offres exceptionnelles\n\n"
                + "Merci de votre fidélité et bon shopping sur notre plateforme!";
        sendEmail(SMTP_USERNAME, to, subject, body);



    }


    public String getVerificationCode(){
        return code;
    }


    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static void sendEmail(String from, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.trust","*");


        props.put("mail.debug", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }















}