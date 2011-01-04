package com.example.promove.menu;

import com.example.promove.application.PromoveApplication;
import com.example.promove.form.AvariaForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;

public class MenuAvaria  extends CssLayout{
	private Button add;
	private Button list;
	private PromoveApplication app;
	
	public MenuAvaria(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}

	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Gerenciar Avarias");
		title.addStyleName("section");
		
		add = new NativeButton("Criar Avaria");
		list = new NativeButton("Listar");
		
		addListeners(add, list);
		addComponents(title, add, list);
				
	}
	
	private void addListeners(Button... btns) {
		for (Button b : btns) {
			b.addListener(new MenuItemListener());
		}
	}

	private void addComponents(Component... components) {
		for (Component component : components) {
			addComponent(component);
		}
	}
	
	class MenuItemListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			addAndRemoveStyle(event.getButton(), add, list);
			if(event.getButton() == add) {
				AvariaForm form = new AvariaForm();
				app.setMainView(form.getFormLayout());
				
			}
		}

		private void addAndRemoveStyle(Button clicked, Button...others) {
			for (Button b : others) {
				if(b == clicked)
					clicked.addStyleName("selected");
				else
					b.removeStyleName("selected");
					
			}
		}
	}
}
