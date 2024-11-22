package com.sam.email;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class DealerTrackNotifications {

	private static final Logger LOG = LoggerFactory.getLogger(DealerTrackNotifications.class);

	Date newdate = new Date();
	Timestamp ts = new Timestamp(newdate.getTime());

	SendEmailNotification sendEmail = new SendEmailNotification();

	public TemplateEngine templateEngineConfig() {
		TemplateEngine templateEngine = new TemplateEngine();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(0);
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	public void newDTApplication(String[] toEmailId, String appId, String date, String userName, String appType,
			String creditAmount, String applicationId, String lenderDealerId, String dealerName,
			Map<String, String> emailConfigMap) throws ParseException {

		String amount = null;
		if (!(creditAmount == null)) {
			String convertedString = new DecimalFormat("#,###.##").format(Double.parseDouble(creditAmount));
			amount = "$ " + convertedString;
		}

		Context context = new Context();
		context.setVariable(Constants.dealer_Id, appId);
		context.setVariable(Constants.received_Date, date);
		context.setVariable(Constants.user_Name, userName);
		context.setVariable(Constants.app_Type, appType);
		context.setVariable(Constants.credit_Amount, amount);
		context.setVariable(Constants.application_ID, applicationId);
		context.setVariable(Constants.dealer_ID, lenderDealerId);
		context.setVariable(Constants.dealer_NAME, dealerName);

		String templateName = templateEngineConfig().process(Constants.new_Application_Template, context);
		try {
			sendEmail.dealerTrackEmailNotification(toEmailId, Constants.new_Application_Subject, templateName,
					emailConfigMap);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}

	}

	public void newDTOnlineApplication(String[] toEmailId, String filenames, String appId, String fullName,
			String typeCustomer, String requestedDate, Map<String, String> emailConfigMap) throws ParseException {

		Context context = new Context();
		context.setVariable(Constants.appId, appId);
		context.setVariable(Constants.fullName, fullName);
		context.setVariable(Constants.typeCustomer, typeCustomer);
		context.setVariable(Constants.requestedDate, requestedDate);

		String templateName = templateEngineConfig().process(Constants.newOnlineCreditApplication, context);
		try {
			sendEmail.dealerTrackOnlineEmailNotification(toEmailId, Constants.new_Application_Subject, templateName,
					filenames, emailConfigMap);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}

	}

	public void lockApplication(String[] toEmailId, String appId, String date, String userName, String appType,
			String creditAmount, String applicationId, String emailId, Map<String, String> emailConfigMap)
			throws ParseException {

		String amount = null;
		if (!(creditAmount == null)) {
			String convertedString = new DecimalFormat("#,###.##").format(Double.parseDouble(creditAmount));
			amount = "$ " + convertedString;
		}

		Context context = new Context();
		context.setVariable(Constants.email_Id, emailId);
		context.setVariable(Constants.dealer_Id, appId);
		context.setVariable(Constants.received_Date, date);
		context.setVariable(Constants.user_Name, userName);
		context.setVariable(Constants.app_Type, appType);
		context.setVariable(Constants.credit_Amount, amount);
		context.setVariable(Constants.application_ID, applicationId);
		String templateName = templateEngineConfig().process(Constants.lock_Application_Template, context);

		try {
			sendEmail.dealerTrackEmailNotification(toEmailId, Constants.lock_Application_Subject, templateName,
					emailConfigMap);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending  App Lock notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}

	}

	public void UnLockApplication(String[] toEmailId, String appId, String date, String userName, String appType,
			String creditAmount, String applicationId, String emailId, Map<String, String> emailConfigMap)
			throws ParseException {

		String amount = null;
		if (!(creditAmount == null)) {
			String convertedString = new DecimalFormat("#,###.##").format(Double.parseDouble(creditAmount));
			amount = "$ " + convertedString;
		}

		Context context = new Context();
		context.setVariable(Constants.email_Id, emailId);
		context.setVariable(Constants.dealer_Id, appId);
		context.setVariable(Constants.received_Date, date);
		context.setVariable(Constants.user_Name, userName);
		context.setVariable(Constants.app_Type, appType);
		context.setVariable(Constants.credit_Amount, amount);
		context.setVariable(Constants.application_ID, applicationId);
		String templateName = templateEngineConfig().process(Constants.UnLock_Application_Template, context);

		try {
			sendEmail.dealerTrackEmailNotification(toEmailId, Constants.UnLock_Application_Subject, templateName,
					emailConfigMap);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending  App UnLock notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}

	}

	public String dtSendsComment(String[] toEmailIds) throws ParseException {
		Context context = new Context();
		Date newdate = new Date();
		Timestamp ts = new Timestamp(newdate.getTime());

		context.setVariable("timestampDate", ts);

		SendEmailNotification sendEmail;
		sendEmail = new SendEmailNotification();
		String templateName = templateEngineConfig().process("commentsTemplate", context);
		String msg = null;
		try {

			msg = sendEmail.dealerTrackEmailNotification(toEmailIds,
					"Dealertrack Credit Application Notification- Dealer Track Send Comments", templateName, null);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

}
