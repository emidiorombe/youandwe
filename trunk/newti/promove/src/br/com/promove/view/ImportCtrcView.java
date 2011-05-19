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
	private Label state = new Label();
	private Label fileName = new Label();
	private Label textualProgress = new Label();
	private Button cancelProcessing;
	private Button import_from_server;
	private ByteArrayOutputStream file = new ByteArrayOutputStream();
	private ImportacaoService importService;

	private ProgressIndicator pi = new ProgressIndicator();

	private Upload upload = new Upload(null, new ImportCtrcUploader(this));
	private PromoveApplication app;

	public ImportCtrcView(PromoveApplication app) {
		this.app = app;
		buildLayout();
		importService = ServiceFactory.getService(ImportacaoService.class);
		
	}

	private void buildLayout() {
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		Label label = new Label("<h3>Importar um único arquivo</h3>");
		label.setContentMode(Label.CONTENT_XHTML);
		
		layout.addComponent(label);

        upload.setImmediate(true);
        upload.setButtonCaption("Selecione...");
        layout.addComponent(upload);
        
        cancelProcessing = new Button("Cancelar");
        cancelProcessing.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                upload.interruptUpload();
            }
        });
        
        cancelProcessing.setVisible(false);
        cancelProcessing.setStyleName("small");

        Panel p = createStatusPanel(cancelProcessing);

        layout.addComponent(p);
        upload.addListener(new UploadStartListener(this));
        upload.addListener(new UploadProgressListener(this));
        upload.addListener(new UploadSucessListener(this));
        upload.addListener(new UploadFailedListener(this));

	}

	private Panel createStatusPanel(final Button cancelProcessing) {
		Panel p = new Panel("Status");
        p.setSizeUndefined();
        FormLayout l = new FormLayout();
        l.setMargin(true);
        p.setContent(l);
        HorizontalLayout stateLayout = new HorizontalLayout();
        stateLayout.setSpacing(true);
        stateLayout.addComponent(state);
        stateLayout.addComponent(cancelProcessing);
        stateLayout.setCaption("Status atual:");
        state.setValue("Aguardando...");
        l.addComponent(stateLayout);
        fileName.setCaption("Nome do arquivo:");
        l.addComponent(fileName);
        pi.setCaption("Progresso:");
        pi.setVisible(false);
        l.addComponent(pi);
        textualProgress.setVisible(false);
        l.addComponent(textualProgress);
		return p;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}
	
	
	class ImportCtrcUploader implements Receiver{
		private String fileName;
        private String mtype;
		private ImportCtrcView view;
        
		public ImportCtrcUploader(ImportCtrcView view) {
			this.view = view;
		}

		@Override
		public OutputStream receiveUpload(String filename, String MIMEType) {
			if(!filename.endsWith("xml")) {
				showErrorMessage(view.getLayout(), "Formato de arquivo não reconhecido.");
				upload.interruptUpload();
			}
			this.fileName = filename;
			this.mtype = MIMEType;
			return file;
		}
	}
	
	class UploadStartListener implements StartedListener{
		private ImportCtrcView view;
		public UploadStartListener(ImportCtrcView view ) {
			this.view = view;
		}

		@Override
		public void uploadStarted(StartedEvent event) {
            pi.setValue(0f);
            pi.setVisible(true);
            pi.setPollingInterval(500); 
            textualProgress.setVisible(true);
            state.setValue("Enviando...");
            fileName.setValue(event.getFilename());

            cancelProcessing.setVisible(true);
		}
	}
	
	class UploadProgressListener implements ProgressListener{
		private ImportCtrcView view;

		public UploadProgressListener(ImportCtrcView view) {
			this.view = view;
		}

		@Override
		public void updateProgress(long readBytes, long contentLength) {
			 pi.setValue(new Float(readBytes / (float) contentLength));
             textualProgress.setValue("Recebido " + readBytes  + " bytes de " + contentLength);
		}
	}
	
	class UploadSucessListener implements SucceededListener{
		private ImportCtrcView view;

		public UploadSucessListener(ImportCtrcView view) {
			this.view = view;
		}

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			try {
				cancelProcessing.setVisible(false);
				state.setValue("Recebido...");
				pi.setValue(100f);
				importService.importCtrc(new String(file.toByteArray()));
				showSuccessMessage(view.getLayout(), "Arquivo importado com sucesso...");
			}catch (PromoveException pe) {
				showErrorMessage(view, "Não foi possivel importar o arquivo de Ctrcs.");
			}
		}
	}
	
	class UploadFailedListener implements FailedListener{
		private ImportCtrcView view;

		public UploadFailedListener(ImportCtrcView view) {
			this.view = view;
		}

		@Override
		public void uploadFailed(FailedEvent event) {
			pi.setValue(0f);
            pi.setVisible(false);
            textualProgress.setVisible(false);
            state.setValue("Falhou...");
            fileName.setValue(event.getFilename());

            cancelProcessing.setVisible(false);
		}
	}
	
}
