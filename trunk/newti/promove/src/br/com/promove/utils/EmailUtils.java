package br.com.promove.utils;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class EmailUtils {
	public static void sendHtml(String from, String tos[], String subject, String content) throws PromoveException {
		//content = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"
		content = "<HTML><HEAD></HEAD><BODY><SPAN style=\"FONT-FAMILY: 'Arial','sans-serif'; FONT-SIZE: 10pt\">"
				+ content
				+ "</BODY></HTML>";
		
		send(from, tos, subject, content);
	}

	public static void send(String from, String tos[], String subject, String content) throws PromoveException {
		CadastroService cadastroService = ServiceFactory.getService(CadastroService.class);
		Map<String, String> params = cadastroService.buscarTodosParametrosAsMap();
		
		// Create a mail session
		Properties props = new Properties();
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		
		props.put("mail.smtp.host", params.get("smtpHost"));
		props.put("mail.smtp.socketFactory.port", params.get("smtpPort"));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", params.get("smtpPort"));
		//props.put("mail.smtp.submitter", params.get("smtpPwd"));
		//Session session = Session.getDefaultInstance(props, null);
		//Session session = Session.getInstance(props);
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						CadastroService cadastroService = ServiceFactory.getService(CadastroService.class);
						try {
							Map<String, String> params = cadastroService.buscarTodosParametrosAsMap();
							return new PasswordAuthentication(params.get("smtpEmail"), params.get("smtpPwd"));
						} catch (PromoveException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
		
		// Construct the message
		Message msg = new MimeMessage(session);
		try {
			for(int i = 0; i < tos.length; i++) {
				msg.setFrom(new InternetAddress(from));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(tos[i]));
				//msg.setRecipient(Message.RecipientType.CC, new InternetAddress(tos[i]));
				msg.setSubject(subject);
				//msg.setText(content);
				msg.setContent(content, "text/html");
				msg.setSentDate(new Date());
				
				// Send the message
				//Transport transport = session.getTransport("smtp");
				//transport.connect("smtp.promoveseguros.com.br", 465, "sica@promoveseguros.com.br", "acisaevo");
				
				Transport.send(msg);
			}
		} catch (AddressException e) {
			throw new PromoveException(e);
		} catch (MessagingException e) {
			throw new PromoveException(e);
		}
	}
}
