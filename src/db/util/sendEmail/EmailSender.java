package db.util.sendEmail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import db.configuration.Configuration;

public class EmailSender {

	private final static String myEmailAccount = "876633022@qq.com";
	private final static String myEmailPassword = "fhkotiafgtjvbcga";
	
	private final static String myEmailSMTPHost = "smtp.qq.com";
	
	private static Session createSession() {
		Properties props = new Properties();                    // 参数配置
		props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
		
		final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		
		Session session = Session.getDefaultInstance(props);
		
		return session;
	}
	
	private static void sendEmail(MimeMessage message,Session session) throws MessagingException {
		Transport transport = session.getTransport();
		
		transport.connect(myEmailAccount, myEmailPassword);
		
		transport.sendMessage(message, message.getAllRecipients());
		
		transport.close();
	}
	
	  private static MimeMessage createMimeMessage(Session session, String receiveMail,String username,String content) throws MessagingException, UnsupportedEncodingException   {
	        MimeMessage message = new MimeMessage(session);

	        message.setFrom(new InternetAddress(myEmailAccount, "DB DESIGN", "UTF-8"));

	        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, username, "UTF-8"));

	        message.setSubject("邮箱认证", "UTF-8");

	        message.setContent(content, "text/html;charset=UTF-8");

	        message.setSentDate(new Date());

	        message.saveChanges();

	        return message;
	    }
	
	public static boolean sendActivationEmail(String username,String activationCode,String eMailAddress) {
		
		Session session=EmailSender.createSession();
		
		String domainName=Configuration.getConfigurations("domain-name");
		
		String link = null;
		
		String content = null;
		
		String port=Configuration.getConfigurations("port");
		
		if(port==null) {
			port=":8080";
		}else if(port.equals("80")){
			port="";
		}else {
			port=":"+port;
		}
		
		if(domainName!=null) {
			link="http://"+domainName+port+"/DB/UserAction/Activate?activationCode="+activationCode;
		}else {
			String IPAddress=Configuration.getConfigurations("ip-address");
			if(IPAddress!=null) {
				link="http://"+IPAddress+port+"/DB/UserAction/Activate?activationCode="+activationCode;
			}
		}
		
		if(link!=null)
			content="欢迎注册我们的外卖系统，请点击该链接以激活您的账号，"
					+ link
					+ "，请不要回复该邮件，谢谢。";
		else
			content="欢迎注册我们的外卖系统，请使用激活码:"+activationCode
					+ "来激活您的账号，请不要回复该邮件，谢谢。";
		
		try {
		
			MimeMessage message=EmailSender.createMimeMessage(session, eMailAddress, username+" 用户", content);
			
			//EmailSender.sendEmail(message, session);
			
			return true;
		
		}catch(Exception e) {
			
			return false;
			
		}
	}
	
}
