package br.com.promove.async;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.entity.Avaria;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;

public class EnviarEmailAvariasJob implements Job {

	private static Logger log = Logger.getLogger(ImportCTRCJob.class);

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		try {
			JobDataMap data = ctx.getJobDetail().getJobDataMap();
			String tos[] = data.getString("dest").split(";");
			
			AvariaService avariaService = ServiceFactory.getService(AvariaService.class);
			String conteudo = montarConteudo(avariaService.buscarAvariasPorData(new Date())); 
			
			send("smtp.promoveseguros.com.br", 25, "sica@promoveseguros.com.br", tos, "Avarias de ", conteudo);
		} catch (Exception e) {
			log.error("Erro no envio de e-mail de avarias " + e.getMessage());
			e.printStackTrace();
		}
	}

	private String montarConteudo(List<Avaria> buscarAvariasPorData) {
		StringBuilder conteudo = new StringBuilder();
		for (Avaria av : buscarAvariasPorData) {
			conteudo.append(av.getVeiculo().getChassi()).append(";");
			conteudo.append(av.getVeiculo().getModelo().getDescricao()).append(";");
			conteudo.append(av.getOrigem().getDescricao()).append(";");
			conteudo.append(av.getLocal().getDescricao()).append(";");
			conteudo.append(av.getTipo().getDescricao()).append(";");
			conteudo.append("\n");
		}
		return conteudo.toString();
	}

	public static void send(String smtpHost, int smtpPort, String from,	String tos[], String subject, String content) throws AddressException,
			MessagingException {
		// Create a mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		Session session = Session.getDefaultInstance(props, null);

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(tos[0]));
		for(int i = 1; i < tos.length; i++) {
			msg.setRecipient(Message.RecipientType.CC, new InternetAddress(tos[i]));
		}
		msg.setSubject(subject);
		msg.setText(content);

		// Send the message
		Transport.send(msg);
	}

}
