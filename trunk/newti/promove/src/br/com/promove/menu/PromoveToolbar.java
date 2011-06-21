package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;

import com.vaadin.terminal.ClassResource;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;

public class PromoveToolbar extends CssLayout{
	//Toolbar items
	private NativeButton toolbar_avaria = new NativeButton("Avarias");
	private NativeButton toolbar_averbacao = new NativeButton("Averbação");
	private NativeButton toolbar_geral = new NativeButton("Cadastros");
	private NativeButton toolbar_relatorios = new NativeButton("Relatórios");
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


		Button b = new Button("Sair");
		b.addStyleName("borderless");
		b.addListener(new LogoutListener(app));
		right.addComponent(b);

		CssLayout left = new CssLayout();
		left.setSizeUndefined();
		left.addStyleName("left");
		addComponent(left);

		Label title = new Label("SICA");
		title.addStyleName("h1");
		left.addComponent(title);
		
		toolbar_avaria.addListener(new ToolbarEventListener(app));
		toolbar_averbacao.addListener(new ToolbarEventListener(app));
		toolbar_geral.addListener(new ToolbarEventListener(app));
		toolbar_relatorios.addListener(new ToolbarEventListener(app));
		
		left.addComponent(new NativeButton(""));
		left.addComponent(toolbar_avaria);
		left.addComponent(toolbar_averbacao);
		left.addComponent(toolbar_geral);
		left.addComponent(toolbar_relatorios);
		
	}
	
	class ToolbarEventListener implements ClickListener{

		private PromoveApplication app;

		public ToolbarEventListener(PromoveApplication app) {
			this.app = app;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			WebApplicationContext ctx = (WebApplicationContext) app.getContext();
			if(ctx.getHttpSession().getAttribute("loggedUser") == null) {
				return;
			}
			if(event.getSource() == toolbar_avaria) {
				app.setMenuView(app.getMenuAvaria());
			}else if(event.getSource() == toolbar_averbacao) {
				app.setMenuView(app.getMenuAverbacao());
			}else if(event.getSource() == toolbar_geral) {
				app.setMenuView(app.getMenuGeral());
			}else if(event.getSource() == toolbar_relatorios) {
				app.setMainView(new RelatorioSWF());
			}
			
		}
		
	}
	
	class LogoutListener implements ClickListener{

		private PromoveApplication app;

		public LogoutListener(PromoveApplication app) {
			this.app = app;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			WebApplicationContext ctx = (WebApplicationContext) app.getContext();
			ctx.getHttpSession().invalidate();
			
		}
		
	}
	

	public class RelatorioSWF extends VerticalLayout {

	    public RelatorioSWF() {
	    	ThemeResource swf = new ThemeResource("swf/pie.swf");
	        Embedded e = new Embedded(null, swf);
	        e.setType(Embedded.TYPE_OBJECT);
	        e.setMimeType("application/x-shockwave-flash");
	        e.setParameter("allowFullScreen", "true");
	        e.setWidth("600px");
	        e.setHeight("400px");
	        Label lbl = new Label("<h2>Relatório de Avarias por Origem</h2>");
	        lbl.setContentMode(Label.CONTENT_XHTML);
	        addComponent(lbl);
	        addComponent(e);
	    }
	}
}
