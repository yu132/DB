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
		Properties props = new Properties();                    // ��������
		props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
		props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
		props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
		
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

	        message.setSubject("������֤", "UTF-8");

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
			content="��ӭע�����ǵ�����ϵͳ�������������Լ��������˺ţ�"
					+ link
					+ "���벻Ҫ�ظ����ʼ���лл��";
		else
			content="��ӭע�����ǵ�����ϵͳ����ʹ�ü�����:"+activationCode
					+ "�����������˺ţ��벻Ҫ�ظ����ʼ���лл��";
		
		try {
		
			MimeMessage message=EmailSender.createMimeMessage(session, eMailAddress, username+" �û�", content);
			
			//EmailSender.sendEmail(message, session);
			
			return true;
		
		}catch(Exception e) {
			
			return false;
			
		}
	}
	
}
