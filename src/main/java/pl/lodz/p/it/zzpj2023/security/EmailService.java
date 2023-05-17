package pl.lodz.p.it.zzpj2023.security;

import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;

@ApplicationScoped
public class EmailService {
    public static void main(String[] args ) {
        EmailService emailService = new EmailService();
        emailService.sendVerificationCodeEmail("gharteon@gmail.com", "some-code");
    }

    public boolean sendVerificationCodeEmail(String to, String code) {
        return sendEmail(to, "Welcome to SSBD!", "Your verification link is: https://team-4.proj-sum.it.p.lodz.pl/api/verifyCode?code=" + code + ".");
    }

    public boolean sendResetPasswordEmail(String to, String token) {
        // return true;
        return sendEmail(to, "Reset password", "Your link to change password is: https://team-4.proj-sum.it.p.lodz.pl/api/resetPassword?token=" + token);
    }

    boolean sendEmail(String to, String title, String content) {
        //provide sender's email ID
        String from = "ssbd.grupa-4@mail.com";
        //provide Mailtrap's username
        final String username = "nesteruk060@gmail.com";
        //provide Mailtrap's password
        final String password = "xsmtpsib-b89ceb30d5481e70267a706731e27ba4a03030d9d5b7c4c651bfb30d6e8efa76-6wE5AMYv3X7VZhFp";
        //provide Mailtrap's host address
        String host = "smtp-relay.sendinblue.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        //create the Session object
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            //set email subject field
            message.setSubject(title);
            //set the content of the email message
            message.setContent(content, "text/html");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
            return true;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
