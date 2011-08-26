package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportCtrcView;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErroImportCtrcForm extends BaseForm {
	private ErroImportCtrcView view;
	private VerticalLayout layout = new VerticalLayout();
	private CtrcService ctrcService;
	private CadastroService cadastroService;
	private PromoveApplication app;
	private InconsistenciaCtrc inconsistencia;
	
	private Button save;
	private Button saveAll;
	private Button remove;
	private Button export;
	
	public ErroImportCtrcForm(PromoveApplication app) {
		this.app = app;
		this.inconsistencia = new InconsistenciaCtrc();
		ctrcService = ServiceFactory.getService(CtrcService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroCtrcFormListener());
		remove = new Button("Excluir CTRC", new ErroCtrcFormListener());
		saveAll = new Button("Revalidar Todos", new ErroCtrcFormListener());
		export = new Button("Exportar Lista", new ErroCtrcFormListener());

		createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}
	
	public void createFormBody(BeanItem<VeiculoCtrc> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroCtrcFieldFactory()); 
		setVisibleItemProperties(new Object[] {"chassiInvalido"});
	}

	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(saveAll);
		//footer.addComponent(export);
		footer.setVisible(true);

		return footer;
	}

	
	class ErroCtrcFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItemDataSource();
						if (item.getBean().getId() == null || item.getBean().getChassiInvalido() == null)
							throw new IllegalArgumentException("Selecione um veículo!");
						String chassi = item.getBean().getChassiInvalido().substring(0, 17);
						List<Veiculo> v = cadastroService.buscarVeiculosPorChassi(chassi);
						if (v.size() == 0) {
							throw new IllegalArgumentException("Veículo com chassi " + chassi + " não encontrado");
						}
						
						item.getBean().setVeiculo(v.get(0));
						item.getBean().setModelo(v.get(0).getModelo().getDescricao());
						item.getBean().setMsgErro("");
						
						if (ctrcService.salvarVeiculoCtrcDeInconsistencia(item.getBean())) {
							view.getTables().getTableCtrc().reloadTable();
							view.getTables().getTableVeiculo().removeAllItems();
							createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
						} else {
							view.getTables().getTableVeiculo().reloadTable(item.getBean().getInconsistencia());							
						}
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
					if (inconsistencia.getId() == null) {
						throw new IllegalArgumentException("Selecione um CTRC");
					}
					
					ctrcService.excluirInconsistenciaCtrc(inconsistencia);
					view.getTables().getTableCtrc().reloadTable();
					view.getTables().getTableVeiculo().removeAllItems();
					createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
					
					showSuccessMessage(view, "Inconsistência excluida!");
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível excluir Inconsistência");
				}catch(Exception e){
					showErrorMessage(view, e.getMessage());
				}
			}else if(event.getButton() == saveAll) {
				try {
					List<InconsistenciaCtrc> buscarTodasInconsistenciasDeCtrcs = ctrcService.buscarTodasInconsistenciasCtrc();
					for (InconsistenciaCtrc inc : buscarTodasInconsistenciasDeCtrcs) {
						if(ctrcService.buscarCtrcDuplicadoPorFiltros(inc.getCtrc()).size() == 0) {
							ctrcService.revalidarInconsistencia(inc, true);
						} else {
							ctrcService.excluirInconsistenciaCtrc(inc);
						}
					}
					showSuccessMessage(view, "Inconsistências salvas!");
					view.getTables().getTableCtrc().reloadTable();
					view.getTables().getTableVeiculo().removeAllItems();
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					de.printStackTrace();
					showErrorMessage(view, "Não foi possível salvar Inconsistências");
				}
			}
		}
	}
	
	class ErroCtrcFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			System.out.println(propertyId); /////
			if (propertyId.equals("chassiInvalido")) {
				String chassi = null;
				BeanItem<VeiculoCtrc> bitem = (BeanItem<VeiculoCtrc>) item;
				if (bitem.getBean() != null && bitem.getBean().getVeiculo() == null &&
						bitem.getBean().getChassiInvalido() != null)
					 chassi = bitem.getBean().getChassiInvalido();
				final ComboBox c = new ComboBox("Veículos");
				System.out.println("   chassi " + (chassi == null ? "" : chassi)); /////
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

	public void setView(ErroImportCtrcView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return layout;
	}


	public void setInconsistencia(InconsistenciaCtrc inconsistencia) {
		this.inconsistencia = inconsistencia;
	}


	public InconsistenciaCtrc getInconsistencia() {
		return inconsistencia;
	}

}
