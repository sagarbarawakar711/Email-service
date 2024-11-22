package com.sam.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class YodleeNotifications {
	
	private static final Logger LOG = LoggerFactory.getLogger(YodleeNotifications.class);
	
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
	
	public void invitaionMail(String email, String OTP, String url, String expirationDate, String firstName, String middleName, String lastName) {
		
		String[] toEmails = {email};
		LOG.info("EMAIL AND OTP : " + toEmails[0] + "-----" + OTP);
		String fullName = firstName + " "+middleName+" "+lastName;
		
		Context context = new Context();
		context.setVariable("fullName", fullName);
		context.setVariable("expirationDate", expirationDate);
		context.setVariable("OTP", OTP);
		context.setVariable("creditURL", "credit@luxuryleasepartners.com");
		context.setVariable("URL", url+"?otp="+OTP);
		
		String templateName = templateEngineConfig().process("InvitationTemplate", context);
		try {
			sendEmail.yodleeEmailNotification(toEmails, "Verify the bank account information on LLP  Lease Application", templateName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}
		
	}
	
public void reportIssueMail(String email, String OTP,String report) {
		
		String[] toEmails = {email};
		System.out.println("EMAIL AND OTP : " + toEmails[0] + "-----" + OTP);
		
		Context context = new Context();
		context.setVariable("OTP", OTP);
		context.setVariable("URL", "http://localhost:8080/releaseNotes?otp="+OTP+"&role=creditAnalyst");
		context.setVariable("report", report);
		
		System.out.println("REPORT : " + report);
		
		String templateName = templateEngineConfig().process("ReportIssueTemplate", context);
		try {
			sendEmail.yodleeEmailNotification(toEmails, "Customer Data Issue", templateName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}
		
	}
	
public void creditAnalyst(String accountId, String email, String errorCode, String errorDesc, String dealId, String firstName, String middleName, String lastName, String customerEmail, String vehicle, String vin, String businessName, String invitationCode) {
		
		String[] toEmails = {email};
		String subject = "";
		 
		Context context = new Context();
		context.setVariable("accountId", accountId);
		if(errorCode != null) {
			subject = "Lease#-"+dealId+" - Unsuccessful   Account validation";
			context.setVariable("dealId", dealId);
			context.setVariable("invitationCode", invitationCode);
			context.setVariable("firstName", firstName);
			context.setVariable("middleName", middleName);
			context.setVariable("lastName", lastName);
			context.setVariable("businessName", businessName);
			context.setVariable("customerEmail", customerEmail);
			context.setVariable("vehicle", vehicle);
			context.setVariable("vin", vin);
			context.setVariable("error", "error:");
			context.setVariable("errorDesc", errorDesc);
		}else {
			subject = " Lease#-"+dealId+" - successful Account validation";
			context.setVariable("dealId", dealId);
			context.setVariable("invitationCode", invitationCode);
			context.setVariable("firstName", firstName);
			context.setVariable("middleName", middleName);
			context.setVariable("lastName", lastName);
			context.setVariable("businessName", businessName);
			context.setVariable("customerEmail", customerEmail);
			context.setVariable("vehicle", vehicle);
			context.setVariable("vin", vin);
			
			
		}
		
		String templateName = templateEngineConfig().process("CreditAnalystTemplate", context);
		try {
			sendEmail.yodleeEmailNotification(toEmails, subject, templateName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
					e.getMessage());
		}
		
	}

public void cancel(String lesseeName, String dealNumber, String email) {
	String[] toEmails = {email};
	
	
	Context context = new Context();
	context.setVariable("lesseeName", lesseeName);
	context.setVariable("dealNumber", dealNumber);
	
	String templateName = templateEngineConfig().process("CancellationTemplate", context);
	try {
		sendEmail.yodleeEmailNotification(toEmails, "Customer Cancellation", templateName);
	} catch (Exception e) {
		e.printStackTrace();
		LOG.error("Error while sending new App notification from DealerTrackNotification, Error Message: {}",
				e.getMessage());
	}
}
}
