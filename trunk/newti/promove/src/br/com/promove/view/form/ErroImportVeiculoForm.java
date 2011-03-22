package br.com.promove.view.form;

import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import br.com.promove.entity.Cor;
import br.com.promove.entity.InconsistenciaVeiculo;
import br.com.promove.entity.Modelo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportVeiculoView;

public class ErroImportVeiculoForm extends BaseForm{

	private ErroImportVeiculoView view;
	private VerticalLayout layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	private Button save;
	private Button saveAll;
	private Button remove;
	
	public ErroImportVeiculoForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroVeiculoFormListener());
		remove = new Button("Excluir", new ErroVeiculoFormListener());
		saveAll = new Button("Revalidar Todos", new ErroVeiculoFormListener());

		createFormBody(new BeanItem<InconsistenciaVeiculo>(new InconsistenciaVeiculo()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		
	}
	
	public void createFormBody(BeanItem<InconsistenciaVeiculo> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroVeiculoFieldFactory()); 
		setVisibleItemProperties(new Object[] { "chassi", "modelo", "cor"});

	}

	public void addNewTipoAvaria() {
		createFormBody(new BeanItem<InconsistenciaVeiculo>(new InconsistenciaVeiculo()));
	}

	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(saveAll);
		footer.setVisible(true);

		return footer;
	}

	public void setView(ErroImportVeiculoView view) {
		this.view = view;
		
	}

	public Component getFormLayout() {
		return layout;
	}
	
	class ErroVeiculoFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<InconsistenciaVeiculo> item = (BeanItem<InconsistenciaVeiculo>) getItemDataSource();
						item.getBean().getVeiculo().setCor((Cor)item.getItemProperty("cor").getValue());
						item.getBean().getVeiculo().setModelo((Modelo)item.getItemProperty("modelo").getValue());
						cadastroService.salvarVeiculo(item.getBean().getVeiculo());
						cadastroService.excluirInconsistenciaVeiculo(item.getBean());
						view.getTable().reloadTable();
						showSuccessMessage(view, "Inconsistência salva!");
						
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Inconsistência");
				}
				
			}else if(event.getButton() == remove){
				try{
					BeanItem<InconsistenciaVeiculo> item = (BeanItem<InconsistenciaVeiculo>) getItemDataSource();
					cadastroService.excluirInconsistenciaVeiculo(item.getBean());
					view.getTable().reloadTable();
					showSuccessMessage(view, "Inconsistência excluida!");
						
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível excluir Inconsistência");
				}
			}else if(event.getButton() == saveAll) {
				try {
					List<InconsistenciaVeiculo> buscarTodasInconsistenciasDeVeiculos = cadastroService.buscarTodasInconsistenciasDeVeiculos();
					for (InconsistenciaVeiculo inc : buscarTodasInconsistenciasDeVeiculos) {
						if(cadastroService.buscarVeiculosPorChassi(inc.getChassi()).size() == 0) {
							if(inc.getTipo() == 1) {
								
								if(inc.getCorInvalida() != null) {
									List<Cor> cores = cadastroService.buscaCorPorCodigoExterno(inc.getCorInvalida());
									if((cores.size() == 0)) {
										continue;
									}else {
										inc.getVeiculo().setCor(cores.get(0));
									}
								}
								
								if(inc.getModeloInvalido() != null) {
									List<Modelo> modelos = cadastroService.buscarModeloPorCodigoOuDescricao(inc.getModeloInvalido(), null);
									 if(modelos.size() == 0) {
										continue;
									}else {
										inc.getVeiculo().setModelo(modelos.get(0));
									}
								}
								 
							}else if(inc.getTipo() == 2) {
								if(inc.getModeloInvalido() != null && cadastroService.buscarModeloPorCodigoOuDescricao(null, inc.getModeloInvalido()).size() == 0) {
									continue;
								}
							}
							cadastroService.salvarVeiculo(inc.getVeiculo());
							cadastroService.excluirInconsistenciaVeiculo(inc);
						}else {
							cadastroService.excluirInconsistenciaVeiculo(inc);
						}
					}
					showSuccessMessage(view, "Inconsistências salvas!");
					view.getTable().reloadTable();
					

				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Inconsistências");
				}
			}
			
		}
		
	}
	
	class ErroVeiculoFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
			}
			
			if(propertyId.equals("modelo")) {
				try {
					ComboBox c = new ComboBox("Modelo");
					c.addContainerProperty("label", String.class, null);
				
					for(Modelo m: cadastroService.buscarTodosModelos()) {
						Item i = c.addItem(m);
						i.getItemProperty("label").setValue(m.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Modelo obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0) {
						Modelo f2 = ((BeanItem<InconsistenciaVeiculo>) getItemDataSource()).getBean().getModelo();
						if(f2 != null) {
							Iterator<Modelo> it = c.getItemIds().iterator(); 
							while(it.hasNext()) {
								Modelo f1 = it.next();
								if(f2.getId().equals(f1.getId())) {
									c.setValue(f1);
								}
							}
						}
					
					}
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view, "Não foi possível buscar os Modelos");
				}
			}else if(propertyId.equals("cor")) {
				try {
					ComboBox c = new ComboBox("Cor");
					c.addContainerProperty("label", String.class, null);
				
					for(Cor cor: cadastroService.buscarTodasCores()) {
						Item i = c.addItem(cor);
						i.getItemProperty("label").setValue(cor.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Tipo obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0) {
						Cor f2 = ((BeanItem<InconsistenciaVeiculo>) getItemDataSource()).getBean().getCor();
						if(f2 != null) {
							Iterator<Cor> it = c.getItemIds().iterator(); 
							while(it.hasNext()) {
								Cor f1 = it.next();
								if(f2.getId().equals(f1.getId())) {
									c.setValue(f1);
								}
							}
						}
					
					}
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view, "Não foi possível buscar as Cores");
				}
			}
			return f;
		}
	}

}
