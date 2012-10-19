package br.com.promove.view.form;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ErroImportAvariaView;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ErroImportAvariaForm extends BaseForm {

	private ErroImportAvariaView view;
	private VerticalLayout layout = new VerticalLayout();
	private CadastroService cadastroService;
	private AvariaService avariaService;
	private ExportacaoService exportacaoService;
	private PromoveApplication app;
	private ImportacaoService importService;
	private HashMap<String, TipoAvaria> tiposDescricao;
	private HashMap<String, LocalAvaria> locaisDescricao;
	
	private Button save;
	private Button saveAll;
	private Button saveDupl;
	private Button remove;
	private Button export;
	private Upload depara = new Upload(null, new ImportDeparaUploader(this));
	private ByteArrayOutputStream file = new ByteArrayOutputStream();
	
	public ErroImportAvariaForm(PromoveApplication app) {
		this.app = app;
		cadastroService = ServiceFactory.getService(CadastroService.class);
		avariaService = ServiceFactory.getService(AvariaService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		importService = ServiceFactory.getService(ImportacaoService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroAvariaFormListener());
		remove = new Button("Excluir", new ErroAvariaFormListener());
		saveAll = new Button("Revalidar Inconsistencias", new ErroAvariaFormListener());
		saveDupl = new Button("Revalidar Duplicidades", new ErroAvariaFormListener());
		export = new Button("Exportar Lista", new ErroAvariaFormListener());
		depara.setImmediate(true);
        depara.setButtonCaption("Importar De/Para...");

		createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		
		Label footer = new Label("<br><i><b>De/Para:</b> Chassi atual; Chassi correto</i><br>");
		footer.setContentMode(Label.CONTENT_XHTML);
		layout.addComponent(footer);
		
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		
		depara.addListener(new UploadStartListener(this));
        depara.addListener(new UploadProgressListener(this));
        depara.addListener(new UploadSucessListener(this));
        depara.addListener(new UploadFailedListener(this));
		
	}
	
	public void createFormBody(BeanItem<InconsistenciaAvaria> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroVeiculoFieldFactory()); 
		setVisibleItemProperties(new Object[] {"chassiInvalido"});
	}

	//public void addNewTipoAvaria() {
	//	createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
	//}

	private Component createFooter() {
		WebApplicationContext ctx = (WebApplicationContext) app.getContext();
		Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		if(user.getTipo().getId() == 1 || user.getTipo().getId() == 2)
			footer.addComponent(remove);
		footer.addComponent(saveAll);
		footer.addComponent(saveDupl);
		footer.addComponent(export);
		footer.addComponent(depara);
		footer.setVisible(true);

		return footer;
	}
	
	class ErroAvariaFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<InconsistenciaAvaria> item = (BeanItem<InconsistenciaAvaria>) getItemDataSource();
						if (item.getBean().getId() == null || item.getBean().getChassiInvalido() == null)
							throw new IllegalArgumentException("Selecione um registro!");
						if (item.getBean().getAvaria().getTipo() == null)
							throw new IllegalArgumentException("Tipo de Avaria Inválido!");
						if (item.getBean().getAvaria().getLocal() == null)
							throw new IllegalArgumentException("Peça Inválida!");
						String chassi = item.getBean().getChassiInvalido().substring(0, 17);
						List<Veiculo> v = cadastroService.buscarVeiculosPorChassi(chassi);
						if (v.size() == 0) {
							throw new IllegalArgumentException("Veículo com chassi " + chassi + " não encontrado");
						}else
							item.getBean().setVeiculo(v.get(0));

						avariaService.salvarAvaria(item.getBean().getAvaria());
						avariaService.excluirInconsistenciaImportAvaria(item.getBean());
						//view.getTable().reloadTable();
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Inconsistência salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(IllegalArgumentException ie) {
					showErrorMessage(view, ie.getMessage());
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível salvar Inconsistência");
				}
				
			}else if(event.getButton() == remove){
				try{
					BeanItem<InconsistenciaAvaria> item = (BeanItem<InconsistenciaAvaria>) getItemDataSource();
					avariaService.excluirInconsistenciaImportAvaria(item.getBean());
					//view.getTable().reloadTable();
					view.getTable().getContainer().removeItem(item.getBean());
					showSuccessMessage(view, "Inconsistência excluida!");
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível excluir Inconsistência");
				}
			}else if(event.getButton() == saveAll) {
				try {
					loadTipos();
					loadLocais();
					
					List<InconsistenciaAvaria> lista = avariaService.buscarTodasInconsistenciasAvaria();
					for (InconsistenciaAvaria inc : lista) {
						if (inc.getTipo() == null) {
							String tipoAvaria = StringUtilities.getValueFromErrorMessage(inc.getMsgErro(), "Tipo");
							inc.setTipo(tiposDescricao.get(tipoAvaria));
						}
						if (inc.getTipo() != null)
							inc.setMsgErro(StringUtilities.removeErrorMessage(inc.getMsgErro(), "Tipo"));
						
						if (inc.getLocal() == null) {
							String localAvaria = StringUtilities.getValueFromErrorMessage(inc.getMsgErro(), "Local");
							inc.setLocal(locaisDescricao.get(localAvaria));
						}
						if (inc.getLocal() != null)
							inc.setMsgErro(StringUtilities.removeErrorMessage(inc.getMsgErro(), "Local"));
						
						avariaService.salvarAvariaDeInconsistencias(inc, true);
					}
					view.getTable().reloadTable();
					showSuccessMessage(view, "Inconsistências salvas!");

				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível salvar Inconsistências");
				}
			}else if(event.getButton() == saveDupl) {
				try {
					loadTipos();
					loadLocais();
					
					List<InconsistenciaAvaria> lista = avariaService.buscarTodasInconsistenciasAvaria();
					for (InconsistenciaAvaria inc : lista) {
						if (inc.getTipo() == null) {
							String tipoAvaria = StringUtilities.getValueFromErrorMessage(inc.getMsgErro(), "Tipo");
							inc.setTipo(tiposDescricao.get(tipoAvaria));
						}
						if (inc.getTipo() != null)
							inc.setMsgErro(StringUtilities.removeErrorMessage(inc.getMsgErro(), "Tipo"));
						
						if (inc.getLocal() == null) {
							String localAvaria = StringUtilities.getValueFromErrorMessage(inc.getMsgErro(), "Local");
							inc.setLocal(locaisDescricao.get(localAvaria));
						}
						if (inc.getLocal() != null)
							inc.setMsgErro(StringUtilities.removeErrorMessage(inc.getMsgErro(), "Local"));
						
						avariaService.salvarAvariaDeInconsistencias(inc, false);
					}
					view.getTable().reloadTable();
					showSuccessMessage(view, "Inconsistências salvas!");

				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível salvar Inconsistências");
				}
			}else if(event.getButton() == export) {
				try {
					List<InconsistenciaAvaria> lista = avariaService.buscarTodasInconsistenciasAvaria();
					String fileOut = exportacaoService.exportarXLSInconsistenciaAvarias(lista);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=incavarias.xls&file=" + fileOut));
				} catch (Exception e) {
					showErrorMessage(view, "Não foi possível gerar arquivo.");
				}
			}
		}
	}
	
	class ErroVeiculoFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if (propertyId.equals("chassiInvalido")) {
				String chassi = null;
				BeanItem<InconsistenciaAvaria> bitem = (BeanItem<InconsistenciaAvaria>) item;
				if (bitem.getBean() != null && bitem.getBean().getVeiculo() == null && 
						bitem.getBean().getChassiInvalido() != null)
					chassi = bitem.getBean().getChassiInvalido();
				final ComboBox c = new ComboBox("Veículos");
				try {
					c.addContainerProperty("label", String.class, null);
					c.setRequired(true);
					c.setRequiredError("Veículo obrigatório");
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setItemCaptionPropertyId("label");
					c.setNewItemsAllowed(true);
					if(chassi != null) {
						Item i2 = c.addItem(new Veiculo(chassi));
						i2.getItemProperty("label").setValue(chassi);
						for(Veiculo v : cadastroService.buscarVeiculosPorFZ(chassi)) {
							Item i = c.addItem(v);
							i.getItemProperty("label").setValue(v.getChassi() + " - " + new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getDataCadastro())) + " - " + v.getModelo().getDescricao());
						}
					}
					c.setNewItemHandler(new NewItemHandler() {
						
						@Override
						public void addNewItem(String newV) {
							Item i = c.addItem(new Veiculo(newV));
							i.getItemProperty("label").setValue(newV);
				            c.setValue(new Veiculo(newV));
							
						}
					});
				}catch(Exception e) {
					e.printStackTrace();
					showErrorMessage(view,"Não foi possível buscar Veículos pelo FZ");
				}
				return c;
			}			
			return f;
		}
	}

	class ImportDeparaUploader implements Receiver {
		private String fileName;
        private String mtype;
		private ErroImportAvariaForm view;
        
		public ImportDeparaUploader(ErroImportAvariaForm view) {
			this.view = view;
		}

		@Override
		public OutputStream receiveUpload(String filename, String MIMEType) {
			if(!filename.endsWith("csv")) {
				showErrorMessage(view.getLayout(), "Formato de arquivo não reconhecido.");
				depara.interruptUpload();
			}
			this.fileName = filename;
			this.mtype = MIMEType;
			return file;
		}
	}
	
	class UploadStartListener implements StartedListener{
		private ErroImportAvariaForm view;
		public UploadStartListener(ErroImportAvariaForm view ) {
			this.view = view;
		}

		@Override
		public void uploadStarted(StartedEvent event) {
		}
	}
	
	class UploadProgressListener implements ProgressListener{
		private ErroImportAvariaForm view;

		public UploadProgressListener(ErroImportAvariaForm view) {
			this.view = view;
		}

		@Override
		public void updateProgress(long readBytes, long contentLength) {
		}
	}
	
	class UploadSucessListener implements SucceededListener{
		private ErroImportAvariaForm view;

		public UploadSucessListener(ErroImportAvariaForm view) {
			this.view = view;
		}

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			try {
				importService.importDeParaAvaria(new String(file.toByteArray()));
				view.view.getTable().reloadTable();
				showSuccessMessage(view.getLayout(), "De/Para importado com sucesso...");
			}catch (PromoveException pe) {
				pe.printStackTrace();
				showErrorMessage(view, "Não foi possivel importar o De/Para.");
			}
		}
	}
	
	class UploadFailedListener implements FailedListener{
		private ErroImportAvariaForm view;

		public UploadFailedListener(ErroImportAvariaForm view) {
			this.view = view;
		}

		@Override
		public void uploadFailed(FailedEvent event) {
		}
	}
	
	public void setView(ErroImportAvariaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return layout;
	}

	private void loadTipos() throws PromoveException {
		tiposDescricao = new HashMap<String, TipoAvaria>();
		List<TipoAvaria> lista = avariaService.buscarTodosTipoAvaria();
		for (TipoAvaria tipo : lista) {
			if (tipo.getDescricaoSeguradora() != null && !tipo.getDescricaoSeguradora().isEmpty()) {
				for (String descricao : tipo.getDescricaoSeguradora().split(";")) {
					tiposDescricao.put(descricao, tipo);
				}
			}
		}
	}
	
	private void loadLocais() throws PromoveException {
		locaisDescricao = new HashMap<String, LocalAvaria>();
		List<LocalAvaria> lista = avariaService.buscarTodosLocaisAvaria();
		for (LocalAvaria local : lista) {
			if (local.getDescricaoSeguradora() != null && !local.getDescricaoSeguradora().isEmpty()) {
				for (String descricao : local.getDescricaoSeguradora().split(";")) {
					locaisDescricao.put(descricao, local);
				}
			}
		}
	}
}
