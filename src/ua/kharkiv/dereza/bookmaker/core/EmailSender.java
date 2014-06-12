package ua.kharkiv.dereza.bookmaker.core;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.Authenticator;

import org.apache.log4j.Logger;

/**
 * Class for sending email.
 * 
 * @author dereza
 *
 */
public class EmailSender {
	
	private static final Logger log = Logger.getLogger(EmailSender.class);
	
	private Properties props;
	private Session session;
	
	/**
	 * Method sends a message to the specified email via SSL
	 * 
	 * @param Email address
	 * @param Email message
	 */
	public void sendMessage(String toEmail, String messageEmail) {
		props = new Properties();
		props.put("mail.smtp.host", Constants.MAIL_SMTP_HOST);
		props.put("mail.smtp.socketFactory.port",Constants.MAIL_SMTP_SOCKETFACTORY_PORT);
		props.put("mail.smtp.socketFactory.class",Constants.MAIL_SMTP_SOCKETFACTORY_CLASS);
		props.put("mail.smtp.auth", Constants.MAIL_SMTP_AUTH);
		props.put("mail.smtp.port", Constants.MAIL_SMTP_PORT);

		session = Session.getDefaultInstance(props,
				new Authenticator() {
						public PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(Constants.MAIL_USERNAME, Constants.MAIL_PASSWORD);
					}
				});		
		try{		 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.MAIL_ADRESS));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
			message.setSubject("Bookmaker");
			message.setText(messageEmail);
 
			Transport.send(message);
			log.info("Message was sent to -->" + toEmail);
		}catch (MessagingException ex) {
			log.error("Message wasn't send to email -->" + toEmail, ex);
			throw new Error("Message wasn't send to email -->" + toEmail, ex);
		}
	}	
}