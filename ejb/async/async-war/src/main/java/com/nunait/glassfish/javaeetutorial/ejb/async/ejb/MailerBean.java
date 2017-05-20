/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.async.ejb;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 12:32:54 ART
 */
@Named
@Stateless
public class MailerBean {
    private static final Logger logger = Logger.getLogger(MailerBean.class.getName());
    
    @Resource(name = "mail/myStubSession")
    private Session session;
    
    @Asynchronous
    public Future<String> sendMessage(String email) {
        String status;
        try {            
            Properties properties = new Properties();
            properties.put("mail.smtp.port", "3025");
            session = Session.getInstance(properties);
            Message message = new MimeMessage(session);
            message.setFrom();
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            message.setSubject("Mensaje de prueba desde ejemplo async");
            message.setHeader("X-Mailer", "JavaMail");
            DateFormat dateFormatter = DateFormat
                    .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
            Date timeStamp = new Date();
            String messageBody =
                    "Este es un mensaje de prueba desde el ejemplo async " +
                    "del tutorial de Java EE. Este fuen enviado el " +
                    dateFormatter.format(timeStamp) + ".";
            message.setText(messageBody);
            message.setSentDate(timeStamp);
            Transport.send(message);
            status = "Enviado";
            logger.log(Level.INFO, "Mail enviado a {0}", email);
        } catch (MessagingException e) {
            logger.severe("Error al enviar mensaje.");
            status = "Error encontrado: " + e.getMessage();
            logger.severe(e.getMessage());
        }
        return new AsyncResult<>(status);
    }
    
}
