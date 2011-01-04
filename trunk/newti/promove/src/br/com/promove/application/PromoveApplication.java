package br.com.promove.application;


import br.com.promove.menu.MenuAvaria;
import br.com.promove.menu.MenuGeral;
import br.com.promove.menu.PromoveToolbar;
import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PromoveApplication extends Application {
	private VerticalLayout main = new VerticalLayout();
	private SplitPanel center_layout = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
	
	// Views references
	private PromoveToolbar toolbar;  
	private MenuAvaria menu_avaria;
	private MenuGeral menu_geral;

	@Override
	public void init() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		setTheme("reindeermods");
		
		Window mainWindow = new Window("Promove", main);
		setMainWindow(mainWindow);
		main.setSizeFull();

		main.addComponent(getToolbar());

		main.addComponent(createCenterlayout());
		main.setExpandRatio(center_layout, 1);

		setMenuView(getMenuAvaria());

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
