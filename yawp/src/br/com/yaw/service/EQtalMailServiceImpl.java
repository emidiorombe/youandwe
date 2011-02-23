package br.com.yaw.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;

import static br.com.yaw.resource.ResourceBundleFactory.*;

public class EQtalMailServiceImpl implements EQtalMailService {

	@Override
	public void sendCreateUser(User user) throws ServiceException {
		
		Session session = Session.getDefaultInstance(new Properties(), null);
		String msgBody = getMessage("user_add_mail",  user.getContactEmail(), user.getAuthKey());
		
		System.err.println(msgBody);
		
		try {
			Message msg = new MimeMessage(session);
			msg.setSubject("Bem vindo ao EQtal");
			msg.setFrom(new InternetAddress("rafael@eqtal.com.br", "Contato EQtal"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getContactEmail()));
			msg.setText(msgBody);
			Transport.send(msg);
		
		} catch (MessagingException me) {
			throw new ServiceException(me);
			
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(e);
		}
		
	}

}