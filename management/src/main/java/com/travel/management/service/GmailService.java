package com.travel.management.service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.travel.management.exception.EmailFailedToSendException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class GmailService {
    private final Gmail gmail;

    @Autowired
    public GmailService(Gmail gmail){
        this.gmail=gmail;
    }

    public void sendEmail(String toAddress,String subject,String content) throws Exception{
        Properties properties = new Properties();
        Session session = Session.getInstance(properties,null);
        MimeMessage email =new MimeMessage(session);

        try {
            email.setFrom(new InternetAddress("assemomar202@gmail.com"));
            email.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(toAddress)));
            email.setSubject(subject);
            email.setText(content);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
            com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
            message.setRaw(encodedEmail);

            message = gmail.users().messages().send("me", message).execute();
        }catch (Exception exception){
            throw new EmailFailedToSendException(exception.getMessage());
        }
    }
}
