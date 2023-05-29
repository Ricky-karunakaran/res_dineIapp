package helper;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
// https://developers.google.com/api-client-library/java
// https://developers.google.com/gmail/api/quickstart/java
public class EmailService {
  
    public void sendEmail(String receiver, String title, String content) throws GeneralSecurityException, IOException {
        final String username = "apikey";
        final String password = "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";

        // Setup SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ricky.k@graduate.utm.my"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(title);
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send email. Error: " + e.getMessage());
        }
    }
    

}
