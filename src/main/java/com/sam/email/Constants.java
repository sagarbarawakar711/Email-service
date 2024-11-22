package com.sam.email;

public interface Constants {
//	com.sun.mail.smtp.SMTPSendFailedException: 550 User paymentprocessing@luxuryleasepartners.com has exceeded its 24-hour sending limit. Messages to 250 recipients out of 250 allowed have been sent. Relay quota will reset in 21.99 hours.

//	public static final String PASS_WORD= "gomz spxm bivd prfe";
//	public static final String ATEK_EMAIL_PASS_WORD= "gomz spxm bivd prfe";
//	public static final String ATEK_EMAIL="ravikhamla.dev@gmail.com";
	
	public static final String PASS_WORD= "Y/_ckum_$N\\Kq~9g";
	public static final String ATEK_EMAIL_PASS_WORD= "Y/_ckum_$N\\Kq~9g";
	public static final String ATEK_EMAIL="prodops@luxuryleasepartners.com";
	
//	public static final String PASS_WORD= "0bEF^(Z!`zkm;kEi|u,%";
//	public static final String ATEK_EMAIL_PASS_WORD= "0bEF^(Z!`zkm;kEi|u,%";
//	public static final String ATEK_EMAIL="paymentprocessing@luxuryleasepartners.com";
	
	public String ATEK_MAIL_HOST="smtp.office365.com";
	public int ATEK_MAIL_PORT= 3535;
//	public int ATEK_MAIL_PORT= 587;
//	public int ATEK_MAIL_SSL_PORT= 587;
	public int ATEK_MAIL_SSL_PORT= 3535;
	public String ATEK_COMPANY= "Luxury Lease Partners";

//	public String ATEK_MAIL_HOST="smtp.gmail.com";
//	public int ATEK_MAIL_PORT= 587;
//	public int ATEK_MAIL_SSL_PORT= 587;
//	public String ATEK_COMPANY= "Luxury Lease Partners";

	public static final String LLP_EMAIL_PASS_WORD=  "0bEF^(Z!`zkm;kEi|u,%" ;// "payllp210!";
	public static final String LLP_EMAIL="paymentprocessing@luxuryleasepartners.com";
	
//	public static final String LLP_EMAIL_PASS_WORD= "Y/_ckum_$N\\Kq~9g";
//	public static final String LLP_EMAIL="prodops@luxuryleasepartners.com";

	public String LLP_MAIL_HOST="smtp.office365.com";//"smtpout.secureserver.net";
	public int LLP_MAIL_PORT= 3535;
	public int LLP_MAIL_SSL_PORT=587; // 465;secured port
	public String LLP_COMPANY= "Luxury Lease Partners";

	public String dealer_Id= "dealerId";
	public String email_Id= "emailId";
	public String timestamp_Date= "timestampDate";
	public String received_Date= "receivedDate";
	public String user_Name= "userName";
	public String app_Type = "appType";
	public String full_Name = "fullName";
	public String credit_Amount ="creditAmount";
	public String application_ID ="applicationId";
	public String dealer_ID = "lenderdealerId";
	public String dealer_NAME = "dealerName";
	
	public String new_Application_Template= "newApplicationTemplate";
	public String lock_Application_Template= "lockApplicationTemplate";
	public String UnLock_Application_Template= "UnLockApplicationTemplate";

	public String new_Application_Subject= "Dealertrack Credit Application Notification- New Apps received";
	public String lock_Application_Subject= "Credit Application Notification- Application Lock";
	public String UnLock_Application_Subject= "Credit Application Notification- Application UnLock";

	public static final String ACCOUNT_EMAIL_PASS_WORD= "vo@;PD;Y'qRts6\\Z5~kM"; //"LLP@cc0untsV3r210!";
	public static final String ACCOUNT_EMAIL="accountverification@luxuryleasepartners.com";

	public String ACCOUNT_MAIL_HOST="smtp.gmail.com";
	public int ACCOUNT_MAIL_PORT= 587;
	public String appId ="appId";
	public String fullName = "fullName";
	public String typeCustomer ="typeCustomer";
	public String requestedDate ="requestedDate";
    public String newOnlineCreditApplication ="newOnlineCreditApplication";
	
	
}
