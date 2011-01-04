package br.com.promove.view.form;

import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.Window.Notification;

public class BaseForm extends Form{
	protected static final String SELECIONE = "Selecione...";
	
	protected void showSuccessMessage(Component view, String msg) {
		Notification notification = new Notification("Sucesso!", msg, Notification.TYPE_HUMANIZED_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setDelayMsec(2000);
		view.getWindow().showNotification(notification);
	}
	
	protected void showErrorMessage(Component view, String msg) {
		Notification notification = new Notification("Erro!", msg, Notification.TYPE_ERROR_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setDelayMsec(Notification.DELAY_FOREVER);
		view.getWindow().showNotification(notification);
		
	}
}
