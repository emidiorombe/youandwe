package br.com.promove.view;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Usuario;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class LoginView extends VerticalLayout {

	private PromoveApplication app;
	private TextField user;
	private TextField password;
	private Button ok;
	private CadastroService cadastroService;

	public LoginView(PromoveApplication app) {
		this.app = app;
		cadastroService = ServiceFactory.getService(CadastroService.class);
		setMargin(true);
		setSpacing(true);
		setWidth("100%");
		
		Label lbl = new Label("<h2>Autenticação</h2>");
		lbl.setContentMode(Label.CONTENT_XHTML);
		lbl.setWidth(null);
		addComponent(lbl);
		setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
		
		user = new TextField("Usuário");
		user.setRequired(true);
		user.setRequiredError("Preencimento obrigatório!");
		user.setWidth(null);
		user.focus();
		addComponent(user);
		setComponentAlignment(user, Alignment.MIDDLE_CENTER);

		password = new TextField("Senha");
		password.setSecret(true);
		password.setRequired(true);
		password.setRequiredError("Preencimento obrigatório!");
		password.setWidth(null);
		addComponent(password);
		setComponentAlignment(password, Alignment.MIDDLE_CENTER);

		ok = new Button("Login");
		ok.addListener(new LoginClickListener(app));
		ok.setWidth(null);
		ok.setClickShortcut(KeyCode.ENTER);
		addComponent(ok);
		setComponentAlignment(ok, Alignment.MIDDLE_CENTER);
	}
	
	class LoginClickListener implements ClickListener{
		private PromoveApplication app;

		public LoginClickListener(PromoveApplication app) {
			this.app = app;
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			try {
				
				Usuario usuario = cadastroService.autenticarUsuario(user.getValue().toString(), password.getValue().toString().toUpperCase());
				
				if(usuario == null) {
					throw new PromoveException("Usuário ou senha inválido!");
				}
				
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				ctx.getHttpSession().setAttribute("loggedUser", usuario);
				app.buildAutenticatedWindow();
			} catch (PromoveException e) {
				Notification notification = new Notification("Erro!", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				notification.setPosition(Notification.POSITION_CENTERED_TOP);
				notification.setDelayMsec(Notification.DELAY_FOREVER);
				app.getMainWindow().showNotification(notification);
			} catch(NumberFormatException ne) {
				Notification notification = new Notification("Erro!", "Usuário Inválido!", Notification.TYPE_ERROR_MESSAGE);
				notification.setPosition(Notification.POSITION_CENTERED_TOP);
				notification.setDelayMsec(Notification.DELAY_FOREVER);
				app.getMainWindow().showNotification(notification);
			}
		}
		
	}
	
	
}
