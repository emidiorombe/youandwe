package com.example.promove.application;

import java.util.Iterator;

import com.example.promove.menu.MenuAvaria;
import com.example.promove.menu.PromoveToolbar;
import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class PromoveApplication extends Application {
	private VerticalLayout main = new VerticalLayout();
	private SplitPanel center_layout = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
	
	// Views references
	private PromoveToolbar toolbar;  
	private MenuAvaria menu_avaria;

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
	
	private CssLayout getToolbar() {
		if(toolbar == null)
			toolbar = new PromoveToolbar(this);
		return toolbar;
	}
	
}
