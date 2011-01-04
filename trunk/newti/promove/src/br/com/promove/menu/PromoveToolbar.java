package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PromoveToolbar extends CssLayout{
	//Toolbar items
	private NativeButton toolbar_avaria = new NativeButton("Avarias");
	private NativeButton toolbar_geral = new NativeButton("Cadastros Gerais");
	private PromoveApplication app;
	
	public PromoveToolbar(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}

	private void buildMenu() {
		setWidth("100%");
		addStyleName("toolbar-invert");

		CssLayout right = new CssLayout();
		right.setSizeUndefined();
		right.addStyleName("right");
		addComponent(right);


		Button b = new Button("Ajuda");
		b.addStyleName("borderless");
		right.addComponent(b);

		CssLayout left = new CssLayout();
		left.setSizeUndefined();
		left.addStyleName("left");
		addComponent(left);

		Label title = new Label("Promove");
		title.addStyleName("h1");
		left.addComponent(title);
		
		toolbar_avaria.addListener(new ToolbarEventListener());
		toolbar_geral.addListener(new ToolbarEventListener());
		
		left.addComponent(new NativeButton(""));
		left.addComponent(toolbar_avaria);
		left.addComponent(toolbar_geral);
		
	}
	
	class ToolbarEventListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getSource() == toolbar_avaria) {
				app.setMenuView(app.getMenuAvaria());
			}else if(event.getSource() == toolbar_geral) {
				app.setMenuView(app.getMenuGeral());
			}
			
		}
		
	}
}
