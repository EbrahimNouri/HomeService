package ir.maktab.homeservice.util;

import ir.maktab.homeservice.entity.base.Person;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@Component
public class EmailSenderUtil {

    private final JavaMailSender mailSender;


    public void sendVerificationEmail(Person e, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = e.getEmail();
        String fromAddress = "homeservice.springboot@gmail.com";
        String senderName = "home service";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", e.getFirstname() + " " + e.getLastname());
        String verifyURL = siteURL + e.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
