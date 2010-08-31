package br.com.yaw.service;

import java.io.IOException;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;

import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;

public class EQtalMailServiceImpl implements EQtalMailService {

	@Override
	public void sendCreateUser(User user) throws ServiceException {
		MailService mailService = MailServiceFactory.getMailService();
		Message msg = new Message();
		msg.setSubject("Bem vindo ao EQtal");
		msg.setTo(user.getContactEmail());
		msg.setHtmlBody("Bem vindo ao EQtal, você acaboud e se cadastrar. Clique no link para ativar sua conta");
		
		try {
			mailService.send(msg);
		
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		
	}

}
