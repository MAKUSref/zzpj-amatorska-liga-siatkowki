package pl.lodz.p.it.ssbd2023.ssbd04.security;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

@ApplicationScoped
public class EmailService {

    Config config = ConfigProvider.getConfig();

    Dotenv dotenv = Dotenv.load();

    public static void main(String[] args ) {
        EmailService emailService = new EmailService();
        // emailService.sendVerificationCodeEmail("gharteon@gmail.com", "some-code", "pl");
        emailService.sendBlockAccountInformation("l.krol8006@gmail.com", "pl");
//        emailService.sendBlockAccountInformation("gharteon@gmail.com", "pl");
//        emailService.sendUnblockAccountInformationMail("gharteon@gmail.com", "pl");
//        emailService.sendUnblockAccountInformationMail("gharteon@gmail.com", "en");
//        emailService.sendAccountActivationInformationMail("gharteon@gmail.com", "pl");
//        emailService.sendAccountActivationInformationMail("gharteon@gmail.com", "en");
    }

    public boolean sendVerificationCodeEmail(String to, String code, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(
                to,
                messages.getString("mail.welcome"),
                messages.getString("mail.verification.link") + ": " + config.getValue("server.domain", String.class) + config.getValue("email.verification", String.class) +"?token=" + code + "."
//                String.format("%s: %s%s?code=%s.", messages.getString("mail.verification.link"), config.getValue("server.domain", String.class) ,config.getValue("email.activation", String.class), code)
        );

    }
    public boolean sendResetPasswordEmail(String to, String token, String language) {
        // return true;
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(
                to,
                messages.getString("mail.reset.password"),
                messages.getString("mail.reset.password") + ": " + config.getValue("server.domain", String.class) + config.getValue("email.resetpassword", String.class) +"?token=" + token + "."
        );
    }

    public boolean sendActivationEmail(String to, String token, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(
                to,
                messages.getString("account.account.activation"),
                messages.getString("mail.activation.message") + ": " + config.getValue("server.domain", String.class) + config.getValue("email.activation", String.class) +"?token=" + token + "."
        );
    }

    public boolean sendEmailChangeEmail(String to, String token, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("mail.changed.message"),
                messages.getString("mail.changed.verify.message") + ": " + config.getValue("server.domain", String.class) + config.getValue("email.resetemail", String.class) +"?token=" + token + "."

        );
    }

    public boolean sendBlockAccountInformation(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.blocked"), messages.getString("mail.blocked.message"));
    }

    public boolean sendUnblockAccountInformationMail(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.unblocked"), messages.getString("mail.unblocked.message"));
    }

    public boolean sendApproveAccountInformation(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.approved"), messages.getString("mail.approved.message"));
    }

    public boolean sendDisapproveAccountInformationMail(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.disapproved"), messages.getString("mail.disapproved.message"));
    }

    public boolean sendAccountActivationInformationMail(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.active"), messages.getString("mail.activated.message"));
    }

    public boolean sendDeletedAccountInformationMail(String to, String language) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.deleted"), messages.getString("mail.deleted.message"));
    }

    public boolean sendAddRoleInformation(String to, String language, String role) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.added.role"), messages.getString("mail.added.role.message") + " " + role);
    }

    public boolean sendRemoveRoleInformation(String to, String language, String role) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", new Locale(language));
        return sendEmail(to, messages.getString("account.account.removed.role"), messages.getString("mail.deleted.role.message") + " " + role);
    }

    public boolean sendEmail(String to, String title, String content) {
        if (to.contains("donotsend")) {
            return true;
        }
        //provide sender's email ID
        String from = "ssbd.grupa-4@mail.com";
        //provide Mailtrap's accountname
        // final String accountname = "gharteon@gmail.com";
        final String accountname = "michal.milowicz16@gmail.com";
        //provide Mailtrap's password
        // final String password = "xsmtpsib-b89ceb30d5481e70267a706731e27ba4a03030d9d5b7c4c651bfb30d6e8efa76-6wE5AMYv3X7VZhFp";
        // final String password = "JIha7MRYXm4xwrbq";
        final String password = dotenv.get("EMAIL_PASSWORD");
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
                return new PasswordAuthentication(accountname, password);
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
            message.setContent(content, "text/html; charset=utf-8");
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