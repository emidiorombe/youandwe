package br.com.promove.view;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.form.BaseForm;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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

public class ImportAvariaView extends BaseForm implements Serializable{
	private VerticalLayout layout = new VerticalLayout();
	private Label state = new Label();
	private Label fileName = new Label();
	private Label textualProgress = new Label();
	private Button cancelProcessing;
	private ByteArrayOutputStream file = new ByteArrayOutputStream();
	private ImportacaoService importService;

	private ProgressIndicator pi = new ProgressIndicator();

	private Upload upload = new Upload(null, new ImportAvariaUploader());

	public ImportAvariaView() {
		buildLayout();
		importService = ServiceFactory.getService(ImportacaoService.class);
		
	}

	private void buildLayout() {
		layout.setSpacing(true);

		layout.addComponent(new Label("Selecione o arquivo a ser importado."));

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
	
	
	class ImportAvariaUploader implements Receiver{
		private String fileName;
        private String mtype;
        
		@Override
		public OutputStream receiveUpload(String filename, String MIMEType) {
			this.fileName = filename;
			this.mtype = MIMEType;
			return file;
		}
	}
	
	class UploadStartListener implements StartedListener{
		private ImportAvariaView view;
		public UploadStartListener(ImportAvariaView view ) {
			this.view = view;
		}

		@Override
		public void uploadStarted(StartedEvent event) {
            pi.setValue(0f);
            pi.setVisible(true);
            pi.setPollingInterval(500); 
            textualProgress.setVisible(true);
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());

            cancelProcessing.setVisible(true);
		}
	}
	
	class UploadProgressListener implements ProgressListener{
		private ImportAvariaView view;

		public UploadProgressListener(ImportAvariaView view) {
			this.view = view;
		}

		@Override
		public void updateProgress(long readBytes, long contentLength) {
			 pi.setValue(new Float(readBytes / (float) contentLength));
             textualProgress.setValue("Recebido " + readBytes  + " bytes de " + contentLength);
		}
	}
	
	class UploadSucessListener implements SucceededListener{
		private ImportAvariaView view;

		public UploadSucessListener(ImportAvariaView view) {
			this.view = view;
		}

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			try {
				importService.importAvaria(new String(file.toByteArray()));
			}catch (PromoveException pe) {
				showErrorMessage(view, "Não foi possivel importar o arquivo de Avarias.");
			}
		}
	}
	
	class UploadFailedListener implements FailedListener{
		private ImportAvariaView view;

		public UploadFailedListener(ImportAvariaView view) {
			this.view = view;
		}

		@Override
		public void uploadFailed(FailedEvent event) {
			System.out.println(":::::::::::::::::::::UPLOAD FALHOU - " + event.getReason().getMessage());
		}
	}
	
}
