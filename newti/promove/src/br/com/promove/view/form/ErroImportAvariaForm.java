package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErroImportAvariaForm extends BaseForm {

	private ErroImportAvariaView view;
	private VerticalLayout layout = new VerticalLayout();
	private CadastroService cadastroService;
	private AvariaService avariaService;
	private ExportacaoService exportacaoService;
	private PromoveApplication app;
	
	private Button save;
	private Button saveAll;
	private Button remove;
	private Button export;
	
	public ErroImportAvariaForm(PromoveApplication app) {
		this.app = app;
		cadastroService = ServiceFactory.getService(CadastroService.class);
		avariaService = ServiceFactory.getService(AvariaService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}
	
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroAvariaFormListener());
		remove = new Button("Excluir", new ErroAvariaFormListener());
		saveAll = new Button("Revalidar Todos", new ErroAvariaFormListener());
		export = new Button("Exportar Lista", new ErroAvariaFormListener());

		createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		
	}
	
	public void createFormBody(BeanItem<InconsistenciaAvaria> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroVeiculoFieldFactory()); 
		setVisibleItemProperties(new Object[] {"chassiInvalido"});

	}

	public void addNewTipoAvaria() {
		createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
	}

	private Component createFooter() {
		WebApplicationContext ctx = (WebApplicationContext) app.getContext();
		Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		if(user.getTipo().getId() == 1 || user.getTipo().getId() == 2)
			footer.addComponent(remove);
		footer.addComponent(saveAll);
		footer.addComponent(export);
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
					view.getTable().reloadTable();
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
					List<InconsistenciaAvaria> lista = avariaService.buscarTodasInconsistenciasAvaria();
					for (InconsistenciaAvaria inc : lista) {
						avariaService.salvarAvariaDeInconsistencias(inc);
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
					String file = exportacaoService.exportarXLSInconsistenciaAvarias(lista);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=incavarias.xls&file=" + file));
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
					Item i2 = c.addItem(new Veiculo(chassi));
					i2.getItemProperty("label").setValue(chassi);
					if(chassi != null)
						for(Veiculo v : cadastroService.buscarVeiculosPorFZ(chassi)) {
							Item i = c.addItem(v);
							i.getItemProperty("label").setValue(v.getChassi() + " - " + new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getDataCadastro())) + " - " + v.getModelo().getDescricao());
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


	public void setView(ErroImportAvariaView view) {
		this.view = view;
		
	}

	public Component getFormLayout() {
		return layout;
	}

}
