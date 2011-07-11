package br.com.promove.view;


import java.io.Serializable;

import br.com.promove.application.PromoveApplication;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ExportCadastroView implements Serializable{
	private VerticalLayout layout = new VerticalLayout();
	private Button exportar;
	private Button exportarAntigo;
	private PromoveApplication app;

	public ExportCadastroView(PromoveApplication app) {
		this.app = app;
		buildView();
	}

	private void buildView() {
		exportar = new Button("Exportar", new ExportCadastroListener(this));
		exportarAntigo = new Button("Exportar (Antigo)", new ExportCadastroListener(this));
		Label label = new Label("<h3>Exportar Cadastros Básicos</h3>");
		label.setContentMode(Label.CONTENT_XHTML);
		layout.addComponent(label);
		layout.addComponent(exportar);
		//layout.addComponent(exportarAntigo);
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		
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
			String novo = "1";
			if(event.getButton() == exportarAntigo)  novo = "0";
			// TODO remover

			WebApplicationContext ctx = (WebApplicationContext) app.getContext();
			String path = ctx.getHttpSession().getServletContext().getContextPath();
			event.getButton().getWindow().open(new ExternalResource(path + "/export?action=cadastro&novo=" + novo));
			// TODO remover parametro
			
		}
		
	}
}
