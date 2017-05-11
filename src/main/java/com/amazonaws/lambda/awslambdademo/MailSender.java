package com.amazonaws.lambda.awslambdademo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Pranav - 11-May-2017 2:06:23 pm
 *
 */
public class MailSender {
	
	
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final Integer SMTP_PORT = 587;
//	private static final String TRANSPORTPROTOCOL = "smtp";
	private static final boolean SMTP_AUTH = true;
	private static final boolean STRTTLS = true;
	
	
	private static final String USERNAME = "pranav.pathak@wishtreetech.com";
	private static final String PASSWORD = "pgk@6070";
	
	
	public void sendMail(Email email){
		Properties props = new Properties();
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.smtp.starttls.enable", STRTTLS);
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", SMTP_PORT);
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME, PASSWORD);
					}
				  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email.getTo()));
			message.setSubject(email.getSubject());
			message.setText(email.getMessage());

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
