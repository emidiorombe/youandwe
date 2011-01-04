package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.ExtensaoAvariaView;
import br.com.promove.view.LocalAvariaView;
import br.com.promove.view.OrigemAvariaView;
import br.com.promove.view.TipoAvariaView;
import br.com.promove.view.form.AvariaForm;
import br.com.promove.view.form.ExtensaoAvariaForm;
import br.com.promove.view.form.LocalAvariaForm;
import br.com.promove.view.form.OrigemAvariaForm;
import br.com.promove.view.form.TipoAvariaForm;
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
	private Button tipo;
	private Button local;
	private Button origem;
	private Button extensao;
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
		tipo = new NativeButton("Tipos de Avaria");
		local = new NativeButton("Locais de Avaria");
		origem = new NativeButton("Origens de Avaria");
		extensao = new NativeButton("Extensões de Avaria");
		
		addListeners(add, list, tipo, local, origem, extensao);
		addComponents(title, add, list, tipo, local, origem, extensao);
				
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
			addAndRemoveStyle(event.getButton(), add, list, tipo, local, origem, extensao);
			if(event.getButton() == add) {
				AvariaForm form = new AvariaForm();
				app.setMainView(form.getFormLayout());
				
			}else if(event.getButton() == tipo){
				TipoAvariaTable table = new TipoAvariaTable();
				TipoAvariaForm form = new TipoAvariaForm();
				app.setMainView(new TipoAvariaView(table, form));
			}else if(event.getButton() == local){
				LocalAvariaTable table = new LocalAvariaTable();
				LocalAvariaForm form = new LocalAvariaForm();
				app.setMainView(new LocalAvariaView(table, form));
			}else if(event.getButton() == origem){
				OrigemAvariaTable table = new OrigemAvariaTable();
				OrigemAvariaForm form = new OrigemAvariaForm();
				app.setMainView(new OrigemAvariaView(table, form));
			}else if(event.getButton() == extensao){
				ExtensaoAvariaTable table = new ExtensaoAvariaTable();
				ExtensaoAvariaForm form = new ExtensaoAvariaForm();
				app.setMainView(new ExtensaoAvariaView(table, form));
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
