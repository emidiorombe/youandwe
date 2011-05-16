package br.com.promove.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.view.form.BaseForm;

import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ExportAvariaView extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private Button exportar;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private PromoveApplication app;

	public ExportAvariaView(PromoveApplication app) {
		this.app = app;
		buildView();
	}

	private void buildView() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		txtDe = new PopupDateField("De");
		txtDe.setLocale(new Locale("pt", "BR"));
		txtDe.setResolution(DateField.RESOLUTION_DAY);
		
		txtAte = new PopupDateField("Até");
		txtAte.setLocale(new Locale("pt", "BR"));
		txtAte.setResolution(DateField.RESOLUTION_DAY);
		
		exportar = new Button("Exportar", new ExportAvariaListener(this));
		
		layout.addComponent(this);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.addComponent(exportar);
		
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(exportar);
		footer.setVisible(true);
		
		return footer;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	class ExportAvariaListener implements ClickListener{
		
		private ExportAvariaView view;

		public ExportAvariaListener(ExportAvariaView view) {
			this.view = view;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null;
				String fileName = "avarias_" + new SimpleDateFormat("yyyyMMdd").format(de) + "_" + new SimpleDateFormat("yyyyMMdd").format(ate) + ".xml";
				
				if(de == null || ate == null)
					throw new IllegalArgumentException("Informe um período para busca.");
				
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				String path = ctx.getHttpSession().getServletContext().getContextPath();
				event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_avarias&fileName=" + fileName + "&de=" + new SimpleDateFormat("dd/MM/yyyy").format(de) + "&ate=" + new SimpleDateFormat("dd/MM/yyyy").format(ate)));
			} catch (IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}
		}
		
	}
}
