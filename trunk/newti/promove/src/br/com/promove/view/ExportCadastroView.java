package br.com.promove.view;


import java.io.Serializable;

import br.com.promove.application.PromoveApplication;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ExportCadastroView implements Serializable{
	private VerticalLayout layout = new VerticalLayout();
	private Button exportar;
	private PromoveApplication app;

	public ExportCadastroView(PromoveApplication app) {
		this.app = app;
		buildView();
	}

	private void buildView() {
		exportar = new Button("Exportar", new ExportCadastroListener(this));
		layout.addComponent(exportar);
		
	}
	
	
	
	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}



	class ExportCadastroListener implements ClickListener{
		
		private ExportCadastroView view;

		public ExportCadastroListener(ExportCadastroView view) {
			this.view = view;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			WebApplicationContext ctx = (WebApplicationContext) app.getContext();
			String path = ctx.getHttpSession().getServletContext().getContextPath();
			event.getButton().getWindow().open(new ExternalResource(path + "/export"));
			
		}
		
	}
}
