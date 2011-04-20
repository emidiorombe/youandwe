package br.com.promove.application;

import br.com.promove.menu.MenuAvaria;
import br.com.promove.menu.MenuAverbacao;
import br.com.promove.menu.MenuGeral;
import br.com.promove.menu.PromoveToolbar;
import br.com.promove.view.LoginView;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PromoveApplication extends Application {
	private VerticalLayout main;
	private SplitPanel center_layout = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
	
	// Views references
	private PromoveToolbar toolbar;  
	private MenuAvaria menu_avaria;
	private MenuAverbacao menu_averbacao;
	private MenuGeral menu_geral;

	@Override
	public void init() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		setTheme("reindeermods");
		main = new VerticalLayout();
		Window mainWindow = new Window("SICA", main);
		setMainWindow(mainWindow);
		main.setSizeFull();
		main.addComponent(getToolbar());
		main.addComponent(createCenterlayout());
		
		buildAutenticatedWindow();
		main.setExpandRatio(center_layout, 1);
		getMainWindow().addURIHandler(new PromoveURIHandler());

	}

	public void buildAutenticatedWindow() {
		if(isAutenticated()) {
			setMenuView(getMenuAvaria());
			setMainView(new VerticalLayout());
		}else {
			setMainView(new LoginView(this));
		}
	}
	
	public boolean isAutenticated() {
		WebApplicationContext ctx = (WebApplicationContext) getContext();
		return ctx.getHttpSession().getAttribute("loggedUser") != null;
	}
	
	public void setMenuView(Component c) {
		center_layout.setFirstComponent(c);
	}
	
	public void setMainView(Component c) {
		center_layout.setSecondComponent(c);
	}
	
	private Component createCenterlayout() {
		center_layout.addStyleName("small blue white");
		center_layout.setSplitPosition(20);
		return center_layout;
	}
	
	public MenuAvaria getMenuAvaria() {
		if(menu_avaria == null) {
			menu_avaria = new MenuAvaria(this);
		}

		return menu_avaria;
	}
	
	public MenuAverbacao getMenuAverbacao() {
		if(menu_averbacao == null) {
			menu_averbacao = new MenuAverbacao(this);
		}

		return menu_averbacao;
	}
	
	public MenuGeral getMenuGeral() {
		if(menu_geral == null)
			menu_geral = new MenuGeral(this);
		
		return menu_geral;
	}
	
	private CssLayout getToolbar() {
		if(toolbar == null)
			toolbar = new PromoveToolbar(this);
		return toolbar;
	}
}
