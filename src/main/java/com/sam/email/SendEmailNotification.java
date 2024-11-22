package com.sam.email;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailNotification {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String sendMail(String toEmailIds[], String ccEmailIds[], String bccEmailIds[], String subject,
			String templateName, Map<String, Object> templateData, Map<String, String> emailConfigMap,
			Map<String, String> emailClientsMap) throws MessagingException {
		String result = "";
		Properties props = new Properties();
//		props.setProperty("mail.transport.protocol", "smtp");
//		props.setProperty("mail.host", Constants.ATEK_MAIL_HOST);
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "587");
//		props.put("mail.debug", "false");
//		props.put("mail.smtp.socketFactory.port", "587");

		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", splitString(emailConfigMap.get("smtpHost"))[1]);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", splitString(emailConfigMap.get("smtpPort"))[1]);
		props.put("mail.debug", "false");
		props.put("mail.smtp.socketFactory.port", splitString(emailConfigMap.get("smtpPort"))[1]);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfigMap.get("emailUser"), emailConfigMap.get("password"));
			}
		});

		try {
			// session.setDebug(true);
			String emailList[] = null;
			if (emailClientsMap != null) {
				emailList = splitStringByComma(emailClientsMap.get("toEmailIds"));
			}
			Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(emailConfigMap.get("emailUser"));

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(subject);
			message.setContent(templateName, "text/html");
			if (toEmailIds != null) {
				for (int i = 0; i < toEmailIds.length; i++) {
					String email = toEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (ccEmailIds != null) {
				for (int i = 0; i < ccEmailIds.length; i++) {
					String email = ccEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (bccEmailIds != null) {
				for (int i = 0; i < bccEmailIds.length; i++) {
					String email = bccEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (emailList != null && emailList.length > 0) {
				for (int i = 0; i < emailList.length; i++) {
					String email = emailList[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (MessagingException e) {
			logger.error(e.getMessage());
			result = "Failed to Connect Mail server";
			throw e;
		}
		result = "Mail Sent Success!";
		return result;
	}

	public String llpRemainder(String toEmailIds[], String ccEmailIds[], String bccEmailIds[], String subject,
			String templateName, Map<String, Object> templateData) throws MessagingException {
		String result = "";
		System.out.println("inside--------------");

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", Constants.ATEK_MAIL_HOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", 587);
		props.put("mail.debug", "false");
		props.put("mail.smtp.socketFactory.port", 587);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constants.ATEK_EMAIL, Constants.ATEK_EMAIL_PASS_WORD);
			}
		});

		try {
			// session.setDebug(true);
			Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(Constants.ATEK_EMAIL);

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setFrom(new InternetAddress(Constants.ATEK_EMAIL));
			message.setSubject(subject);
			message.setContent(templateName, "text/html");
			if (toEmailIds != null) {
				for (int i = 0; i < toEmailIds.length; i++) {
					String email = toEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (ccEmailIds != null) {
				for (int i = 0; i < ccEmailIds.length; i++) {
					String email = ccEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (bccEmailIds != null) {
				for (int i = 0; i < bccEmailIds.length; i++) {
					String email = bccEmailIds[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			transport.connect(Constants.ATEK_MAIL_HOST, 587, Constants.ATEK_EMAIL, Constants.ATEK_EMAIL_PASS_WORD);
			Transport.send(message);
			transport.close();
		} catch (MessagingException e) {
			logger.error(e.getMessage());
			result = "Failed to Connect Mail server";
			throw e;
		}
		result = "Mail Sent Success!";
		return result;
	}

	public String sendAttachMent(String toEmailIds[], String ccEmailIds[], String bccEmailIds[], String subject,
			String template, Map<String, Object> templateData) throws MessagingException {
		String result = "";
		System.out.println("inside----------2----");
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", Constants.ATEK_MAIL_HOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "false");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constants.ATEK_EMAIL, Constants.PASS_WORD);
			}
		});

		try {
			// session.setDebug(true);
			// session.setDebug(true);
			Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(Constants.ATEK_EMAIL);

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(subject);
			message.setContent(template, "text/html");
			if (toEmailIds != null) {
				for (int i = 0; i < toEmailIds.length; i++) {
					String email = toEmailIds[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (ccEmailIds != null) {
				for (int i = 0; i < ccEmailIds.length; i++) {
					String email = ccEmailIds[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}
			if (bccEmailIds != null) {
				for (int i = 0; i < bccEmailIds.length; i++) {
					String email = bccEmailIds[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}

			transport.connect(Constants.ATEK_MAIL_HOST, 587, Constants.ATEK_EMAIL, Constants.ATEK_EMAIL_PASS_WORD);
			Transport.send(message);
			transport.close();
		} catch (MessagingException e) {
			logger.error(e.getMessage());
			result = "Failed to Connect Mail server";
			throw e;
		}
		result = "Mail Sent Success!";
		return result;
	}

	public void testmethod(String subject, String content, String toMailID[], Map<String, String> emailConfigDetail,
			Map<String, String> emailClientsMap) {
//		final String SMTP_HOST_NAME = Constants.LLP_MAIL_HOST; // smtp URL
//		final int SMTP_HOST_PORT = Constants.LLP_MAIL_SSL_PORT; // port number
//		String SMTP_AUTH_USER = Constants.LLP_EMAIL; // email_id of sender
//		String SMTP_AUTH_PWD = Constants.LLP_EMAIL_PASS_WORD; // password of sender email_id

		final String SMTP_HOST_NAME = splitString(emailConfigDetail.get("smtpHost"))[1];
		final int SMTP_HOST_PORT = convertStringToInt(splitString(emailConfigDetail.get("smtpPort"))[1]); // port number
		String SMTP_AUTH_USER = emailConfigDetail.get("emailUser"); // email_id of sender
		String SMTP_AUTH_PWD = emailConfigDetail.get("password"); // password of sender email_id
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
					return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
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
			Address[] from = InternetAddress.parse(SMTP_AUTH_USER);// Your domain email
			message.addFrom(from);
			if (toMailID != null && toMailID.length > 0) {
				for (int i = 0; i < toMailID.length; i++) {
					String email = toMailID[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			} else {
				String emailList[] = splitStringByComma(emailClientsMap.get("toEmailIds"));
				for (int i = 0; i < emailList.length; i++) {
					String email = emailList[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			}

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendEmail::" + e.getMessage());
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

	public void dealertracktestmethod(String subject, String content, String toMailID[], Map<String, String> emailConfigMap) {
		final String SMTP_HOST_NAME = splitString(emailConfigMap.get("smtpHost"))[1]; // smtp URL
		final int SMTP_HOST_PORT = convertStringToInt(splitString(emailConfigMap.get("smtpPort"))[1]); // port number
		String SMTP_AUTH_USER = emailConfigMap.get("emailUser"); // email_id of sender
		String SMTP_AUTH_PWD = emailConfigMap.get("password"); // password of sender email_id
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
					return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
				}
			});

			mailSession.setDebug(false);
			transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject(subject);
			message.setContent(content, "text/html");

			message.saveChanges();

			message.setSentDate(new Date());
			Address[] from = InternetAddress.parse(emailConfigMap.get("emailUser"));// Your domain email
			message.addFrom(from);
			if (toMailID != null) {
				for (int i = 0; i < toMailID.length; i++) {
					String email = toMailID[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			} else {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailConfigMap.get("emailUser")));
			}

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendEmail::" + e.getMessage());
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

	public void dealertrackonlinetestmethod(String subject, String content, String toMailID[], String filenames, Map<String, String> emailConfigMap) {
		final String SMTP_HOST_NAME = splitString(emailConfigMap.get("smtpHost"))[1]; // SMTP server address
		final int SMTP_HOST_PORT = convertStringToInt(splitString(emailConfigMap.get("smtpPort"))[1]); // Port for TLS/STARTTLS
		String SMTP_AUTH_USER = emailConfigMap.get("emailUser"); // Sender's email address
		String SMTP_AUTH_PWD = emailConfigMap.get("password"); // Sender's email password
		Transport transport = null;
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", splitString(emailConfigMap.get("smtpPort"))[1]);
			props.put("mail.debug", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.connectiontimeout", "5000");
			props.put("mail.smtp.timeout", "5000");

			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
				}
			});

			mailSession.setDebug(true);
			transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);

			// Construct message body
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = "";
			if (!filename.isEmpty()) {
				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(content, "text/html");
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(filename);

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart2);
				multipart.addBodyPart(htmlPart);
				message.setContent(multipart);
			} else {
				message.setContent(content, "text/html");
			}

			// Set message details
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.addFrom(InternetAddress.parse(emailConfigMap.get("emailUser")));

			// Add recipients
			if (toMailID != null) {
				for (String email : toMailID) {
					if (email != null && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			} else {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailConfigMap.get("emailUser")));
			}

			// Connect and send message
			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendEmail::" + e.getMessage());
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void yodleetestmethod1(String subject, String content, String toMailID[]) {
		final String SMTP_HOST_NAME = Constants.ACCOUNT_MAIL_HOST; // smtp URL
		final int SMTP_HOST_PORT = Constants.ACCOUNT_MAIL_PORT; // port number
		String SMTP_AUTH_USER = Constants.ACCOUNT_EMAIL; // email_id of sender
		String SMTP_AUTH_PWD = Constants.ACCOUNT_EMAIL_PASS_WORD; // password of sender email_id
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
					return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
				}
			});

			mailSession.setDebug(false);
			transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject(subject);
			message.setContent(content, "text/html");

			message.saveChanges();

			message.setSentDate(new Date());
			Address[] from = InternetAddress.parse(Constants.ACCOUNT_EMAIL);// Your domain email
			message.addFrom(from);
			if (toMailID != null) {
				for (int i = 0; i < toMailID.length; i++) {
					String email = toMailID[i];
					if (null != email && !email.isEmpty())
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}
			} else {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(Constants.ACCOUNT_EMAIL));
			}

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendEmail::" + e.getMessage());
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

	public String llpPaymentRemainder(String toEmailIds[], String ccEmailIds[], String bccEmailIds[], String subject,
			String templateNameContent, Map<String, Object> templateData, Map<String, String> emailConfigDetail,
			Map<String, String> emailClientsMap) throws MessagingException {
		logger.error("To email to send:::1    " + subject + " :  " + toEmailIds.length);
		testmethod(subject, templateNameContent, toEmailIds, emailConfigDetail, emailClientsMap);
		String returnStatus = "done!!!!!!!!!!!!!!!!!!!!!!";
		System.out.println("To email to sent:::2    ");
		return returnStatus;

	}

	public String dealerTrackEmailNotification(String toEmailIds[], String subject, String templateNameContent, Map<String, String> emailConfigMap)
			throws MessagingException {

		System.out.println("subject : " + subject);
		System.out.println("toEmailIds : " + toEmailIds);
		logger.error("To email to send:::1    " + subject + " :  " + toEmailIds.length);
		dealertracktestmethod(subject, templateNameContent, toEmailIds, emailConfigMap);
		String returnStatus = "done!!!!!!!!!!!!!!!!!!!!!!";
		System.out.println("To email to sent:::2    ");
		return returnStatus;

	}

	public String dealerTrackOnlineEmailNotification(String toEmailIds[], String subject, String templateNameContent,
			String filenames, Map<String, String> emailConfigMap) throws MessagingException {

		System.out.println("subject : " + subject);
		System.out.println("toEmailIds : " + toEmailIds);
		logger.error("To email to send:::1    " + subject + " :  " + toEmailIds.length);
		dealertrackonlinetestmethod(subject, templateNameContent, toEmailIds, filenames, emailConfigMap);
		String returnStatus = "done!!!!!!!!!!!!!!!!!!!!!!";
		System.out.println("To email to sent:::2    ");
		return returnStatus;

	}

	public String yodleeEmailNotification(String toEmailIds[], String subject, String templateNameContent)
			throws MessagingException {

		logger.info("subject : " + subject);
		logger.info("toEmailIds : " + toEmailIds);
		logger.error("To email to send:::1    " + subject + " :  " + toEmailIds.length);
		sendYodleeNotification(subject, templateNameContent, toEmailIds);
		String returnStatus = "done!!!!!!!!!!!!!!!!!!!!!!";
		logger.info("To email to sent:::2    ");
		return returnStatus;

	}

	// ky
	public void sendYodleeNotification(String subject, String content, String toMailID[]) { // (String subject, String
																							// content,String
																							// toMailID[]) {
		final String SMTP_HOST_NAME = Constants.LLP_MAIL_HOST; // smtp URL
		final int SMTP_HOST_PORT = Constants.LLP_MAIL_SSL_PORT; // port number
		String SMTP_AUTH_USER = Constants.ACCOUNT_EMAIL; // email_id of sender
		String SMTP_AUTH_PWD = Constants.ACCOUNT_EMAIL_PASS_WORD; // password of sender email_id
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
					return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
				}
			});

			mailSession.setDebug(false);
			transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject(subject);

			String sb = content;

			message.setContent(sb, "text/html; charset=utf-8");
			message.saveChanges();

			message.setSentDate(new Date());
			Address[] from = InternetAddress.parse(Constants.ACCOUNT_EMAIL);// Your domain email
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

			//
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(toMailID));
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress("faith@luxuryleasepartners.com")); //Send email To (Type
			// email ID that you want to send)

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendEmail::" + e.getMessage());
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

	private String[] splitString(String inString) {
		return StringUtils.split(inString, "|");
	}

	private String[] splitStringByComma(String inString) {
		return StringUtils.split(inString, ",");
	}

	public int convertStringToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
