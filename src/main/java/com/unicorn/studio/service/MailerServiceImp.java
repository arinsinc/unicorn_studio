package com.unicorn.studio.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.internet.MimeMessage;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MailerServiceImp implements MailerService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    /**
     * Send emails to registered users
     * @param email
     * @param type
     * @param token
     */
    @Override
    public void sendEmail(String fullName, String email, String type, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            Map<String, String> emailObject = createEmailTemplate(type, fullName,email, token);
            helper.setFrom("no-reply@finayo.de");
            helper.setTo("arins.inc@gmail.com");
            helper.setSubject(emailObject.get("subject"));
            helper.setText(emailObject.get("htmlTemplate"), true);
            javaMailSender.send(message);
        }
        catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * Create email template by email type
     * @param type
     * @param fullName
     * @param token
     * @return emailObject
     */
    private LinkedHashMap<String, String> createEmailTemplate(String type, String fullName, String email, String token) {
        String subject = new String();
        String messageText = new String();
        Context mailContext = new Context();
        LinkedHashMap<String, Object> templateModel = new LinkedHashMap<>();
        LinkedHashMap<String, String> emailObject = new LinkedHashMap<>();
        mailContext.setVariable("firstName", fullName.split(" ")[0]);
        mailContext.setVariable("lastName", fullName.split(" ")[1].charAt(0));
        mailContext.setVariable("email", email);
        mailContext.setVariable("token", token);
        switch (type) {
            case "confirm":
                subject = "Welcome to Finayo";
                messageText = springTemplateEngine.process("email_verification.html", mailContext);
                break;
            case "verified":
                subject = "Finayo: Account verified";
                messageText = springTemplateEngine.process("email_verified.html", mailContext);
                break;
            case "password_reset":
                subject = "Finayo: Password reset";
                messageText = springTemplateEngine.process("password_reset_.html", mailContext);
                break;
            case "password_changed":
                subject = "Finayo: Password changed";
                messageText = springTemplateEngine.process("password_updated.html", mailContext);
                break;
        }
        emailObject.put("subject",subject);
        emailObject.put("htmlTemplate", messageText);
        return emailObject;
    }
}
