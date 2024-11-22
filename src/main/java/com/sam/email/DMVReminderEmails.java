/**
 * 
 */
package com.sam.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author PA
 *
 */
public class DMVReminderEmails {

	public static void sendEmail(String subject, String content, String toMailID[]) {
		final String SMTP_HOST_NAME = Constants.LLP_MAIL_HOST; // smtp URL
		final int SMTP_HOST_PORT = Constants.LLP_MAIL_SSL_PORT; // port number
		String SMTP_AUTH_USER = Constants.LLP_EMAIL; // email_id of sender
		String SMTP_AUTH_PWD = Constants.LLP_EMAIL_PASS_WORD; // password of sender email_id
		Transport transport = null;
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "true");

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(SMTP_HOST_NAME, SMTP_AUTH_PWD);
				}
			});

			mailSession.setDebug(false);
			transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);

			String sb = "<head>" + "<style type=\"text/css\">" + "  .red { color: #f00; }" + "</style>" + "</head>"
					+ "<h1 class=\"blue\">" + message.getSubject() + "</h1>" + "<p>" + " <strong>"
					+ "Payment Reminder as on Today's Date:" + new Date() + "</strong>.</p>" + content;

			message.setContent(sb, "text/html; charset=utf-8");
			message.saveChanges();

			message.setSentDate(new Date());
			Address[] from = InternetAddress.parse(Constants.LLP_EMAIL);// Your domain email
			message.addFrom(from);
			if (toMailID != null) {
				for (int i = 0; i < toMailID.length; i++) {
					String email = toMailID[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			} else {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(Constants.ATEK_EMAIL));
			}

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (SendFailedException se) {
			se.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}
	
	public static void main(String args[]) {
		sendEmail("Test", "Test body", new String[]{"muthu.p@syntrino.net"});
	}
}
