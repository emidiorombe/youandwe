package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.ClimaAvariaView;
import br.com.promove.view.ExtensaoAvariaView;
import br.com.promove.view.LocalAvariaView;
import br.com.promove.view.OrigemAvariaView;
import br.com.promove.view.TipoAvariaView;
import br.com.promove.view.form.AvariaForm;
import br.com.promove.view.form.ClimaForm;
import br.com.promove.view.form.ExtensaoAvariaForm;
import br.com.promove.view.form.LocalAvariaForm;
import br.com.promove.view.form.OrigemAvariaForm;
import br.com.promove.view.form.TipoAvariaForm;
import br.com.promove.view.table.AvariaTable;
import br.com.promove.view.table.ClimaTable;
import br.com.promove.view.table.ExtensaoAvariaTable;
import br.com.promove.view.table.LocalAvariaTable;
import br.com.promove.view.table.OrigemAvariaTable;
import br.com.promove.view.table.TipoAvariaTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;

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
		list = new NativeButton("Listar Avarias");
		
		addListeners(add, list);
		addComponents(add, list);
				
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
			}else if(event.getButton() == list){
				AvariaTable table = new AvariaTable(app);
				app.setMainView(table);
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
