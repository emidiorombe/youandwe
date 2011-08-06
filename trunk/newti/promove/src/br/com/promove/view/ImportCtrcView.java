package br.com.promove.view;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import br.com.promove.application.PromoveApplication;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.Config;
import br.com.promove.view.form.BaseForm;

import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class ImportCtrcView extends BaseForm implements Serializable{
	private VerticalLayout layout = new VerticalLayout();
	private Button import_from_server;
	private ImportacaoService importService;


	private PromoveApplication app;

	public ImportCtrcView(PromoveApplication app) {
		this.app = app;
		buildLayout();
		importService = ServiceFactory.getService(ImportacaoService.class);
		
	}

	private void buildLayout() {
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		Label label = new Label("<h3>Importar do WebService</h3>");
		label.setContentMode(Label.CONTENT_XHTML);
		
		layout.addComponent(label);

        import_from_server = new Button("Importar", new ImportFromServerLIstener());
        layout.addComponent(import_from_server);

	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}
	
	
	class ImportFromServerLIstener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				String url = ctx.getHttpSession().getServletContext().getInitParameter("ctrc_ws_url");
				importService.importarGabardo(url + "?dataIni=2005-01-01&dataFim=2011-12-31");
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
