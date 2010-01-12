package br.com.yaw.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContatoServlet
 */
public class ContatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("form_contato".equals(action)) {
			String nome = request.getParameter("txtNome");
			String email = request.getParameter("txtEmail");
			String assunto = request.getParameter("txtAssunto");
			String comentario = request.getParameter("txtComent");

			String host = getServletConfig().getInitParameter("host_smtp");
	
			try {
				Properties props = System.getProperties();
				/*props.put("mail.transport.protocol", "smtp"); // define
																// protocolo de
																// envio como
																// SMTP
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host); // server SMTP do GMAIL
				props.put("mail.smtp.auth", "true"); // ativa autenticacao
				props.put("mail.smtp.user", user); // usuario ou seja, a conta
													// que esta enviando o email
													// (tem que ser do GMAIL)
				//props.put("mail.debug", "true");
				props.put("mail.smtp.port", "465"); // porta
				props.put("mail.smtp.socketFactory.port", "465"); // mesma porta
																	// para o
																	// socket
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");

				// Cria um autenticador que sera usado a seguir
				SimpleAuth auth = null;
				auth = new SimpleAuth(user, pass);*/

				// Session - objeto que ira realizar a conexão com o servidor
				/*
				 * Como há necessidade de autenticação é criada uma autenticacao
				 * que é responsavel por solicitar e retornar o usuário e senha
				 * para autenticação
				 */
				Session session = Session.getDefaultInstance(props, null);
				//session.setDebug(true);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email));
				message.addRecipient(Message.RecipientType.CC,
						new InternetAddress("contato@yaw.com.br"));
				message.setSubject(assunto);
				message.setText(nome + " - " + email  + "\n" + comentario);

				Transport.send(message);
				
				response.sendRedirect(request.getContextPath()+ "/contato/contato.html");

			} catch (Exception e) {
				
			}
		}
	}

}

class SimpleAuth extends Authenticator {
	public String username = null;
	public String password = null;

	public SimpleAuth(String user, String pwd) {
		username = user;
		password = pwd;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
